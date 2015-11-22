import org.w3c.dom.events.Event
import kotlin.reflect.KClass

@native("Phaser.Game") class PhaserGame(val width: Int, val height: Int, val auto: String, id: String) {
	@native val state: PhaserState = noImpl
	@native val load: PhaserLoader = noImpl
	@native val add: PhaserGameObjectFactory = noImpl
	@native val physics: PhaserPhysics = noImpl
	@native val input: PhaserInput = noImpl

	@native fun add() {

	}
}

interface AssertName

data class Assert(@native val name: AssertName, @native val url: String) {
	constructor(name: String, url: String) : this(name as AssertName, url)
}

/*
interface {

}
*/

interface GameStateType

fun GameStateType(name: String): GameStateType = name as GameStateType

open class GameState(game: PhaserGame) {
	@native open fun preload() {
	}

	@native open fun create() {
	}

	@native open fun update() {
	}

	@native open fun render() {
	}
}

@native interface PhaserState {
	@native fun add(state: GameStateType, clazz: (game: PhaserGame) -> GameState)
	@native fun start(state: GameStateType)
}

@native("Phaser.Loader") class PhaserLoader(game: PhaserGame) {
	@native fun image(name: AssertName, url: String)
	val hasLoaded: Boolean = noImpl
	val isLoading: Boolean = noImpl
	val onFileComplete: PhaserSignal<Any> = noImpl
}

@native("Phaser.World") class PhaserWorld {

}

@native("Phaser.Group") class PhaserGroup {

}

@native("Phaser.GameObjectFactory") class PhaserGameObjectFactory(val game: PhaserGame) {
	@native val world: PhaserWorld = noImpl
	@native fun sprite(x: Number, y: Number, key: AssertName): PhaserSprite = noImpl
	@native fun text(x:Number, y:Number, text:String): PhaserText

}

@native interface Point {
	@native val x: Double
	@native val y: Double

	@native fun set(x: Number): Unit = noImpl
	@native fun set(x: Number, y: Number): Unit = noImpl
}

@native("Phaser.Text") class PhaserText(game: PhaserGame, x: Double, y: Double, text: String, style: Any) {

}


@native interface PhaserSprite {
	@native var angle: Double
	@native var position: Point
	@native var anchor: Point
	@native var rotation: Double
}

fun PhaserLoader.image(assert: Assert) = this.image(assert.name, assert.url)
fun PhaserLoader.image(vararg asserts: Assert) {
	for (assert in asserts) {
		this.image(assert.name, assert.url)
	}
}

@native val PhaserSprite.body: PhaserPhysics.Body get() = noImpl

@native("Phaser.Physics") class PhaserPhysics {
	@native enum class Type

	companion object {
		@native val ARCADE: Type = noImpl
	}

	@native val arcade: PhaserArcade = noImpl
	@native fun startSystem(type: Type)
	@native fun enable(sprite: PhaserSprite, type: Type)

	@native interface Body {
		var drag: Point
		var maxVelocity: Point
		var angularVelocity: Double
		var acceleration: Point
	}
}

@native interface PhaserArcade {
	@native fun accelerationFromRotation(rotation: Double, acceleration: Double, vector: Point)
}

@native class PhaserInput {
	@native val keyboard: PhaserKeyboard = noImpl
}

@native interface PhaserKey {

}

@native("Phaser.SignalBinding") class PhaserSignalBinding<T>() {
	@native fun detach(): ((T) -> Unit)? = noImpl
	@native fun execute(arg: T): Any = noImpl
	@native fun getListener(): (T) -> Unit = noImpl
	@native fun getSignal(): PhaserSignal<T> = noImpl
	@native fun isBound(): Boolean = noImpl
	@native fun isOnce(): Boolean = noImpl
}

@native("Phaser.Signal") class PhaserSignal<T>() {
	@native var active: Boolean = noImpl
	@native var memorize: Boolean = noImpl
	@native fun add(listener: (T) -> Unit, listenerContext: Any, priority: Int, args: Array<Any>): PhaserSignalBinding<T> = noImpl
	@native fun addOnce(listener: (T) -> Unit, listenerContext: Any, priority: Int, args: Array<Any>): PhaserSignalBinding<T> = noImpl
	@native fun remove(listener: (T) -> Unit, context: Any): (T) -> Unit = noImpl
	@native fun removeAll(context: Any): (T) -> Unit = noImpl
	@native fun dispatch(value: T): Unit = noImpl
	@native fun dispose(): Unit = noImpl
	@native fun forget(): Unit = noImpl
	@native fun getNumListeners(): Int = noImpl
	@native fun halt(): Unit = noImpl
	@native fun has(listener: (T) -> Unit, context: Any): Boolean = noImpl
}

@native("Phaser.Key") class PhaserKeyInfo(@native val game: PhaserGame, @native val keyCode: Int) {
	@native val altKey: Boolean = noImpl
	@native val ctrlKey: Boolean = noImpl
	@native val shiftKey: Boolean = noImpl

	@native fun downDuration(duration: Double): Boolean = noImpl
	@native fun upDuration(duration: Double): Boolean = noImpl
	@native fun reset(hard: Boolean = true) = noImpl

	@native val timeDown: Double = noImpl
	@native val timeUp: Double = noImpl
	@native val duration: Double = noImpl
	@native val repeats: Int = noImpl
	@native val event: Event = noImpl
	@native val isDown: Boolean = noImpl
	@native val isUp: Boolean = noImpl
	@native val onDown: PhaserSignal<Boolean> = noImpl
	@native val onUp: PhaserSignal<Boolean> = noImpl
	@native var onHoldCallback: () -> Unit = noImpl
	@native var onHoldContext: Any = noImpl
}

@native("Phaser.Keyboard") class PhaserKeyboard {
	companion object {
		@native val LEFT: PhaserKey
		@native val RIGHT: PhaserKey
		@native val UP: PhaserKey
		@native val DOWN: PhaserKey
	}

	@native fun addKey(key: PhaserKey): PhaserKeyInfo
}

/*
var game = new Phaser.Game(gameProperties.screenWidth, gameProperties.screenHeight, Phaser.AUTO, 'gameDiv');
game.state.add(states.game, gameState);
game.state.start(states.game);
*/