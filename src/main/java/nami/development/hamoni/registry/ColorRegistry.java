package nami.development.hamoni.registry;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ColorRegistry {

    protected final Map<String, Integer> colors = new HashMap<>();

    public void register(String key, int rgb) {
        colors.put(key.toLowerCase(), rgb & 0xFFFFFF);
    }

    public void register(String key, Color color) {
        register(key, color.getRGB());
    }

    public Integer get(String key) {
        return colors.get(key.toLowerCase());
    }
}