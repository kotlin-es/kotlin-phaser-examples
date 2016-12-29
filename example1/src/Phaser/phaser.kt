package Phaser

import org.w3c.dom.events.Event

@JsName("Phaser.Game")
external class Game(val width: Double, val height: Double, val auto: String, id: String) {
	val state: PhaserState
	val load: Loader
	val add: PhaserGameObjectFactory
	val physics: Physics
	val input: Input

	fun add() {

	}
}

@JsName("Phaser.Keyboard")
external class Keyboard {
	companion object {
		val LEFT: PhaserKey
		val RIGHT: PhaserKey
		val UP: PhaserKey
		val DOWN: PhaserKey
	}

	fun addKey(key: PhaserKey): Key
}

@JsName("Phaser.Key")
external class Key(val game: Phaser.Game, val keyCode: Int) {
	val altKey: Boolean
	val ctrlKey: Boolean
	val shiftKey: Boolean

	fun downDuration(duration: Double): Boolean
	fun upDuration(duration: Double): Boolean
	fun reset(hard: Boolean = true)

	val timeDown: Double
	val timeUp: Double
	val duration: Double
	val repeats: Int
	val event: Event
	val isDown: Boolean
	val isUp: Boolean
	val onDown: Signal<Boolean>
	val onUp: Signal<Boolean>
	var onHoldCallback: () -> Unit
	var onHoldContext: Any
}


external interface AssertName

data class Assert(@JsName("name") val name: AssertName, @JsName("url") val url: String) {
	constructor(name: String, url: String) : this(name as AssertName, url)
}

/*
interface {

}
*/

external interface GameStateType

fun GameStateType(name: String): GameStateType = name as GameStateType

/*
fun GameStateType(name: String): GameStateType {
	val nn: dynamic = name
	return nn as GameStateType
}
*/

open class GameState(game: Phaser.Game) {
	open fun preload() {
	}

	open fun create() {
	}

	open fun update() {
	}

	open fun render() {
	}
}

external interface PhaserState {
	fun add(state: GameStateType, clazz: (game: Phaser.Game) -> GameState)
	fun start(state: GameStateType)
}

@JsName("Phaser.Cache")
external class Cache(val game: Phaser.Game) {
	companion object {
		val BINARY: Int
		val BITMAPDATA: Int
		val BITMAPFONT: Int
		val CANVAS: Int
		val IMAGE: Int
		val JSON: Int
		val PHYSICS: Int
		val RENDER_TEXTURE: Int
		val SHADER: Int
		val SOUND: Int
		val TEXT: Int
		val TEXTURE: Int
		val TILEMAP: Int
		val VIDEO: Int
		val XML: Int
	}

	var autoResolveURL: Boolean
	var onSoundUnlock: Signal<Any>

	fun addBinary(key: String, binaryData: Any): Unit
	// ...
	// ...
}

@JsName("Phaser.Loader")
external class Loader(val game: Phaser.Game) {
	fun image(name: AssertName, url: String)
	val hasLoaded: Boolean
	val isLoading: Boolean
	val onFileComplete: Signal<Any>
}

@JsName("Phaser.World")
external class World {

}

@JsName("Phaser.Group")
external class Group {

}

@JsName("Phaser.GameObjectFactory")
external class PhaserGameObjectFactory(val game: Phaser.Game) {
	val world: World
	fun sprite(x: Number, y: Number, key: AssertName): Sprite
	fun text(x: Number, y: Number, text: String): Text
	fun text(x: Number, y: Number, text: String, style: TextStyle): Text

}

@JsName("Phaser.Point")
external class Point(var x: Double, var y: Double) {
	fun set(x: Number): Unit
	fun set(x: Number, y: Number): Unit
}

external interface TextStyle {
	var font: String
	var fontStyle: String
	var fontVariant: String
	var fontWeight: Int
	var fontSize: String
	var backgroundColor: String
	var fill: String
	var align: String
	var boundsAlignH: String
	var boundsAlignV: String
	var stroke: String
	var strokeThickness: Number
	var wordWrap: Boolean
	var wordWrapWidth: Int
	var tabs: Int
}

fun TextStyle(
	font: String? = null, // "bold 20pt Arial"
	fontStyle: String? = null, // (from font)
	fontVariant: String? = null, // (from font)
	fontWeight: Int? = null, // (from font)
	fontSize: String? = null, // (from font)
	backgroundColor: String? = null,
	fill: String? = null, // 'black'
	align: String? = null, // 'left'
	boundsAlignH: String? = null, //
	boundsAlignV: String? = null, //
	stroke: String? = null, // 'black'
	strokeThickness: Number? = null, // 0
	wordWrap: Boolean? = null, // false
	wordWrapWidth: Int? = null, // 100
	tabs: Int? = null // 0
): TextStyle {
	var out = js("({})")
	if (font != null) out.font = font
	if (fontStyle != null) out.fontStyle = fontStyle
	if (fontVariant != null) out.fontVariant = fontVariant
	if (fontWeight != null) out.fontWeight = fontWeight
	if (fontSize != null) out.fontSize = fontSize
	if (backgroundColor != null) out.backgroundColor = backgroundColor
	if (fill != null) out.fill = fill
	if (align != null) out.align = align
	if (boundsAlignH != null) out.boundsAlignH = boundsAlignH
	if (boundsAlignV != null) out.boundsAlignV = boundsAlignV
	if (stroke != null) out.stroke = stroke
	if (strokeThickness != null) out.strokeThickness = strokeThickness
	if (wordWrap != null) out.wordWrap = wordWrap
	if (wordWrapWidth != null) out.wordWrapWidth = wordWrapWidth
	if (tabs != null) out.tabs = tabs
	return out
}

@JsName("Phaser.Text")
external class Text(val game: Game, x: Double, y: Double, text: String, style: TextStyle? = null) {

}


@JsName("Phaser.Sprite")
external class Sprite(val game: Game) {
	var key: Any?
	var frame: Any?
	var x: Double
	var y: Double
	var angle: Double
	var position: Point
	var anchor: Point
	var rotation: Double

	val body: Physics_Arcade_Body
	val arcadeBody: Physics_Arcade_Body
}

fun Loader.image(assert: Assert) = this.image(assert.name, assert.url)
fun Loader.image(vararg asserts: Assert) = asserts.forEach { this.image(it.name, it.url) }

interface DirectionObj {
	var up: Boolean
	var down: Boolean
	var left: Boolean
	var right: Boolean
}

@JsName("Phaser.Physics")
external class Physics {
	enum class Type

	companion object {
		val ARCADE: Type
	}

	val arcade: Physics_Arcade
	fun startSystem(type: Type)
	fun enable(sprite: Sprite, type: Type)
}

@JsName("Phaser.Physics.Arcade") external class Physics_Arcade {
	fun accelerationFromRotation(rotation: Double, acceleration: Double, vector: Point)
}

@JsName("Phaser.Physics.Arcade.Body") external class Physics_Arcade_Body(val sprite: Sprite) {
	var acceleration: Point
	var allowGravity: Boolean
	var allowRotation: Boolean
	var angle: Double
	var angularAcceleration: Double
	var angularDrag: Double
	var angularVelocity: Double
	var blocked: DirectionObj
	var bottom: Double
	var bounce: Point
	var center: Point
	var checkCollision: DirectionObj
	var collideWorldBounds: Boolean
	var customSeparateX: Boolean
	var customSeparateY: Boolean
	var deltaMax: Point
	var dirty: Boolean
	var drag: Point
	var embedded: Boolean
	var enable: Boolean
	var facing: Double
	var friction: Point
	val game: Game
	var gravity: Point
	val halfHeight: Double
	val halfWidth: Double
	val height: Double
	var immovable: Boolean
	var mass: Double
	var maxAngular: Double
	var maxVelocity: Point
	var moves: Boolean
	val newVelocity: Point
	var offset: Point
	var overlapX: Point
	var overlapY: Point
	val position: Point
	val preRotation: Double
	val prev: Point
	val right: Double
	var rotation: Double
	var skipQuadTree: Boolean
	val sourceHeight: Double
	val sourceWidth: Double
	val speed: Double
	var syncBounds: Boolean
	var tilePadding: Point
	var touching: DirectionObj
	var type: Int
	var velocity: Point
	var wasTouching: DirectionObj
	val width: Double
	var x: Double
	var y: Double

	fun deltaAbsX(): Double
	fun deltaAbsY(): Double
	fun deltaX(): Double
	fun deltaY(): Double
	fun deltaZ(): Double
	fun destroy(): Unit
	fun hitTest(x: Number, y: Number): Boolean
	fun onFloor(): Boolean
	fun onWall(): Boolean
	fun render(context: Any, body: Physics_Arcade_Body, color: String, filled: Boolean): Unit
	fun renderBodyInfo(body: Physics_Arcade_Body, x: Number, y: Number, color: String): Unit
	fun reset(x: Number, y: Number): Unit
	fun setSize(width: Number, height: Number): Unit
	fun setSize(width: Number, height: Number, offsetX: Number, offsetY: Number): Unit
}

@JsName("Phaser.Math")
external object PhaserMath {
	//fun <T : Number> difference(a: T, b: T): T
	val PI2: Double

	fun angleBetween(x1: Number, y1: Number, x2: Number, y2: Number): Double
	fun angleBetweenY(x1: Number, y1: Number, x2: Number, y2: Number): Double
	fun angleBetweenPoints(point1: Point, point2: Point): Double
	fun angleBetweenPointsY(point1: Point, point2: Point): Double
	fun average(vararg n: Number): Double
	fun bernstein(n: Number, i: Number): Double
	fun bezierInterpolation(v: Array<Number>, k: Number): Double
	fun catmullRom(p0: Number, p1: Number, p2: Number, p3: Number, t: Number): Double
	fun catmullRomInterpolation(v: Array<Number>, k: Number): Double
	fun ceilTo(value: Number, place: Number, base: Number): Double
	fun chanceRoll(chance: Number): Boolean
	fun clamp(x: Number, a: Number, b: Number): Double
	fun clampBottom(x: Number, a: Number): Double
	fun degToRad(degrees: Number): Double
	fun difference(a: Number, b: Number): Double
	fun distance(x1: Number, y1: Number, x2: Number, y2: Number): Double
	fun distancePow(x1: Number, y1: Number, x2: Number, y2: Number, pow: Number = 2.0): Double
	fun distanceSq(x1: Number, y1: Number, x2: Number, y2: Number): Double
	fun factorial(value: Number): Double
	fun floorTo(value: Number, place: Number, base: Number): Double
	fun fuzzyCeil(`val`: Number, epsilon: Number = 0.0001): Double
	fun fuzzyEqual(a: Number, b: Number, epsilon: Number = 0.0001): Boolean
	fun fuzzyFloor(`val`: Number, epsilon: Number = 0.0001): Double
	fun fuzzyGreaterThan(a: Number, b: Number, epsilon: Number = 0.0001): Boolean


}

@JsName("Phaser.Input") external class Input(val game: Game) {
	val keyboard: Phaser.Keyboard
}

external interface PhaserKey {

}

@JsName("Phaser.SignalBinding")
external class SignalBinding<T>() {
	fun detach(): ((T) -> Unit)?
	fun execute(arg: T): Any
	fun getListener(): (T) -> Unit
	fun getSignal(): Signal<T>
	fun isBound(): Boolean
	fun isOnce(): Boolean
}

@JsName("Phaser.Signal")
external class Signal<T>() {
	var active: Boolean
	var memorize: Boolean
	fun add(listener: (T) -> Unit, listenerContext: Any, priority: Int, args: Array<Any>): SignalBinding<T>
	fun addOnce(listener: (T) -> Unit, listenerContext: Any, priority: Int, args: Array<Any>): SignalBinding<T>
	fun remove(listener: (T) -> Unit, context: Any): (T) -> Unit
	fun removeAll(context: Any): (T) -> Unit
	fun dispatch(value: T): Unit
	fun dispose(): Unit
	fun forget(): Unit
	fun getNumListeners(): Int
	fun halt(): Unit
	fun has(listener: (T) -> Unit, context: Any): Boolean
}


/*
var game = new Phaser.Game(gameProperties.screenWidth, gameProperties.screenHeight, Phaser.AUTO, 'gameDiv');
game.state.add(states.game, gameState);
game.state.start(states.game);
*/