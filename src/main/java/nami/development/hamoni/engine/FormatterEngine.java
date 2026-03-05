package nami.development.hamoni.engine;

import nami.development.hamoni.HamoniFormatter;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.*;

import java.util.*;

public class FormatterEngine {
    private static final Map<String, ChatFormatting> FORMATTING_CACHE = new HashMap<>();

    static {
        for (ChatFormatting f : ChatFormatting.values()) {
            if (f.getName() != null) {
                FORMATTING_CACHE.put(f.getName().toLowerCase(), f);
            }
        }
    }

    private final HamoniFormatter api;

    public FormatterEngine(HamoniFormatter api) {
        this.api = api;
    }

    public Component format(String input, boolean parseTextRegistry) {
        if (input == null || input.isEmpty()) return Component.empty();
        MutableComponent result = Component.empty();
        StyleStack stack = new StyleStack();
        Style current = Style.EMPTY;

        int len = input.length();
        int i = 0;

        while (i < len) {
            char c = input.charAt(i);
            if (c != '{') {
                int start = i;

                while (i < len && input.charAt(i) != '{') {
                    i++;
                }

                result.append(Component.literal(input.substring(start, i)).setStyle(current));
                continue;
            }

            int end = input.indexOf('}', i);

            if (end == -1) {
                result.append(Component.literal(input.substring(i)).setStyle(current));
                break;
            }

            String tag = input.substring(i + 1, end);

            if (tag.isEmpty()) {
                current = stack.isEmpty() ? Style.EMPTY : stack.pop();
                i = end + 1;
                continue;
            }

            tag = tag.toLowerCase();

            Integer color = resolveColor(tag);

            if (color != null) {
                stack.push(current);
                current = current.withColor(color);
                i = end + 1;
                continue;
            }

            if (parseTextRegistry) {
                String txt = resolveText(tag);

                if (txt != null) {
                    result.append(Component.literal(txt).setStyle(current));
                    i = end + 1;
                    continue;
                }
            }

            ChatFormatting formatting = FORMATTING_CACHE.get(tag);

            if (formatting != null) {
                stack.push(current);
                current = current.applyFormat(formatting);
                i = end + 1;
                continue;
            }

            if (tag.startsWith("#")) {
                Integer hex = parseHexColor(tag);

                if (hex != null) {
                    stack.push(current);
                    current = current.withColor(hex);
                    i = end + 1;
                    continue;
                }
            }

            result.append(Component.literal(input.substring(i, end + 1)).setStyle(current));
            i = end + 1;
        }
        return result;
    }

    private Integer parseHexColor(String tag) {
        try {
            String hex = tag.substring(1);
            if (hex.length() == 3) {
                char r = hex.charAt(0);
                char g = hex.charAt(1);
                char b = hex.charAt(2);

                hex = "" + r + r + g + g + b + b;
            }

            return Integer.parseInt(hex, 16) & 0xFFFFFF;
        } catch (Exception ignored) {}
        return null;
    }

    private Integer resolveColor(String tag) {
        Integer staticColor = api.colors().get(tag);
        if (staticColor != null) return staticColor;
        var dyn = api.dynamicColors().get(tag);
        if (dyn != null) return dyn.getAsInt() & 0xFFFFFF;
        return null;
    }

    private String resolveText(String tag) {
        var staticText = api.text().get(tag);
        if (staticText != null) return staticText.get();
        var dynText = api.dynamicText().get(tag);
        if (dynText != null) return dynText.get();
        return null;
    }
}