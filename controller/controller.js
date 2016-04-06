var game = new Phaser.Game(800, 600, Phaser.AUTO, 'game', {
    preload: preload,
    create: create,
    update: update
});

var paddle;
var paddleUp;
var paddleLeft;
var paddleRight;
var j;
var target;





var BLACK = "0x111213";
var YELLOW = "0xf7df1e";
var GRAY = "0x808080";
var WHITE = "0xFFFFFF";

var textStyle = {
    font: "38px Arial",
    fill: "#eeefef",
    strokeThickness: 5,
    stroke: "#111213"
};





var movePaddle = function(pos) {};
var movePaddleUp = function(pos) {};
var movePaddleLeft = function(pos) {};
var movePaddleRight = function(pos) {};

function preload() {
    addMessageHandler(function(msg) {
            movePaddle = function(pos) {
                sendToGame("move", pos);
            }
            movePaddleUp = function(pos) {
                sendToGame("moveUp", pos);
            }
            movePaddleLeft = function(pos) {
                sendToGame("moveLeft", pos);
            }
            movePaddleRight = function(pos) {
                sendToGame("moveRight", pos);
            }
    });
}

function create() {
    game.scale.fullScreenScaleMode = Phaser.ScaleManager.EXACT_FIT;
    game.scale.scaleMode = Phaser.ScaleManager.EXACT_FIT;
    game.scale.refresh();

    game.stage.backgroundColor = BLACK;

    game.input.onDown.add(function(pointer) {
        var data;

        if (pointer.targetObject) {
            data = pointer.targetObject.sprite.data;
        }

        
	if (!game.scale.isFullScreen) {
            game.scale.startFullScreen(false);
        }

        target = pointer.targetObject;
    }, this);
}

function createPaddleSprite() {
  for (i = 0; i < 4; i++) {
        if (i==3) {
          j = 2;
        }
    var g = game.add.graphics(0, 0);
    g.beginFill(YELLOW, 1);
        g.drawRect((i > 1 ? 1/2 : i)*((2*game.stage.width/3)), (i < 2 ? 1 : 0+j) * ((game.stage.height/3)), (game.stage.width/3), (game.stage.height/3));
        s = game.add.sprite(0, 0);
        s.addChild(g);
        s.data = i;
        s.inputEnabled = true;
    s.input.enableDrag();
        s.input.allowVerticalDrag = false;
        s.input.allowHorizontalDrag = false;
        s.input.boundsRect = new Phaser.Rectangle(0,0, game.stage.width/3, game.stage.height/3);


   if (i==3) {
	  paddle = s;
	  
    } else if (i==2) {

          paddleUp = s;
    } else if (i==1) {
           paddleRight= s;


    } else if (i==0) {
          paddleLeft= s;

    }
    }
}




function update() {
    if (target && target.sprite === paddle && target.isDragged) {
        var newPos = 1;

        
            movePaddle(newPos);
        
    } else if (target && target.sprite === paddleUp && target.isDragged) {
         var newPos = 1;
            movePaddleUp(newPos);
    } else if (target && target.sprite === paddleLeft && target.isDragged) {
        var newPos = 1;

        
            movePaddleLeft(newPos);
        
    } else if (target && target.sprite === paddleRight && target.isDragged) {
        var newPos = 1;

        
            movePaddleRight(newPos);
        
    }




            if (!paddleUp || !paddle || !paddleRight || !paddleLeft) {
                createPaddleSprite();
            }


}

