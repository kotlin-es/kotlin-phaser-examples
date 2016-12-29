import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineIntrinsics
import kotlin.coroutines.startCoroutine

private val UNDECIDED: Any? = Any()
private val RESUMED: Any? = Any()

private class Fail(val exception: Throwable)


@SinceKotlin("1.1")
public inline suspend fun <T> suspendCoroutine(crossinline block: (Continuation<T>) -> Unit): T =
	CoroutineIntrinsics.suspendCoroutineOrReturn { c: Continuation<T> ->
		val safe = SafeContinuation(c)
		block(safe)
		safe.getResult()
	}


class SafeContinuation<in T>(private val delegate: Continuation<T>) : Continuation<T> {
	@Volatile
	private var result: Any? = UNDECIDED

	private fun cas(expect: Any?, update: Any?): Boolean {
		if (result == expect) {
			result = update
			return true
		} else {
			return false
		}
	}

	override fun resume(value: T) {
		while (true) { // lock-free loop
			val result = this.result // atomic read
			when (result) {
				UNDECIDED -> if (cas(UNDECIDED, value)) return
				CoroutineIntrinsics.SUSPENDED -> if (cas(CoroutineIntrinsics.SUSPENDED, RESUMED)) {
					delegate.resume(value)
					return
				}
				else -> throw IllegalStateException("Already resumed")
			}
		}
	}

	override fun resumeWithException(exception: Throwable) {
		while (true) { // lock-free loop
			val result = this.result // atomic read
			when (result) {
				UNDECIDED -> if (cas(UNDECIDED, Fail(exception))) return
				CoroutineIntrinsics.SUSPENDED -> if (cas(CoroutineIntrinsics.SUSPENDED, RESUMED)) {
					delegate.resumeWithException(exception)
					return
				}
				else -> throw IllegalStateException("Already resumed")
			}
		}
	}

	@PublishedApi
	internal fun getResult(): Any? {
		var result = this.result // atomic read
		if (result == UNDECIDED) {
			if (cas(UNDECIDED, CoroutineIntrinsics.SUSPENDED)) return CoroutineIntrinsics.SUSPENDED
			result = this.result // reread volatile var
		}
		when (result) {
			RESUMED -> return CoroutineIntrinsics.SUSPENDED // already called continuation, indicate SUSPENDED upstream
			is Fail -> throw result.exception
			else -> return result // either SUSPENDED or data
		}
	}
}

class Promise<T : Any?> {
	class Deferred<T : Any?> {
		val promise = Promise<T>()
		fun resolve(value: T): Unit = run { promise.complete(value, null) }
		fun reject(error: Throwable): Unit = run { promise.complete(null, error) }
		fun toContinuation(): Continuation<T> {
			val deferred = this
			return object : Continuation<T> {
				override fun resume(value: T) = deferred.resolve(value)
				override fun resumeWithException(exception: Throwable) = deferred.reject(exception)
			}
		}
	}

	companion object {
		fun <T> resolved(value: T) = Promise<T>().complete(value, null)
		fun <T> rejected(error: Throwable) = Promise<T>().complete(null, error)
	}

	private var value: T? = null
	private var error: Throwable? = null
	private var done: Boolean = false
	private val resolvedHandlers = ArrayList<(T) -> Unit>()
	private val rejectedHandlers = ArrayList<(Throwable) -> Unit>()
	private val cancelHandlers = ArrayList<(Throwable) -> Unit>()

	private fun flush() {
		if (!done) return
		if (error != null) {
			while (rejectedHandlers.isNotEmpty()) {
				val handler = rejectedHandlers.removeAt(0)
				handler(error ?: RuntimeException())
			}
		} else {
			while (resolvedHandlers.isNotEmpty()) {
				val handler = resolvedHandlers.removeAt(0)
				handler(value!!)
			}
		}
	}

	internal fun complete(value: T?, error: Throwable?): Promise<T> {
		if (!this.done) {
			if (value == null && error == null) {
				throw RuntimeException("Invalid completion!")
			}

			this.value = value
			this.error = error
			this.done = true

			if (error != null && this.rejectedHandlers.isEmpty()) {
				println(error!!.message)
			}

			flush()
		}
		return this
	}

	fun then(resolved: (T) -> Unit) {
		resolvedHandlers += resolved
		flush()
	}

	fun then(resolved: (T) -> Unit, rejected: (Throwable) -> Unit) {
		resolvedHandlers += resolved
		rejectedHandlers += rejected
		flush()
	}

	fun then(c: Continuation<T>) {
		this.then(
			resolved = { c.resume(it) },
			rejected = { c.resumeWithException(it) }
		)
	}

	suspend fun await(): T = suspendCoroutine(this::then)
}

fun <T> async(callback: suspend () -> T): Promise<T> {
	val deferred = Promise.Deferred<T>()
	callback.startCoroutine(deferred.toContinuation())
	return deferred.promise
}

suspend fun <T> await(promise: Promise<T>): T = suspendCoroutine<T> { c ->
	promise.then(c)
}

suspend fun <T> asyncFun(callback: suspend () -> T): T = suspendCoroutine<T> { c ->
	callback.startCoroutine(c)
}

external fun setTimeout(callback: () -> Unit, time: Int)

suspend fun sleep(time: Int) = await(sleepAsync(time))

fun sleepAsync(time: Int): Promise<Unit> {
	val deferred = Promise.Deferred<Unit>()
	setTimeout({
		deferred.resolve(Unit)
	}, time)
	return deferred.promise
}
