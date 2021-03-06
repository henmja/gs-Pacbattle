package com.github.henmja.bachelor.pacbattle;

import com.github.henmja.bachelor.pacbattle.game.Pacbattle;
import com.github.henmja.bachelor.pacbattle.network.GameSession;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;

import java.awt.*;

public class Application {
    private static Pacbattle game;
    private static GameSession gameSession;

    private Application() {
        game = new Pacbattle("Pacbattle");
        createGameSession();
        startGame();
    }

    public static GameSession getGameSession() {
        return gameSession;
    }

    public static void fatalError(String error) {
        System.out.println(error);
        exit();
    }

    private static void exit() {
        System.out.println("Unable to recover; exiting...");
        System.exit(1);
    }

    public static void main(String[] args) {
        new Application();
    }

    private void createGameSession() {
        try {
            gameSession = new GameSession(game);
            gameSession.connect();
        } catch (Exception e) {
            fatalError("Could not start websocket server: " + e.getMessage());
        }
    }

    private void startGame() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");

        try {
            Display.setResizable(false);

            AppGameContainer app = new AppGameContainer(new ScalableGame(game, Pacbattle.WIDTH, Pacbattle.HEIGHT));
            app.setDisplayMode(width, height, false);
            app.setTargetFrameRate(60);
            app.setMouseGrabbed(true);
            app.setAlwaysRender(true);
            app.start();
        } catch (SlickException e) {
            fatalError("Could not start spacebattle: " + e.getMessage());
        }
    }
}
