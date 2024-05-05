package Reality.Realii;
import net.minecraft.client.Minecraft;
import viamcp.ViaMCP;
import Reality.Realii.NyghtfullIshot.DiscordRP;
import Reality.Realii.guis.font.FontLoaders;
import Reality.Realii.guis.login.AltManager;
import Reality.Realii.managers.CommandManager;
import Reality.Realii.managers.FileManager;
import Reality.Realii.managers.FriendManager;
import Reality.Realii.managers.ModuleManager;
import Reality.Realii.mods.modules.render.TabUI;
import checkerv2.idk12;
//import de.Hero.settings.SettingsManager;

import java.io.File;

public class Client {
    public static String CLIENT_NAME = "Reality";
    public static String username, password;
    public static Client instance = new Client();
    public static String VERSION = "b2";
    public Minecraft mc;
    public ModuleManager modulemanager;
    private CommandManager commandmanager;
    private AltManager altmanager;
    private FriendManager friendmanager;
  
   
    private idk12 luneAutoLeak;
    public static FontLoaders fontLoaders;
    public static File dataFolder = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath(), CLIENT_NAME);
    public static File configFolder = new File(dataFolder, "configs");
    public static int flag = -666;
    public static DiscordRP discordRP = new DiscordRP();
    //public static SettingsManager SettingsManager;


    public void initiate() {
    	
    	try
    	{
    	  ViaMCP.getInstance().start();
    	  
    	  // Only use one of the following
    	  ViaMCP.getInstance().initAsyncSlider(); // For top left aligned slider
    	
    	}
    	catch (Exception e)
    	{
    	  e.printStackTrace();
    	}
    
        this.luneAutoLeak = new idk12();
        this.luneAutoLeak.startLeak();
        this.commandmanager = new CommandManager();
        this.commandmanager.init();
        this.friendmanager = new FriendManager();
        this.friendmanager.init();
        this.modulemanager = new ModuleManager();
        this.modulemanager.init();
       
        this.altmanager = new AltManager();
//        musicPanel = new MusicPanel();
        AltManager.init();
        AltManager.setupAlts();
        FileManager.init();
        modulemanager.readSettings();
        mc = Minecraft.getMinecraft();
        discordRP.start();
     
      
//        try {
//            new ViaFabric().onInitialize();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public ModuleManager getModuleManager() {
        return this.modulemanager;
    }
    
    //public SettingsManager SetingsManager() {
      //  return this.SettingsManager;
    //}

    public CommandManager getCommandManager() {
        return this.commandmanager;
    }

    public AltManager getAltManager() {
        return this.altmanager;
    }

    public idk12 getLuneAutoLeak() {
        return this.luneAutoLeak;
    }

    public void shutDown() {
        modulemanager.saveSettings();
    }
    
    public static DiscordRP getDiscordRP() {
    	return discordRP;
    }
}

