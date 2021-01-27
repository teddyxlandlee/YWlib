package bilibili.ywsuoyi;

import bilibili.ywsuoyi.feature.nbt.nbtFeatuer;
import bilibili.ywsuoyi.reg.QuickReg;
import net.fabricmc.fabric.api.biomes.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biomes.v1.OverworldClimate;
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
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * @see QuickReg
 * @deprecated use {@link QuickReg} for better compatibility!
 */
@Deprecated
public class qr {
    public static String ID = "";
    public static void setId(String id){
        ID=id;
    }
    public static void ri(String name, Item item){
        Registry.register(Registry.ITEM,new Identifier(ID,name),item);
    }
    public static void rb(String name, Block block,ItemGroup group){
        Registry.register(Registry.BLOCK,new Identifier(ID,name),block);
        Registry.register(Registry.ITEM, new Identifier(ID, name), new BlockItem(block, new Item.Settings().group(group)));
    }
    public static Biome rbio(String name, Biome biome, OverworldClimate climate,double weight){
        OverworldBiomes.addContinentalBiome(biome,climate,weight);
        return Registry.register(Registry.BIOME, new Identifier(ID, name), biome);
    }

    public static StructureFeature<DefaultFeatureConfig> rnbt(String name,Identifier nbt,Identifier loottable,int chance){
        StructureFeature<DefaultFeatureConfig> f = new nbtFeatuer(DefaultFeatureConfig.CODEC,nbt,loottable,chance);
        StructureFeature.STRUCTURES.put(name.toLowerCase(Locale.ROOT), f);
        return Registry.register(Registry.STRUCTURE_FEATURE, name.toLowerCase(Locale.ROOT), f);
    }
    public static <T extends BlockEntity> BlockEntityType<T> rbe(String name, Supplier<T> supplier, Block block){
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(ID, name), BlockEntityType.Builder.create(supplier, block).build(null));
    }

    public static <T extends ScreenHandler> ScreenHandlerType<T> rsh(String name, ScreenHandlerRegistry.ExtendedClientHandlerFactory<T> factory){
        return ScreenHandlerRegistry.registerExtended(new Identifier(ID, name), factory);
    }
}
