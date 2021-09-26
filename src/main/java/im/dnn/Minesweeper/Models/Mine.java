package im.dnn.Minesweeper.Models;

import im.dnn.Minesweeper.Constants.Keys;
import im.dnn.Minesweeper.Minesweeper;
import im.dnn.Minesweeper.Utils.Logger;
import im.dnn.Minesweeper.Utils.Settings;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Mine {
    private final Minesweeper context;
    private final ItemStack item;
    private final Material material;

    public Mine (Minesweeper context, Material material) {
        this.context = context;
        this.material = material;

        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;

        String displayName = Settings.getString(Keys.MINES_DISPLAY_NAME).replaceAll("<material>", material.name());
        itemMeta.setDisplayName(displayName);

        NamespacedKey key = new NamespacedKey(context, Keys.KEY_ITEM_MINE);
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, Keys.KEY_IS_MINE);

        NamespacedKey glowId = new NamespacedKey(context, Keys.KEY_GLOW_ENCHANT);
        Glow glow = new Glow(glowId);
        itemMeta.addEnchant(glow, 1, true);

        item.setItemMeta(itemMeta);

        this.item = item;
    }

    public String getName() {
        return this.material.name();
    }

    public ItemStack getItem (int amount) {
        this.item.setAmount(amount);
        return this.item;
    }

    public static boolean isMine (Minesweeper ctx, ItemStack item) {
        NamespacedKey key = new NamespacedKey(ctx, Keys.KEY_ITEM_MINE);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if(container.has(key, PersistentDataType.INTEGER)) {
            return Objects.equals(container.get(key, PersistentDataType.INTEGER), Keys.KEY_IS_MINE);
        }

        return false;
    }

    public ShapedRecipe getRecipe () {
        String shapeId = Keys.KEY_ITEM_MINE + this.material.name();
        NamespacedKey key = new NamespacedKey(context, shapeId);
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("TTT", "TBT", "TTT");

        recipe.setIngredient('T', Material.TNT);
        recipe.setIngredient('B', this.material);

        return recipe;
    }
}
