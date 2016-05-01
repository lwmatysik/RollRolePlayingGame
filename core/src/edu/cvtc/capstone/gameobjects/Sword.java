package edu.cvtc.capstone.gameobjects;


/**
 * Created by Lance Matysik on 5/1/16.
 */
public class Sword {

    private String swordName;
    private int attackModifier;

    public Sword(String swordName, int attackModifier) {
        this.swordName = swordName;
        this.attackModifier = attackModifier;
    }

    public String getSwordName() {
        return swordName;
    }

    public void setSwordName(String swordName) {
        this.swordName = swordName;
    }

    public int getAttackModifier() {
        return attackModifier;
    }

    public void setAttackModifier(int attackModifier) {
        this.attackModifier = attackModifier;
    }

}
