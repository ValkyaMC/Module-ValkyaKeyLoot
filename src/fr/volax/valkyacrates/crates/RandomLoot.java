package fr.volax.valkyacrates.crates;

import java.util.List;
import java.util.Random;

public class RandomLoot {
    private static Random rand = new Random();

    public static ItemCrate randomLoot(Crate crate){
        List<ItemCrate> itemCrates = crate.getItems();

        int rdm = randInt(1, itemCrates.size());
        ItemCrate itemCrate = itemCrates.get(rdm - 1);
        int chance = randInt(0, 10000);

        while (chance > itemCrate.getRarity()){
            rdm = randInt(1, itemCrates.size());
            itemCrate = itemCrates.get(rdm - 1);
            chance = randInt(0, 10000);
        }
        return itemCrate;
    }

    public static int randInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
}
