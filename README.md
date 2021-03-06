# Pacbattle
As the name indicates, Pacbattle is inspired by Pac-Man. It can be two to four players. Pacbattle is a melee game, so it is best suited for four-player. 
All the players starts with three health points. Each player can have max five health points and minimum one (if a player has zero health points the given player is dead). 

When the game starts, a timer starts counting from sixty to zero. You can either win by eating all your opponents before the timer reaches zero or by having the highest score after the timer has reached zero (If for example a player is dead, but not all besides one are dead, the win order is solely based on score and it does not matter that the given player died).

 In order to “kill” a specific opponent you must bite the opponent until the opponent’s health bar reaches zero (If a player is bitten, the given player’s health and score is decreased by one). In order to bite an opponent you must intersect the given opponent whilst being behind the given opponent. Each time you bite an opponent, both your health and score will increase by one while the opponent’s health and score will decrease by one. 

There are four yellow dots/food respawning at different locations at an interval of three seconds. Eating food increases score and health by one. The players’ speed increases/decreases proportionally with the given player’s size.



## Table of Contents

Precompiled

Develop

Compile and package 

Screenshots 

Launcher 

Controller

## Precompiled

Download the game system from https://github.com/s111/gamesystem.


The game packaged together with the controller. [All Releases](https://github.com/henmja/gs-Pacbattle/releases/tag/v1)

Unzip and the pacbattle folder can now be added to the game systems games directory.

## Develop

Download the game system from https://github.com/s111/gamesystem.

Download the repository:

git clone github.com/henmja/gs-pacbattle Import the project into your IDE of choice. Choose a new maven project and import the pom.xml file. For your IDE to be able to compile the games, you first need to have maven generate the native libraries needed by the game. You do this by executing the following command:

mvn generate-resources Then you need to set VM options to -Djava.library.path=target/natives.

You should now be able to launch the game from your IDE. Remember that you need the game system running in the background for it to work.

## Compile and package

To package the game for distribuiton you must execute the following command:

mvn clean compile assembly:single 

Now create this folder structure:

pacbattle/bin Copy game.json, screenshot.png and the controller folder into the pacbattle folder. Copy the target/natives folder into it too and rename it to lib. Now copy the jar file in the target folder into the pacbattle/bin folder and rename it to pacbattle.jar.

You should now have a folder named pacbattle which looks something like this:

pacbattle/game.json pacbattle/controller/index.html pacbattle/controller/controller.js pacbattle/bin/pacbattle.jar pacbattle/lib/lwjgl.dll pacbattle/lib/... This folder can now be added to the game systems games directory.

## Screenshots

### Launcher
![image](https://cloud.githubusercontent.com/assets/10501925/14324923/3fbdfd04-fc27-11e5-94b8-cb62e461c944.png)
### Controller
![image](https://cloud.githubusercontent.com/assets/10501925/14324931/4da0fa48-fc27-11e5-97ef-e8524c6e086d.png)
