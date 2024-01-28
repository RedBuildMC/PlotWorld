package fr.redbuild.spigot;

import net.kyori.adventure.text.minimessage.MiniMessage;
import fr.redbuild.models.spigot.plugin.PluginController;
import fr.redbuild.models.spigot.scoreboard.ScoreBoard;
import fr.redbuild.models.spigot.utils.injector.Injector;
import fr.redbuild.models.spigot.utils.mongo.CodecController;
import fr.redbuild.spigot.plot.Plot;
import fr.redbuild.spigot.plot.PlotManager;
import fr.redbuild.models.spigot.world.WorldManager;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

@SuppressWarnings("deprecation")
public class Main extends PluginController {

    public static Main INSTANCE;
    final int[] origineParcelle = { 6, 6 };
    final int tailleParcelle = 44;
    final int intervalle = 12;
    @Override
    public void onLoad() {
        setPluginPackage("fr.redbuild.spigot");
    }

    @Override
    public void pluginStart() {
        System.out.println("Plugin enable");
        if(getServer().getWorld(getConfig().getString("plotworld")) == null){
            Injector.getInstance(WorldManager.class).addWorld(getConfig().getString("plotworld.name"),getConfig().getString("plotworld.name"),getConfig().getString("plotworld.name"),"");
        }
        INSTANCE = this;
        new ScoreBoard("main", Injector.getInstance(MiniMessage.class).deserialize("<dark_red>Red<gold>Build"))
                .addLine(2, "grade : Aucun")
                .addLine(1,
                        ChatColor.DARK_RED + "Red" + ChatColor.GOLD + "Build" + ChatColor.WHITE + "." + ChatColor.GOLD
                                + "org") 
                .addLine(0, ChatColor.RED + "[1.19.4]").registerAll();
        Injector.getInstance(CodecController.class).registerCodecProvider(CodecRegistries.fromProviders(PojoCodecProvider.builder().register(
                        //plot
                        Plot.class
                ).build()));
        Injector.getInstance(PlotManager.class).initPlot(new Location(Bukkit.getServer().getWorld(getConfig().getString("plotworld.name")), origineParcelle[0], -63, origineParcelle[1]),383, tailleParcelle, intervalle, 16);
         getServer().getScheduler().runTaskTimer(this, () -> {
            Bukkit.broadcast(Injector.getInstance(MiniMessage.class).deserialize("<bold><dark_red>Red<gold>Build <gray> » <yellow>Le serveur est en cours de développement, merci de votre compréhension."));
        }, 0, 6000);
    }

    @Override
    public String dbName() {
        return getConfig().getString("database.name");
    }

    @Override
    public void pluginStop() {
    }

}
