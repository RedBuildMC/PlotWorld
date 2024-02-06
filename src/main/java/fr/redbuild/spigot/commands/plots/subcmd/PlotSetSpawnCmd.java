package fr.redbuild.spigot.commands.plots.subcmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.redbuild.models.spigot.Autowired.Autowired;
import fr.redbuild.models.spigot.commands.SubCmd.SubCmd;
import fr.redbuild.models.spigot.commands.arg.Argument;
import fr.redbuild.models.spigot.logger.CtMsg;
import fr.redbuild.spigot.plot.Plot;
import fr.redbuild.spigot.plot.PlotManager;
import fr.redbuild.spigot.plot.PlotRepository;

public class PlotSetSpawnCmd extends SubCmd{
    @Autowired
    private PlotManager plotManager;

    @Autowired
    private PlotRepository plotRepository;

    public PlotSetSpawnCmd(Boolean optional) {
        super("setspawn", optional);
    }

    @Override
    public void execute(CommandSender sender, String arg1, Argument arg2) {
        if(sender instanceof Player player){
            Plot plot = plotManager.getPlot(player);
            if(plot != null){
                if(plot.isClaimed()){
                    if(!plot.getOwnPlayers().contains(player.getUniqueId())){
                        CtMsg.sendMiniMessage("<red>Vous n'êtes pas propriétaire de ce plot",player);
                        return;
                    }
                    plot.setSpawn(player.getLocation());
                    plotRepository.save(plot);
                    CtMsg.sendMiniMessage("<green>Vous avez défini le spawn de ce plot",player);
                }else{
                    CtMsg.sendMiniMessage("<red>Ce plot n'est pas réclamé",player);
                }
            }else{
                CtMsg.sendMiniMessage("<red>Vous devez être dans un plot pour executer cette commande",sender);
            }
        }
    }
}
