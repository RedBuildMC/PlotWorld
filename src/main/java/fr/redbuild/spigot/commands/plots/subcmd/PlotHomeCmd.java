package fr.redbuild.spigot.commands.plots.subcmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.redbuild.models.spigot.Autowired.Autowired;
import fr.redbuild.models.spigot.commands.SubCmd.SubCmd;
import fr.redbuild.models.spigot.commands.arg.Argument;
import fr.redbuild.models.spigot.commands.defaultarguments.StringArgument;
import fr.redbuild.models.spigot.logger.CtMsg;
import fr.redbuild.spigot.plot.PlotManager;

public class PlotHomeCmd extends SubCmd{
    @Autowired
    private PlotManager plotManager;
    public PlotHomeCmd(Boolean optional) {
        super("home", optional);
        rc(1, new StringArgument("plot", true));
    }

    @Override
    public void execute(CommandSender arg0, String arg1, Argument arg2) {
        if(arg0 instanceof Player player){
            int id = 0;
            if(arg2.getOptional(1, String.class).isPresent()){
                try {
                    id = Integer.parseInt(arg2.getOptional(1, String.class).get());
                } catch (NumberFormatException e) {
                    CtMsg.sendMiniMessage("<red>Vous devez entrer un nombre", player);
                    return;
                }
            }
            if(plotManager.havePlot(player))
                player.teleport(plotManager.getPlot(player, id).getSpawn());
            else
                CtMsg.sendMiniMessage("<red>Vous n'avez pas de plot", player);
        }
    }
    
    
}
