package fr.redbuild.spigot.plot;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.redbuild.models.spigot.Autowired.Autowired;
import fr.redbuild.models.spigot.region.RegionController;

public class PlotManager {
    private List<Plot> loadedPlots = new ArrayList<>();
     @Autowired
     private RegionController regionController; 
     @Autowired
     private PlotRepository plotRepository; 
     
    public void unclaimPlot(Plot plot,Player player){
        loadedPlots.remove(loadedPlots.stream().filter(nullPlot -> nullPlot.getId().equals(plot.getId())).findFirst().orElse(plot));
        if(regionController.getRegion(player) != regionController.DEFAULT_REGION){
            regionController.deRegisterRegion(regionController.getRegion(player));
        }else{
            regionController.deRegisterRegion(plot.getRegion());
        }
        plot.resetall();
        plotRepository.save(plot);
    }

    public void initPlot(Location originePlot,int size, int plotSize, int intervalle, int nombrePlot) {
        Bukkit.getLogger().info("§4§lRed§6§lBuild §7» §eInit plot");
        if(plotRepository.findAll().size() > 0){
            Bukkit.getLogger().info("§4§lRed§6§lBuild §7» §cPlot already init");
            Bukkit.getLogger().info("§4§lRed§6§lBuild §7» §eLoad plot from the database");
            loadedPlots();
            Bukkit.getLogger().info("§4§lRed§6§lBuild §7» §aLoading plot success");
            return;
        }
        int x = originePlot.getBlockX();
        int z = originePlot.getBlockZ();
        int y = originePlot.getBlockY();
        Location start = new Location(originePlot.getWorld(), x, y,
                        z);
                Location end = new Location(originePlot.getWorld(),x, y + y + size,
                        z);
                createPlot(start, end);
        for (int i = 0; i < nombrePlot; i++) {
            for (int j = 0; j < nombrePlot; j++) {
                //en haut a droite
                start = new Location(originePlot.getWorld(), x + (plotSize + intervalle) * i, y,
                        z + (plotSize + intervalle) * j);
                end = new Location(originePlot.getWorld(), x + (plotSize + intervalle) * i + plotSize,y + size,
                        z + (plotSize + intervalle) * j + plotSize);
                createPlot(start, end);
                //en bas a droite
                start = new Location(originePlot.getWorld(), x - (plotSize + intervalle) * i, y,
                        z + (plotSize + intervalle) * j);
                end = new Location(originePlot.getWorld(), x - (plotSize + intervalle) * i + plotSize,y + size,
                        z + (plotSize + intervalle) * j + plotSize);
                createPlot(start, end);
                //en haut a gauche
                start = new Location(originePlot.getWorld(), x + (plotSize + intervalle) * i, y,
                        z - (plotSize + intervalle) * j);
                end = new Location(originePlot.getWorld(), x + (plotSize + intervalle) * i + plotSize,y + size,
                        z - (plotSize + intervalle) * j + plotSize);
                createPlot(start, end);
                //en bas a gauche
                start = new Location(originePlot.getWorld(), x - (plotSize + intervalle) * i, y,
                        z - (plotSize + intervalle) * j);
                end = new Location(originePlot.getWorld(), x - (plotSize + intervalle) * i + plotSize  ,y + size,
                        z - (plotSize + intervalle) * j + plotSize);
                createPlot(start, end);
            }
        }
        Bukkit.getLogger().info("§4§lRed§6§lBuild §7» §aInit plot success");
    }

    public void resetPlot(Plot plot) {   
        plot.reset();
    }

    public boolean isInPlot(Player player) {
        return getPlot(player) != null;
    }

    public boolean isOwner(Player player) {
        Plot plot = getPlot(player);
        if(plot != null && plot.getOwnPlayers() != null){
            return plot.getOwnPlayers().contains(player.getUniqueId());
        }
        return false;
    }

    public void createPlot(Location start,Location end) {
        Plot plot = new Plot(start, end, end, new ArrayList<>());
        plotRepository.save(plot);
        if(plot.isClaimed())
                loadPlot(plot);
    }

    public void loadedPlots(){
        plotRepository.findAll().forEach(plot -> {
            if(plot.isClaimed())
                loadPlot(plot);
        });
    }

    public void loadPlot(Plot plot){
        loadedPlots.add(plot);
        regionController.registerRegion(plot.getRegion());
    }

    public Plot getPlot(Player player) {
        for (Plot plot : plotRepository.findAll()) {
            if (plot.isInside(player)) {
                return plot;
            }
        }
        return null;
    }

    public Plot getLoadedPlot(Player player) {
        for (Plot plot : loadedPlots) {
            if (plot.isInside(player)) {
                return plot;
            }
        }
        return null;
    }

    public Plot getLoadedPlot(Location location) {
        for (Plot plot : loadedPlots) {
            if (plot.isInside(location)) {
                return plot;
            }
        }
        return null;
    }

    public void realoadLoadedPlot(){
        loadedPlots.clear();
        plotRepository.findAll().forEach(plot -> {
            if(plot.isClaimed())
                loadPlot(plot);
        });
    }

    public boolean isInsidePlot(Player player) {
        return getPlot(player) != null;
    }

    public boolean isInPlot(Location location) {
        return getPlot(location) != null;
    }

    public Plot getPlot(Location location) {
        for (Plot plot : plotRepository.findAll()) {
            if (plot.isInside(location)) {
                return plot;
            }
        }
        return null;
    }
}
