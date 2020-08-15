package bilibili.ywsuoyi.feature.nbt;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.Random;

public class piece extends SimpleStructurePiece {
    private final Identifier template;
    private final BlockRotation rotation;
    private Identifier loot;

    public piece(StructureManager manager, Identifier identifier,Identifier loot, BlockPos pos, BlockRotation rotation) {
        super(nbtFeatuer.YWlibPoece, 0);
        this.template = identifier;
        this.loot=loot;
        this.pos = pos;
        this.rotation = rotation;
        this.initializeStructureData(manager);
    }

    public piece(StructureManager manager, CompoundTag tag) {
        super(nbtFeatuer.YWlibPoece, tag);
        this.template = new Identifier(tag.getString("Template"));
        this.rotation = BlockRotation.valueOf(tag.getString("Rot"));
        this.initializeStructureData(manager);
    }

    public void initializeStructureData(StructureManager manager) {
        Structure structure = manager.getStructureOrBlank(this.template);
        StructurePlacementData structurePlacementData = (new StructurePlacementData()).setRotation(this.rotation).setMirror(BlockMirror.NONE).setPosition(pos).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
        this.setStructureData(structure, this.pos, structurePlacementData);
    }

    public void toNbt(CompoundTag tag) {
        super.toNbt(tag);
        tag.putString("Template", this.template.toString());
        tag.putString("Rot", this.rotation.name());
    }

    public void handleMetadata(String metadata, BlockPos pos, WorldAccess world, Random random, BlockBox boundingBox) {
        if ("chest".equals(metadata)) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
            BlockEntity blockEntity = world.getBlockEntity(pos.down());
            if (blockEntity instanceof ChestBlockEntity) {
                ((ChestBlockEntity)blockEntity).setLootTable(loot, random.nextLong());
            }

        }
    }

    public boolean generate(ServerWorldAccess serverWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
        int yHeight = serverWorldAccess.getTopY(Heightmap.Type.WORLD_SURFACE_WG, this.pos.getX() + 8, this.pos.getZ() + 8);
        this.pos = this.pos.add(0, yHeight - 1, 0);
        return super.generate(serverWorldAccess, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, blockPos);
    }
}

