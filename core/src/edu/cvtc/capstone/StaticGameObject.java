package edu.cvtc.capstone;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Lance Matysik on 3/25/16.
 */

public class StaticGameObject {

    private Body body;
    private BodyDef bodyDef;

    private float x, y;

    public StaticGameObject(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Body create(Texture texture, World world) {
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(this.x, this.y);
        bodyDef.fixedRotation = true;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(texture.getWidth() / 2, texture.getHeight() / 2, new Vector2(texture.getWidth() / 2, texture.getHeight() / 2), 0f);

        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = polygonShape;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(new Sprite(texture));
        return body;
    }

}

