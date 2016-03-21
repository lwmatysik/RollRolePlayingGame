package edu.cvtc.capstone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class RollRolePlayingGame extends ApplicationAdapter implements InputProcessor {

    private OrthographicCamera camera;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private SpriteBatch spriteBatch;

    private Body player;

    private Rock rock;

    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean movingUp = false;
    private boolean movingDown = false;

    private Array<Body> bodies = new Array<Body>();

    @Override
    public void create() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        Assets.load();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width / 2, height / 2);

        world = new World(new Vector2(), false);
        box2DDebugRenderer = new Box2DDebugRenderer();

        rock = new Rock(width / 2 - Assets.rock.getWidth() / 2, height / 2 - Assets.rock.getHeight() / 2, 1, 0.5f, 0.3f);
        player = rock.create(Assets.rock, world);

        spriteBatch = new SpriteBatch();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1 / 60f, 6, 2);

        handleHeldKey();

        camera.position.set(player.getPosition().x, player.getPosition().y, 1f);
        camera.update();

        spriteBatch.begin();
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x, body.getPosition().y);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(spriteBatch);
            }
        }
        spriteBatch.end();

        box2DDebugRenderer.render(world, camera.combined);
    }

    public void handleHeldKey() {
        if (movingLeft) {
            player.setTransform(player.getPosition().x -= 1f, player.getPosition().y, player.getAngle());
        } else if (movingRight) {
            player.setTransform(player.getPosition().x += 1f, player.getPosition().y, player.getAngle());
        } else if (movingUp) {
            player.setTransform(player.getPosition().x, player.getPosition().y += 1f, player.getAngle());
        } else if (movingDown) {
            player.setTransform(player.getPosition().x, player.getPosition().y -= 1f, player.getAngle());
        }
    }

    @Override
    public void dispose() {
        box2DDebugRenderer.dispose();
        world.dispose();
        Assets.rock.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                player.setTransform(player.getPosition().x -= 1f, player.getPosition().y, player.getAngle());
                movingLeft = true;
                break;
            case Input.Keys.RIGHT:
                player.setTransform(player.getPosition().x += 1f, player.getPosition().y, player.getAngle());
                movingRight = true;
                break;
            case Input.Keys.UP:
                player.setTransform(player.getPosition().x, player.getPosition().y += 1f, player.getAngle());
                movingUp = true;
                break;
            case Input.Keys.DOWN:
                player.setTransform(player.getPosition().x, player.getPosition().y -= 1f, player.getAngle());
                movingDown = true;
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                movingLeft = false;
                break;
            case Input.Keys.RIGHT:
                movingRight = false;
                break;
            case Input.Keys.UP:
                movingUp = false;
                break;
            case Input.Keys.DOWN:
                movingDown = false;
                break;
            default:
                break;
        }
        return false;
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
