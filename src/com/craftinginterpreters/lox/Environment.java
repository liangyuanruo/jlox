package com.craftinginterpreters.lox;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    final Environment enclosing;
    private final Map<String, Object> values = new HashMap<>();

    Environment() {
        enclosing = null; // global (root) scope
    }

    Environment(Environment enclosing) {
        this.enclosing = enclosing; // local scope
    }

    void define(String name, Object value) {
        values.put(name, value);
    }

    Object get(Token name) {
        if (values.containsKey(name.lexeme)) { // local scope
            return values.get(name.lexeme);
        }

        if (enclosing != null) return enclosing.get(name); // parent scope

        throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
    }

    void assign(Token name, Object value) {
        if (values.containsKey(name.lexeme)) {
            values.put(name.lexeme, value); // local scope
            return;
        }

        if (enclosing != null) {
            enclosing.assign(name, value); // parent scope
            return;
        }

        throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
    }
}
