package fr.redbuild.spigot.commands.tp;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import fr.redbuild.models.spigot.commands.Cmd;
import fr.redbuild.models.spigot.commands.arg.Argument;
import fr.redbuild.spigot.Main;

public class LobbyCmd extends Cmd{

    public LobbyCmd() {
        super("lobby", "commande pour se tp au slobby");
        setPermission("redbuild.tp.lobby");
    }

    @Override
    public void execute(CommandSender sender, String arg1, Argument arg2) {
        if(sender instanceof Player player){
            player.teleport(new Location(Bukkit.getWorld(Main.INSTANCE.getConfig().getString("lobby.name")), Main.INSTANCE.getConfig().getDouble("lobby.spawn.x"), Main.INSTANCE.getConfig().getDouble("lobby.spawn.y"), Main.INSTANCE.getConfig().getDouble("lobby.spawn.z")));
        }else{
            sender.sendMessage("Vous devez être un joueur pour executer cette commande");
        }
    }
    
}
