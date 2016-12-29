import Phaser.*
import kotlin.properties.Delegates

fun main(args: Array<String>) {
	async {
		for (n in 0 until 10) {
			println("HEY!$n")
			await(sleepAsync(1000))
		}
		println("DONE!")
	}.then {
		println("DONE2!")
	}
	val game = Game(gameProperties.screenWidth, gameProperties.screenHeight, "auto", "gameDiv")
	game.state.add(GameStates.GAME, ::MyGameState)
	game.state.start(GameStates.GAME)
	val z = PhaserMath.difference(1.0, 2.0)
}

object GameStates {
	val GAME = GameStateType("game")
}

object gameProperties {
	val screenWidth = 640.0
	val screenHeight = 480.0
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

class MyGameState(val game: Phaser.Game) : GameState(game) {
	val load = game.load
	val keyboard: Phaser.Keyboard get() = game.input.keyboard
	val key_left by lazy { keyboard.addKey(Phaser.Keyboard.LEFT) }
	val key_right by lazy { keyboard.addKey(Phaser.Keyboard.RIGHT) }
	val key_thrust by lazy { keyboard.addKey(Phaser.Keyboard.UP) }
	var shipSprite: Sprite by Delegates.notNull()

	override fun preload() {
		load.image(Assets.ship, Assets.bullet, Assets.asteroidLarge, Assets.asteroidMedium, Assets.asteroidSmall)
	}

	override fun create() {
		println("create")

		game.add.text(10, 10, "Hello World!", TextStyle(
			font = "40px Arial",
			fill = "red"
		))

		shipSprite = game.add.sprite(
			shipProperties.startX,
			shipProperties.startY,
			Assets.ship.name
		).apply {
			angle = -90.0
			anchor.set(0.5, 0.5)
		}

		// Physics
		game.physics.startSystem(Physics.ARCADE)

		game.physics.enable(shipSprite, Physics.ARCADE)
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

		checkBoundaries(this.shipSprite)
	}

	private fun checkBoundaries(sprite: Sprite) {
		if (sprite.x < 0) sprite.x += game.width
		if (sprite.x > game.width) sprite.x -= game.width
		if (sprite.y < 0) sprite.y += game.height
		if (sprite.y > game.height) sprite.y -= game.height
	}
}

