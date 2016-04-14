package edu.cvtc.capstone;

import com.badlogic.gdx.Game;
import edu.cvtc.capstone.screens.MainMenuScreen;

/**
 * Created by lancematysik on 4/14/16.
 */
public class RollRolePlayingApplication extends Game {

    private Game game;

    public RollRolePlayingApplication() {
        game = this;
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void create() {
        setScreen(new MainMenuScreen(game));
    }
}
