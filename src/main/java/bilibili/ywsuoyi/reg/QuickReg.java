package bilibili.ywsuoyi.reg;

import bilibili.ywsuoyi.feature.nbt.nbtFeatuer;
import bilibili.ywsuoyi.qr;
import net.fabricmc.fabric.api.biomes.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biomes.v1.OverworldClimate;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.function.Supplier;

import static net.minecraft.util.registry.Registry.*;

/**
 * This one is more compatible than {@link qr} with more
 * than one mod depend on YWlib.
 * @see qr
 * @author YWsuoyi
 * @author teddyxlandlee
 */
public class QuickReg {
    @NotNull
    private final String ID;
    public QuickReg(@NotNull String id) {
        this.ID = id;
    }

    public Item item(@NotNull String name, Item item) {
        return register(ITEM, new Identifier(ID, name), item);
    }

    public Item item(@NotNull String name, Item.Settings settings) {
        return item(name, new Item(settings));
    }

    public Item item(@NotNull String name, ItemGroup itemGroup) {
        return item(name, new Item(new FabricItemSettings().group(itemGroup)));
    }

    public Block blockOnly(@NotNull String name, Block block) {
        return register(BLOCK, new Identifier(ID, name), block);
    }

    public BlockItemContainer blockWithItem(@NotNull String name, Block block, Item.Settings settings) {
        Identifier identifier2 = new Identifier(ID, name);
        return new BlockItemContainer(
                register(BLOCK, identifier2, block),
                register(ITEM, identifier2, new BlockItem(block, settings))
        );
    }

    public BlockItemContainer blockWithItem(@NotNull String name, Block block, ItemGroup itemGroup) {
        return blockWithItem(name, block, new FabricItemSettings().group(itemGroup));
    }

    public <T extends BlockEntity> BlockEntityType<T> blockEntityType(String name, Supplier<T> supplier, Block... blocks) {
        return register(BLOCK_ENTITY_TYPE, new Identifier(ID, name), BlockEntityType.Builder.create(supplier, blocks).build(null));
    }

    /*Below: Directly use qr*/
    public Biome overworldBiome(String name, Biome biome, OverworldClimate overworldClimate, double weight) {
        OverworldBiomes.addContinentalBiome(biome, overworldClimate, weight);
        return register(BIOME, new Identifier(ID, name), biome);
    }

    public StructureFeature<? extends DefaultFeatureConfig> structureFeature(String name, Identifier nbt, Identifier lootable, int chance) {
        StructureFeature<DefaultFeatureConfig> feature = new nbtFeatuer(DefaultFeatureConfig.CODEC, nbt, lootable, chance);
        String lower = name.toLowerCase(Locale.ROOT);
        StructureFeature.STRUCTURES.put(lower, feature);
        return register(STRUCTURE_FEATURE, lower, feature);
    }

    public <T extends ScreenHandler>ScreenHandlerType<T> extendedScreenHandlerType(String name, ScreenHandlerRegistry.ExtendedClientHandlerFactory<T> factory) {
        return ScreenHandlerRegistry.registerExtended(new Identifier(ID, name), factory);
    }
}
