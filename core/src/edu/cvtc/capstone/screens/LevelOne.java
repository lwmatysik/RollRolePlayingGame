package edu.cvtc.capstone.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import edu.cvtc.capstone.gameobjects.Player;

/**
 * Created by ruhk5 on 4/18/2016.
 */
public class LevelOne implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Game game;

    private Stage stage;
    private Skin skin;

    private Player player;

    private Music music;

    public LevelOne(Game game, Player player) {
        this.game = game;
        this.player = player;

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas("ui/uiskin.atlas"));

        map = new TmxMapLoader().load("maps/LevelOneFinal.tmx");

        renderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();

        player.setMap(map);

        player.setPosition(20 * player.getCollisionLayer().getTileWidth(), (player.getCollisionLayer().getHeight() - 25) * player.getCollisionLayer().getTileHeight());

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set((int)(player.getX() + player.getWidth() / 2), (int)(player.getY() + player.getHeight() / 2), 0);
        camera.zoom = 1.2f;
        camera.update();

        renderer.setView(camera);

        renderer.render(new int[] {1,2,3,4,6});

        renderer.getBatch().begin();

        player.draw(renderer.getBatch());
        renderer.getBatch().end();

        renderer.render(new int[] {5,7});

        if (player.readyForNextLevel()) {
            player.setVelocity(new Vector2(0,0));
            music.pause();
            game.setScreen(new LevelTwo(game, player, this));
        }

        if (player.readyForBattle()) {
            player.setVelocity(new Vector2(0,0));
            music.pause();
            game.setScreen(new BattleScreen(game, player, this, 1));

        }

        if (player.readyForBossBattle()) {
            player.setVelocity(new Vector2(0,0));
            music.pause();
            game.setScreen(new BattleScreen(game, player, this, 1, true));
        }

        if (player.foundItem()) {
            showMessage(player.getMessage());
        }

        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 2.5f;
        camera.viewportHeight = height / 2.5f;
    }

    @Override
    public void show() {

        player.setMap(map);

        music = Gdx.audio.newMusic(Gdx.files.internal("music/dungeon_theme.mp3"));

        music.setLooping(true);
        music.setVolume(0.66f);
        music.play();

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

    public void showMessage(String message) {
        final Table winTable = new Table(skin);
        winTable.setBackground(skin.getDrawable("default-rect"));
        winTable.setPosition(320, 614);
        winTable.setSize(660, 96);

        final Label winLabel = new Label(message, skin);
        winLabel.setFontScale(2f);
        winLabel.setAlignment(Align.center);

        winTable.add(winLabel).width(600).height(86).center();
        stage.addActor(winTable);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {

                for(Actor actor : stage.getActors()) {
                    actor.remove();
                }




            }
        }, 2);
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