package edu.cvtc.capstone.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import edu.cvtc.capstone.gameobjects.Rock;

/**
 * Created by Lance Matysik on 4/14/16.
 */

public class GameMenu implements Screen {

    private Game game;
    private Skin skin;
    private Stage stage;
    private List list;
    private ScrollPane scrollPane;
    private Table table;

    private String[] temporaryTestItemList;

    private Rock rock;

    public GameMenu(Game game, Rock rock) {
        this.game = game;
        this.rock = rock;
    }

    @Override
    public void show() {
        game.pause();

        createTestList();
        stage = new Stage();

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas("ui/uiskin.atlas"));

        table = new Table(skin);
        table.setFillParent(true);
        table.setBackground(skin.getDrawable("default-rect"));

        list = new List(skin);
        list.setItems(temporaryTestItemList);

        scrollPane = new ScrollPane(list, skin);
        Label characterLevel = new Label("Level: " + rock.getCharacterLevel(), skin);
        Label expPoints = new Label("Exp Points: " + rock.getExperiencePoints(), skin);
        Label health = new Label("Health: ", skin);
        Label sword = new Label("Sword: ", skin);
        Label armor = new Label("Armor: ", skin);
        Texture rockTexture = new Texture(Gdx.files.internal("images/rock_with_eyes.png"));
        Image rock = new Image(rockTexture);

        table.add(rock).pad(10).width(100).height(100).spaceRight(500);
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
                        game.setScreen(new RollRolePlayingGame(game));
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

        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    public void createTestList() {

        temporaryTestItemList = new String[20];

        for (int i = 0; i < 20; i++) {
            temporaryTestItemList[i] = "Item #" + (i + 1);
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
