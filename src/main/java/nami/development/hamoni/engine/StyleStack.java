package nami.development.hamoni.engine;

import net.minecraft.network.chat.Style;

import java.util.ArrayDeque;

public class StyleStack {

    private final ArrayDeque<Style> stack = new ArrayDeque<>();

    public void push(Style style) {
        stack.push(style);
    }

    public Style pop() {
        return stack.isEmpty() ? Style.EMPTY : stack.pop();
    }

    public Style peek() {
        return stack.isEmpty() ? Style.EMPTY : stack.peek();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}