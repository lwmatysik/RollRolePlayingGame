package edu.cvtc.capstone.gameobjects;

/**
 * Created by Lance Matysik on 3/20/16.
 */

public class Rock {

    private int characterLevel;
    private int experiencePoints;
    private int attack;
    private int defense;
    private int maxHealth;
    private int currentHealth;
    private int numberOfPotionsInInventory;

    private Sword sword;
    private Armor armor;

    public Rock() {

        this.characterLevel = 1;
        this.experiencePoints = 0;
        this.attack = 1;
        this.defense = 1;
        this.maxHealth = 100;
        this.currentHealth = 50;
        this.numberOfPotionsInInventory = 1;
        this.armor = new Armor("Leather", 1);
        this.sword = new Sword("Wood", 1);
    }

    public void levelUp() {
        this.characterLevel += 1;
        this.attack += 1;
        this.defense += 1;
        this.maxHealth += 100;
    }

    public int getCharacterLevel() {
        return characterLevel;
    }

    public void setCharacterLevel(int characterLevel) {
        this.characterLevel = characterLevel;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public int getAttack() {
        return attack * sword.getAttackModifier();
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public String getSwordName() {
        return sword.getSwordName();
    }

    public void setSword(String swordName, int attackModifier) {
        this.sword.setSwordName(swordName);
        this.sword.setAttackModifier(attackModifier);
    }

    public String getArmorName() {
        return armor.getArmorName();
    }

    public void setArmor(String armorName, int defenseModifier) {
        this.armor.setArmorName(armorName);
        this.armor.setDefenseModifier(defenseModifier);
    }

    public void addPotion() {
        this.numberOfPotionsInInventory++;
    }

    public void usePotion() {
        if(this.numberOfPotionsInInventory > 0) {
            this.numberOfPotionsInInventory--;
            if (this.maxHealth + 50 > this.maxHealth) {
                this.currentHealth = this.maxHealth;
            } else {
                maxHealth += 50;
            }
        }
    }

    public int getNumberOfPotionsInInventory() {
        return this.numberOfPotionsInInventory;
    }
}
