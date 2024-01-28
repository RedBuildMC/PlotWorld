package fr.redbuild.spigot.commands.plots.subcmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.redbuild.models.spigot.Autowired.Autowired;
import fr.redbuild.models.spigot.commands.SubCmd.SubCmd;
import fr.redbuild.models.spigot.commands.arg.Argument;
import fr.redbuild.models.spigot.logger.CtMsg;
import fr.redbuild.models.spigot.utils.ConfirmeChat;
import fr.redbuild.spigot.plot.PlotManager;

public class PlotResetCmd extends SubCmd{
    @Autowired
    private PlotManager plotManager;
    @Autowired
    private ConfirmeChat confirmeChat;

    public PlotResetCmd(Boolean optional) {
        super("reset", optional);
    }

    @Override
    public void execute(CommandSender sender, String arg1, Argument arg2) {
        if(sender instanceof Player player){
            if(plotManager.isInPlot(player)){
                if(plotManager.isOwner(player)){
                    confirmeChat.sendConfirmMsg("§6êtes vous sûre de réinitialiser supprimer le plot ? : ","§2[Delete] ","§4 [Cancel]", () -> {
                        plotManager.resetPlot(plotManager.getPlot(player));
                    CtMsg.sendMessage("§aVotre plot a été reset", sender);
                    }, () -> CtMsg.sendMessage("§aCancel !", player), player);
                }else{
                    CtMsg.sendMessage("§cVous n'êtes pas le propriétaire de ce plot", sender);
                }
            }else{
                CtMsg.sendMessage("§cVous devez être dans un plot pour executer cette commande", sender);
            }
        }else{
            CtMsg.sendMessage("§cVous devez être un joueur pour executer cette commande", sender);
        }
        
    }
    
}
