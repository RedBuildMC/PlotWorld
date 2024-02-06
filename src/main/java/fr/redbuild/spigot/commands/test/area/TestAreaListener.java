package fr.redbuild.spigot.commands.test.area;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.redbuild.models.spigot.Autowired.Autowired;
import fr.redbuild.models.spigot.Gui.ItemBuilder;

public class TestAreaListener implements Listener{
    @Autowired
    private TestAreaManager testAreaManager;

    // @EventHandler
    // public void onJoin(PlayerJoinEvent event){
    //     new ItemBuilder(Material.ARROW, "Test").onInteract((player,action,e) -> {
    //         if(action.isRightClick()){
    //             testAreaManager.setPos1(player);
    //         }else if(action.isLeftClick()){
    //             testAreaManager.setPos2(player);
    //         }
    //     }).safeGive(event.getPlayer());
    // }
}
