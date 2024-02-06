package fr.redbuild.spigot.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.redbuild.models.spigot.Autowired.Autowired;
import fr.redbuild.models.spigot.mode.BuildMode;
import fr.redbuild.models.spigot.packet.PacketUtils;
import fr.redbuild.models.spigot.region.RegionController;
import fr.redbuild.spigot.Main;
import fr.redbuild.spigot.plot.PlotManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;

public class PlayerListener implements Listener {
    @Autowired
    private BuildMode buildMode;

    @Autowired
    private RegionController regionController;

    @Autowired
    private PlotManager plotManager;
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Component header = Component.Serializer.fromJson("[\"\",{\"text\":\"caca\",\"obfuscated\":true,\"color\":\"dark_purple\"},{\"text\":\" \\u2583\\u2585\\u2587\\u2589 \",\"color\":\"gray\"},{\"text\":\"Red\",\"color\":\"dark_red\"},{\"text\":\"Build \",\"color\":\"gold\"},{\"text\":\"\\u2589\\u2587\\u2585\\u2583 \",\"color\":\"gray\"},{\"text\":\"caca\",\"obfuscated\":true,\"color\":\"dark_purple\"}]");
        Component footer = Component.Serializer.fromJson("{\"text\":\"Bienvenue sur RedBuild !\",\"color\":\"gray\"}");
        PacketUtils.sendPacket(event.getPlayer(), new ClientboundTabListPacket(header, footer));
        event.getPlayer().teleport(new Location(Bukkit.getWorld(Main.INSTANCE.getConfig().getString("world.name")), Main.INSTANCE.getConfig().getDouble("world.spawn.x"), Main.INSTANCE.getConfig().getDouble("world.spawn.y"), Main.INSTANCE.getConfig().getDouble("world.spawn.z"), Main.INSTANCE.getConfig().getInt("world.spawn.yaw"), Main.INSTANCE.getConfig().getInt("world.spawn.pitch")));
    }

    @EventHandler
    public void onEntityExplose(EntityExplodeEvent event) {
        if (buildMode.isSafeRegion(regionController.getRegion(event.getLocation())) || plotManager.isInPlot(event.getLocation()))
            event.setCancelled(true);
    }
}
