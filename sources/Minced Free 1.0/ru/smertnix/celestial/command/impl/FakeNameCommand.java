package ru.smertnix.celestial.command.impl;


import com.mojang.realmsclient.gui.ChatFormatting;

import ru.smertnix.celestial.command.CommandAbstract;
import ru.smertnix.celestial.ui.notification.NotificationMode;
import ru.smertnix.celestial.ui.notification.NotificationRenderer;
import ru.smertnix.celestial.utils.other.ChatUtils;
import ru.smertnix.celestial.utils.other.NameUtils;

public class FakeNameCommand
        extends CommandAbstract {
    public static String oldName;

    public FakeNameCommand() {
        super("fakename", "fakename", "�6.fakename" + ChatFormatting.WHITE + " set �3<name> |" + ChatFormatting.WHITE + " reset", "�6.fakename" + ChatFormatting.WHITE + " set �3<name> |" + ChatFormatting.WHITE + " reset", "fakename", "name", "setname");
    }

    @Override
    public void execute(String ... arguments) {
        try {
            if (arguments.length >= 2) {
                oldName = "Sikorsky";
                    if (arguments[1].equalsIgnoreCase("reset")) {
                    	NameUtils.setName(oldName);
                        ChatUtils.addChatMessage("��� ��������!");
                    } else {
                    	if (arguments[1].length() > 12) {
                    	    ChatUtils.addChatMessage("��������� ��� ������� " + ChatFormatting.RED + "bolshoy");
                    	} else {
                    		NameUtils.setName(arguments[1]);
                            ChatUtils.addChatMessage("��� �������� �� " + ChatFormatting.GREEN + arguments[1]);
                    	}
                    }
            } else {
            	ChatUtils.addChatMessage("�f�������� �������! ���������� �����������:");
            	ChatUtils.addChatMessage("�fname �7<���> (����� ��������� ���)");
            	ChatUtils.addChatMessage("�fname �creset (����� �������� ���)");
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}
