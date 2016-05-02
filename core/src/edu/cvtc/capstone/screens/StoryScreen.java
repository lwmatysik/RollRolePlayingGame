package edu.cvtc.capstone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class StoryScreen implements Screen{
    private BitmapFont font;
    private SpriteBatch batch;
    private Texture texture;
    
    
	@Override
	public void show() {
		// TODO Auto-generated method stub
		texture = new Texture(Gdx.files.internal("images/castle_background.png"));
		font = new BitmapFont();
	    font.setColor(Color.RED);
	    font.getData().setScale(3);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
	    batch.begin();
	    batch.draw(texture, 0, 0);
	    font.draw(batch, "Roll Role Playing Game", 430, 600);
	    batch.end();
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
		
	}

	
}
