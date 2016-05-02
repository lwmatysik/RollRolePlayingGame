package edu.cvtc.capstone.gameobjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import edu.cvtc.capstone.screens.CharacterMenuScreen;

/**
 * Created by ruhk5 on 4/18/2016.
 */
public class Player extends Sprite {

    private TiledMap map;

    /** the movement velocity */
    private Vector2 velocity = new Vector2();

    private boolean readyForNextLevel;
    private boolean readyForPreviousLevel;

    private Vector2 previousPosition;

    private float speed = 150 * 2;

    private TiledMapTileLayer collisionLayer;
    private TiledMapTileLayer itemLayer;

    private float tileWidth;
    private float tileHeight;

    public Player(Sprite sprite) {
        super(sprite);
        this.setX(10);
        this.setY(10);
    }

    @Override
    public void draw(Batch spriteBatch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch);
    }

    public boolean nextLevel() {
        return readyForNextLevel;
    }

    public void removeNextLevel() {
        readyForNextLevel = false;
    }

    public boolean previousLevel() {
        return readyForPreviousLevel;
    }

    public void removePreviousLevel() {
        readyForPreviousLevel = false;
    }


    public void update(float delta) {

        // clamp velocity
        if(velocity.y > speed)
                velocity.y = speed;
        else if(velocity.y < -speed)
            velocity.y = -speed;

        // save old position
        float oldX = getX(), oldY = getY();
        boolean collisionX = false, collisionY = false, nextLevel = false, previousLevel = false;

        this.tileWidth = collisionLayer.getTileWidth();
        this.tileHeight = collisionLayer.getTileHeight();

        if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("chest")) {
            itemLayer.getCell((int) ((getX()) / tileWidth), (int) ((getY()) / tileHeight)).setTile(collisionLayer.getCell(1,0).getTile());
        }

        if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("door")) {
            itemLayer.getCell((int) ((getX()) / tileWidth), (int) ((getY()) / tileHeight)).setTile(collisionLayer.getCell(0,0).getTile());
            //collisionLayer.getCell((int) ((getX()) / tileWidth), (int) ((getY()) / tileHeight)).setTile(null);
        }

//        if (detectXCollision("chest") || detectYCollision("chest")) {
//            itemLayer.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) ((getY() + getHeight()) / tileHeight)).setTile(null);
//            collisionLayer.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) ((getY() + getHeight()) / tileHeight)).setTile(collisionLayer.getCell((int) (getX()   / tileWidth), (int) (getY()  / tileHeight)).getTile());
//
//
//        }

        // move on x
        setX(getX() + velocity.x * delta);

        collisionX = detectXCollision("wall");
        nextLevel = detectXCollision("nextlevel");
        previousLevel = detectXCollision("previouslevel");


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

        if (!previousLevel) {
            previousLevel = detectYCollision("previouslevel");
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

        if (previousLevel) {
            readyForPreviousLevel = true;
        }
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setXVelocity(float x) {
        velocity.x = x;
    }

    public void setYVelocity(float y) {
        velocity.y = y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getXPos() {
        return getX();
    }

    public float getYPos() {
        return getY();
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }

    public void setItemLayer(TiledMapTileLayer tileLayer) { this.itemLayer = tileLayer;}



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

    public void setMap(TiledMap map) {
        this.map = map;
        this.collisionLayer = ((TiledMapTileLayer) map.getLayers().get(0));
        this.itemLayer = ((TiledMapTileLayer) map.getLayers().get(7));
    }



}
