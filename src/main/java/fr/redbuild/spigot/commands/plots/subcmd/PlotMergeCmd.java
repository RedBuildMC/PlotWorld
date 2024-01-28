package fr.redbuild.spigot.commands.plots.subcmd;

// import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.redbuild.models.spigot.Autowired.Autowired;
import fr.redbuild.models.spigot.commands.SubCmd.SubCmd;
import fr.redbuild.models.spigot.commands.arg.Argument;
import fr.redbuild.models.spigot.logger.CtMsg;
import fr.redbuild.spigot.plot.PlotManager;

public class PlotMergeCmd extends SubCmd{
    @Autowired
    private PlotManager plotManager;

    public PlotMergeCmd(Boolean optional) {
        super("merge", optional);
    }

    @Override
    public void execute(CommandSender sender, String arg1, Argument args) {
        if(sender instanceof Player player){
            if(plotManager.isInPlot(player)){
                if(plotManager.isOwner(player)){
                    // if(player.getFacing() == BlockFace.NORTH)
                }
            }else{
                CtMsg.sendMiniMessage("<red>Vous devez être dans un plot pour executer cette commande",sender);
            }
        }else{
            CtMsg.sendMiniMessage("<red>Vous devez être un joueur pour executer cette commande",sender);
        }
    }
    
}
