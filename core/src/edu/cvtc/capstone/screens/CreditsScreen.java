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
	private Label developerLabel;
	private Label dateCreated;
	private Label classCreated;
	private Skin skin;
	private Stage stage;
	private Table table;
	
	public CreditsScreen(Game game) {
		this.game = game;
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
		
		dateCreated = new Label("CVTC Spring 2016", skin);
		developerLabel = new Label("Developers:", skin);
        developerNames = new Label("      Teia Sweitzer\n      Lance Matysik\n      Steven Dahlstrom", skin);
		classCreated = new Label("IT-SD Capstone", skin);
		dateCreated.setFontScale(4);
		classCreated.setFontScale(4);
		developerLabel.setFontScale(3);
		developerNames.setFontScale(2);
		
		
		
		table.add(dateCreated).padBottom(15).height(80).center().top();
		table.row();
		table.add(classCreated).padBottom(15).height(50).center();
		table.row();
		table.add(developerLabel).pad(10).height(50).center();
		table.row();
		table.add(developerNames).pad(50).height(90).center();
		table.row();
		
		TextButton backButton = new TextButton("Back", skin);
		table.add(backButton).pad(10).height(50).center().width(100f);
		
		stage.addActor(table);
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
