package io.github.teamsanctum.entities;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

/**
 * An editing utility object.
 *
 * @since 1.0.0
 * @author ms5984
 */
public interface Edits {
    /**
     * Gets a map view of the edits in this utility.
     * <p>
     * Keys represent property names; values describe a desired mapping as
     * {@linkplain Supplier Suppliers}. These may return null, but key-values
     * should not be directly assigned to {@code null}.
     * <p>
     * The map may be read-only.
     *
     * @return a map view of edits
     */
    @NotNull Map<String, Supplier<? extends @Nullable Object>> getEdits();

    /**
     * Finalizes the edits in this utility into a staging object.
     * <p>
     * An update is not processed until {@link StagedUpdate#process()}
     * is called.
     *
     * @param updating the entity being updated
     * @return a staging object
     */
    @Contract("_ -> new")
    @NotNull StagedUpdate stage(@NotNull Entity.Mutable updating);
}
