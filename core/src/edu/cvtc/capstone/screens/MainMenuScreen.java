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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import edu.cvtc.capstone.gameobjects.Player;

/**
 * Created by tsweitzer on 4/14/16.
 */
public class MainMenuScreen implements Screen {

    private Game game;
    private Skin skin;
    private BitmapFont font;
    private Texture texture;
    private Stage stage;
    private SpriteBatch batch;
    private Player player;

    public MainMenuScreen(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    @Override
    public void show() {
        texture = new Texture(Gdx.files.internal("images/newBackground.jpg"));
        stage = new Stage();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
        font.getData().setScale(3);
        
        createBasicSkin();
        TextButton creditsGameButton = new TextButton("Credits", skin);
        TextButton newGameButton = new TextButton("New game", skin); // Use the initialized skin
        TextButton exitGameButton = new TextButton("Quit", skin);
        
        newGameButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , 450);
        creditsGameButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8, 300);
        exitGameButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8, 150);
        
        stage.addActor(newGameButton);
        stage.addActor(creditsGameButton);
        stage.addActor(exitGameButton);
        Gdx.input.setInputProcessor(stage);

        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelOne(game, player));
            }
        });
        
        creditsGameButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		game.setScreen(new CreditsScreen(game, player));
        	}
        });
        

        exitGameButton.addListener(new ClickListener() {
        	@Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
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
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(texture, 0, 0);
        font.draw(batch, "Roll Role Playing Game", 430, 600);
        batch.end();
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
        batch.dispose();
        stage.dispose();
        skin.dispose();
    }
}
