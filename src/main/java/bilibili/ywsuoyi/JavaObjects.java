package bilibili.ywsuoyi;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class JavaObjects {
    public static <T> T requireNonNullElse(@Nullable T obj, @NotNull T defaultObj) {
        return (obj != null) ? obj : Objects.requireNonNull(defaultObj, "defaultObj");
    }
}
