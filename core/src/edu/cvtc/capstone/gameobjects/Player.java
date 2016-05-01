package edu.cvtc.capstone.gameobjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import edu.cvtc.capstone.screens.CharacterMenuScreen;

/**
 * Created by ruhk5 on 4/18/2016.
 */
public class Player extends Sprite implements InputProcessor {

    /** the movement velocity */
    private Vector2 velocity = new Vector2();

    private boolean readyForNextLevel;

    private float speed = 600 * 2, gravity = 0;

    private TiledMapTileLayer collisionLayer;

    private float tileWidth;
    private float tileHeight;

    public Player(Sprite sprite) {
        super(sprite);
    }

    @Override
    public void draw(Batch spriteBatch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch);
    }

    public boolean nextLevel() {
        return readyForNextLevel;
    }

    public void changeLevel(Game game) {
        readyForNextLevel = false;
        game.setScreen(new CharacterMenuScreen(game, new Rock(1, 2)));

    }


    public void update(float delta) {

        // clamp velocity
        if(velocity.y > speed)
                velocity.y = speed;
        else if(velocity.y < -speed)
            velocity.y = -speed;

        // save old position
        float oldX = getX(), oldY = getY();
        boolean collisionX = false, collisionY = false, nextLevel = false;

        this.tileWidth = collisionLayer.getTileWidth();
        this.tileHeight = collisionLayer.getTileHeight();

        // move on x
        setX(getX() + velocity.x * delta);

        collisionX = detectXCollision("wall");
        nextLevel = detectXCollision("nextlevel");

        // react to x collision
        if(collisionX) {
            setX(oldX);
            velocity.x = 0;
        }

        // move on y
        setY(getY() + velocity.y * delta);

        collisionY = detectYCollision("wall");
        if (!nextLevel) {
            nextLevel = detectYCollision("nextlevel");
        }

        // react to y collision
        if(collisionY) {
            setY(oldY);
            velocity.y = 0;
        }

        if (nextLevel) {
            //prolly use some kind of current level
            readyForNextLevel = true;


        }
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Input.Keys.UP:
            case Input.Keys.W:
                velocity.y = speed;
                break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                velocity.y = -speed;
                break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                velocity.x = -speed;
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                velocity.x = speed;
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Input.Keys.A:
            case Input.Keys.D:
            case Input.Keys.LEFT:
            case Input.Keys.RIGHT:
                velocity.x = 0;
                break;
            case Input.Keys.W:
            case Input.Keys.S:
            case Input.Keys.UP:
            case Input.Keys.DOWN:
                velocity.y = 0;
                break;
            default:
                break;
        }
        return true;
    }

    private boolean detectXCollision(String key) {

        boolean collision = false;
        if(velocity.x < 0) { // going left
            // top left
            collision = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight()) / tileHeight)).getTile().getProperties().containsKey(key);

            // middle left
            if(!collision) {
                collision = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight)).getTile().getProperties().containsKey(key);
            }

            // bottom left
            if(!collision) {
                collision = collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight)).getTile().getProperties().containsKey(key);
            }
        } else if(velocity.x > 0) { // going right
            // top right
            collision = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight()) / tileHeight)).getTile().getProperties().containsKey(key);

            // middle right
            if(!collision) {
                collision = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight)).getTile().getProperties().containsKey(key);
            }

            // bottom right
            if(!collision) {
                collision = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) (getY() / tileHeight)).getTile().getProperties().containsKey(key);
            }
        }

        return collision;
    }

    private boolean detectYCollision(String key) {

        boolean collision = false;

        if(velocity.y < 0) { // going down
            // bottom left
            collision = collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight)).getTile().getProperties().containsKey(key);

            // bottom middle
            if(!collision) {
                collision = collisionLayer.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) (getY() / tileHeight)).getTile().getProperties().containsKey(key);
            }

            // bottom right
            if(!collision) {
                collision = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) (getY() / tileHeight)).getTile().getProperties().containsKey(key);
            }

        } else if(velocity.y > 0) { // going up
            // top left
            collision = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight()) / tileHeight)).getTile().getProperties().containsKey(key);

            // top middle
            if(!collision)
                collision = collisionLayer.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) ((getY() + getHeight()) / tileHeight)).getTile().getProperties().containsKey(key);

            // top right
            if(!collision)
                collision = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight()) / tileHeight)).getTile().getProperties().containsKey(key);
        }

        return collision;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
