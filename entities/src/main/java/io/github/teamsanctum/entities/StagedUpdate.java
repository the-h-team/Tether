package io.github.teamsanctum.entities;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * A staged update which may include required validation logic.
 *
 * @since 1.0.0
 * @author ms5984
 */
public interface StagedUpdate {
    /**
     * Gets the entity that will be updated.
     *
     * @return the entity
     */
    @NotNull Entity.Mutable getUpdatingEntity();

    /**
     * Gets the unique ID for this update.
     * <p>
     * Such an ID may be used by a {@linkplain #process()} implementation
     * to provide for run-once safety.
     *
     * @return the id of this update
     */
    @NotNull UUID getId();

    /**
     * Gets an {@linkplain Edits} object for this change.
     * <p>
     * The returned object is a new copy.
     *
     * @return a copy of the staged edits
     */
    @Contract("-> new")
    @NotNull Edits staged();

    /**
     * Executes the update.
     * <p>
     * Some updates may yield a map output. The map may be empty but
     * must not be null.
     * <p>
     * If the update fails, the future will complete exceptionally.
     *
     * @return an update future
     */
    @NotNull CompletableFuture<@NotNull Map<String, Object>> process();
}
