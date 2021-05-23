package commands;

import java.util.Arrays;
import java.util.List;

import dev.jeinton.mwutils.MwScoreboardData;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import utils.TabCompletionUtil;

public class CommandHypixelMessage extends CommandBase implements ICommand {

	@Override
	public String getCommandName() {
		return "message";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/msg <playername> <your message>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length < 1) {
		      sender.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "Usage : " + getCommandUsage(sender)));
		      return;
		    }
		
		String message = "/msg " + args[0];
		for (int i = 1; i < args.length; i++) {
		      message = message + " " + args[i];
		    }
		(Minecraft.getMinecraft()).thePlayer.sendChatMessage(message);
		
	}
	
	@Override
	public List<String> getCommandAliases()
    {
        return Arrays.<String>asList(new String[] {"w", "msg"});
    }
	
	@Override
	  public boolean canCommandSenderUseCommand(ICommandSender sender) {
	    return true;
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		return (MwScoreboardData.isitPrepPhase() ? null : getListOfStringsMatchingLastWord(args, TabCompletionUtil.getOnlinePlayersByName()));
	}
	
}
