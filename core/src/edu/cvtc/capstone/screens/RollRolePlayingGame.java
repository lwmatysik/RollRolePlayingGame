package edu.cvtc.capstone.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import edu.cvtc.capstone.Assets;
import edu.cvtc.capstone.controls.CharacterKeyboard;
import edu.cvtc.capstone.gameobjects.Rock;
import edu.cvtc.capstone.gameobjects.TopHatMonster;

public class RollRolePlayingGame implements Screen {

    private Game game;

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer otmr;

    private World world;
    private CharacterKeyboard characterKeyboard;
    private Box2DDebugRenderer box2DDebugRenderer;
    private SpriteBatch spriteBatch;

    private Body rockBody;

    private Rock rock;
    private TopHatMonster topHatMonster;

    private Array<Body> bodies = new Array<Body>();
    
    private boolean colliding;

    public RollRolePlayingGame(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        Assets.load();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width / 2, height / 2);

        world = new World(new Vector2(), true);
        box2DDebugRenderer = new Box2DDebugRenderer();

        rock = new Rock(width / 2 - Assets.rock.getWidth() / 2, height / 2 - Assets.rock.getHeight() / 2);
        topHatMonster = new TopHatMonster(500f, 300f);
        topHatMonster.create(Assets.topHatMonster, world);

        rockBody = rock.create(Assets.rock, world);
        
        colliding = false;

        characterKeyboard = new CharacterKeyboard(rockBody, rock, game);

        spriteBatch = new SpriteBatch();

        otmr = new OrthogonalTiledMapRenderer(Assets.map);

        createContactListener();

        Gdx.input.setInputProcessor(characterKeyboard);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1 / 60f, 6, 2);

        characterKeyboard.handleHeldKey();

        otmr.setView(camera);
        camera.position.set(rockBody.getPosition().x, rockBody.getPosition().y, 0);
        camera.setToOrtho(false);
        camera.update();
        otmr.render();

        createContactListener();

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

        if (colliding) {
            Assets.fanwoodText18.draw(spriteBatch, "COLLISION DETECTED!", 20, 680);
        } else {
            Assets.fanwoodText18.draw(spriteBatch, "", 20, 680);
        }

        Assets.fanwoodText18.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 20);
        Assets.fanwoodText18.draw(spriteBatch, "PLAYER POS X: " + rockBody.getPosition().x + "POS Y: " + rockBody.getPosition().y, 1000, 20);

        spriteBatch.end();

        box2DDebugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    private void createContactListener() {

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                colliding = true;
                System.out.println("COLLISION");
            }

            @Override
            public void endContact(Contact contact) {
                colliding = false;
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }
    
    @Override
    public void dispose() {
        box2DDebugRenderer.dispose();
        world.dispose();
        Assets.rock.dispose();
        Assets.topHatMonster.dispose();
        Assets.fanwoodText18.dispose();
        Assets.map.dispose();
        otmr.dispose();
    }

}