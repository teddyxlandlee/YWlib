package bilibili.ywsuoyi.feature.nbt;

import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.List;

public class start extends StructureStart<DefaultFeatureConfig> {
    public final nbtFeatuer f;
    public start(StructureFeature<DefaultFeatureConfig> feature, int chunkX, int chunkZ, BlockBox box, int references, long seed) {
        super(feature, chunkX, chunkZ, box, references, seed);
        this.f= (nbtFeatuer) feature;
    }

    @Override
    public void init(ChunkGenerator chunkGenerator, StructureManager structureManager, int x, int z, Biome biome, DefaultFeatureConfig featureConfig) {
        int k = x * 16;
        int l = z * 16;
        BlockPos blockPos = new BlockPos(k, 0, l);
        BlockRotation blockRotation = BlockRotation.random(this.random);
        addPieces(structureManager, blockPos, blockRotation, this.children,f.nbt,f.loot);
        this.setBoundingBoxFromChildren();
    }

    public static void addPieces(StructureManager manager, BlockPos pos, BlockRotation rotation, List<StructurePiece> pieces, Identifier nbt, Identifier loot) {
        pieces.add(new piece(manager, nbt,loot, pos, rotation));
    }
}
