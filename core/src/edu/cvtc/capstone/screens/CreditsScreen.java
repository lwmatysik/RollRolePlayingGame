package edu.cvtc.capstone.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import edu.cvtc.capstone.gameobjects.Player;

/**
 * Created by tsweitzer on 4/21/16.
 */
public class CreditsScreen implements Screen {

	private Game game;
	private Player player;
	private Label developerNames;
	private Label dateCreated;
	private Skin skin;
	private Stage stage;
	private Table table;
	
	public CreditsScreen(Game game, Player player) {
		this.game = game;
		this.player = player;
	}

	@Override
	public void show() {
	    //yourBitmapFontName = new BitmapFont();
		//developerNames = "Created by: Teia Sweitzer, Lance Matysik, Steven Dahlstrom";
		game.pause();

		stage = new Stage();

		skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas("ui/uiskin.atlas"));

		table = new Table(skin);
		table.setFillParent(true);
		table.setBackground(skin.getDrawable("default-rect"));
		
        developerNames = new Label("Developers: Teia Sweitzer\n Lance Matysik  \n Steven Dahlstrom  \n", skin);
		dateCreated = new Label("Spring 2016 IT Capstone Class", skin);
		developerNames.setFontScale(2);
		dateCreated.setFontScale(2);
		
		table.add(developerNames).pad(10).height(50).spaceLeft(450).spaceRight(500);
		table.row();
		table.add(dateCreated).pad(10).height(50).spaceLeft(450).spaceRight(500);
		table.row();
		
		TextButton backButton = new TextButton("Back", skin);
		backButton.setPosition(620, 250);
		
		stage.addActor(table);
		stage.addActor(backButton);
		Gdx.input.setInputProcessor(stage);
		
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				 game.setScreen(new MainMenuScreen(game, player));
			}
		});
	
	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
