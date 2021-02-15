package bilibili.ywsuoyi.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Item with enchantment glint
 * @author YWsuoyi
 * @author teddyxlandlee
 */
public class EnchantedItem extends Item {

    public EnchantedItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}