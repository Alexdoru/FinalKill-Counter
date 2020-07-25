package dev.p0ke.fkcounter;

import com.orangemarshall.hudproperty.HudPropertyApi;

import dev.jeinton.mwutils.MwScoreboardData;
import dev.jeinton.mwutils.event.MwGameIdChangeEvent;
import dev.jeinton.mwutils.MwScoreboardParser;
import dev.p0ke.fkcounter.command.FKCounterCommand;
import dev.p0ke.fkcounter.config.ConfigHandler;
import dev.p0ke.fkcounter.gui.FKCounterGui;
import dev.p0ke.fkcounter.util.KillCounter;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = FKCounterMod.MODID, version = FKCounterMod.VERSION)
public class FKCounterMod {
	
    public static final String MODID = "fkcounter";
    public static final String VERSION = "2.1";
    
    private static FKCounterMod instance;
    
    private ConfigHandler configHandler;
    private HudPropertyApi hudManager;

    private KillCounter killCounter = null;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	configHandler = new ConfigHandler(event.getSuggestedConfigurationFile());
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	instance = this;
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(MwScoreboardParser.instance());

		hudManager = HudPropertyApi.newInstance();
		hudManager.register(new FKCounterGui());
		
		configHandler.loadConfig();
    }
    
    @EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ClientCommandHandler.instance.registerCommand(new FKCounterCommand());
	}
    
    @SubscribeEvent
    public void onMwGameIdChange(MwGameIdChangeEvent event) {
        if (event.getType() == MwGameIdChangeEvent.EventType.CONNECT) {
            MwScoreboardData mwData = MwScoreboardParser.instance().getMwScoreboardData();
            if (killCounter == null || !killCounter.getGameId().equals(mwData.getGameId())) {
                killCounter = new KillCounter(mwData.getGameId());
            }
        }
    }

    public static boolean isInMwGame() {
        MwScoreboardData mwData = MwScoreboardParser.instance().getMwScoreboardData();
        return (mwData.getGameId() != null);
    }

    // TODO: fix this
    public void forceToggle() {
    	if(killCounter != null) {
    		killCounter = null;
    	} else {
    		killCounter = new KillCounter(null);
    	}
    }
    
    public KillCounter getKillCounter() {
    	return killCounter;
    }
    
    public ConfigHandler getConfigHandler() {
    	return configHandler;
    }
    
    public HudPropertyApi getHudManager() {
    	return hudManager;
    }

    public static FKCounterMod instance() {
    	return instance;
    }
}
