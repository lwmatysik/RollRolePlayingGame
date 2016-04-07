package edu.cvtc.capstone.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.cvtc.capstone.RollRolePlayingGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.title = "Roll Role Playing Game";
		config.width = 1280;
		config.height = 720;
		
		// Teia's laptop is 720p but the dock on her Mac blocks the game
		// the fullscreen settting is so she can see the whole play area
		config.fullscreen = true;
		
		new LwjglApplication(new RollRolePlayingGame(), config);
	}
}
