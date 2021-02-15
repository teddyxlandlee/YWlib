package bilibili.ywsuoyi.mixin;

import net.minecraft.advancement.Advancement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Advancement.Task.class)
public interface AdvancementTaskAccessor {
    @Accessor
    void setRequirements(String[][] req);

    @Accessor
    String[][] getRequirements();
}