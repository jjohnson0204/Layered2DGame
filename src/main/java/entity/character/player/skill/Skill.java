package entity.character.player.skill;

public class Skill {
    private String name;
    private int level;
    private int experience;

    public Skill(String name) {
        this.name = name;
        this.level = 1;
        this.experience = 0;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public void levelUp() {
        level++;
    }

    public void gainExperience(int amount) {
        experience += amount;
        if (experience >= level * 100) {
            levelUp();
            experience = 0;
        }
    }
}