package bilibili.ywsuoyi.mixin;

import bilibili.ywsuoyi.advancement.AdvancementTaskJsonCallback;
import com.google.gson.JsonObject;
import net.minecraft.advancement.Advancement;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Advancement.Task.class)
public class MixinAdvancementTask {
    @Inject(at = @At("RETURN"), method = "fromJson")
    private static void onModifyFromJson(JsonObject obj, AdvancementEntityPredicateDeserializer predicateDeserializer, CallbackInfoReturnable<Advancement.Task> cir) {
        AdvancementTaskJsonCallback.EVENT.invoker().setupCriteria(cir.getReturnValue(), predicateDeserializer.getAdvancementId(), obj, predicateDeserializer);
    }
}
