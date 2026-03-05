package nami.development.hamoni;

import nami.development.hamoni.engine.FormatterEngine;
import nami.development.hamoni.registry.*;
import net.minecraft.network.chat.Component;

public class HamoniFormatter {
    private final ColorRegistry colorRegistry = new ColorRegistry();
    private final DynamicColorRegistry dynamicColorRegistry = new DynamicColorRegistry();
    private final TextRegistry textRegistry = new TextRegistry();
    private final DynamicTextRegistry dynamicTextRegistry = new DynamicTextRegistry();
    private final FormatterEngine engine;

    private HamoniFormatter() {
        this.engine = new FormatterEngine(this);
    }

    public ColorRegistry colors() {
        return colorRegistry;
    }

    public DynamicColorRegistry dynamicColors() {
        return dynamicColorRegistry;
    }

    public TextRegistry text() {
        return textRegistry;
    }

    public DynamicTextRegistry dynamicText() {
        return dynamicTextRegistry;
    }

    public Component format(String input, boolean parseTextRegistry) {
        return engine.format(input, parseTextRegistry);
    }
}