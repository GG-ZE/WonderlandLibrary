package ru.smertnix.celestial.command;


import ru.smertnix.celestial.utils.Helper;
import ru.smertnix.celestial.utils.other.ChatUtils;


public abstract class CommandAbstract implements Command, Helper {


    private final String name;
    private final String description;
    private final String usage;
    private final String[] aliases;

    public CommandAbstract(String name, String description, String usage, String... aliases) {
        this.name = name;
        this.description = description;
        this.aliases = aliases;
        this.usage = usage;
    }

    public void usage() {
        ChatUtils.addChatMessage("�f������� �c\"" + usage + "\"�f �� �������");
        ChatUtils.addChatMessage("�f���������� �������� �c.help�f ��� ��������� ������ ���� ������!");
    }

    public String getUsage() {
        return this.usage;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String[] getAliases() {
        return this.aliases;
    }
}
