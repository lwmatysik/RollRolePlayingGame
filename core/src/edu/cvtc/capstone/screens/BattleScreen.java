package edu.cvtc.capstone.screens;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import edu.cvtc.capstone.Assets;
import edu.cvtc.capstone.gameobjects.BossMonster;
import edu.cvtc.capstone.gameobjects.Player;
import edu.cvtc.capstone.gameobjects.RandomMonster;
import edu.cvtc.capstone.gameobjects.Rock;
import edu.cvtc.capstone.tweenaccessors.ImageAccessor;
import edu.cvtc.capstone.tweenaccessors.LabelAccessor;

import java.util.Random;

/**
 * Created by Lance Matysik on 4/30/16.
 */
public class BattleScreen implements Screen {

    private Game game;
    private Player player;
    private Screen previousScreen;
    private Stage stage;
    private Skin skin;
    private SpriteBatch spriteBatch;
    private Table table;
    private Music music;

    private int currentDungeonLevel;
    private Rock rock;

    private boolean bossFight = false;
    private RandomMonster randomMonster;
    private BossMonster bossMonster;

    protected Image bossMonsterImage;
    protected Image randomMonsterImage;
    protected Image rockImage;
    private Label rockHealth;

    private TweenManager tweenManager;

    private String damageToMonster = "";
    private String damageToRock = "";

    public BattleScreen(Game game, Player player, Screen screen, int currentDungeonLevel) {
        this.game = game;
        this.player = player;
        this.currentDungeonLevel = currentDungeonLevel;
        this.rock = player.getRock();
        this.previousScreen = screen;
    }

    public BattleScreen(Game game, Player player, Screen screen, int currentDungeonLevel, boolean bossFight) {
        this.game = game;
        this.player = player;
        this.currentDungeonLevel = currentDungeonLevel;
        this.rock = player.getRock();
        this.bossFight = true;
        this.previousScreen = screen;
    }

    @Override
    public void show() {
        stage = new Stage();
        spriteBatch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas("ui/uiskin.atlas"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music/battle_theme.mp3"));

        makeHero();
        makeEnemy();

        music.setLooping(true);
        music.setVolume(0.61f);
        music.play();

        table = new Table(skin);
        table.setBackground(skin.getDrawable("default-rect"));
        table.setPosition(0, 0);
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 3.8f);

        TextButton attackButton = new TextButton("Attack", skin);
        final TextButton potionsButton = new TextButton("Potions", skin);

        rockHealth = new Label("Health: " + rock.getCurrentHealth() + " / " + rock.getMaxHealth(), skin);
        rockHealth.setFontScale(1.5f);

        Label monsterName = new Label("", skin);

        if (!bossFight) {
            monsterName.setText(randomMonster.toString());
        } else {
            monsterName.setText(bossMonster.toString());
        }

        monsterName.setFontScale(1.5f);

        table.add(attackButton).width(200f).height(60f).pad(5f).top().left();
        table.row();
        table.add(potionsButton).width(200f).height(60f).pad(5f).left().spaceRight(300f);
        table.add(rockHealth).width(200f).height(60f).center().spaceRight(300f);
        table.add(monsterName).width(200f).height(60f).right();

        Gdx.input.setInputProcessor(stage);

        stage.addActor(table);

        Texture battleBgTexture = new Texture(Gdx.files.internal("images/battlebg.png"));
        Image battleBackground = new Image(battleBgTexture);
        battleBackground.setPosition(0, Gdx.graphics.getHeight() / 3.8f);
        battleBackground.setFillParent(true);

        stage.addActor(battleBackground);

        if (!bossFight) {
            randomMonsterImage.setPosition(Gdx.graphics.getWidth() / 6f, Gdx.graphics.getHeight() / 3f);
            randomMonsterImage.scaleBy(2.25f);
            stage.addActor(randomMonsterImage);
        } else {
            bossMonsterImage.setPosition(Gdx.graphics.getWidth() / 6f, Gdx.graphics.getHeight() / 3f);
            bossMonsterImage.scaleBy(2.25f);
            stage.addActor(bossMonsterImage);
        }

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

        potionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (rock.getNumberOfPotionsInInventory() > 0) {
                    rock.usePotion();

                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/potion.ogg"));
                    sound.play(0.9f);

                    rockHealth.setText("Health: " + rock.getCurrentHealth() + " / " + rock.getMaxHealth());

                    if (rock.getNumberOfPotionsInInventory() > 0) {
                        potionsButton.setText("Potions");
                    } else {
                        potionsButton.setText("No potions left!");
                        potionsButton.clearListeners();
                    }

                } else {
                    potionsButton.setText("No potions left!");
                    potionsButton.clearListeners();
                }
            }
        });

    }

    private void battleLogic() {
        Random critical = new Random();

        if (!bossFight) {
            Gdx.input.setInputProcessor(null);

            final Integer rockDamagesMonster = rock.getAttack() * (critical.nextInt(12) + 12) - randomMonster.getDefenseModifier();
            final Integer monsterDamagesRock = randomMonster.getAttackModifier() * (critical.nextInt(12) + 12) - rock.getDefense();

            randomMonster.setCurrentHealth(randomMonster.getCurrentHealth() - rockDamagesMonster);
            rock.setCurrentHealth(rock.getCurrentHealth() - monsterDamagesRock);

            Timeline.createSequence()
                    .push(Tween.to(rockImage, ImageAccessor.X, 0.4f).target(Gdx.graphics.getWidth() - 300f, Gdx.graphics.getHeight() / 3f).delay(0.50f))
                    .push(Tween.to(randomMonsterImage, ImageAccessor.ALPHA, 0.10f).repeatYoyo(6, 0.10f))
                    .push(Tween.to(randomMonsterImage, ImageAccessor.ALPHA, 0f).target(1))
                    .push(Tween.to(rockImage, ImageAccessor.X, 0.4f).target(Gdx.graphics.getWidth() - 200f, Gdx.graphics.getHeight() / 3f).delay(0.50f))
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int i, BaseTween<?> baseTween) {

                            if (rock.getCurrentHealth() > 0) {

                                if (randomMonster.getCurrentHealth() < 1) {
                                    playerWins();
                                } else {
                                    Gdx.input.setInputProcessor(stage);
                                }
                            } else {
                                music.stop();
                                game.setScreen(new LoseGameScreen(game));
                            }
                        }
                    })
                    .start(tweenManager);

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/smash.ogg"));
                    sound.play(0.9f);
                    damageToMonster = rockDamagesMonster.toString();
                    damageToRock = monsterDamagesRock.toString();
                    rockHealth.setText("Health: " + rock.getCurrentHealth() + " / " + rock.getMaxHealth());
                }
            }, 2);

        } else {
            Gdx.input.setInputProcessor(null);

            final Integer rockDamagesMonster = rock.getAttack() * (critical.nextInt(12) + 12) - bossMonster.getDefenseModifier();
            final Integer monsterDamagesRock = bossMonster.getAttackModifier() * (critical.nextInt(12) + 12) - rock.getDefense();

            bossMonster.setCurrentHealth(bossMonster.getCurrentHealth() - rockDamagesMonster);
            rock.setCurrentHealth(rock.getCurrentHealth() - monsterDamagesRock);

            Timeline.createSequence()
                    .push(Tween.to(rockImage, ImageAccessor.X, 0.4f).target(Gdx.graphics.getWidth() - 300f, Gdx.graphics.getHeight() / 3f).delay(0.50f))
                    .push(Tween.to(bossMonsterImage, ImageAccessor.ALPHA, 0.10f).repeatYoyo(6, 0.10f))
                    .push(Tween.to(bossMonsterImage, ImageAccessor.ALPHA, 0f).target(1))
                    .push(Tween.to(rockImage, ImageAccessor.X, 0.4f).target(Gdx.graphics.getWidth() - 200f, Gdx.graphics.getHeight() / 3f).delay(0.50f))
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int i, BaseTween<?> baseTween) {

                            if (rock.getCurrentHealth() > 0) {

                                if (bossMonster.getCurrentHealth() < 1) {
                                    playerWins();
                                } else {
                                    Gdx.input.setInputProcessor(stage);
                                }
                            } else {
                                game.setScreen(new LoseGameScreen(game));
                            }
                        }
                    })
                    .start(tweenManager);

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/smash.ogg"));
                    sound.play(0.9f);
                    damageToMonster = rockDamagesMonster.toString();
                    damageToRock = monsterDamagesRock.toString();
                    rockHealth.setText("Health: " + rock.getCurrentHealth() + " / " + rock.getMaxHealth());
                }
            }, 2);
        }
    }

    private void playerWins() {

        if (!bossFight) {
            rock.setExperiencePoints(rock.getExperiencePoints() + 100);
            rock.setLevelUpCounter(rock.getLevelUpCounter() + 100);
        } else {
            rock.setExperiencePoints(rock.getExperiencePoints() + 250);
            rock.setLevelUpCounter(rock.getLevelUpCounter() + 250);
        }

        Table winTable = new Table(skin);
        winTable.setBackground(skin.getDrawable("default-rect"));
        winTable.setPosition(320, 614);
        winTable.setSize(660, 96);

        final Label winLabel = new Label("You win.", skin);
        winLabel.setFontScale(2f);
        winLabel.setAlignment(Align.center);

        winTable.add(winLabel).width(600).height(86).center();
        stage.addActor(winTable);

        music.stop();
        music = Gdx.audio.newMusic(Gdx.files.internal("music/victory_jingle.ogg"));
        music.setLooping(false);
        music.setVolume(0.71f);
        music.play();

        Tween.registerAccessor(Label.class, new LabelAccessor());

        if (rock.getLevelUpCounter() == 500) {

            rock.setLevelUpCounter(0);
            rock.levelUp();

            Timeline.createSequence()
                    .push(Tween.to(winLabel, LabelAccessor.ALPHA, 0.7f).target(0))
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int i, BaseTween<?> baseTween) {
                            winLabel.setFontScale(1.25f);
                            winLabel.setText("Rock's new ATK = " + rock.getAttack() + ", DEF = " + rock.getDefense() + ", Max Health = " + rock.getMaxHealth() + ".");
                        }
                    }).delay(1.5f)
                    .push(Tween.to(winLabel, LabelAccessor.ALPHA, 0.7f).target(1))
                    .start(tweenManager);

        } else {

            Timeline.createSequence()
                    .push(Tween.to(winLabel, LabelAccessor.ALPHA, 0.7f).target(0))
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int i, BaseTween<?> baseTween) {
                            if (!bossFight) {
                                winLabel.setText("Rock gains 100 Exp.");
                            } else {
                                winLabel.setText("Rock gains 250 Exp.");
                            }
                        }
                    })
                    .push(Tween.to(winLabel, LabelAccessor.ALPHA, 1.5f).target(1))
                    .start(tweenManager);
        }

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(previousScreen);
            }
        }, 10);

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
            bossMonster = new BossMonster(currentDungeonLevel);
            Texture bossMonsterTexture = new Texture(Gdx.files.internal(bossMonster.getFileString()));
            bossMonsterImage = new Image(bossMonsterTexture);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        stage.act(delta);
        stage.draw();

        spriteBatch.begin();
        Assets.fanwoodText42White.draw(spriteBatch, damageToMonster, Gdx.graphics.getWidth() / 6f + 122, Gdx.graphics.getHeight() / 3f - 8);
        Assets.fanwoodText42White.draw(spriteBatch, damageToRock, Gdx.graphics.getWidth() - 140, Gdx.graphics.getHeight() / 3f - 8);
        spriteBatch.end();

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
        spriteBatch.dispose();
        music.dispose();
    }
}
