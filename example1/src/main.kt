import kotlin.browser.document
import kotlin.properties.Delegates

fun main(args: Array<String>) {
	val game = PhaserGame(gameProperties.screenWidth, gameProperties.screenHeight, "auto", "gameDiv")
	game.state.add(GameStates.GAME, ::MyGameState)
	game.state.start(GameStates.GAME)
}

object GameStates {
	val GAME = GameStateType("game")
}

object gameProperties {
	val screenWidth = 640
	val screenHeight = 480
}

object Assets {
	val ship = Assert("ship", "assets/ship.png")
	val bullet = Assert("bullet", "assets/bullet.png")
	val asteroidLarge = Assert("asteroidLarge", "assets/asteroidLarge.png")
	val asteroidMedium = Assert("asteroidMedium", "assets/asteroidMedium.png")
	val asteroidSmall = Assert("asteroidSmall", "assets/asteroidSmall.png")
}

object shipProperties {
	val startX = gameProperties.screenWidth * 0.5
	val startY = gameProperties.screenHeight * 0.5
	val acceleration = 300.0
	val drag = 100.0
	val maxVelocity = 300.0
	val angularVelocity = 200.0
}

class MyGameState(val game: PhaserGame) : GameState(game) {
	val load = game.load
	val key_left by lazy { game.input.keyboard.addKey(PhaserKeyboard.LEFT) }
	val key_right by lazy { game.input.keyboard.addKey(PhaserKeyboard.RIGHT) }
	val key_thrust by lazy { game.input.keyboard.addKey(PhaserKeyboard.UP) }
	var shipSprite: PhaserSprite by Delegates.notNull()

	override fun preload() {
		load.image(Assets.ship, Assets.bullet, Assets.asteroidLarge, Assets.asteroidMedium, Assets.asteroidSmall)
	}

	override fun create() {
		println("create")

		game.add.text(10, 10, "Hello World!")

		shipSprite = game.add.sprite(
			shipProperties.startX,
			shipProperties.startY,
			Assets.ship.name
		).apply {
			angle = -90.0
			anchor.set(0.5, 0.5)
		}

		// Physics
		game.physics.startSystem(PhaserPhysics.ARCADE)

		game.physics.enable(shipSprite, PhaserPhysics.ARCADE)
		shipSprite.body.drag.set(shipProperties.drag)
		shipSprite.body.maxVelocity.set(shipProperties.maxVelocity)
	}

	override fun update() {
		shipSprite.body.angularVelocity = if (key_left.isDown) {
			-shipProperties.angularVelocity
		} else if (key_right.isDown) {
			shipProperties.angularVelocity
		} else {
			0.0
		}

		if (key_thrust.isDown) {
			game.physics.arcade.accelerationFromRotation(
				shipSprite.rotation,
				shipProperties.acceleration,
				shipSprite.body.acceleration
			)
		} else {
			this.shipSprite.body.acceleration.set(0)
		}
	}
}
