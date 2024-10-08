package io.github.teamsanctum.entities.util;

import io.github.teamsanctum.entities.Entity;
import io.github.teamsanctum.entities.Value;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utilities to assist with {@link Entity#attributes()} processing.
 *
 * @since 1.0.0
 * @author ms5984
 */
public final class AttributesFilter {
    private AttributesFilter() { throw new IllegalStateException(); }
    /**
     * Filter an output map so that its values are only {@link Value.Always}.
     *
     * @param attributes an attributes map
     * @return a filtered attribute map
     * @see Entity#attributes()
     */
    public static @NotNull Map<String, Value.Always<?>> always(
            @NotNull Map<String, Value<? extends @UnknownNullability Object>> attributes
    ) {
        return attributes.entrySet().stream()
                .filter(e -> Value.isAlways(e.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (Value.Always<?>) e.getValue()));
    }

    /**
     * Filter an output map so that its values are only {@link Value.OrNull}.
     *
     * @param attributes an attributes map
     * @return a filtered attribute map
     * @see Entity#attributes()
     */
    public static @NotNull Map<String, Value.OrNull<?>> orNull(
            @NotNull Map<String, Value<? extends @UnknownNullability Object>> attributes
    ) {
        return attributes.entrySet().stream()
                .filter(e -> Value.isOrNull(e.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (Value.OrNull<?>) e.getValue()));
    }
}
