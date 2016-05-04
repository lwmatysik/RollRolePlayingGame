package edu.cvtc.capstone.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import edu.cvtc.capstone.Assets;
import edu.cvtc.capstone.gameobjects.Player;
import edu.cvtc.capstone.gameobjects.Rock;
import edu.cvtc.capstone.gameobjects.TopHatMonster;

import static edu.cvtc.capstone.Assets.rock;

/**
 * Created by ruhk5 on 4/18/2016.
 */
public class LevelOne implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Game game;

    private Player player;

    public LevelOne(Game game, Player player) {
        this.game = game;
        this.player = player;

        //map = new TmxMapLoader().load("maps/level1.tmx");
        map = new TmxMapLoader().load("maps/LevelOneFinal.tmx");

        renderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();

        player.setMap(map);

        //player.setCollisionLayer((TiledMapTileLayer) map.getLayers().get(0));
        //player.setItemLayer((TiledMapTileLayer) map.getLayers().get(7));


        //player.setPosition(11 * player.getCollisionLayer().getTileWidth(), (player.getCollisionLayer().getHeight() - 14) * player.getCollisionLayer().getTileHeight());
        player.setPosition(20 * player.getCollisionLayer().getTileWidth(), (player.getCollisionLayer().getHeight() - 25) * player.getCollisionLayer().getTileHeight());

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set((int)(player.getX() + player.getWidth() / 2), (int)(player.getY() + player.getHeight() / 2), 0);
        //camera.zoom = 1.9f;
        camera.zoom = 1.2f;
        camera.update();

        renderer.setView(camera);

        //renderer.render(new int[] {1,2,3,4,5});
        renderer.render(new int[] {1,2,3,4,6});

        renderer.getBatch().begin();

        player.draw(renderer.getBatch());
        renderer.getBatch().end();

        renderer.render(new int[] {5,7});

        //terrible way of doing this, must rethink
        if (player.nextLevel()) {
            player.removeNextLevel();
            player.setVelocity(new Vector2(0,0));
            game.setScreen(new LevelTwo(game, player, this));
        }

        if (player.readyForBattle()) {
            player.removeBattle();
            player.setVelocity(new Vector2(0,0));
            game.setScreen(new BattleScreen(game, player, player.getRock(), 1));
        }


    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 2.5f;
        camera.viewportHeight = height / 2.5f;
    }

    @Override
    public void show() {



        //player = new Player(new Sprite(new Texture("images/rock_with_eyes_pixelated.png")), (TiledMapTileLayer) map.getLayers().get(0));

        player.setMap(map);

        //player.setCollisionLayer((TiledMapTileLayer) map.getLayers().get(0));
        //player.setItemLayer((TiledMapTileLayer) map.getLayers().get(7));
        //player.setPosition(11 * player.getCollisionLayer().getTileWidth(), (player.getCollisionLayer().getHeight() - 14) * player.getCollisionLayer().getTileHeight());




        //Gdx.input.setInputProcessor(player);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.UP:
                    case Input.Keys.W:
                        player.setYVelocity(player.getSpeed());
                        break;
                    case Input.Keys.DOWN:
                    case Input.Keys.S:
                        player.setYVelocity(-player.getSpeed());
                        break;
                    case Input.Keys.LEFT:
                    case Input.Keys.A:
                        player.setXVelocity(-player.getSpeed());
                        break;
                    case Input.Keys.RIGHT:
                    case Input.Keys.D:
                        player.setXVelocity(player.getSpeed());
                        break;
                    case Input.Keys.ESCAPE:
                        callCharacterMenu();
                        break;
                    case Input.Keys.SHIFT_LEFT:
                        player.setSpeed(10);
                    default:
                        break;
                }
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {

                switch(keycode) {
                    case Input.Keys.A:
                    case Input.Keys.D:
                    case Input.Keys.LEFT:
                    case Input.Keys.RIGHT:
                        player.setXVelocity(0);
                        break;
                    case Input.Keys.W:
                    case Input.Keys.S:
                    case Input.Keys.UP:
                    case Input.Keys.DOWN:
                        player.setYVelocity(0);
                        break;
                    case Input.Keys.SHIFT_LEFT:
                        player.setSpeed(600);
                    default:
                        break;
                }
                return true;
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
        });

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void callCharacterMenu() {
        game.setScreen(new CharacterMenuScreen(game, player.getRock(), this));
    }

    @Override
    public void hide() {
        //dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        player.getTexture().dispose();
    }

}