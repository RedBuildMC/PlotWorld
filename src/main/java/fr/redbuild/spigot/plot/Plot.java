package fr.redbuild.spigot.plot;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import fr.redbuild.models.spigot.block.BlockUtils;
import fr.redbuild.models.spigot.region.Region;
import fr.redbuild.models.spigot.utils.injector.Injector;
import fr.redbuild.models.spigot.utils.mongo.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class Plot {
    @BsonId
    @Id
    private UUID id = UUID.randomUUID();
    @BsonProperty("start")
    private Location start;
    @BsonProperty("end")
    private Location end;
    @BsonProperty("spawn")
    private Location spawn;
    @BsonProperty("owners")
    private List<UUID> ownPlayers;
    @BsonProperty("trusteds")
    private List<UUID> trustedPlayers = new ArrayList<>();
    @BsonProperty("addeds")
    private List<UUID> addedPlayers = new ArrayList<>();
    @BsonProperty("banneds")
    private List<UUID> bannedPlayers = new ArrayList<>();
    @BsonProperty("region")
    private Region region;

    public Plot(Location start, Location end, Location spawn, @NotNull List<Player> ownPlayers) {
        this.start = start;
        this.end = end;
        this.spawn = spawn;
        for (Player player : ownPlayers) {
            this.ownPlayers.add(player.getUniqueId());
        }
        this.region = new Region(start, end,null,"plot-" + id.toString(),this.id,false);
    }
    @Deprecated
    public Plot(){
        this.start = null;
        this.end = null;
        this.spawn = null;
        this.ownPlayers = null;
        this.region = null;
    }

    public void resetall() {
        this.spawn = start;
        this.ownPlayers = null;
        this.trustedPlayers = new ArrayList<>();
        this.addedPlayers = new ArrayList<>();
        this.bannedPlayers = new ArrayList<>();
        modifyBorder(Material.SMOOTH_STONE_SLAB, Material.RED_CONCRETE);
        reset();
    }

    public void reset(){
        this.spawn = start;
        this.trustedPlayers = new ArrayList<>();
        this.addedPlayers = new ArrayList<>();
        this.bannedPlayers = new ArrayList<>();
        BlockUtils blockUtils = Injector.getInstance(BlockUtils.class);
        Location s = start;
        Location e = end;
        s.setY(-64);
        e.setY(-64);
        blockUtils.fill(s, e,Material.BEDROCK);
        s.setY(-63);
        e.setY(63);
        blockUtils.fill(s, e,Material.STONE);
        s.setY(64);
        e.setY(64);
        blockUtils.fill(s, e,Material.GRASS_BLOCK);
        s.setY(65);
        e.setY(319);
        blockUtils.fill(s, e,Material.AIR);
    }

    public void modifyBorder(Material top, Material bot) {
        BlockUtils blockUtils = Injector.getInstance(BlockUtils.class);
        var minX = Math.min(start.getBlockX(), end.getBlockX()) -1;
        var minZ = Math.min(start.getBlockZ(), end.getBlockZ()) -1;

        var maxX = Math.max(start.getBlockX(), end.getBlockX()) +1;
        var maxZ = Math.max(start.getBlockZ(), end.getBlockZ()) +1;
        //Top border
        blockUtils.fill(minX, 65, minZ, maxX, 65, minZ, start.getWorld(), top);
        blockUtils.fill(minX, 65, minZ, minX, 65, maxZ, start.getWorld(), top); 
        blockUtils.fill(minX, 65, maxZ, maxX, 65, maxZ, start.getWorld(), top);
        blockUtils.fill(maxX, 65, minZ, maxX, 65, maxZ, start.getWorld(), top);
        //Bot border
        blockUtils.fill(minX, 64, minZ, maxX, -63, minZ, start.getWorld(), bot);
        blockUtils.fill(minX, 64, minZ, minX, -63, maxZ, start.getWorld(), bot);
        blockUtils.fill(minX, 64, maxZ, maxX, -63, maxZ, start.getWorld(), bot);
        blockUtils.fill(maxX, 64, minZ, maxX, -63, maxZ, start.getWorld(), bot);
        
    }
    @BsonIgnore
    public boolean isInside(Location location) {
        return location.getX() >= start.getX() && location.getX() <= end.getX() && location.getZ() >= start.getZ()
                && location.getZ() <= end.getZ();
    }
    @BsonIgnore
    public boolean isInside(Player player){
        return isInside(player.getLocation());
    }
    @BsonIgnore
    public boolean isClaimed() {
        return ownPlayers != null;
    }
    @BsonIgnore
    public boolean isOwner(Player player) {
        if(ownPlayers == null)
            return false;
        return ownPlayers.contains(player.getUniqueId());
    }

    public void addOwner(UUID uuid) {
        ownPlayers.add(uuid);
    }

    public void addTrusted(UUID uuid) {
        trustedPlayers.add(uuid);
    }

    public void addAdded(UUID uuid) {
        addedPlayers.add(uuid);
    }

    public void addBanned(UUID uuid) {
        bannedPlayers.add(uuid);
    }
}
