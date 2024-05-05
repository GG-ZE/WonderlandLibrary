package io.github.raze.commands.system;

import io.github.raze.utilities.system.Methods;

import java.util.Arrays;
import java.util.List;

public abstract class Command implements Methods {

    public String name, description, syntax;
    public List<String> aliases;

    public Command(String name, String description, String syntax, String... aliases) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
        this.aliases = Arrays.asList(aliases);
    }

    public abstract String onCommand(String[] args, String command);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSyntax() {
        return syntax;
    }

    public List<String> getAliases() {
        return aliases;
    }
}
