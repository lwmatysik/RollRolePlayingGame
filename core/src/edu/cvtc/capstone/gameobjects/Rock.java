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
    private int levelUpCounter;

    private Sword sword;
    private Armor armor;

    public Rock() {
        this.characterLevel = 1;
        this.experiencePoints = 0;
        this.levelUpCounter = 0;
        this.attack = 1;
        this.defense = 1;
        this.maxHealth = 100;
        this.currentHealth = 100;
        this.numberOfPotionsInInventory = 1;
        this.armor = new Armor("Leather", 1);
        this.sword = new Sword("Wood", 1);
    }

    public void levelUp() {
        this.characterLevel += 1;
        this.attack += 1;
        this.defense += 1;
        this.maxHealth += 50;
        this.currentHealth = this.maxHealth;
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

    public int getLevelUpCounter() {
        return levelUpCounter;
    }

    public void setLevelUpCounter(int levelUpCounter) {
        this.levelUpCounter = levelUpCounter;
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
        return defense * armor.getDefenseModifier();
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
        if(numberOfPotionsInInventory > 0) {
            this.numberOfPotionsInInventory--;

            if (currentHealth + 50 > maxHealth) {
                this.currentHealth = maxHealth;
            } else {
                this.currentHealth += 50;
            }
        }
    }

    public int getNumberOfPotionsInInventory() {
        return numberOfPotionsInInventory;
    }
}
