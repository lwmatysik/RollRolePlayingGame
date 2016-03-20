package edu.cvtc.capstone;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lance Matysik on 3/20/16.
 */

public class GameObject {

    public final Vector2 position;

    public GameObject(float x, float y) {
        this.position = new Vector2(x, y);
    }
}