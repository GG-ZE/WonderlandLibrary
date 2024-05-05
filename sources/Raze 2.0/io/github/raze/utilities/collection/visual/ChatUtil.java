package io.github.raze.utilities.collection.visual;

import io.github.raze.utilities.system.Methods;
import net.minecraft.util.ChatComponentText;

public class ChatUtil implements Methods {

    public static void addChatMessage(String message, boolean prefix) {
        message = prefix ? "§f[R]§7: " + message : message;
        mc.thePlayer.addChatMessage(new ChatComponentText(message));
    }

    public static void addChatMessage(String message) {
        addChatMessage(message, true);
    }

}
