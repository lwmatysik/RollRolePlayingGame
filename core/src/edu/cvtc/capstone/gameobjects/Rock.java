package edu.cvtc.capstone.gameobjects;

/**
 * Created by Lance Matysik on 3/20/16.
 */

public class Rock extends DynamicGameObject {

    private int characterLevel;
    private int experiencePoints;
    private int attack;
    private int defense;
    private Sword sword;
    private Armor armor;

    public Rock(float x, float y, int characterLevel, int experiencePoints, int attack, int defense) {
        super(x, y);
        this.characterLevel = 1;
        this.experiencePoints = 0;
        this.attack = 1;
        this.defense = 1;
        this.armor = new Armor("Leather", 1);
        this.sword = new Sword("Wood", 1);
    }

    public void setCharacterLevel(int characterLevel) {
        this.characterLevel = characterLevel;
    }

    public int getCharacterLevel() {
        return characterLevel;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public void levelUp() {
        this.characterLevel += 1;
        this.attack += 1;
        this.defense += 1;
    }

}
