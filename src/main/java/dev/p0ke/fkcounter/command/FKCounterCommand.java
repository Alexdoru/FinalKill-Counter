package dev.p0ke.fkcounter.command;

import java.util.List;
import java.util.stream.Collectors;

import dev.p0ke.fkcounter.FKCounterMod;
import dev.p0ke.fkcounter.gui.FKCounterSettingsGui;
import dev.p0ke.fkcounter.util.DelayedTask;
import dev.p0ke.fkcounter.util.KillCounter;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class FKCounterCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "fks";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/fks <help|p|players|say|settings>";
	}
	
	@Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		KillCounter killCounter = FKCounterMod.instance().getKillCounter();
		
		if(args.length > 0 && args[0].equalsIgnoreCase("settings")) {
			
			new DelayedTask(() -> Minecraft.getMinecraft().displayGuiScreen(new FKCounterSettingsGui()), 1);

		} else if(args.length > 0 && ( args[0].equalsIgnoreCase("players") || args[0].equalsIgnoreCase("player")  || args[0].equalsIgnoreCase("p") ) ) {
			
			if(killCounter == null) return;
			String msg = "";
			msg += EnumChatFormatting.RED + "RED" + EnumChatFormatting.WHITE + ": " + 
					(killCounter.getPlayers(KillCounter.RED_TEAM).entrySet().stream().map(entry -> entry.getKey() + " (" + entry.getValue() + ")").collect(Collectors.joining(", "))) + "\n";
			msg += EnumChatFormatting.GREEN + "GREEN" + EnumChatFormatting.WHITE + ": " + 
					(killCounter.getPlayers(KillCounter.GREEN_TEAM).entrySet().stream().map(entry -> entry.getKey() + " (" + entry.getValue() + ")").collect(Collectors.joining(", "))) + "\n";
			msg += EnumChatFormatting.YELLOW + "YELLOW" + EnumChatFormatting.WHITE + ": " + 
					(killCounter.getPlayers(KillCounter.YELLOW_TEAM).entrySet().stream().map(entry -> entry.getKey() + " (" + entry.getValue() + ")").collect(Collectors.joining(", "))) + "\n";
			msg += EnumChatFormatting.BLUE + "BLUE" + EnumChatFormatting.WHITE + ": " + 
					(killCounter.getPlayers(KillCounter.BLUE_TEAM).entrySet().stream().map(entry -> entry.getKey() + " (" + entry.getValue() + ")").collect(Collectors.joining(", ")));
			
			sender.addChatMessage(new ChatComponentText(msg));
			
		} else if(args.length > 0 && args[0].equalsIgnoreCase("say")) {
			
			if(killCounter == null) return;
			String msg = "";
			
			msg += "Red: " + killCounter.getKills(KillCounter.RED_TEAM) +", ";
			msg += "Green: " + killCounter.getKills(KillCounter.GREEN_TEAM) +", ";
			msg += "Yellow: " + killCounter.getKills(KillCounter.YELLOW_TEAM) +", ";
			msg += "Blue: " + killCounter.getKills(KillCounter.BLUE_TEAM);
			
			(Minecraft.getMinecraft()).thePlayer.sendChatMessage(msg);
			
		} else if(args.length > 0 && args[0].equalsIgnoreCase("help")) {
			
			sender.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "Usage : " + getCommandUsage(sender)));
			
		} else {
			if(killCounter == null) return;
			String msg = "";
			msg += EnumChatFormatting.RED + "RED" + EnumChatFormatting.WHITE + ": " + killCounter.getKills(KillCounter.RED_TEAM) + "\n";
			msg += EnumChatFormatting.GREEN + "GREEN" + EnumChatFormatting.WHITE + ": " + killCounter.getKills(KillCounter.GREEN_TEAM) + "\n";
			msg += EnumChatFormatting.YELLOW + "YELLOW" + EnumChatFormatting.WHITE + ": " + killCounter.getKills(KillCounter.YELLOW_TEAM) + "\n";
			msg += EnumChatFormatting.BLUE + "BLUE" + EnumChatFormatting.WHITE + ": " + killCounter.getKills(KillCounter.BLUE_TEAM);
			
			sender.addChatMessage(new ChatComponentText(msg));
		
		}
		
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		String[] fksarguments = {"players","say","settings","help"};
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, fksarguments) : null;
	}
	

}
