package edu.cvtc.capstone.gameobjects;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Lance Matysik on 5/1/16.
 */
public class RandomMonster {

    private int currentHealth;
    private int maxHealth;
    private int attackModifier;
    private int defenseModifier;
    private int currentLevel;
    private ArrayList<String> monsterImageFileList;
    private String randomMonsterImageString;

    public RandomMonster(int currentLevel) {
        this.currentLevel = currentLevel;

        this.currentHealth = currentLevel * 50;
        this.maxHealth = currentLevel * 50;
        this.attackModifier = currentLevel * 1;
        this.defenseModifier = currentLevel * 1;

        populateMonsterImageFileList();
        Random random = new Random();
        randomMonsterImageString = monsterImageFileList.get(random.nextInt(9));
    }

    private void populateMonsterImageFileList() {
        monsterImageFileList = new ArrayList<String>();
        monsterImageFileList.add(0, "images/DemonicJester.png");
        monsterImageFileList.add(1, "images/Drgonmjeur.png");
        monsterImageFileList.add(2, "images/Foimores.png");
        monsterImageFileList.add(3, "images/Goblin.png");
        monsterImageFileList.add(4, "images/MagnusDeimos.png");
        monsterImageFileList.add(5, "images/Manchot.png");
        monsterImageFileList.add(6, "images/Pazuzu upgen.png");
        monsterImageFileList.add(7, "images/Quadrapen.png");
        monsterImageFileList.add(8, "images/yomotsuikusa.png");
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public String getFileString() {
        return randomMonsterImageString;
    }

    @Override
    public String toString() {
        return randomMonsterImageString.substring(7, randomMonsterImageString.lastIndexOf('.')).toUpperCase();
    }
}
