package Reality.Realii.mods.modules.movement;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import Reality.Realii.event.EventHandler;
import Reality.Realii.event.events.world.EventPreUpdate;
import Reality.Realii.guis.material.themes.Classic;
import Reality.Realii.mods.Module;
import Reality.Realii.mods.ModuleType;
import Reality.Realii.mods.modules.render.ArrayList2;

public class InvMove extends Module{

	public InvMove(){
		super("GuiMove", ModuleType.Movement);
	}
	
	@EventHandler
	public void onUpdate(EventPreUpdate event) {
		if (ArrayList2.Sufix.getValue().equals("On")) {
        	
            
    		this.setSuffix("Vannila");
    	}
    		
    	 
    	 if (ArrayList2.Sufix.getValue().equals("Off")) {
         	
    	        
     		this.setSuffix("");
     	}
			KeyBinding[] key = { this.mc.gameSettings.keyBindForward, this.mc.gameSettings.keyBindBack, this.mc.gameSettings.keyBindLeft, this.mc.gameSettings.keyBindRight, this.mc.gameSettings.keyBindSprint };
			KeyBinding[] array;
			for (int length = (array = key).length, i = 0; i < length; ++i) {
				KeyBinding b = array[i];
				KeyBinding.setKeyBindState(b.getKeyCode(), Keyboard.isKeyDown(b.getKeyCode()));
			}
	}
}
