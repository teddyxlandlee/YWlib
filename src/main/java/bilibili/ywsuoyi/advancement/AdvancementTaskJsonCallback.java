package bilibili.ywsuoyi.advancement;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.advancement.Advancement;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.util.Identifier;

@FunctionalInterface
public interface AdvancementTaskJsonCallback {
    Event<AdvancementTaskJsonCallback> EVENT = EventFactory.createArrayBacked(AdvancementTaskJsonCallback.class,
            advancementTaskJsonCallbacks -> (task, id, obj, deserializer) -> {
        for (AdvancementTaskJsonCallback cb : advancementTaskJsonCallbacks)
            cb.setupCriteria(task, id, obj, deserializer);
        });

    void setupCriteria(Advancement.Task task, Identifier id, JsonObject obj, AdvancementEntityPredicateDeserializer deserializer);
}
