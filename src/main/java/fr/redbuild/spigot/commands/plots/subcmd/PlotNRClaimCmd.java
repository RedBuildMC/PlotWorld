package fr.redbuild.spigot.commands.plots.subcmd;

import java.util.List;

import org.bson.Document;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.redbuild.models.spigot.Autowired.Autowired;
import fr.redbuild.models.spigot.commands.SubCmd.SubCmd;
import fr.redbuild.models.spigot.commands.arg.Argument;
import fr.redbuild.models.spigot.commands.defaultarguments.PlayerArgument;
import fr.redbuild.models.spigot.logger.CtMsg;
import fr.redbuild.models.spigot.user.User;
import fr.redbuild.models.spigot.user.UserManager;
import fr.redbuild.spigot.plot.Plot;
import fr.redbuild.spigot.plot.PlotManager;
import fr.redbuild.spigot.plot.PlotRepository;

public class PlotNRClaimCmd extends SubCmd{
    @Autowired
    private PlotManager plotManager;
    @Autowired
    private PlotRepository plotRepository;
    @Autowired
    private UserManager userManager;

    public PlotNRClaimCmd(Boolean optional) {
        super("nrclaim", optional);
        rc(1, new PlayerArgument("player", true));
        setPermission("redbuild.plots.nrclaim");
    }

    @Override
    public void execute(CommandSender sender, String command, Argument args) {
        if(args.getOptional(1, Player.class).isPresent()){
            Player player = args.getOptional(1, Player.class).get();
            if(plotManager.isInPlot(player)){
                    Plot plot = plotManager.getPlot(player);
                    plot.setOwnPlayers(List.of(player.getUniqueId()));
                    if (plot.isClaimed())
            plot.getOwnPlayers().forEach(uuid -> {
                User user = userManager.getUser(uuid);
                if(!user.hasAttribute("plots"))
                    user.setAttribute("plots", plotManager.plotToDocument(List.of(plot)));
                else{
                    List<Plot> plots = (List<Plot>) plotManager.documentToPlot((Document) user.getAttribute("plots"));
                    if(!plots.contains(plot)){
                        plots.add(plot);
                        user.setAttribute("plots", plotManager.plotToDocument(plots));
                    }
                }
            });
                    plotRepository.save(plot);
                    plotManager.realoadLoadedPlot();
                    CtMsg.sendMiniMessage("<green>Ce plot appartien maintenant à <gold>" + player.getName(),sender);
                    CtMsg.sendMiniMessage("<gold>" + sender.getName() + " <green>Vous à mis la propriété de ce plot",player);
            }else{
                CtMsg.sendMiniMessage("<red>Vous devez être dans un plot pour executer cette commande",sender);
            }
        }else if(sender instanceof Player player){
            if(plotManager.isInPlot(player)){
                    Plot plot = plotManager.getPlot(player);
                    plot.setOwnPlayers(List.of(player.getUniqueId()));
                    if (plot.isClaimed())
            plot.getOwnPlayers().forEach(uuid -> {
                User user = userManager.getUser(uuid);
                if(!user.hasAttribute("plots"))
                    user.setAttribute("plots", plotManager.plotToDocument(List.of(plot)));
                else{
                    List<Plot> plots = (List<Plot>) plotManager.documentToPlot((Document) user.getAttribute("plots"));
                    if(!plots.contains(plot)){
                        plots.add(plot);
                        user.setAttribute("plots", plotManager.plotToDocument(plots));
                    }
                }
            });
                    plotRepository.save(plot);
                    plotManager.realoadLoadedPlot();
                    CtMsg.sendMiniMessage("<green>Ce plot vous appartien maintenant",sender);
            }else{
                CtMsg.sendMiniMessage("<red>Vous devez être dans un plot pour executer cette commande",sender);
            }
        }else{
            CtMsg.sendMiniMessage("<red>Vous devez être un joueur pour executer cette commande",sender);
        }
    }
    
}
