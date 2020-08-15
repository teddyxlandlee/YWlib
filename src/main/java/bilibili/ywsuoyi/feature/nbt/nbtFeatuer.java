package bilibili.ywsuoyi.feature.nbt;

import com.mojang.serialization.Codec;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class nbtFeatuer extends StructureFeature<DefaultFeatureConfig> {
    public static final StructurePieceType YWlibPoece = Registry.register(Registry.STRUCTURE_PIECE, "ywlibpoece", piece::new);
    public Identifier nbt;
    public Identifier loot;
    public int r;
    public nbtFeatuer(Codec<DefaultFeatureConfig> codec, Identifier nbt,Identifier loottable,int chance) {
        super(codec);
        this.nbt=nbt;
        this.loot=loottable;
        this.r=chance;
    }
    public GenerationStep.Feature method_28663() {
        return GenerationStep.Feature.SURFACE_STRUCTURES;
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int i, int j, Biome biome, ChunkPos chunkPos, DefaultFeatureConfig featureConfig) {
        return chunkRandom.nextInt(r)==0;
    }

    public StructureFeature.StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return start::new;
    }
}
