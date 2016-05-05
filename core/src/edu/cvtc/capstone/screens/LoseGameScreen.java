package edu.cvtc.capstone.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import edu.cvtc.capstone.Assets;
import edu.cvtc.capstone.gameobjects.Player;
import edu.cvtc.capstone.gameobjects.Rock;

/**
 * Created by tsweitzer on 5/2/16.
 */

public class LoseGameScreen implements Screen{
	private SpriteBatch batch;
    private Texture texture;
    private Stage stage;
	private Skin skin;
	private Game game;
	private Player player;

	public LoseGameScreen(Game game) {
		this.game = game;
	}

	@Override
	public void show() {

		final Player player = new Player(new Sprite(Assets.rock), new Rock());

		final Music music = Gdx.audio.newMusic(Gdx.files.internal("music/gameover.mp3"));
		music.setVolume(0.61f);
		music.play();

		stage = new Stage();
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas("ui/uiskin.atlas"));

		Gdx.input.setInputProcessor(stage);

		texture = new Texture(Gdx.files.internal("images/castle_background.png"));
	    Texture enemyTexture = new Texture(Gdx.files.internal("images/top_hat_monster.png"));
	    Texture rockTexture = new Texture(Gdx.files.internal("images/rock_with_eyes_dead.png"));

		final Image rock = new Image(rockTexture);
	    rock.setPosition(900, 163);
			    
		Image enemy = new Image(enemyTexture);
	    enemy.setPosition(300, 163);
			    
	    Label label = new Label("You have failed me for the last time!",skin);
	    label.setX(400);
		label.setY(200);
		label.setWidth(500);
	    label.setHeight(200);
	    label.setFontScale(2);

        TextButton tryAgainButton = new TextButton("Try Again", skin);
		tryAgainButton.setPosition(Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2);
        tryAgainButton.setHeight(100);
		tryAgainButton.setWidth(300);

	    stage.addActor(label);
	    stage.addActor(tryAgainButton);
	    stage.addActor(rock);
	    stage.addActor(enemy);
	    
	    tryAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                music.setLooping(false);
				music.stop();
				game.setScreen(new MainMenuScreen(game, player));
            }
        });
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
	    batch.begin();
	    batch.draw(texture, 0, 0);
	    batch.end();
	    stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		skin.dispose();
		
	}
   

}
