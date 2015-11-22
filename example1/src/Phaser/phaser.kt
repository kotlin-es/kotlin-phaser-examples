package Phaser

import org.w3c.dom.events.Event
import kotlin.reflect.KClass

@native("Phaser.Game") class Game(val width: Int, val height: Int, val auto: String, id: String) {
	@native val state: PhaserState = noImpl
	@native val load: Loader = noImpl
	@native val add: PhaserGameObjectFactory = noImpl
	@native val physics: Physics = noImpl
	@native val input: Input = noImpl

	@native fun add() {

	}
}

@native("Phaser.Keyboard") class Keyboard {
	companion object {
		@native val LEFT: PhaserKey = noImpl
		@native val RIGHT: PhaserKey = noImpl
		@native val UP: PhaserKey = noImpl
		@native val DOWN: PhaserKey = noImpl
	}

	@native fun addKey(key: PhaserKey): Key
}

@native("Phaser.Key") class Key(@native val game: Phaser.Game, @native val keyCode: Int) {
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

open class GameState(game: Phaser.Game) {
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
	@native fun add(state: GameStateType, clazz: (game: Phaser.Game) -> GameState)
	@native fun start(state: GameStateType)
}

@native("Phaser.Loader") class Loader(game: Phaser.Game) {
	@native fun image(name: AssertName, url: String)
	val hasLoaded: Boolean = noImpl
	val isLoading: Boolean = noImpl
	val onFileComplete: PhaserSignal<Any> = noImpl
}

@native("Phaser.World") class World {

}

@native("Phaser.Group") class Group {

}

@native("Phaser.GameObjectFactory") class PhaserGameObjectFactory(val game: Phaser.Game) {
	@native val world: World = noImpl
	@native fun sprite(x: Number, y: Number, key: AssertName): Sprite = noImpl
	@native fun text(x: Number, y: Number, text: String): Text

}

@native("Phaser.Point") class Point(@native var x: Double, @native var y: Double) {
	@native fun set(x: Number): Unit = noImpl
	@native fun set(x: Number, y: Number): Unit = noImpl
}

@native("Phaser.Text") class Text(game: Game, x: Double, y: Double, text: String, style: Any) {

}


@native interface Sprite {
	@native var angle: Double
	@native var position: Point
	@native var anchor: Point
	@native var rotation: Double
}

fun Loader.image(assert: Assert) = this.image(assert.name, assert.url)
fun Loader.image(vararg asserts: Assert) = asserts.forEach { this.image(it.name, it.url) }

@native val Sprite.body: Physics.Body get() = noImpl

@native("Phaser.Physics") class Physics {
	@native enum class Type

	companion object {
		@native val ARCADE: Type = noImpl
	}

	@native val arcade: Arcade = noImpl
	@native fun startSystem(type: Type)
	@native fun enable(sprite: Sprite, type: Type)

	@native interface Body {
		var drag: Point
		var maxVelocity: Point
		var angularVelocity: Double
		var acceleration: Point
	}

	@native class Arcade {
		@native fun accelerationFromRotation(rotation: Double, acceleration: Double, vector: Point)
	}
}

@native("Phaser.Math") object PhaserMath {
	//@native fun <T : Number> difference(a: T, b: T): T = noImpl
	@native val PI2: Double = noImpl

	@native fun angleBetween(x1: Number, y1: Number, x2: Number, y2: Number): Double = noImpl
	@native fun angleBetweenY(x1: Number, y1: Number, x2: Number, y2: Number): Double = noImpl
	@native fun angleBetweenPoints(point1: Point, point2: Point): Double = noImpl
	@native fun angleBetweenPointsY(point1: Point, point2: Point): Double = noImpl
	@native fun average(vararg n: Number): Double = noImpl
	@native fun bernstein(n: Number, i: Number): Double = noImpl
	@native fun bezierInterpolation(v: Array<Number>, k: Number): Double = noImpl
	@native fun catmullRom(p0: Number, p1: Number, p2: Number, p3: Number, t: Number): Double = noImpl
	@native fun catmullRomInterpolation(v: Array<Number>, k: Number): Double = noImpl
	@native fun ceilTo(value: Number, place: Number, base: Number): Double = noImpl
	@native fun chanceRoll(chance: Number): Boolean = noImpl
	@native fun clamp(x: Number, a: Number, b: Number): Double = noImpl
	@native fun clampBottom(x: Number, a: Number): Double = noImpl
	@native fun degToRad(degrees: Number): Double = noImpl
	@native fun difference(a: Number, b: Number): Double = noImpl
	@native fun distance(x1: Number, y1: Number, x2: Number, y2: Number): Double = noImpl
	@native fun distancePow(x1: Number, y1: Number, x2: Number, y2: Number, pow: Number = 2.0): Double = noImpl
	@native fun distanceSq(x1: Number, y1: Number, x2: Number, y2: Number): Double = noImpl
	@native fun factorial(value: Number): Double = noImpl
	@native fun floorTo(value: Number, place: Number, base: Number): Double = noImpl
	@native fun fuzzyCeil(`val`: Number, epsilon: Number = 0.0001): Double = noImpl
	@native fun fuzzyEqual(a: Number, b: Number, epsilon: Number = 0.0001): Boolean = noImpl
	@native fun fuzzyFloor(`val`: Number, epsilon: Number = 0.0001): Double = noImpl
	@native fun fuzzyGreaterThan(a: Number, b: Number, epsilon: Number = 0.0001): Boolean = noImpl


}

@native("Phaser.Input") class Input(@native val game: Game) {
	@native val keyboard: Phaser.Keyboard = noImpl
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



/*
var game = new Phaser.Game(gameProperties.screenWidth, gameProperties.screenHeight, Phaser.AUTO, 'gameDiv');
game.state.add(states.game, gameState);
game.state.start(states.game);
*/