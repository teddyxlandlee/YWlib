package bilibili.ywsuoyi.client.endpoem;

import com.mojang.serialization.Lifecycle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;

@Environment(EnvType.CLIENT)
public class CreditsRegistry {
    public static final Registry<Identifier> CREDITS_RESOURCES;
    static final RegistryKey<Registry<Identifier>> CREDITS_RESOURCES_KEY;

    static {
        CREDITS_RESOURCES_KEY = RegistryKey.ofRegistry(new Identifier("ywlib", "credits_resources"));
        CREDITS_RESOURCES = new SimpleRegistry<>(CREDITS_RESOURCES_KEY, Lifecycle.experimental());
    }
}