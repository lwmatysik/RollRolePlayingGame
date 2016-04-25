package edu.cvtc.capstone.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import edu.cvtc.capstone.gameobjects.Player;

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
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set((int)(player.getX() + player.getWidth() / 2), (int)(player.getY() + player.getHeight() / 2), 0);
        camera.zoom = 1.9f;
        camera.update();

        renderer.setView(camera);

        renderer.render(new int[] {1,2,3,4,5});

        renderer.getBatch().begin();
        player.draw(renderer.getBatch());
        renderer.getBatch().end();

        //renderer.render(new int[] {1});

        //terrible way of doing this, must rethink
        if (player.nextLevel()) {
            player.changeLevel(game);
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 2.5f;
        camera.viewportHeight = height / 2.5f;
    }

    @Override
    public void show() {
        map = new TmxMapLoader().load("maps/level1.tmx");

        renderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();

        //player = new Player(new Sprite(new Texture("images/rock_with_eyes_pixelated.png")), (TiledMapTileLayer) map.getLayers().get(0));
        player.setCollisionLayer((TiledMapTileLayer) map.getLayers().get(0));
        player.setPosition(11 * player.getCollisionLayer().getTileWidth(), (player.getCollisionLayer().getHeight() - 14) * player.getCollisionLayer().getTileHeight());

        Gdx.input.setInputProcessor(player);
    }

    @Override
    public void hide() {
        dispose();
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