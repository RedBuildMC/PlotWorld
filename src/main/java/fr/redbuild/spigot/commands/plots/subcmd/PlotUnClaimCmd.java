package fr.redbuild.spigot.commands.plots.subcmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.redbuild.models.spigot.Autowired.Autowired;
import fr.redbuild.models.spigot.commands.SubCmd.SubCmd;
import fr.redbuild.models.spigot.commands.arg.Argument;
import fr.redbuild.models.spigot.logger.CtMsg;
import fr.redbuild.models.spigot.utils.ConfirmeChat;
import fr.redbuild.spigot.plot.PlotManager;
import fr.redbuild.spigot.plot.PlotRepository;
import net.md_5.bungee.api.ChatColor;

public class PlotUnClaimCmd extends SubCmd{
    @Autowired
    private PlotManager plotManager;
    @Autowired
    private PlotRepository plotRepository;
    @Autowired
    private ConfirmeChat confirmeChat;

    public PlotUnClaimCmd(Boolean optional) {
        super("unclaim", optional);
    }

    @Override
    public void execute(CommandSender sender, String arg1, Argument arg2) {
        if(sender instanceof Player player){
            if(plotManager.getPlot(player) != null){
                if(plotManager.getPlot(player).isOwner(player)){
                    confirmeChat.sendConfirmMsg(ChatColor.GOLD + "êtes vous sûre de vouloir supprimer le plot ? : ", ChatColor.GREEN + "§2[Delete] ","§4 [Cancel]", () -> {
                        plotManager.unclaimPlot(plotManager.getPlot(player),player);
                        sender.sendMessage("§4§lRed§6§lBuild §7» §aPlot supprimée");
                    }, () -> CtMsg.sendMessage("§aCancel !", player), player);
                }else{
                    CtMsg.sendMessage("§cVous n'êtes pas le propriétaire de ce plot", sender);
                }
            }else{
                    CtMsg.sendMessage("§cVous n'êtes pas sur un plot", sender);
                }
        }else{
            sender.sendMessage("§4§lRed§6§lBuild §7» §cYou must be a player to execute this command");
        }
    }
    
}
