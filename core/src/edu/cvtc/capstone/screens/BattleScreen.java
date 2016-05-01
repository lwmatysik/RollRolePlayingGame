package edu.cvtc.capstone.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.cvtc.capstone.gameobjects.Player;
import edu.cvtc.capstone.gameobjects.Rock;

/**
 * Created by Lance Matysik on 4/30/16.
 */
public class BattleScreen implements Screen {

    private Game game;
    private Player player;
    private Stage stage;
    private SpriteBatch spriteBatch;
    private int currentLevel;
    private Rock rock;

    public BattleScreen(Game game, Player player, int currentDungeonLevel, Rock rock) {
        this.game = game;
        this.player = player;
        this.currentLevel = currentDungeonLevel;
        this.rock = rock;
    }

    @Override
    public void show() {
        stage = new Stage();
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.end();

        stage.act();
        stage.draw();

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

    @Override
    public void dispose() {

    }
}


