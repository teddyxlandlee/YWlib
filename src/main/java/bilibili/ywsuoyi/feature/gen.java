package bilibili.ywsuoyi.feature;

import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.*;

public class gen {
    public static void addNbtFeatureToBiome(Biome biome, StructureFeature<DefaultFeatureConfig> feature) {
        biome.addStructureFeature(feature.configure(DefaultFeatureConfig.INSTANCE));
    }

    public static void addNbtFeatureToOverWorldWithoutBiome(Biome biome, StructureFeature<DefaultFeatureConfig> feature) {
        for (Biome b : Registry.BIOME) {
            if (b.getCategory() != Biome.Category.THEEND && b.getCategory() != Biome.Category.NETHER && b != biome) {
                b.addStructureFeature(feature.configure(DefaultFeatureConfig.INSTANCE));
            }
        }
    }

    public static void addNbtFeatureToOverWorld(StructureFeature<DefaultFeatureConfig> feature) {
        for (Biome b : Registry.BIOME) {
            if (b.getCategory() != Biome.Category.THEEND && b.getCategory() != Biome.Category.NETHER) {
                b.addStructureFeature(feature.configure(DefaultFeatureConfig.INSTANCE));
            }
        }
    }

    public static void addNbtFeatureToTheEnd(StructureFeature<DefaultFeatureConfig> feature) {
        Biomes.THE_END.addStructureFeature(feature.configure(DefaultFeatureConfig.INSTANCE));
    }

    public static void addNbtFeatureToNether(StructureFeature<DefaultFeatureConfig> feature) {
        for (Biome b : Registry.BIOME) {
            if (b.getCategory() == Biome.Category.NETHER) {
                b.addStructureFeature(feature.configure(DefaultFeatureConfig.INSTANCE));
            }
        }
    }

    public static void addOreToBiome(Biome biome, Block block, int size, int count, int bottom, int top) {
        biome.addFeature(
                GenerationStep.Feature.UNDERGROUND_ORES,
                Feature.ORE
                        .configure(new OreFeatureConfig(OreFeatureConfig.Target.NATURAL_STONE, block.getDefaultState(), size))
                        .createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(count, bottom, 0, top))));
    }

    public static void addOreToToTheEnd(Block block, int size, int count, int bottom, int top) {
                Biomes.THE_END.addFeature(
                        GenerationStep.Feature.UNDERGROUND_ORES,
                        Feature.ORE
                                .configure(new OreFeatureConfig(OreFeatureConfig.Target.NATURAL_STONE, block.getDefaultState(), size))
                                .createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(count, bottom, 0, top))));
    }
    public static void addOreToToOverNether(Block block, int size, int count, int bottom, int top) {
        for (Biome b : Registry.BIOME) {
            if (b.getCategory() == Biome.Category.NETHER) {
                b.addFeature(
                        GenerationStep.Feature.UNDERGROUND_ORES,
                        Feature.ORE
                                .configure(new OreFeatureConfig(OreFeatureConfig.Target.NATURAL_STONE, block.getDefaultState(), size))
                                .createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(count, bottom, 0, top))));
            }
        }
    }
    public static void addOreToToOverWorld(Block block, int size, int count, int bottom, int top) {
        for (Biome b : Registry.BIOME) {
            if (b.getCategory() != Biome.Category.THEEND && b.getCategory() != Biome.Category.NETHER) {
                b.addFeature(
                        GenerationStep.Feature.UNDERGROUND_ORES,
                        Feature.ORE
                                .configure(new OreFeatureConfig(OreFeatureConfig.Target.NATURAL_STONE, block.getDefaultState(), size))
                                .createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(count, bottom, 0, top))));
            }
        }
    }
    public static void addOreToToOverWorldWithoutBiome(Biome biome, Block block, int size, int count, int bottom, int top) {
        for (Biome b : Registry.BIOME) {
            if (b.getCategory() != Biome.Category.THEEND && b.getCategory() != Biome.Category.NETHER && b != biome) {
                b.addFeature(
                        GenerationStep.Feature.UNDERGROUND_ORES,
                        Feature.ORE
                                .configure(new OreFeatureConfig(OreFeatureConfig.Target.NATURAL_STONE, block.getDefaultState(), size))
                                .createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(count, bottom, 0, top))));
            }
        }
    }
}
