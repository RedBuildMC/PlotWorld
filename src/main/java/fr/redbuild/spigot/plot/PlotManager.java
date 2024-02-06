package fr.redbuild.spigot.plot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.redbuild.models.spigot.Autowired.Autowired;
import fr.redbuild.models.spigot.region.RegionController;
import fr.redbuild.models.spigot.user.User;
import fr.redbuild.models.spigot.user.UserManager;

public class PlotManager {
    private List<Plot> loadedPlots = new ArrayList<>();
     @Autowired
     private RegionController regionController; 
     @Autowired
     private PlotRepository plotRepository;
     @Autowired
     private UserManager userManager; 
     
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

    public boolean havePlot(Player player){
        return getHisPlot(player) != null;
    }

    public Plot getPlot(Player player,int id){
        User user = userManager.getUser(player);
        if(user.hasAttribute("plots")){
            List<Plot> plots = documentToPlot((Document) user.getAttribute("plots"));
            if(plots.size() > id)
                return plots.get(id);
            else
                return plots.get(0);
        }
        return null;
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
        if (plot.isClaimed())
            plot.getOwnPlayers().forEach(uuid -> {
                User user = userManager.getUser(uuid);
                if(user != null)
                    if(!user.hasAttribute("plots"))
                        user.setAttribute("plots", plotToDocument(List.of(plot)));
                    else{
                        List<Plot> plots = documentToPlot((Document) user.getAttribute("plots"));
                        if(plots == null)
                           plots = new ArrayList<>();
                        if(!plots.contains(plot)){
                            plots.add(plot);
                            user.setAttribute("plots", plotToDocument(plots));
                        }
                    }
            });
        loadedPlots.add(plot);
        regionController.registerRegion(plot.getRegion());
    }
    
    public Document plotToDocument(List<Plot> plots) {
            Document doc = new Document();
            List<String> plotIds = new ArrayList<>();
            for (Plot plot : plots) {
                if(!plotIds.contains(plot.getId().toString()))
                    plotIds.add(plot.getId().toString());
            }
            doc.append("plotIds", plotIds);
            return doc;
        }

    public List<Plot> documentToPlot(Document document){
        List<Plot> plots = new ArrayList<>();
        if(document != null){
            List<String> plotIds = document.getList("plotIds", String.class);
            if(plotIds != null){
                plotIds.forEach(id -> {
                    plotRepository.findById(UUID.fromString(id)).ifPresent(plots::add);
                });
            }
        }
        return plots;
    }

    public List<Plot> getHisPlot(Player player){
        User user = userManager.getUser(player);
        if(user.hasAttribute("plots")){
            return documentToPlot((Document) user.getAttribute("plots"));
        }
        return null;
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
