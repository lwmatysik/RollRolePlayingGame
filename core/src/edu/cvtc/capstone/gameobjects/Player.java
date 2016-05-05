package edu.cvtc.capstone.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
/**
 * Created by ruhk5 on 4/18/2016.
 */
public class Player extends Sprite {

    private TiledMap map;

    private Rock rock;

    private Vector2 velocity = new Vector2();

    private boolean readyForNextLevel;
    private boolean readyForPreviousLevel;
    private boolean readyForBattle;
    private boolean readyForBoss;
    private boolean foundItem;
    private String itemMessage;

    private Music equip;
    private Music squish;
    private Music smash;
    private Music potion;

    private float speed = 500;
    private float distanceSinceLastBattle = 0;
    private float distanceSinceLastSquish = 0;

    private TiledMapTileLayer collisionLayer;
    private TiledMapTileLayer itemLayer;

    private float tileWidth;
    private float tileHeight;

    public Player(Sprite sprite, Rock rock) {
        super(sprite);
        this.setX(10);
        this.setY(10);
        this.rock = rock;

        initSounds();

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

    public boolean readyForPreviousLevel() {
        if (this.readyForPreviousLevel) {
            readyForPreviousLevel = false;
            return true;
        } else {
            return false;
        }
    }

    public void update(float delta) {

        if(velocity.y > speed)
                velocity.y = speed;
        else if(velocity.y < -speed)
            velocity.y = -speed;

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
                potion.play();
            }

            if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("sword")) {

                itemLayer.getCell((int) ((getX()) / tileWidth), (int) ((getY()) / tileHeight)).setTile(null);

                if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("iron")) {
                    rock.setSword("Iron Sword", 2000);
                    foundItem = true;
                    itemMessage = "You found an Iron Sword!";
                    equip.play();
                } else if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("gold")) {
                    rock.setSword("Gold Sword", 3);
                    foundItem = true;
                    itemMessage = "You found a Gold Sword!";
                    equip.play();
                } else if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("diamond")) {
                    rock.setSword("Diamond Sword", 4);
                    foundItem = true;
                    itemMessage = "You found a Diamond Sword!";
                    equip.play();
                } else if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("molten")) {
                    rock.setSword("Magma Sword", 6);
                    foundItem = true;
                    itemMessage = "You found the Magma Sword!";
                    equip.play();
                }

                collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).setTile(collisionLayer.getCell(0,0).getTile());

            }

            if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("armor")) {

                itemLayer.getCell((int) ((getX()) / tileWidth), (int) ((getY()) / tileHeight)).setTile(null);

                if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("iron")) {
                    rock.setArmor("Iron Armor", 5);
                    foundItem = true;
                    itemMessage = "You found Iron Armor!";
                    equip.play();
                } else if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("gold")) {
                    rock.setArmor("Gold Armor", 15);
                    foundItem = true;
                    itemMessage = "You found Gold Armor!";
                    equip.play();
                } else if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("diamond")) {
                    rock.setArmor("Diamond Armor", 30);
                    foundItem = true;
                    itemMessage = "You found Diamond Armor!";
                    equip.play();
                } else if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("molten")) {
                    rock.setArmor("Magma Armor", 50);
                    foundItem = true;
                    itemMessage = "You found the Magma Armor!";
                    equip.play();
                }
                    collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).setTile(collisionLayer.getCell(0,0).getTile());

            }


        }

        if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("door")) {
            itemLayer.getCell((int) ((getX()) / tileWidth), (int) ((getY()) / tileHeight)).setTile(collisionLayer.getCell(0,0).getTile());

            collisionLayer.getCell((int) ((getX()) / tileWidth), (int) ((getY()) / tileHeight)).setTile(collisionLayer.getCell(0,0).getTile());
            smash.play();
        }

        if (collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).getTile().getProperties().containsKey("boss")) {
            itemLayer.getCell((int) ((getX()) / tileWidth), (int) ((getY()) / tileHeight)).setTile(null);
            collisionLayer.getCell((int) (getX()  / tileWidth), (int) (getY()  / tileHeight)).setTile(collisionLayer.getCell(0,0).getTile());
            readyForBoss = true;
        }

        setX(getX() + velocity.x * delta);

        collisionX = detectXCollision("wall");
        nextLevel = detectXCollision("nextlevel");
        previousLevel = detectXCollision("previouslevel");

        if(collisionX) {
            setX(oldX);
            velocity.x = 0;
        }

        setY(getY() + velocity.y * delta);

        collisionY = detectYCollision("wall");
        if (!nextLevel) {
            nextLevel = detectYCollision("nextlevel");
        }

        if (!previousLevel) {
            previousLevel = detectYCollision("previouslevel");
        }

        if(collisionY) {
            setY(oldY);
            velocity.y = 0;
        }

        if (nextLevel) {
            readyForNextLevel = true;
        }

        if (previousLevel) {
            readyForPreviousLevel = true;
        }

        distanceSinceLastBattle += (Math.sqrt(Math.pow((getX() - oldX), 2) + Math.pow((getY() - oldY), 2)  ) );
        distanceSinceLastSquish += (Math.sqrt(Math.pow((getX() - oldX), 2) + Math.pow((getY() - oldY), 2)  ) );

        if ( distanceSinceLastBattle >= 1000000 ) {
            readyForBattle = true;
            distanceSinceLastBattle = 0;
        }

        if (distanceSinceLastSquish >= 48 ) {
            squish.play();
            distanceSinceLastSquish = 0;
        }
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

    private void initSounds() {
        equip = Gdx.audio.newMusic(Gdx.files.internal("sounds/pickup.ogg"));
        equip.setLooping(false);
        equip.setVolume(0.66f);

        squish = Gdx.audio.newMusic(Gdx.files.internal("sounds/stomp.ogg"));
        squish.setLooping(false);
        squish.setVolume(0.66f);

        smash = Gdx.audio.newMusic(Gdx.files.internal("sounds/smash.ogg"));
        smash.setLooping(false);
        smash.setVolume(0.66f);

        potion = Gdx.audio.newMusic(Gdx.files.internal("sounds/potion.ogg"));
        potion.setLooping(false);
        potion.setVolume(0.66f);
    }



}
