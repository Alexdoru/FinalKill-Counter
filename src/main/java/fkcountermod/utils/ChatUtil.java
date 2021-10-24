package fkcountermod.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;

public class ChatUtil {

	public static void addChatMessage(IChatComponent msg) {
		
		if(Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().thePlayer != null) {			
			Minecraft.getMinecraft().thePlayer.addChatMessage(msg);		
		}
		
	}

}
