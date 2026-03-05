package nami.development.hamoni.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TextRegistry {

    private final Map<String, Supplier<String>> registry = new HashMap<>();

    public void register(String key, Supplier<String> supplier) {
        registry.put(key.toLowerCase(), supplier);
    }

    public Supplier<String> get(String key) {
        return registry.get(key.toLowerCase());
    }
}