package edu.cvtc.capstone.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.cvtc.capstone.gameobjects.Rock;

/**
 * Created by Lance Matysik on 4/14/16.
 */

public class CharacterMenuScreen implements Screen {

    private Game game;
    private Skin skin;
    private Stage stage;
    private List list;
    private ScrollPane scrollPane;
    private Table table;

    private Screen previousScreen;

    private String[] potionList;

    private Rock rock;

    public CharacterMenuScreen(Game game, Rock rock, Screen screen) {
        this.game = game;
        this.rock = rock;
        this.previousScreen = screen;
    }

    @Override
    public void show() {
        stage = new Stage();

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas("ui/uiskin.atlas"));

        table = new Table(skin);
        table.setFillParent(true);
        table.setBackground(skin.getDrawable("default-rect"));

        createPotionList();

        scrollPane = new ScrollPane(list, skin);

        Label characterLevel = new Label("Level: " + rock.getCharacterLevel(), skin);
        Label expPoints = new Label("Exp Points: " + rock.getExperiencePoints(), skin);
        Label health = new Label("Health: " + rock.getCurrentHealth() + " / " + rock.getMaxHealth(), skin);
        Label sword = new Label("Sword: " + rock.getSwordName(), skin);
        Label armor = new Label("Armor: " + rock.getArmorName(), skin);

        Texture rockTexture = new Texture(Gdx.files.internal("images/rock_with_eyes.png"));
        Image rockImage = new Image(rockTexture);

        table.add(rockImage).pad(10).width(100).height(100).spaceRight(500);
        table.add(scrollPane).expandY().pad(10).width(500).height(500).top().right();
        table.row();
        table.add(characterLevel).pad(10).width(150).height(100).left().spaceRight(500);
        table.row();
        table.add(expPoints).pad(10).width(150).height(100).left().spaceRight(500);
        table.row();
        table.add(health).pad(10).width(150).height(100).left().spaceRight(500);
        table.row();
        table.add(sword).pad(10).width(150).height(100).left().spaceRight(500);
        table.row();
        table.add(armor).pad(10).width(150).height(100).left().spaceRight(500);

        stage.addActor(table);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.ESCAPE:
                        game.setScreen(previousScreen);
                        break;
                    default:
                        break;
                }
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
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
        });
        inputMultiplexer.addProcessor(stage);

        scrollPane.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (rock.getCurrentHealth() != rock.getMaxHealth()) {
                    rock.usePotion();
                    show();
                }
            }
        });

        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    public void createPotionList() {

        list = new List(skin);

        if (rock.getNumberOfPotionsInInventory() > 0) {
            potionList = new String[rock.getNumberOfPotionsInInventory()];

            for (int i = 0; i < potionList.length; i++) {
                potionList[i] = "Potion";
            }

            list.setItems(potionList);

        } else {
            list.clearItems();
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
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
        stage.dispose();
        skin.dispose();
    }
}
