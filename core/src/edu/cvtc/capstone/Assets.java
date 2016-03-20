package edu.cvtc.capstone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Lance Matysik on 3/20/16.
 */

public class Assets {

    public static Texture rock;

    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load() {
        rock = loadTexture("images/rock_with_eyes.png");
    }

}
