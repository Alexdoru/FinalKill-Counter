package fkcountermod.commands;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class CommandReport extends CommandBase implements ICommand {

	@Override
	public String getCommandName() {
		return "report";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/report <player> <cheats>";
	}

	@Override
	  public boolean canCommandSenderUseCommand(ICommandSender sender) {
	    return true;
	  }
	
	@Override
	public int compareTo(ICommand o) {
		return 0;
	}
	
	@Override
	public List<String> getCommandAliases()
    {
        return Arrays.<String>asList(new String[] {"wdr", "report", "watchdogreport"});
    }
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		String[] cheats = {"aura","aimbot","bhop","velocity","reach","speed","ka","killaura","forcefield","antiknockback","autoclicker","fly","dolphin","jesus"};
		return (args.length > 1 ? getListOfStringsMatchingLastWord(args, cheats) : null);
	}
	
	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length < 1) {
		      sender.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "Usage : " + getCommandUsage(sender)));
		      return;
		    }
		
		String message = "/report " + args[0];
		for (int i = 1; i < args.length; i++) {
		      message = message + " " + args[i];
		    }
		(Minecraft.getMinecraft()).thePlayer.sendChatMessage(message);
	}

}
