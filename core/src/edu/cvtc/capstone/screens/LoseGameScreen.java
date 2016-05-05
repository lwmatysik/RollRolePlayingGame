package edu.cvtc.capstone.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
 * Created by tsweitzer on 5/2/16.
 */

public class LoseGameScreen implements Screen{
	private SpriteBatch batch;
    private Texture texture;
    private Stage stage;
    private Game game;
    private Player player;
	private Skin skin;
		    
	public LoseGameScreen(Game game) {
		this.game = game;
	}

	@Override
	public void show() {
		stage = new Stage();
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas("ui/uiskin.atlas"));
		Gdx.input.setInputProcessor(stage);
		texture = new Texture(Gdx.files.internal("images/castle_background.png"));
	    Texture enemyTexture = new Texture(Gdx.files.internal("images/top_hat_monster.png"));
	    Texture rockTexture = new Texture(Gdx.files.internal("images/rock_with_eyes.png"));
	    Image rock = new Image(rockTexture);
	    rock.setPosition(900, 170);
			    
		Image enemy = new Image(enemyTexture);
	    enemy.setPosition(300, 170);
			    
	    Label label = new Label("Game Over! You have failed me for the last time!",skin);
	    label.setX(400);
		label.setY(200);
		label.setWidth(500);;
	    label.setHeight(200);
	    label.setFontScale(2);
				
	    
	    createBasicSkin();
        TextButton tryAgainButton = new TextButton("Try Again", skin);
        tryAgainButton.setPosition(500, 400);
        
	    stage.addActor(label);
	    stage.addActor(tryAgainButton);
	    stage.addActor(rock);
	    stage.addActor(enemy);
	    
	    tryAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game, player));
            }
        });
		
	}
	
	private void createBasicSkin() {
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background",new Texture(pixmap));

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

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
