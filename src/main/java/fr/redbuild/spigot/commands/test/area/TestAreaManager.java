package fr.redbuild.spigot.commands.test.area;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.redbuild.models.spigot.block.Area;
import fr.redbuild.models.spigot.logger.CtMsg;
public class TestAreaManager {
    private Map<Player, Location> pos1 = new HashMap<>();
    private Map<Player, Location> pos2 = new HashMap<>();
    
    public void setPos1(Player player){
        pos1.put(player, player.getLocation());
        CtMsg.sendMiniMessage("<green>Pos1 set !", player);
    }

    public void setPos2(Player player){
        pos2.put(player, player.getLocation());
        CtMsg.sendMiniMessage("<green>Pos2 set !", player);
    }

    public void createArea(Player player){
        if(pos1.containsKey(player) && pos2.containsKey(player)){
            Area area = new Area(pos1.get(player), pos2.get(player));
            area.paste(player.getLocation());
            CtMsg.sendMiniMessage("<green>Area copy paste !", player);
            pos1.remove(player);
            pos2.remove(player);
        }
    }

}
