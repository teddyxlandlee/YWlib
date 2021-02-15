package bilibili.ywsuoyi.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.BiFunction;

public class HackableSapling extends SaplingBlock {
    public HackableSapling(SaplingGenerator generator, Settings settings) {
        super(generator, settings);
    }

    public HackableSapling(BiFunction<Random, Boolean, ConfiguredFeature<TreeFeatureConfig, ?>> func, Settings settings) {
        this(new Generator(func), settings);
    }

    public static class Generator extends SaplingGenerator {
        BiFunction<Random, Boolean, ConfiguredFeature<TreeFeatureConfig, ?>> biFunction;

        public Generator(BiFunction<Random, Boolean, ConfiguredFeature<TreeFeatureConfig, ?>> function) {
            biFunction = function;
        }

        public static Generator of(@Nullable ConfiguredFeature<TreeFeatureConfig, ?> feature) {
            return new Generator((random, aBoolean) -> feature);
        }

        public static Generator of(TreeFeatureConfig treeFeatureConfig) {
            return new Generator((random, aBoolean) -> Feature.TREE.configure(treeFeatureConfig));
        }

        public static Generator of(BiFunction<Random, Boolean, TreeFeatureConfig> function) {
            return new Generator((random, aBoolean) -> Feature.TREE.configure(function.apply(random, aBoolean)));
        }

        @Override
        protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
            return biFunction.apply(random, bl);
        }
    }
}
