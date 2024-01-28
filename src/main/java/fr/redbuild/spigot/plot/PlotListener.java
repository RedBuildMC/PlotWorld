package fr.redbuild.spigot.plot;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.redbuild.models.spigot.Autowired.Autowired;
import fr.redbuild.models.spigot.mode.BuildMode;
import fr.redbuild.models.spigot.region.RegionController;

public class PlotListener implements Listener{
    @Autowired
    public PlotManager plotManager;

    @Autowired
    private BuildMode buildMode;

    @Autowired
    private RegionController regionController;
    
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Location location;
        if(event.getClickedBlock() != null){
            location = event.getClickedBlock().getLocation();
        }else{
            location = player.getLocation();
        }
        if(buildMode.contains(player))
            return;
        if(plotManager.getLoadedPlot(location) == null){
            if(regionController.getRegion(location) == null){
                event.setCancelled(true);
                return;
            }
            return;
        }
        if(plotManager.getLoadedPlot(location).isOwner(player)){
            return;
        }else{
            event.setCancelled(true);
            return;
        }
    }
}
