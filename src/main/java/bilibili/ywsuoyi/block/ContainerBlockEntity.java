package bilibili.ywsuoyi.block;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;

/**
 * @see AbstractLockableContainerBlockEntity
 */
public abstract class ContainerBlockEntity extends LootableContainerBlockEntity implements ExtendedScreenHandlerFactory {
    private DefaultedList<ItemStack> inventory;
    private final int size;

    public ContainerBlockEntity(BlockEntityType<?> type,int size) {
        super(type);
        this.inventory = DefaultedList.ofSize(size, ItemStack.EMPTY);
        this.size=size;
    }


    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        if (!this.serializeLootTable(tag)) {
            Inventories.toTag(tag, this.inventory);
        }

        return tag;
    }

    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.deserializeLootTable(tag)) {
            Inventories.fromTag(tag, this.inventory);
        }

    }

    public int size() {
        return this.size;
    }

    public DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    public void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }
}
