package entity.character;

import entity.Entity;
import entity.character.player.skill.Skill;
import entity.object.equipment.Equipment;
import main.GamePanel;
import state.StateManager;

import java.awt.*;
import java.util.Map;
import java.util.List;

public class Character extends Entity {

    // Class Imports
    private GamePanel gp;
    private Graphics2D g2;

    // Attributes
    private String name;
    private int defaultSpeed;
    private double speed;
    private int maxLife;
    private int life;
    private int maxMana;
    private int mana;
    private int ammo;
    private int level;
    private int strength;
    private int intelligence;
    private int charisma;
    private int luck;
    private int dexterity;
    private int attack;
    private int defense;
    private int exp;
    private int nextLevelExp;
    private int coin;
    private int motion1_duration;
    private int motion2_duration;
    private final boolean alive = true;

    //List
    private Map<String, Equipment> equipment;
    private List<Skill> skills;

    // Constructor
    public Character() {
        super("character");
    }

    public Character(String name) {
        super("character");
        this.gp = (GamePanel) StateManager.get("gp");
        this.g2 = gp.g2;
    }

    public Character(GamePanel gp, Graphics2D g2) {
        super("character", "character:0");
        this.gp = gp;
        this.g2 = g2;
    }

    public void start() {
        // Implement start logic here
    }

    public void stop() {
        // Implement stop logic here
    }

    public void attack(Character target) {
        int damage = this.strength; // Calculate damage based on the character's strength
        target.setLife(target.getLife() - damage); // Subtract the damage from the target's life
    }

    public void defend(int damage) {
        // Reduce damage based on the character's defense
        int reducedDamage = damage - this.defense;
        // Ensure that damage does not go below 0
        reducedDamage = Math.max(reducedDamage, 0);
        // Subtract the reduced damage from the character's life
        this.life -= reducedDamage;
    }

    public void heal(int amount) {
        // Increase the character's life by the specified amount
        this.life += amount;
        // Ensure that life does not go above maxLife
        this.life = Math.min(this.life, this.maxLife);
    }

    public void levelUp() {
        // Increase the character's level
        this.level++;
        // Improve the character's attributes
        this.strength += 10;
        this.defense += 10;
        this.maxLife += 20;
        // Restore the character's life to maxLife
        this.life = this.maxLife;
    }

    public void gainExp(int amount) {
        // Increase the character's experience points
        this.exp += amount;
        // Check if the character has enough experience points to level up
        if (this.exp >= this.nextLevelExp) {
            this.levelUp();
            // Set the experience points for the next level
            this.nextLevelExp *= 2;
        }
    }

    public boolean isAlive() {
        // Check if the character is still alive
        return this.life > 0;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLife() {
        return this.life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getLevel() {
        return this.level;
    }

    public int getDefaultSpeed() {
        return defaultSpeed;
    }

    public void setDefaultSpeed(int defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getAttack() {
        return attack;
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

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getNextLevelExp() {
        return nextLevelExp;
    }

    public void setNextLevelExp(int nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getMotion1_duration() {
        return motion1_duration;
    }

    public void setMotion1_duration(int motion1_duration) {
        this.motion1_duration = motion1_duration;
    }

    public int getMotion2_duration() {
        return motion2_duration;
    }

    public void setMotion2_duration(int motion2_duration) {
        this.motion2_duration = motion2_duration;
    }

    public Map<String, Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(Map<String, Equipment> equipment) {
        this.equipment = equipment;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    // Add other getter and setter methods for the rest of the attributes...
}