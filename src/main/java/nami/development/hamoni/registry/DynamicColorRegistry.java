package nami.development.hamoni.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntSupplier;

public class DynamicColorRegistry {

    private final Map<String, IntSupplier> registry = new HashMap<>();

    public void register(String key, IntSupplier supplier) {
        registry.put(key.toLowerCase(), supplier);
    }

    public IntSupplier get(String key) {
        return registry.get(key.toLowerCase());
    }
}