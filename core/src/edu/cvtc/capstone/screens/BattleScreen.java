package edu.cvtc.capstone.screens;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.cvtc.capstone.gameobjects.Player;
import edu.cvtc.capstone.gameobjects.RandomMonster;
import edu.cvtc.capstone.gameobjects.Rock;
import edu.cvtc.capstone.tweenaccessors.ImageAccessor;

import java.util.Random;

/**
 * Created by Lance Matysik on 4/30/16.
 */
public class BattleScreen implements Screen {

    private Game game;
    private Player player;
    private Stage stage;
    private Skin skin;
    private SpriteBatch spriteBatch;
    private Table table;

    private int currentDungeonLevel;
    private Rock rock;

    private boolean bossFight = false;
    private RandomMonster randomMonster;

    private Image randomMonsterImage;
    private Image rockImage;

    private TweenManager tweenManager;

    public BattleScreen(Game game, Player player, Rock rock, int currentDungeonLevel) {
        this.game = game;
        this.player = player;
        this.currentDungeonLevel = currentDungeonLevel;
        this.rock = rock;
    }

    public BattleScreen(Game game, Player player, Rock rock, int currentDungeonLevel, boolean bossFight) {
        this.game = game;
        this.player = player;
        this.currentDungeonLevel = currentDungeonLevel;
        this.rock = rock;
        this.bossFight = true;
    }

    @Override
    public void show() {
        stage = new Stage();
        spriteBatch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas("ui/uiskin.atlas"));

        makeHero();
        makeEnemy();
        
        table = new Table(skin);
        table.setBackground(skin.getDrawable("default-rect"));
        table.setPosition(0, 0);
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 3.8f);

        TextButton attackButton = new TextButton("Attack", skin);
        TextButton itemButton = new TextButton("Item", skin);

        Label rockHealth = new Label("Health: " + rock.getCurrentHealth() + " / " + rock.getMaxHealth(), skin);
        rockHealth.setFontScale(1.5f);

        Label monsterName = new Label(randomMonster.toString(), skin);
        monsterName.setFontScale(1.5f);

        table.add(attackButton).width(200f).height(60f).pad(5f).top().left();
        table.row();
        table.add(itemButton).width(200f).height(60f).pad(5f).left().spaceRight(300f);
        table.add(rockHealth).width(200f).height(60f).center().spaceRight(300f);
        table.add(monsterName).width(200f).height(60f).right();

        Gdx.input.setInputProcessor(stage);

        stage.addActor(table);

        Texture battleBgTexture = new Texture(Gdx.files.internal("images/battlebg.png"));
        Image battleBackground = new Image(battleBgTexture);
        battleBackground.setPosition(0, Gdx.graphics.getHeight() / 3.8f);
        battleBackground.setFillParent(true);

        stage.addActor(battleBackground);

        randomMonsterImage.setPosition(Gdx.graphics.getWidth() / 6f, Gdx.graphics.getHeight() / 3f);
        randomMonsterImage.scaleBy(2.25f);
        stage.addActor(randomMonsterImage);

        rockImage.setPosition(Gdx.graphics.getWidth() - 200f, Gdx.graphics.getHeight() / 3f);
        rockImage.scaleBy(0.90f);
        stage.addActor(rockImage);

        tweenManager = new TweenManager();
        Tween.registerAccessor(Image.class, new ImageAccessor());

        attackButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                battleLogic();
            }
        });

    }

    private void battleLogic() {
        Random random = new Random();

        if (!bossFight) {
            Gdx.input.setInputProcessor(null);

            Timeline.createSequence()
                    .push(Tween.to(rockImage, ImageAccessor.X, 0.4f).target(Gdx.graphics.getWidth() - 300f, Gdx.graphics.getHeight() / 3f).delay(0.50f))
                    .push(Tween.to(randomMonsterImage, ImageAccessor.ALPHA, 0.10f).repeatYoyo(6, 0.10f))
                    .push(Tween.to(randomMonsterImage, ImageAccessor.ALPHA, 0f).target(1))
                    .push(Tween.to(rockImage, ImageAccessor.X, 0.4f).target(Gdx.graphics.getWidth() - 200f, Gdx.graphics.getHeight() / 3f).delay(0.50f))
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int i, BaseTween<?> baseTween) {
                            Gdx.input.setInputProcessor(stage);
                        }
                    })
                    .start(tweenManager);

        } else {
            // bossfight
        }
    }

    private void makeHero() {
        Texture rockTexture = new Texture(Gdx.files.internal("images/rock_with_eyes.png"));
        rockImage = new Image(rockTexture);
    }

    private void makeEnemy() {
        if (!bossFight) {
            randomMonster = new RandomMonster(currentDungeonLevel);
            Texture randomMonsterTexture = new Texture(Gdx.files.internal(randomMonster.getFileString()));
            randomMonsterImage = new Image(randomMonsterTexture);
        } else {
            // boss logic
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        spriteBatch.begin();
        spriteBatch.end();

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
        spriteBatch.dispose();;
    }
}


