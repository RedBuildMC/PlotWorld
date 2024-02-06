package fr.redbuild.spigot.commands.test.area;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.redbuild.models.spigot.Autowired.Autowired;
import fr.redbuild.models.spigot.commands.Cmd;
import fr.redbuild.models.spigot.commands.arg.Argument;

public class AreaCmd extends Cmd{
    @Autowired
    private TestAreaManager manager;

    public AreaCmd() {
        super("area", "test");
        setPermission("redbuild.test");
    }

    @Override
    public void execute(CommandSender sender, String arg1, Argument arg2) {
        if(sender instanceof Player player)
            manager.createArea(player);
    }
    
}
