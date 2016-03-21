package edu.cvtc.capstone;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Lance Matysik on 3/20/16.
 */

public class DynamicGameObject {

    private Body body;
    private BodyDef bodyDef;

    private float x, y;
    private float density;
    private float friction;
    private float restitution;

    public DynamicGameObject(float x, float y, float density, float friction, float restitution) {
        this.x = x;
        this.y = y;
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
    }

    public Body create(Texture texture, World world) {
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(this.x, this.y);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        body.setUserData(new Sprite(texture));
        return body;
    }

}
