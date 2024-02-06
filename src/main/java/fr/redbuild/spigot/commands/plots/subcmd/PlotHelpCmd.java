package fr.redbuild.spigot.commands.plots.subcmd;

import org.bukkit.command.CommandSender;

import fr.redbuild.models.spigot.commands.SubCmd.SubCmd;
import fr.redbuild.models.spigot.commands.arg.Argument;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public class PlotHelpCmd extends SubCmd {
    public PlotHelpCmd(boolean optional) {
        super("help", optional);
    }

    @Override
    public void execute(CommandSender arg0, String arg1, Argument arg2) {
        TextComponent helpmsg = Component.text("§4§lRed§6§lBuild §7» §aCommandes disponibles : \n")
                .append(Component.text("§7- §a/plot claim §7» §aRéclamer un plot\n"))
                .append(Component.text("§7- §a/plot unclaim §7» §aAbandonner un plot\n"))
                .append(Component.text("§7- §a/plot reset §7» §aRéinitialiser un plot\n"))
                .append(Component.text("§7- §a/plot info §7» §aVoir les informations d'un plot\n"))
                .append(Component.text("§7- §a/plot nrclaim §7» §aRéclamer un plot non réclamé\n"))
                .append(Component.text("§7- §a/plot home §7» §aTéléporter à votre plot\n"))
                .append(Component.text("§7- §a/plot setspawn §7» §aDéfinir le spawn de votre plot\n"));
        arg0.sendMessage(helpmsg);
    }
}
