package fr.volax.valkyacrates;

import fr.volax.valkyacrates.crates.Crate;
import fr.volax.valkyacrates.crates.ItemCrate;
import fr.volax.valkyacrates.crates.RandomLoot;
import net.minecraft.server.v1_7_R4.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Timmmer {
    int task;
    float y,  size, timer;
    Player player;
    Crate crate;

    public Timmmer(Player player, Crate crate) {
        this.task = 0;
        this.y = 0;
        this.timer = 0;
        this.size = 2;
        this.player = player;
        this.crate = crate;
    }

    public void start(){
        Location location = player.getLocation();
        y = (float) location.getY();
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(ValkyaCrates.getInstance(), () -> {
            ArrayList<PacketPlayOutWorldParticles> packets = new ArrayList<>();

            for (float i = size; i > -size; i = (float) (i - 0.2)) packets.add(new PacketPlayOutWorldParticles(crate.getParticle(),  (float) location.getX() + i, y, (float) location.getZ() + size, (float) 0, (float) 0,(float) 0,0,10));
            for (float i = size; i > -size; i = (float) (i - 0.2)) packets.add(new PacketPlayOutWorldParticles(crate.getParticle(),  (float) location.getX() + i, y, (float) location.getZ() - size, (float) 0, (float) 0,(float) 0,0,10));
            for (float i = size; i > -size; i = (float) (i - 0.2)) packets.add(new PacketPlayOutWorldParticles(crate.getParticle(),  (float) location.getX() + size, y, (float) location.getZ() + i, (float) 0, (float) 0,(float) 0,0,10));
            for (float i = size; i > -size; i = (float) (i - 0.2)) packets.add(new PacketPlayOutWorldParticles(crate.getParticle(),  (float) location.getX() - size, y, (float) location.getZ() + i, (float) 0, (float) 0,(float) 0,0,10));

            packets.forEach(packetPlayOutWorldParticles -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutWorldParticles));
            y = (float) (y + 0.2);
            size = (float) (size - 0.1);
            timer = (float) (timer + 0.2);

            if(timer >= 4){
                Bukkit.getScheduler().cancelTask(task);
                ItemCrate itemCrate = RandomLoot.randomLoot(crate);
                itemCrate.getCommands().forEach(s -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s.replaceAll("%player%", player.getName()).replaceAll("%amount%", String.valueOf(itemCrate.getItemStack().getAmount())).replaceAll("%displayName%", itemCrate.getDisplayName()).replaceAll("&", "§")));
                player.getInventory().addItem(itemCrate.getItemStack());
            }
        },0,3);
    }
}
