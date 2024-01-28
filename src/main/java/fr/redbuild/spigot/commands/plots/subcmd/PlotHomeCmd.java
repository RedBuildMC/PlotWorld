package fr.redbuild.spigot.commands.plots.subcmd;

import org.bukkit.command.CommandSender;

import fr.redbuild.models.spigot.commands.SubCmd.SubCmd;
import fr.redbuild.models.spigot.commands.arg.Argument;

public class PlotHomeCmd extends SubCmd{
    public PlotHomeCmd(Boolean optional) {
        super("home", optional);
    }

    @Override
    public void execute(CommandSender arg0, String arg1, Argument arg2) {
    }
    
    
}
