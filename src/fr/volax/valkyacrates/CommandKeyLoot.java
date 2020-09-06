package fr.volax.valkyacrates;

import fr.volax.valkyacrates.crates.Crate;
import fr.volax.valkyacrates.crates.ItemCrate;
import fr.volax.volaxapi.tool.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CommandKeyLoot implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 1){
            if(args[0].equalsIgnoreCase("loots")){
                if(sender instanceof Player){
                    Player player = (Player)sender;
                    Inventory inventory = Bukkit.createInventory(null, 9, "§6Loots - KeyLoot");
                    for(Crate crates : ValkyaCrates.getCrates().getCratesList()){
                        StringBuilder stringBuilder = new StringBuilder();
                        for(ItemCrate itemCrate : crates.getItems())
                            stringBuilder.append("§e"+itemCrate.getItemStack().getAmount() +"§6x §e" + itemCrate.getDisplayName() + "§e: §6" + itemCrate.getRarity() + "§e%\n");

                        ItemStack iconItem = crates.getIcon();
                        ItemStack iconStack = new ItemBuilder(iconItem.getType(), iconItem.getAmount(), iconItem.getDurability()).setName(crates.getDisplayName()).setLore("§eNom:§6 " + crates.getDisplayName(), "§eRareté:§6 " + crates.getRarity(),"", "§eClic pour voir la liste des loots").toItemStack();
                        inventory.addItem(iconStack);
                    }
                    player.openInventory(inventory);
                }else{
                    sender.sendMessage("§cVous devez être un joueur pour executer cette commande !");
                    return false;
                }
            }else{
                helpMessage(sender);
                return false;
            }
        }else if(args.length == 4){
            if(args[0].equalsIgnoreCase("give")){
                if(!sender.hasPermission("valkya.lootkey.give")){
                    sender.sendMessage("§cVous n'avez pas la permission d'éxecuter cette commande");
                    return false;
                }
                for(Crate crate : ValkyaCrates.getCrates().getCratesList()){
                    if(crate.getName().equalsIgnoreCase(args[1])){
                        Player target = Bukkit.getPlayer(args[2]);
                        int amount = Integer.parseInt(args[3]);
                        if(target == null){
                            sender.sendMessage("Le joueur n'existe pas !");
                            return false;
                        }

                        target.getInventory().addItem(new ItemBuilder(crate.getIcon().getType(), amount, crate.getIcon().getDurability()).setName(crate.getDisplayName()).toItemStack());
                    }
                }
            }else{
                helpMessage(sender);
                return false;
            }
        }else{
            helpMessage(sender);
            return false;
        }
        return false;
    }

    private void helpMessage(CommandSender sender){
        String prefix = "§6Valkya » ";
        if(sender.hasPermission("valkya.keyloot.give"))
            sender.sendMessage(prefix + " §e/keyloot give <KEY_NAME> <PLAYER> <AMOUNT>");
        sender.sendMessage(prefix + " §e/keyloot loots");
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Inventory inventory = event.getClickedInventory();
        if(inventory.getTitle().equals("§6Loots - KeyLoot")){
            event.setCancelled(true);
            if(event.getCurrentItem().getType() == Material.AIR) return;

            for(Crate crates : ValkyaCrates.getCrates().getCratesList()){
                if(event.getCurrentItem().getItemMeta().getDisplayName().equals(crates.getDisplayName())){
                    event.getWhoClicked().closeInventory();
                    for(ItemCrate itemCrate : crates.getItems())
                        ((Player)event.getWhoClicked()).sendMessage("§6Valkya » §e" + itemCrate.getItemStack().getAmount() + "§6x §e" + itemCrate.getDisplayName() + "§e: §e" + (float) itemCrate.getRarity() / 100 + "§6%");
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getItem() == null || event.getItem().getType() == Material.AIR) return;
        for(Crate crate : ValkyaCrates.getCrates().getCratesList()){
            if(event.getItem().getType() == crate.getIcon().getType()){
                if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(crate.getDisplayName())){
                    if(event.getItem().getAmount() == 1) event.getPlayer().setItemInHand(null);
                    else event.getItem().setAmount(event.getItem().getAmount() - 1);

                    event.setCancelled(true);
                    new Timmmer(event.getPlayer(), crate).start();
                }
            }
        }
    }
}
