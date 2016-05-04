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
import edu.cvtc.capstone.screens.BattleScreen;
import edu.cvtc.capstone.screens.CharacterMenuScreen;

/**
 * Created by ruhk5 on 4/18/2016.
 */
public class Player extends Sprite {

    private TiledMap map;

    private Rock rock;

    /** the movement velocity */
    private Vector2 velocity = new Vector2();

    private boolean readyForNextLevel;
    private boolean readyForPreviousLevel;
    private boolean readyForBattle;
    private boolean readyForBoss;
    private boolean foundItem;
    private String itemMessage;

    private Vector2 previousPosition;

    private float speed = 150 * 2;
    private float distanceSinceLastBattle = 0;

    private TiledMapTileLayer collisionLayer;
    private TiledMapTileLayer itemLayer;

    private float tileWidth;
    private float tileHeight;

    public Player(Sprite sprite, Rock rock) {
        super(sprite);
        this.setX(10);
        this.setY(10);
        this.rock = rock;
    }

    @Override
    public void draw(Batch spriteBatch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch);
    }

    public boolean foundItem() {
        if (this.foundItem) {
            foundItem = false;
            return true;
        } else {
            return false;
        }
    }

    public boolean readyForBossBattle() {
        if (this.readyForBoss) {
            readyForBoss = false;
            return true;
        } else {
            return false;
        }
    }

    public String getMessage() {
        return itemMessage;
    }

    public boolean readyForNextLevel() {
        if (this.readyForNextLevel) {
            readyForNextLevel = false;
            return true;
        } else {
            return false;
        }
    }

    public boolean readyForBattle() {

        if (this.readyForBattle) {
            readyForBattle = false;
            return true;
        } else {
            return false;
        }
    }

    public void removeNextLevel() {
        readyForNextLevel = false;
    }

    public boolean readyForPreviousLevel() {
        if (this.readyForPreviousLevel) {
            readyForPreviousLevel = false;
            return true;
        } else {
            return false;
        }
    }

    public void removeBattle() {
        readyForBattle = false;
    }

    public void removePreviousLevel() {
        readyForPreviousLevel = false;
        //game.setScreen(new BattleScreen(game, this, new Rock(1, 2), 1));
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

        if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("item")) {

            if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("potion")) {

                itemLayer.getCell((int) ((getX()) / tileWidth), (int) ((getY()) / tileHeight)).setTile(null);
                collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).setTile(collisionLayer.getCell(0,0).getTile());
                foundItem = true;
                itemMessage = "You found a potion!";

                rock.addPotion();
            }

            if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("sword")) {

                itemLayer.getCell((int) ((getX()) / tileWidth), (int) ((getY()) / tileHeight)).setTile(null);

                collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).setTile(collisionLayer.getCell(0,0).getTile());
                rock.setSword("Iron Sword" , 2);
                foundItem = true;
                itemMessage = "You found an Iron Sword!";
                //readyForBattle = true;
            }

            if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("armor")) {

                itemLayer.getCell((int) ((getX()) / tileWidth), (int) ((getY()) / tileHeight)).setTile(null);
                collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).setTile(collisionLayer.getCell(0,0).getTile());
                rock.setArmor("Iron Armor", 2);
                foundItem = true;
                itemMessage = "You found Iron Armor!";

                rock.setSword("Iron Sword" , 2);

                //readyForBattle = true;
            }


        }

        if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("door")) {
            itemLayer.getCell((int) ((getX()) / tileWidth), (int) ((getY()) / tileHeight)).setTile(collisionLayer.getCell(0,0).getTile());

            //collisionLayer.getCell((int) ((getX()) / tileWidth), (int) ((getY()) / tileHeight)).setTile(null);
        }

        if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("boss")) {
            itemLayer.getCell((int) ((getX()) / tileWidth), (int) ((getY()) / tileHeight)).setTile(null);
            collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).setTile(collisionLayer.getCell(0,0).getTile());
            readyForBoss = true;
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

        distanceSinceLastBattle += (Math.sqrt(Math.pow((getX() - oldX), 2) + Math.pow((getY() - oldY), 2)  ) );
        if ( distanceSinceLastBattle >= 800 ) {
            readyForBattle = true;
            distanceSinceLastBattle = 0;
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

    public Rock getRock() {
        return this.rock;
    }



}
