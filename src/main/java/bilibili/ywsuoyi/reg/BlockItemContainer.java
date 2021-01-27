package bilibili.ywsuoyi.reg;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class BlockItemContainer {
    private final Block block;
    private final Item item;

    public BlockItemContainer(Block block, Item item) {
        this.block = block;
        this.item = item;
    }

    public Block getBlock() {
        return block;
    }

    public Item getItem() {
        return item;
    }
}
