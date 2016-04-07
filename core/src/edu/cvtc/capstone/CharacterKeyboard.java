package edu.cvtc.capstone;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Lance Matysik on 3/25/16.
 */

public class CharacterKeyboard implements InputProcessor {

    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean movingUp = false;
    private boolean movingDown = false;

    private Body player;

    public CharacterKeyboard(Body player) {
        this.player = player;
    }

    public void handleHeldKey() {
        if (movingLeft) {
            player.setTransform(player.getPosition().x -= 1f, player.getPosition().y, player.getAngle());
        } else if (movingRight) {
            player.setTransform(player.getPosition().x += 1f, player.getPosition().y, player.getAngle());
        } else if (movingUp) {
            player.setTransform(player.getPosition().x, player.getPosition().y += 1f, player.getAngle());
        } else if (movingDown) {
            player.setTransform(player.getPosition().x, player.getPosition().y -= 1f, player.getAngle());
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                player.setTransform(player.getPosition().x -= 1f, player.getPosition().y, player.getAngle());
                movingLeft = true;
                break;
            case Input.Keys.RIGHT:
                player.setTransform(player.getPosition().x += 1f, player.getPosition().y, player.getAngle());
                movingRight = true;
                break;
            case Input.Keys.UP:
                player.setTransform(player.getPosition().x, player.getPosition().y += 1f, player.getAngle());
                movingUp = true;
                break;
            case Input.Keys.DOWN:
                player.setTransform(player.getPosition().x, player.getPosition().y -= 1f, player.getAngle());
                movingDown = true;
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