package edu.cvtc.capstone.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import edu.cvtc.capstone.gameobjects.Player;

/**
 * Created by tsweitzer on 4/21/16.
 */

public class WinGameScreen implements Screen{
    private SpriteBatch batch;
    private Texture texture;
	private Stage stage;
	private Game game;
	private Player player;
	private Skin skin;
    
	public WinGameScreen(Game game) {
		this.game = game;
	}

	@Override
	public void show() {
		stage = new Stage();
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas("ui/uiskin.atlas"));

		final Music music = Gdx.audio.newMusic(Gdx.files.internal("music/game_win_music.ogg"));
		music.setVolume(0.68f);
		music.play();

		Gdx.input.setInputProcessor(stage);
		texture = new Texture(Gdx.files.internal("images/castle_background.png"));
	    Texture enemyTexture = new Texture(Gdx.files.internal("images/top_hat_monster.png"));
	    Texture rockTexture = new Texture(Gdx.files.internal("images/rock_with_eyes_andhat.png"));
	    Image rock = new Image(rockTexture);
	    rock.setPosition(900, 163);
	    
	    Image enemy = new Image(enemyTexture);
	    enemy.setPosition(300, 163);
	    
	    Label label = new Label("HAHA! You beat my dungeon!",skin);

	    label.setX(400);
		label.setY(200);
		label.setWidth(500);;
	    label.setHeight(200);
	    label.setFontScale(2);
	    
        TextButton theEndButton = new TextButton("The End", skin);
        theEndButton.setPosition(500, 400);
		theEndButton.setWidth(300);
		theEndButton.setHeight(100);
		
	    stage.addActor(label);
	    stage.addActor(theEndButton);
	    stage.addActor(rock);
	    stage.addActor(enemy);
	    
	    
	    theEndButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CreditsScreen(game, player));
				music.stop();
            }
        });
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
	    batch.begin();
	    batch.draw(texture, 0, 0);
	    batch.end();
	    stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		
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
		stage.dispose();
		skin.dispose();
	}

}
