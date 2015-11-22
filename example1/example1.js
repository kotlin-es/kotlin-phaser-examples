(function (Kotlin) {
  'use strict';
  var _ = Kotlin.defineRootPackage(function () {
    this.GameStates = Kotlin.createObject(null, function () {
      this.GAME = _.GameStateType_61zpoe$('game');
    });
    this.gameProperties = Kotlin.createObject(null, function () {
      this.screenWidth = 640;
      this.screenHeight = 480;
    });
    this.Assets = Kotlin.createObject(null, function () {
      this.ship = _.Assert_puj7f4$('ship', 'assets/ship.png');
      this.bullet = _.Assert_puj7f4$('bullet', 'assets/bullet.png');
      this.asteroidLarge = _.Assert_puj7f4$('asteroidLarge', 'assets/asteroidLarge.png');
      this.asteroidMedium = _.Assert_puj7f4$('asteroidMedium', 'assets/asteroidMedium.png');
      this.asteroidSmall = _.Assert_puj7f4$('asteroidSmall', 'assets/asteroidSmall.png');
    });
    this.shipProperties = Kotlin.createObject(null, function () {
      this.startX = _.gameProperties.screenWidth * 0.5;
      this.startY = _.gameProperties.screenHeight * 0.5;
      this.acceleration = 300.0;
      this.drag = 100.0;
      this.maxVelocity = 300.0;
      this.angularVelocity = 200.0;
    });
  }, /** @lends _ */ {
    main_kand9s$: function (args) {
      var game = new Phaser.Game(_.gameProperties.screenWidth, _.gameProperties.screenHeight, 'auto', 'gameDiv');
      game.state.add(_.GameStates.GAME, Kotlin.getCallableRefForConstructor(_.MyGameState));
      game.state.start(_.GameStates.GAME);
    },
    MyGameState: Kotlin.createClass(function () {
      return [_.GameState];
    }, function $fun(game) {
      $fun.baseInitializer.call(this, game);
      this.game = game;
      this.load = this.game.load;
      this.key_left$delegate = Kotlin.modules['stdlib'].kotlin.lazy_un3fny$(_.MyGameState.key_left$f(this));
      this.key_right$delegate = Kotlin.modules['stdlib'].kotlin.lazy_un3fny$(_.MyGameState.key_right$f(this));
      this.key_thrust$delegate = Kotlin.modules['stdlib'].kotlin.lazy_un3fny$(_.MyGameState.key_thrust$f(this));
      this.shipSprite$delegate = Kotlin.modules['stdlib'].kotlin.properties.Delegates.notNull();
    }, /** @lends _.MyGameState.prototype */ {
      key_left: {
        get: function () {
          return Kotlin.modules['stdlib'].kotlin.getValue_em0fd4$(this.key_left$delegate, this, new Kotlin.PropertyMetadata('key_left'));
        }
      },
      key_right: {
        get: function () {
          return Kotlin.modules['stdlib'].kotlin.getValue_em0fd4$(this.key_right$delegate, this, new Kotlin.PropertyMetadata('key_right'));
        }
      },
      key_thrust: {
        get: function () {
          return Kotlin.modules['stdlib'].kotlin.getValue_em0fd4$(this.key_thrust$delegate, this, new Kotlin.PropertyMetadata('key_thrust'));
        }
      },
      shipSprite: {
        get: function () {
          return this.shipSprite$delegate.getValue_dsk1ci$(this, new Kotlin.PropertyMetadata('shipSprite'));
        },
        set: function (shipSprite) {
          this.shipSprite$delegate.setValue_w32e13$(this, new Kotlin.PropertyMetadata('shipSprite'), shipSprite);
        }
      },
      preload: function () {
        _.image_z0ajh0$(this.load, [_.Assets.ship, _.Assets.bullet, _.Assets.asteroidLarge, _.Assets.asteroidMedium, _.Assets.asteroidSmall]);
      },
      create: function () {
        Kotlin.println('create');
        this.game.add.text(10, 10, 'Hello World!');
        var $receiver = this.game.add.sprite(_.shipProperties.startX, _.shipProperties.startY, _.Assets.ship.name);
        $receiver.angle = -90.0;
        $receiver.anchor.set(0.5, 0.5);
        this.shipSprite = $receiver;
        this.game.physics.startSystem(Phaser.Physics.ARCADE);
        this.game.physics.enable(this.shipSprite, Phaser.Physics.ARCADE);
        this.shipSprite.body.drag.set(_.shipProperties.drag);
        this.shipSprite.body.maxVelocity.set(_.shipProperties.maxVelocity);
      },
      update: function () {
        var tmp$0, tmp$1;
        tmp$1 = this.shipSprite.body;
        if (this.key_left.isDown) {
          tmp$0 = -_.shipProperties.angularVelocity;
        }
         else if (this.key_right.isDown) {
          tmp$0 = _.shipProperties.angularVelocity;
        }
         else {
          tmp$0 = 0.0;
        }
        tmp$1.angularVelocity = tmp$0;
        if (this.key_thrust.isDown) {
          this.game.physics.arcade.accelerationFromRotation(this.shipSprite.rotation, _.shipProperties.acceleration, this.shipSprite.body.acceleration);
        }
         else {
          this.shipSprite.body.acceleration.set(0);
        }
      }
    }, /** @lends _.MyGameState */ {
      key_left$f: function (this$MyGameState) {
        return function () {
          return this$MyGameState.game.input.keyboard.addKey(Phaser.Keyboard.LEFT);
        };
      },
      key_right$f: function (this$MyGameState) {
        return function () {
          return this$MyGameState.game.input.keyboard.addKey(Phaser.Keyboard.RIGHT);
        };
      },
      key_thrust$f: function (this$MyGameState) {
        return function () {
          return this$MyGameState.game.input.keyboard.addKey(Phaser.Keyboard.UP);
        };
      }
    }),
    AssertName: Kotlin.createTrait(null),
    Assert: Kotlin.createClass(null, function (name, url) {
      this.name = name;
      this.url = url;
    }, /** @lends _.Assert.prototype */ {
      component1: function () {
        return this.name;
      },
      component2: function () {
        return this.url;
      },
      copy_65rdel$: function (name, url) {
        return new _.Assert_puj7f4$(name === void 0 ? this.name : name, url === void 0 ? this.url : url);
      },
      toString: function () {
        return 'Assert(name=' + Kotlin.toString(this.name) + (', url=' + Kotlin.toString(this.url)) + ')';
      },
      hashCode: function () {
        var result = 0;
        result = result * 31 + Kotlin.hashCode(this.name) | 0;
        result = result * 31 + Kotlin.hashCode(this.url) | 0;
        return result;
      },
      equals_za3rmp$: function (other) {
        return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.name, other.name) && Kotlin.equals(this.url, other.url)))));
      }
    }),
    Assert_puj7f4$: function (name, url, $this) {
      $this = $this || Object.create(_.Assert.prototype);
      _.Assert.call($this, name, url);
      return $this;
    },
    GameStateType: Kotlin.createTrait(null),
    GameStateType_61zpoe$: function (name) {
      return name;
    },
    GameState: Kotlin.createClass(null, null, /** @lends _.GameState.prototype */ {
      preload: function () {
      },
      create: function () {
      },
      update: function () {
      },
      render: function () {
      }
    }),
    image_h6bct2$: function ($receiver, assert) {
      $receiver.image(assert.name, assert.url);
    },
    image_z0ajh0$: function ($receiver, asserts) {
      var tmp$0, tmp$1, tmp$2;
      tmp$0 = asserts, tmp$1 = tmp$0.length;
      for (var tmp$2 = 0; tmp$2 !== tmp$1; ++tmp$2) {
        var assert = tmp$0[tmp$2];
        $receiver.image(assert.name, assert.url);
      }
    }
  });
  Kotlin.defineModule('example1', _);
  _.main_kand9s$([]);
}(Kotlin));
