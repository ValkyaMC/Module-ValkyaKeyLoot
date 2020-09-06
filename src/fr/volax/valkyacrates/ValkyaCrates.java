package fr.volax.valkyacrates;

import fr.volax.valkyacrates.crates.Crate;
import fr.volax.valkyacrates.crates.Crates;
import fr.volax.valkyacrates.crates.ItemCrate;
import fr.volax.valkyacrates.crates.RandomLoot;
import fr.volax.volaxapi.VolaxAPI;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import fr.volax.volaxapi.tool.config.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ValkyaCrates extends JavaPlugin {
    private static ValkyaCrates instance;
    public static Crates crates;
    private ConfigBuilder configBuilder;

    @Override
    public void onEnable() {
        instance = this;
        VolaxAPI.setInstance(this);
        crates = new Crates();
        configBuilder = new ConfigBuilder(new FileManager(this));

        saveDefaultConfig();
        getCommand("keyloot").setExecutor(new CommandKeyLoot());
        Bukkit.getPluginManager().registerEvents(new CommandKeyLoot(), this);

        Bukkit.getConsoleSender().sendMessage("§a§l----------------------------------------------------------");
        for(String crateList : getConfig().getConfigurationSection("keysloot").getKeys(false)){
            String name = getConfigBuilder().getString("keysloot." + crateList + ".name");
            String displayName = getConfigBuilder().getString("keysloot." + crateList + ".displayName");
            String rarity = getConfigBuilder().getString("keysloot." + crateList + ".rarity");
            String particle = getConfigBuilder().getString("keysloot." + crateList + ".particle");
            int iconID = getConfigBuilder().getInt("keysloot." + crateList + ".itemID");
            int iconMeta = getConfigBuilder().getInt("keysloot." + crateList + "itemMeta");
            ItemStack iconStack = new ItemStack(iconID, 1, (short) iconMeta);
            Crate crate = new Crate(displayName, name, rarity, iconStack, particle);

            Bukkit.getConsoleSender().sendMessage("§aAdd KeyLoot named " + crate.getDisplayName() + "§a("+crate.getName()+") (Rarete:" + rarity + "§a IconID:" + iconID + " IconMeta:" + iconMeta + ")");

            ArrayList<ItemCrate> itemCrates = new ArrayList<>();
            for(String itemList : getConfig().getConfigurationSection("keysloot." + crateList + ".items").getKeys(false)){
                String itemName = getConfigBuilder().getString("keysloot." + crateList + ".items." + itemList + ".displayName");
                int itemID = getConfigBuilder().getInt("keysloot." + crateList + ".items." + itemList + ".itemID");
                int itemMeta = getConfigBuilder().getInt("keysloot." + crateList + ".items." + itemList + ".itemMeta");
                int amount = getConfigBuilder().getInt("keysloot." + crateList + ".items." + itemList + ".amount");
                int chance = getConfigBuilder().getInt("keysloot." + crateList + ".items." + itemList + ".chance");
                List<String> commands = getConfig().getStringList("keysloot." + crateList + ".items." + itemList + ".commands");
                ItemStack itemStack = new ItemStack(itemID, amount, (short) itemMeta);
                itemCrates.add(new ItemCrate(itemStack, itemName, chance, commands));
                Bukkit.getConsoleSender().sendMessage("§aAdd "+itemName+" §ain: " + crate.getDisplayName() + "§a("+crate.getName()+") (ItemID:" + itemID + " ItemMeta:" + itemMeta + " Amount:" + amount + " Chance:"+ (float) chance / 100 +"%)");
            }
            crate.setItems(itemCrates);
            crates.addCrate(crate);
            Bukkit.getConsoleSender().sendMessage("§a§l----------------------------------------------------------");
        }
    }

    public static ValkyaCrates getInstance() {
        return instance;
    }

    public static Crates getCrates() {
        return crates;
    }

    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }
}
