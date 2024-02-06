package fr.redbuild.spigot.commands.plots;

import org.bukkit.command.CommandSender;

import fr.redbuild.models.spigot.commands.Cmd;
import fr.redbuild.models.spigot.commands.arg.Argument;
import fr.redbuild.spigot.commands.plots.subcmd.PlotClaimCmd;
import fr.redbuild.spigot.commands.plots.subcmd.PlotHelpCmd;
import fr.redbuild.spigot.commands.plots.subcmd.PlotHomeCmd;
import fr.redbuild.spigot.commands.plots.subcmd.PlotInfo;
import fr.redbuild.spigot.commands.plots.subcmd.PlotNRClaimCmd;
import fr.redbuild.spigot.commands.plots.subcmd.PlotResetCmd;
import fr.redbuild.spigot.commands.plots.subcmd.PlotSetSpawnCmd;
import fr.redbuild.spigot.commands.plots.subcmd.PlotUnClaimCmd;

import java.util.*;

public class PlotCmd extends Cmd{

    public PlotCmd() {
        super("plot", "Command to manage plot", "plot <claim|help>", List.of("p"));
        rc(1, new PlotClaimCmd(false));
        rc(1, new PlotUnClaimCmd(false));
        rc(1, new PlotResetCmd(false));
        rc(1, new PlotInfo(false));
        rc(1, new PlotNRClaimCmd(false));
        rc(1, new PlotHomeCmd(false));
        rc(1, new PlotSetSpawnCmd(false));
        rc(1, new PlotHelpCmd(false));
    }

    @Override
    public void execute(CommandSender arg0, String arg1, Argument arg2) {
    }
    
}
