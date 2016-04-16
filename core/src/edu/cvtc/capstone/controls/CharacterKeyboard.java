package edu.cvtc.capstone.controls;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.Body;
import edu.cvtc.capstone.gameobjects.Rock;
import edu.cvtc.capstone.screens.CharacterMenuScreen;

/**
 * Created by Lance Matysik on 3/25/16.
 */

public class CharacterKeyboard implements InputProcessor {

    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean movingUp = false;
    private boolean movingDown = false;

    private Body rockBody;

    private Rock rock;

    private Game game;

    public CharacterKeyboard(Body rockBody, Rock rock, Game game) {
        this.rockBody = rockBody;
        this.rock = rock;
        this.game = game;
    }

    public void handleHeldKey() {
        if (movingLeft) {
            rockBody.setTransform(rockBody.getPosition().x -= 1f, rockBody.getPosition().y, rockBody.getAngle());
        } else if (movingRight) {
            rockBody.setTransform(rockBody.getPosition().x += 1f, rockBody.getPosition().y, rockBody.getAngle());
        } else if (movingUp) {
            rockBody.setTransform(rockBody.getPosition().x, rockBody.getPosition().y += 1f, rockBody.getAngle());
        } else if (movingDown) {
            rockBody.setTransform(rockBody.getPosition().x, rockBody.getPosition().y -= 1f, rockBody.getAngle());
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                rockBody.setTransform(rockBody.getPosition().x -= 1f, rockBody.getPosition().y, rockBody.getAngle());
                movingLeft = true;
                break;
            case Input.Keys.RIGHT:
                rockBody.setTransform(rockBody.getPosition().x += 1f, rockBody.getPosition().y, rockBody.getAngle());
                movingRight = true;
                break;
            case Input.Keys.UP:
                rockBody.setTransform(rockBody.getPosition().x, rockBody.getPosition().y += 1f, rockBody.getAngle());
                movingUp = true;
                break;
            case Input.Keys.DOWN:
                rockBody.setTransform(rockBody.getPosition().x, rockBody.getPosition().y -= 1f, rockBody.getAngle());
                movingDown = true;
                break;
            case Input.Keys.ESCAPE:
                game.setScreen(new CharacterMenuScreen(game, rock));
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                movingLeft = false;
                break;
            case Input.Keys.RIGHT:
                movingRight = false;
                break;
            case Input.Keys.UP:
                movingUp = false;
                break;
            case Input.Keys.DOWN:
                movingDown = false;
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}