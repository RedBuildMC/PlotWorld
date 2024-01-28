package fr.redbuild.spigot.commands.plots.subcmd;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.redbuild.models.spigot.Autowired.Autowired;
import fr.redbuild.models.spigot.commands.SubCmd.SubCmd;
import fr.redbuild.models.spigot.commands.arg.Argument;
import fr.redbuild.spigot.plot.Plot;
import fr.redbuild.spigot.plot.PlotManager;
import fr.redbuild.spigot.plot.PlotRepository;

public class PlotClaimCmd extends SubCmd{
    @Autowired
    private PlotManager plotManager; 
    @Autowired
    private PlotRepository plotRepository;

    public PlotClaimCmd(boolean optional) {
        super("claim",optional);
    }

    @Override
    public void execute(CommandSender arg0, String arg1, Argument arg2) {
        if(arg0 instanceof Player player){
            Plot plot = plotManager.getPlot(player);
            if(plot != null){
                if(plot.isClaimed()){
                    arg0.sendMessage("§4§lRed§6§lBuild §7» §cCe plot est déjà réclamé");
                }else{
                    plot.reset();
                    plot.modifyBorder(Material.POLISHED_BLACKSTONE_SLAB, Material.RED_CONCRETE);
                    plot.setOwnPlayers(List.of(player.getUniqueId()));
                    plotRepository.save(plot);
                    plotManager.realoadLoadedPlot();
                    arg0.sendMessage("§4§lRed§6§lBuild §7» §aVous avez réclamé ce plot");
                }
            }else{
                arg0.sendMessage("§4§lRed§6§lBuild §7» §cCeci n'est pas un plot");
            }
        }else{
            arg0.sendMessage("§4§lRed§6§lBuild §7» §cVous devez être un joueur pour exécuter cette commande");
        }
    }
    
}