package edu.cvtc.capstone.gameobjects;

import java.util.ArrayList;

/**
 * Created by Lance Matysik on 5/4/16.
 */

public class BossMonster {

    private int currentHealth;
    private int maxHealth;
    private int attackModifier;
    private int defenseModifier;
    private String bossMonsterImageString;

    private ArrayList<String> bossMonsterImageList;

    public BossMonster(int currentLevel) {
        this.currentHealth = currentLevel * 100;
        this.maxHealth = currentLevel * 100;
        this.attackModifier = currentLevel * 1;
        this.defenseModifier = currentLevel * 1;

        bossMonsterImageList = new ArrayList<String>();

        bossMonsterImageList.add(0, "images/StoneDragon.png");
        bossMonsterImageList.add(1, "images/BlackDragon.png");
        bossMonsterImageList.add(2, "images/WaterDragon.png");
        bossMonsterImageList.add(3, "images/GoldDragon.png");
        bossMonsterImageList.add(4, "images/RedDragon.png");

        bossMonsterImageString = bossMonsterImageList.get(currentLevel - 1);
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
        return bossMonsterImageString;
    }

    public int getAttackModifier() {
        return attackModifier;
    }

    public int getDefenseModifier() {
        return defenseModifier;
    }

    @Override
    public String toString() {
        return bossMonsterImageString.substring(7, bossMonsterImageString.lastIndexOf('.')).toUpperCase();
    }


}
