package fr.redbuild.spigot.commands.tp;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.redbuild.models.spigot.commands.Cmd;
import fr.redbuild.models.spigot.commands.arg.Argument;

public class SpawnCmd extends Cmd{

    public SpawnCmd() {
        super("spawn", "commande pour se tp au spawn");
    }

    @Override
    public void execute(CommandSender arg0, String arg1, Argument arg2) {
        if(arg0 instanceof Player player){
            player.teleport(player.getWorld().getSpawnLocation());
        }else{
            arg0.sendMessage("Vous devez être un joueur pour executer cette commande");
        }
    }
    
}
