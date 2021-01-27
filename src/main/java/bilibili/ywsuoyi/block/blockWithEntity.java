package bilibili.ywsuoyi.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;

public abstract class blockWithEntity extends BlockWithEntity {
    public blockWithEntity(Settings settings) {
        super(settings);
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
