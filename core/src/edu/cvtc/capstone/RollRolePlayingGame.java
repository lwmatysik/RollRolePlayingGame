package edu.cvtc.capstone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class RollRolePlayingGame extends ApplicationAdapter implements InputProcessor {

    private SpriteBatch batch;

    private Rock rock;

    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean movingUp = false;
    private boolean movingDown = false;

    @Override
    public void create() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        Assets.load();

        batch = new SpriteBatch();

        rock = new Rock(width / 2 - Assets.rock.getWidth() / 2, height / 2 - Assets.rock.getHeight() / 2);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleHeldKey();

        batch.begin();
        batch.draw(Assets.rock, rock.position.x, rock.position.y);
        batch.end();
    }

    public void handleHeldKey() {
        if(movingLeft) {
            rock.position.set(rock.position.x -= 1f, rock.position.y);
        } else if(movingRight) {
            rock.position.set(rock.position.x += 1f, rock.position.y);
        } else if(movingUp) {
            rock.position.set(rock.position.x, rock.position.y += 1f);
        } else if(movingDown) {
            rock.position.set(rock.position.x, rock.position.y -= 1f);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        Assets.rock.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                rock.position.set(rock.position.x -= 1f, rock.position.y);
                movingLeft = true;
                break;
            case Input.Keys.RIGHT:
                rock.position.set(rock.position.x += 1f, rock.position.y);
                movingRight = true;
                break;
            case Input.Keys.UP:
                rock.position.set(rock.position.x, rock.position.y += 1f);
                movingUp = true;
                break;
            case Input.Keys.DOWN:
                rock.position.set(rock.position.x, rock.position.y -= 1f);
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
