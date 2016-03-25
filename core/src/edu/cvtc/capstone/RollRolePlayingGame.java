package edu.cvtc.capstone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class RollRolePlayingGame extends ApplicationAdapter {

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer otmr;

    private World world;
    private Keyboard keyboard;
    private Box2DDebugRenderer box2DDebugRenderer;
    private SpriteBatch spriteBatch;

    private Body player;

    private Rock rock;

    private Array<Body> bodies = new Array<Body>();

    @Override
    public void create() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        Assets.load();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width / 2, height / 2);

        world = new World(new Vector2(), true);
        box2DDebugRenderer = new Box2DDebugRenderer();

        rock = new Rock(width / 2 - Assets.rock.getWidth() / 2, height / 2 - Assets.rock.getHeight() / 2, 1, 0.5f, 0.3f);
        player = rock.create(Assets.rock, world);

        keyboard = new Keyboard(player);

        spriteBatch = new SpriteBatch();

        otmr = new OrthogonalTiledMapRenderer(Assets.map);

        Gdx.input.setInputProcessor(keyboard);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1 / 60f, 6, 2);

        keyboard.handleHeldKey();

        otmr.setView(camera);
        camera.position.set(player.getPosition().x, player.getPosition().y, 1f);
        camera.update();
        otmr.render();

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

        Assets.fanwoodText18.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 20);
        Assets.fanwoodText18.draw(spriteBatch, "PLAYER POS X: " + player.getPosition().x + "POS Y: " + player.getPosition().y, 1000, 20);

        spriteBatch.end();

        box2DDebugRenderer.render(world, camera.combined);
    }

    @Override
    public void dispose() {
        box2DDebugRenderer.dispose();
        world.dispose();
        Assets.rock.dispose();
        Assets.fanwoodText18.dispose();
        Assets.map.dispose();
        otmr.dispose();
    }

}