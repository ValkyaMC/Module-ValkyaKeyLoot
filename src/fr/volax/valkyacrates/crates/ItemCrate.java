package fr.volax.valkyacrates.crates;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemCrate {
    private ItemStack itemStack;
    private String displayName;
    private List<String> commands;
    private int rarity;

    public ItemCrate(ItemStack itemStack, String displayName, int rarity, List<String> commands) {
        this.itemStack = itemStack;
        this.displayName = displayName;
        this.rarity = rarity;
        this.commands = commands;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}
