package fr.redbuild.spigot.commands.plots.subcmd;

import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.redbuild.models.spigot.Autowired.Autowired;
import fr.redbuild.models.spigot.commands.SubCmd.SubCmd;
import fr.redbuild.models.spigot.commands.arg.Argument;
import fr.redbuild.models.spigot.logger.CtMsg;
import fr.redbuild.spigot.plot.Plot;
import fr.redbuild.spigot.plot.PlotManager;

public class PlotInfo extends SubCmd{
    @Autowired
    private PlotManager plotManager;

    public PlotInfo(Boolean optional) {
        super("info", optional);
    }

    @Override
    public void execute(CommandSender sender, String command, Argument args) {
        if(sender instanceof Player player){
            if(plotManager.isInPlot(player)){
                    Plot plot = plotManager.getPlot(player);
                    if(plot.getOwnPlayers() != null){
                        CtMsg.sendMiniMessage("<gold>Owners: <green>" + plot.getOwnPlayers().stream().map(uuid -> Bukkit.getOfflinePlayer(uuid).getName()).collect(Collectors.joining(", ")),player);
                    }else{
                        CtMsg.sendMiniMessage("<gold>Owners: <green>Aucun",player);
                    }
                    if(plot.getTrustedPlayers() != null){
                        CtMsg.sendMiniMessage("<gold>Trusteds: <green>" + plot.getTrustedPlayers().stream().map(uuid -> Bukkit.getOfflinePlayer(uuid).getName()).collect(Collectors.joining(", ")),player);
                    }else{
                        CtMsg.sendMiniMessage("<gold>Trusteds: <green>Aucun",player);
                    }
                    if(plot.getAddedPlayers() != null){
                        CtMsg.sendMiniMessage("<gold>Added: <green>" + plot.getAddedPlayers().stream().map(uuid -> Bukkit.getOfflinePlayer(uuid).getName()).collect(Collectors.joining(", ")),player);
                    }else{
                        CtMsg.sendMiniMessage("<gold>Added: <green>Aucun",player);
                    }
                    if(plot.getBannedPlayers() != null){
                        CtMsg.sendMiniMessage("<gold>Banned: <green>" + plot.getBannedPlayers().stream().map(uuid -> Bukkit.getOfflinePlayer(uuid).getName()).collect(Collectors.joining(", ")),player);
                    }else{
                        CtMsg.sendMiniMessage("<gold>Banned: <green>Aucun",player);
                    }
                    if(player.hasPermission("redbuild.plots.info.all")){
                        CtMsg.sendMiniMessage("<gold>Plot: <green>" + plot.getId(),player);
                        CtMsg.sendMiniMessage("<gold>Region: <green>" + plot.getRegion(), player);
                        CtMsg.sendMiniMessage("<gold>Spawn: <green>" + plot.getSpawn(), player);
                    }
        }else{
            CtMsg.sendMiniMessage("<red>Vous devez être un joueur pour executer cette commande",sender);
        }
    }
}
    
}
