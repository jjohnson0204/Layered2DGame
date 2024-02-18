package entity.object;

import entity.Entity;

import java.util.ArrayList;

public class Item {
    public ArrayList<Entity> inventory = new ArrayList<>();
    public boolean stackable = false;
    public final int maxInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public int price;
    public int knockBackPower = 0;
    public int amount = 1;
    public int lightRadius;
    protected int x, y, width, height;
    // Add methods that operate on these attributes
}
