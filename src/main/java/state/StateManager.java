package state;

import java.util.*;
import java.util.function.Consumer;

public class StateManager {
    public static class Pair<K, V> {
        public K key;
        public V value;
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    private static String currentState;
    public final static Map<String, List<Consumer<String>>> states = new HashMap<>();
    public final static Map<String, Object> data = new HashMap<>();
    private static Map<String, Runnable> listeners = new HashMap<>();
    public final static Queue<Pair<String, Consumer<String>>> offQueue = new LinkedList<>();
    public final static Queue<Pair<String, Consumer<String>>> onQueue = new LinkedList<>();
    public static boolean isIterating = false;

    public static void trigger (String state) {
        List<Consumer<String>> originalListeners = states.get(state);
        if (originalListeners != null) {
            List<Consumer<String>> listenersCopy = new ArrayList<>(originalListeners);
            for (Consumer<String> listener : listenersCopy) {
                listener.accept(currentState);
            }
        }
        currentState = state;
    }

    public static void on (String state, Consumer<String> listener) {
        List<Consumer<String>> listeners = states.computeIfAbsent(state, k -> new ArrayList<>());
        if(!isIterating)
            listeners.add(listener);
        else
            onQueue.add(new Pair<>(state, listener));
    }

    public static void off (String state, Consumer<String> listener) {
        List<Consumer<String>> listeners = states.get(state);
        if (listeners != null) {
            if(isIterating)
                offQueue.add(new Pair<>(state, listener));
            else
                listeners.remove(listener);
        }
    }

    public static void off (String state) {
        states.remove(state);
    }

    public static void setState(String state) {
        currentState = state;
    }

    public static String getState() {
        return currentState;
    }

    public static void set(String key, Object value) {
        data.put(key, value);
    }
    public static Object get(String key) {
        return data.get(key);
    }

    public static void resolveOffQueue() {
        while(!offQueue.isEmpty() && !isIterating) {
            Pair<String, Consumer<String>> state = offQueue.poll();
            List<Consumer<String>> listeners = states.get(state.key);
            if (listeners != null) {
                listeners.remove(state.value);
            }
        }
    }
    public static void resolveOnQueue() {
        while(!onQueue.isEmpty() && !isIterating) {
            Pair<String, Consumer<String>> state = onQueue.poll();
            List<Consumer<String>> listeners = states.get(state.key);
            if (listeners != null) {
                listeners.add(state.value);
            }
        }
    }

    //Testing
    public static void on(String event, Runnable listener) {
        listeners.put(event, listener);
    }

    public static void off(String event, Runnable listener) {
        listeners.remove(event, listener);
    }

    public static boolean hasListener(String event) {
        return listeners.containsKey(event);
    }

}