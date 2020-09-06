package fr.volax.valkyacrates.crates;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Crate {
    private String name,displayName, rarity, particle;
    private List<ItemCrate> items;
    private ItemStack icon;

    public Crate(String displayName, String name, String rarity, ItemStack icon, String particle) {
        this.displayName = displayName;
        this.name = name;
        this.rarity = rarity;
        this.icon = icon;
        this.particle = particle;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for(ItemCrate itemCrate : items){
            stringBuilder.append("§6").append(itemCrate.getDisplayName()).append("§e: §6").append(itemCrate.getRarity()).append("§e%");
        }
        return "§eNom:§6 " + name + "\n§eRareté:§6 " + rarity + "\n§eItems:\n" + stringBuilder.toString();
    }

    public String getRarity() {
        return rarity;
    }

    public List<ItemCrate> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public void setItems(List<ItemCrate> items) {
        this.items = items;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getParticle() {
        return particle;
    }

    public void setParticle(String particle) {
        this.particle = particle;
    }
}
