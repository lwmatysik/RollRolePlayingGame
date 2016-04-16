package edu.cvtc.capstone.gameobjects;
/**
 * Created by Lance Matysik on 3/20/16.
 */

public class Rock extends DynamicGameObject {

    private int characterLevel;
    private int experiencePoints;

    public Rock(float x, float y) {
        super(x, y);
        this.characterLevel = 0;
        this.experiencePoints = 0;
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

}
