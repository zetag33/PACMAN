package edu.uoc.pacman;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import edu.uoc.pacman.view.PacmanGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("PACMAN Game");
		config.setResizable(false);
		config.setWindowedMode(PacmanGame.WINDOW_WIDTH, PacmanGame.WINDOW_HEIGHT);;
		new Lwjgl3Application(new PacmanGame(), config);
	}
}
