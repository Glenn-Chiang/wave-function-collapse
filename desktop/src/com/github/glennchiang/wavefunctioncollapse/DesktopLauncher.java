package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.github.glennchiang.wavefunctioncollapse.WaveFunctionCollapse;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(WaveFunctionCollapse.SCREEN_WIDTH, WaveFunctionCollapse.SCREEN_HEIGHT);
		config.setTitle("WaveFunctionCollapse");
		new Lwjgl3Application(new WaveFunctionCollapse(), config);
	}
}
