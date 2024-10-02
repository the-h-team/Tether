package io.github.teamsanctum.entities;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * An object that may be resolved to an entity.
 *
 * @author ms5984
 * @since 1.0.0
 */
@ApiStatus.OverrideOnly
@FunctionalInterface
public interface EntityLike {
    /**
     * Gets the entity for this object.
     * <p>
     * If the object is itself an entity, this method returns {@code this}.
     *
     * @return the entity for this object
     */
    @NotNull Entity asEntity();
}
