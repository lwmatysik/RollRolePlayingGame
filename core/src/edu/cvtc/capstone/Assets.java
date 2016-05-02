package edu.cvtc.capstone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by Lance Matysik on 3/20/16.
 */

public class Assets {

    public static Texture rock;
    public static Texture topHatMonster;

    public static TiledMap map;

    public static BitmapFont fanwoodText18;

    public static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static BitmapFont loadFont(String file, Color color, int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(file));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = size;
        params.color = color;
        return generator.generateFont(params);
    }

    public static TiledMap loadMap(String file) {
        return new TmxMapLoader().load(file);
    }


    public static void load() {
        rock = loadTexture("images/rock_with_eyes_pixelated30.png");
        topHatMonster = loadTexture("images/top_hat_monster.png");
        fanwoodText18 = loadFont("fonts/Fanwood Text.otf", Color.WHITE, 18);
        map = loadMap("maps/Test.tmx");
    }

}
