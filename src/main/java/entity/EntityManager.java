package entity;


import entity.character.Character;
import entity.character.enemy.Enemy;
import entity.character.npc.NPC;
import entity.character.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EntityManager {

    public static class Pair<K, V> {
        public K key;
        public V value;
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private static String currentEntity;
    private static String type;
    public final static Map<String, List<Consumer<String>>> entities = new HashMap<>();
    public final static Map<String, Object> data = new HashMap<>();
    private static final Map<String, Runnable> listeners = new HashMap<>();
    public final static Map<String, List<Character>> characters = new HashMap<>();

    public EntityManager() {
        type = "Player";
        List<Character> characters = EntityManager.getCharacters(type);
        for (Character character : characters) {
            // Check if the character is an instance of Player
            if (character instanceof Player player) {
                // Cast the character to Player and modify its properties
                player.setLife(100);
            }
        }
        type = "NPC"; // replace with the type you want
        for (Character character : characters) {
            // Check if the character is an instance of NPC
            if (character instanceof NPC npc) {
                // Cast the character to NPC and modify its properties
                npc.setDialog("New dialog");
            }
        }

        type = "Enemy"; // replace with the type you want
        characters = EntityManager.getCharacters(type);
        for (Character character : characters) {
            // Check if the character is an instance of Enemy
            if (character instanceof Enemy enemy) {
                // Cast the character to Enemy and modify its properties
                enemy.setDamage(100);
            }
        }
        // Create a new character
        entity.character.player.Player Mage = new entity.character.player.Player("Mage");
        entity.character.player.Player Hunter = new entity.character.player.Player("Hunter");
        entity.character.player.Player Warrior = new entity.character.player.Player("Warrior");
        entity.character.player.Player Rogue = new entity.character.player.Player("Rogue");
        entity.character.player.Player Fighter = new entity.character.player.Player("Fighter");

        // Add the new character to the EntityManager
        EntityManager.addCharacter("Player", Mage);
        EntityManager.addCharacter("Player", Hunter);
        EntityManager.addCharacter("Player", Warrior);
        EntityManager.addCharacter("Player", Rogue);
        EntityManager.addCharacter("Player", Fighter);

        // Create a new NPC
        entity.character.npc.NPC WiseMan = new entity.character.npc.NPC("WiseMan");
        entity.character.npc.NPC Merchant = new entity.character.npc.NPC("Merchant");
        entity.character.npc.NPC AirSpirit = new entity.character.npc.NPC("AirSpirit");
        entity.character.npc.NPC FireSpirit = new entity.character.npc.NPC("FireSpirit");
        entity.character.npc.NPC ThunderSpirit = new entity.character.npc.NPC("ThunderSpirit");
        entity.character.npc.NPC WaterSpirit = new entity.character.npc.NPC("WaterSpirit");

        // Add the new NPC to the EntityManager
        EntityManager.addCharacter("NPC", WiseMan);
        EntityManager.addCharacter("NPC", Merchant);
        EntityManager.addCharacter("NPC", AirSpirit);
        EntityManager.addCharacter("NPC", FireSpirit);
        EntityManager.addCharacter("NPC", ThunderSpirit);

        type = "Player";
        List<Character> players = EntityManager.getCharacters(type);
        for (Character character : players) {
            System.out.println(character.getName());
        }
        type = "NPC";
        List<Character> npcs = EntityManager.getCharacters(type);
        for (Character character : npcs) {
            System.out.println(character.getName());
        }
        type = "Enemy";
        List<Character> enemies = EntityManager.getCharacters(type);
        for (Character character : enemies) {
            System.out.println(character.getName());
        }
    }

    public static void addCharacter(String type, Character character) {
        if (!characters.containsKey(type)) {
            characters.put(type, new ArrayList<>());
        }
        characters.get(type).add(character);
    }

    public static void removeCharacter(String type, int index) {
        List<Character> charactersOfType = characters.get(type);
        if (charactersOfType != null && index >= 0 && index < charactersOfType.size()) {
            charactersOfType.remove(index);
        }
    }

    public static boolean characterExists(String type, String name) {
        List<Character> charactersOfType = characters.get(type);
        if (charactersOfType != null) {
            for (Character character : charactersOfType) {
                if (character.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static Character getCharacter(String type, String name) {
        List<Character> charactersOfType = characters.get(type);
        if (charactersOfType != null) {
            for (Character character : charactersOfType) {
                if (character.getName().equals(name)) {
                    return character;
                }
            }
        }
        return null;
    }
    public static void updateCharacter(String type, String name, Consumer<Character> updater) {
        List<Character> charactersOfType = characters.get(type);
        if (charactersOfType != null) {
            for (Character character : charactersOfType) {
                if (character.getName().equals(name)) {
                    updater.accept(character);
                }
            }
        }
    }

    public List<Character> getAliveCharacters() {
        return characters.values().stream()
                .flatMap(List::stream)
                .filter(Character::isAlive)
                .collect(Collectors.toList());
    }

    public List<Character> getDeadCharacters() {
        return characters.values().stream()
                .flatMap(List::stream)
                .filter(character -> !character.isAlive())
                .collect(Collectors.toList());
    }

    public List<Character> getCharactersByLevel(int level) {
        return characters.values().stream()
                .flatMap(List::stream)
                .filter(character -> character.getLevel() == level)
                .collect(Collectors.toList());
    }

    public List<Character> sortCharactersByLife() {
        return characters.values().stream()
                .flatMap(List::stream)
                .sorted((character1, character2) -> character2.getLife() - character1.getLife())
                .collect(Collectors.toList());
    }

    public List<Character> sortCharactersByLevel() {
        return characters.values().stream()
                .flatMap(List::stream)
                .sorted((character1, character2) -> character2.getLevel() - character1.getLevel())
                .collect(Collectors.toList());
    }

    public static List<Character> getCharacters(String type) {
        return characters.get(type);
    }

    public static void trigger(String entity) {
        currentEntity = entity;
        if (entities.containsKey(entity)) {
            for (Consumer<String> callback : entities.get(entity)) {
                callback.accept(currentEntity);
            }
        }
        if (listeners.containsKey(entity)) {
            listeners.get(entity).run();
        }
    }

    public static void on(String entity, Consumer<String> callback) {
        if (!entities.containsKey(entity)) {
            entities.put(entity, new ArrayList<>());
        }
        entities.get(entity).add(callback);
    }

    public static void on(String entity, Runnable callback) {
        listeners.put(entity, callback);
    }

    public static void off(String entity, Consumer<String> callback) {
        if (entities.containsKey(entity)) {
            entities.get(entity).remove(callback);
        }
    }

    public static void off(String entity, Runnable callback) {
        listeners.remove(entity);
    }

    public static void set(String key, Object value) {
        data.put(key, value);
    }

    public static String getEntity() {
        return currentEntity;
    }

    public static Object get(String key) {
        return data.get(key);
    }

    public static void removeData(String key) {
        data.remove(key);
    }

    public static void clearData() {
        data.clear();
    }

    public static void clearListeners() {
        listeners.clear();
    }

    public static void clearEntities() {
        entities.clear();
    }

    public static void clearAll() {
        clearData();
        clearListeners();
        clearEntities();
    }

}