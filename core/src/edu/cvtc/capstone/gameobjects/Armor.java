package edu.cvtc.capstone.gameobjects;

/**
 * Created by Lance Matysik on 5/1/16.
 */
public class Armor {

    private String armorName;
    private int defenseModifier;

    public Armor(String armorName, int defenseModifier) {
        this.armorName = armorName;
        this.defenseModifier = defenseModifier;
    }

    public int getDefenseModifier() {
        return defenseModifier;
    }

    public void setDefenseModifier(int defenseModifier) {
        this.defenseModifier = defenseModifier;
    }

    public String getArmorName() {
        return armorName;
    }

    public void setArmorName(String armorName) {
        this.armorName = armorName;
    }
}
