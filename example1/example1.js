if (typeof kotlin === 'undefined') {
  throw new Error("Error loading module 'example1'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'example1'.");
}
var example1 = function (_, Kotlin) {
  'use strict';
  var lazy = Kotlin.kotlin.lazy_un3fny$;
  var properties_0 = Kotlin.kotlin.properties;
  MyGameState.prototype = Object.create(GameState.prototype);
  MyGameState.prototype.constructor = MyGameState;
  function main(args) {
    var game = new Phaser.Game(gameProperties_getInstance().screenWidth, gameProperties_getInstance().screenHeight, 'auto', 'gameDiv');
    game.state.add(GameStates_getInstance().GAME, Kotlin.getCallableRefForConstructor(MyGameState));
    game.state.start(GameStates_getInstance().GAME);
    var z = Phaser.Math.difference(1.0, 2.0);
  }
  function GameStates() {
    GameStates_instance = this;
    this.GAME = GameStateType('game');
  }
  GameStates.$metadata$ = {
    type: Kotlin.TYPE.OBJECT,
    classIndex: Kotlin.newClassIndex(),
    simpleName: 'GameStates',
    baseClasses: []
  };
  var GameStates_instance = null;
  function GameStates_getInstance() {
    if (GameStates_instance === null) {
      GameStates_instance = new GameStates();
    }
    return GameStates_instance;
  }
  function gameProperties() {
    gameProperties_instance = this;
    this.screenWidth = 640.0;
    this.screenHeight = 480.0;
  }
  gameProperties.$metadata$ = {
    type: Kotlin.TYPE.OBJECT,
    classIndex: Kotlin.newClassIndex(),
    simpleName: 'gameProperties',
    baseClasses: []
  };
  var gameProperties_instance = null;
  function gameProperties_getInstance() {
    if (gameProperties_instance === null) {
      gameProperties_instance = new gameProperties();
    }
    return gameProperties_instance;
  }
  function Assets() {
    Assets_instance = this;
    this.ship = Assert_init('ship', 'assets/ship.png');
    this.bullet = Assert_init('bullet', 'assets/bullet.png');
    this.asteroidLarge = Assert_init('asteroidLarge', 'assets/asteroidLarge.png');
    this.asteroidMedium = Assert_init('asteroidMedium', 'assets/asteroidMedium.png');
    this.asteroidSmall = Assert_init('asteroidSmall', 'assets/asteroidSmall.png');
  }
  Assets.$metadata$ = {
    type: Kotlin.TYPE.OBJECT,
    classIndex: Kotlin.newClassIndex(),
    simpleName: 'Assets',
    baseClasses: []
  };
  var Assets_instance = null;
  function Assets_getInstance() {
    if (Assets_instance === null) {
      Assets_instance = new Assets();
    }
    return Assets_instance;
  }
  function shipProperties() {
    shipProperties_instance = this;
    this.startX = gameProperties_getInstance().screenWidth * 0.5;
    this.startY = gameProperties_getInstance().screenHeight * 0.5;
    this.acceleration = 300.0;
    this.drag = 100.0;
    this.maxVelocity = 300.0;
    this.angularVelocity = 200.0;
  }
  shipProperties.$metadata$ = {
    type: Kotlin.TYPE.OBJECT,
    classIndex: Kotlin.newClassIndex(),
    simpleName: 'shipProperties',
    baseClasses: []
  };
  var shipProperties_instance = null;
  function shipProperties_getInstance() {
    if (shipProperties_instance === null) {
      shipProperties_instance = new shipProperties();
    }
    return shipProperties_instance;
  }
  function MyGameState(game) {
    GameState.call(this, game);
    this.game = game;
    this.load = this.game.load;
    this.key_left$delegate = lazy(MyGameState$key_left$lambda(this));
    this.key_right$delegate = lazy(MyGameState$key_right$lambda(this));
    this.key_thrust$delegate = lazy(MyGameState$key_thrust$lambda(this));
    this.shipSprite$delegate = properties_0.Delegates.notNull();
  }
  Object.defineProperty(MyGameState.prototype, 'keyboard', {
    get: function () {
      return this.game.input.keyboard;
    }
  });
  Object.defineProperty(MyGameState.prototype, 'key_left', {
    get: function () {
      var $receiver = this.key_left$delegate;
      new Kotlin.PropertyMetadata('key_left');
      return $receiver.value;
    }
  });
  Object.defineProperty(MyGameState.prototype, 'key_right', {
    get: function () {
      var $receiver = this.key_right$delegate;
      new Kotlin.PropertyMetadata('key_right');
      return $receiver.value;
    }
  });
  Object.defineProperty(MyGameState.prototype, 'key_thrust', {
    get: function () {
      var $receiver = this.key_thrust$delegate;
      new Kotlin.PropertyMetadata('key_thrust');
      return $receiver.value;
    }
  });
  Object.defineProperty(MyGameState.prototype, 'shipSprite', {
    get: function () {
      return this.shipSprite$delegate.getValue_dsk1ci$(this, new Kotlin.PropertyMetadata('shipSprite'));
    },
    set: function (shipSprite) {
      this.shipSprite$delegate.setValue_w32e13$(this, new Kotlin.PropertyMetadata('shipSprite'), shipSprite);
    }
  });
  MyGameState.prototype.preload = function () {
    image(this.load, [Assets_getInstance().ship, Assets_getInstance().bullet, Assets_getInstance().asteroidLarge, Assets_getInstance().asteroidMedium, Assets_getInstance().asteroidSmall]);
  };
  MyGameState.prototype.create = function () {
    Kotlin.println('create');
    this.game.add.text(10, 10, 'Hello World!', TextStyle('40px Arial', void 0, void 0, void 0, void 0, void 0, 'red'));
    var $receiver = this.game.add.sprite(shipProperties_getInstance().startX, shipProperties_getInstance().startY, Assets_getInstance().ship.name);
    $receiver.angle = -90.0;
    $receiver.anchor.set(0.5, 0.5);
    this.shipSprite = $receiver;
    this.game.physics.startSystem(Phaser.Physics.ARCADE);
    this.game.physics.enable(this.shipSprite, Phaser.Physics.ARCADE);
    this.shipSprite.body.drag.set(shipProperties_getInstance().drag);
    this.shipSprite.body.maxVelocity.set(shipProperties_getInstance().maxVelocity);
  };
  MyGameState.prototype.update = function () {
    var tmp$, tmp$_0;
    tmp$_0 = this.shipSprite.body;
    if (this.key_left.isDown) {
      tmp$ = -shipProperties_getInstance().angularVelocity;
    }
     else if (this.key_right.isDown) {
      tmp$ = shipProperties_getInstance().angularVelocity;
    }
     else {
      tmp$ = 0.0;
    }
    tmp$_0.angularVelocity = tmp$;
    if (this.key_thrust.isDown) {
      this.game.physics.arcade.accelerationFromRotation(this.shipSprite.rotation, shipProperties_getInstance().acceleration, this.shipSprite.body.acceleration);
    }
     else {
      this.shipSprite.body.acceleration.set(0);
    }
    this.checkBoundaries_0(this.shipSprite);
  };
  MyGameState.prototype.checkBoundaries_0 = function (sprite) {
    if (sprite.x < 0)
      sprite.x = sprite.x + this.game.width;
    if (sprite.x > this.game.width)
      sprite.x = sprite.x - this.game.width;
    if (sprite.y < 0)
      sprite.y = sprite.y + this.game.height;
    if (sprite.y > this.game.height)
      sprite.y = sprite.y - this.game.height;
  };
  function MyGameState$key_left$lambda(this$MyGameState) {
    return function () {
      return this$MyGameState.keyboard.addKey(Phaser.Keyboard.LEFT);
    };
  }
  function MyGameState$key_right$lambda(this$MyGameState) {
    return function () {
      return this$MyGameState.keyboard.addKey(Phaser.Keyboard.RIGHT);
    };
  }
  function MyGameState$key_thrust$lambda(this$MyGameState) {
    return function () {
      return this$MyGameState.keyboard.addKey(Phaser.Keyboard.UP);
    };
  }
  MyGameState.$metadata$ = {
    type: Kotlin.TYPE.CLASS,
    classIndex: Kotlin.newClassIndex(),
    simpleName: 'MyGameState',
    baseClasses: [GameState]
  };
  function Assert(name, url) {
    this.name = name;
    this.url = url;
  }
  Assert.$metadata$ = {
    type: Kotlin.TYPE.CLASS,
    classIndex: Kotlin.newClassIndex(),
    simpleName: 'Assert',
    baseClasses: []
  };
  Assert.prototype.component1 = function () {
    return this.name;
  };
  Assert.prototype.component2 = function () {
    return this.url;
  };
  Assert.prototype.copy_i88sg6$ = function (name, url) {
    return new Assert_init(name === void 0 ? this.name : name, url === void 0 ? this.url : url);
  };
  Assert.prototype.toString = function () {
    return 'Assert(name=' + Kotlin.toString(this.name) + (', url=' + Kotlin.toString(this.url)) + ')';
  };
  Assert.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.name) | 0;
    result = result * 31 + Kotlin.hashCode(this.url) | 0;
    return result;
  };
  Assert.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.name, other.name) && Kotlin.equals(this.url, other.url)))));
  };
  function GameStateType(name) {
    var tmp$;
    return Kotlin.isType(tmp$ = name, Object) ? tmp$ : Kotlin.throwCCE();
  }
  function GameState(game) {
  }
  GameState.prototype.preload = function () {
  };
  GameState.prototype.create = function () {
  };
  GameState.prototype.update = function () {
  };
  GameState.prototype.render = function () {
  };
  GameState.$metadata$ = {
    type: Kotlin.TYPE.CLASS,
    classIndex: Kotlin.newClassIndex(),
    simpleName: 'GameState',
    baseClasses: []
  };
  function TextStyle(font, fontStyle, fontVariant, fontWeight, fontSize, backgroundColor, fill, align, boundsAlignH, boundsAlignV, stroke, strokeThickness, wordWrap, wordWrapWidth, tabs) {
    if (font === void 0)
      font = null;
    if (fontStyle === void 0)
      fontStyle = null;
    if (fontVariant === void 0)
      fontVariant = null;
    if (fontWeight === void 0)
      fontWeight = null;
    if (fontSize === void 0)
      fontSize = null;
    if (backgroundColor === void 0)
      backgroundColor = null;
    if (fill === void 0)
      fill = null;
    if (align === void 0)
      align = null;
    if (boundsAlignH === void 0)
      boundsAlignH = null;
    if (boundsAlignV === void 0)
      boundsAlignV = null;
    if (stroke === void 0)
      stroke = null;
    if (strokeThickness === void 0)
      strokeThickness = null;
    if (wordWrap === void 0)
      wordWrap = null;
    if (wordWrapWidth === void 0)
      wordWrapWidth = null;
    if (tabs === void 0)
      tabs = null;
    var out = {};
    if (font != null)
      out.font = font;
    if (fontStyle != null)
      out.fontStyle = fontStyle;
    if (fontVariant != null)
      out.fontVariant = fontVariant;
    if (fontWeight != null)
      out.fontWeight = fontWeight;
    if (fontSize != null)
      out.fontSize = fontSize;
    if (backgroundColor != null)
      out.backgroundColor = backgroundColor;
    if (fill != null)
      out.fill = fill;
    if (align != null)
      out.align = align;
    if (boundsAlignH != null)
      out.boundsAlignH = boundsAlignH;
    if (boundsAlignV != null)
      out.boundsAlignV = boundsAlignV;
    if (stroke != null)
      out.stroke = stroke;
    if (strokeThickness != null)
      out.strokeThickness = strokeThickness;
    if (wordWrap != null)
      out.wordWrap = wordWrap;
    if (wordWrapWidth != null)
      out.wordWrapWidth = wordWrapWidth;
    if (tabs != null)
      out.tabs = tabs;
    return out;
  }
  function image_0($receiver, assert) {
    $receiver.image(assert.name, assert.url);
  }
  function image($receiver, asserts) {
    var tmp$;
    for (tmp$ = 0; tmp$ !== asserts.length; ++tmp$) {
      var element = asserts[tmp$];
      $receiver.image(element.name, element.url);
    }
  }
  function DirectionObj() {
  }
  DirectionObj.$metadata$ = {
    type: Kotlin.TYPE.TRAIT,
    classIndex: Kotlin.newClassIndex(),
    simpleName: 'DirectionObj',
    baseClasses: []
  };
  _.main_kand9s$ = main;
  Object.defineProperty(_, 'GameStates', {
    get: GameStates_getInstance
  });
  Object.defineProperty(_, 'gameProperties', {
    get: gameProperties_getInstance
  });
  Object.defineProperty(_, 'Assets', {
    get: Assets_getInstance
  });
  Object.defineProperty(_, 'shipProperties', {
    get: shipProperties_getInstance
  });
  _.MyGameState = MyGameState;
  var package$Phaser = _.Phaser || (_.Phaser = {});
  package$Phaser.Assert_init_puj7f4$ = Assert_init;
  package$Phaser.Assert = Assert;
  package$Phaser.GameStateType_61zpoe$ = GameStateType;
  package$Phaser.GameState = GameState;
  package$Phaser.TextStyle_gqcoa5$ = TextStyle;
  package$Phaser.image_we6qu1$ = image_0;
  package$Phaser.image_alw5g3$ = image;
  package$Phaser.DirectionObj = DirectionObj;
  function Assert_init(name, url, $this) {
    $this = $this || Object.create(Assert.prototype);
    Assert.call($this, Kotlin.isType(tmp$ = name, Object) ? tmp$ : Kotlin.throwCCE(), url);
    return $this;
  }
  var tmp$;
  Kotlin.defineModule('example1', _);
  main([]);
  return _;
}(typeof example1 === 'undefined' ? {} : example1, kotlin);
