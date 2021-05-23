package commands;

import java.util.List;

import dev.jeinton.mwutils.MwScoreboardData;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import utils.TabCompletionUtil;

public class CommandHypixelShout extends CommandBase implements ICommand {

	@Override
	public String getCommandName() {
		return "shout";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/shout <message>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		
		String message = "/shout " + args[0];
		for (int i = 1; i < args.length; i++) {
		      message = message + " " + args[i];
		    }
		(Minecraft.getMinecraft()).thePlayer.sendChatMessage(message);
		
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
