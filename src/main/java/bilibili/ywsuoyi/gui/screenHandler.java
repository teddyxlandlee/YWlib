package bilibili.ywsuoyi.gui;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

public abstract class screenHandler extends ScreenHandler {
    public Inventory inventory;
    public Inventory playerInv;
    public BlockEntity e;
    public DefaultedList<Integer> autorender = DefaultedList.of();

    public screenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, PacketByteBuf packetByteBuf) {
        super(type, syncId);
        this.inventory = inventory;
        this.playerInv = playerInventory;
        if (packetByteBuf != null)
            this.e = playerInventory.player.world.getBlockEntity(packetByteBuf.readBlockPos());
        inventory.onOpen(playerInventory.player);
        init();
    }

    public screenHandler(ScreenHandlerType<?> oven, int syncId, PlayerInventory playerInventory, Inventory inventory) {
        this(oven, syncId, playerInventory, inventory, null);
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        return super.onButtonClick(player, id);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public void addPlayerInv(int x, int y) {
        int n;
        int m;
        for (n = 0; n < 3; ++n) {
            for (m = 0; m < 9; ++m) {
                this.addSlot(new Slot(this.playerInv, m + n * 9 + 9, x + m * 18, n * 18 + y));
            }
        }

        for (n = 0; n < 9; ++n) {
            this.addSlot(new Slot(this.playerInv, n, x + n * 18, 58 + y));
        }
    }

    public void addrectInv(int x, int y, int row, int list) {
        int n;
        int m;
        for (n = 0; n < row; n++) {
            for (m = 0; m < list; m++) {
                this.addSlot(new Slot(inventory, m + n * list, x + m * 18, y + n * 18));
            }
        }
    }

    public Slot addSlot(Slot slot, int type) {
        autorender.add(type);
        return super.addSlot(slot);
    }

    /**
     * type 0: do not auto render
     * type 1: render as default slot
     * type 2: render as big slot ex.result of furnace
     **/
    @Override
    public Slot addSlot(Slot slot) {
        autorender.add(1);
        return super.addSlot(slot);
    }

    public abstract void init();

}
