package io.github.teamsanctum.entities;

import org.jetbrains.annotations.*;

import java.util.Map;

/**
 * An entity which may have attributes.
 *
 * @since 1.0.0
 * @author ms5984
 */
@ApiStatus.OverrideOnly
@FunctionalInterface
public interface Entity extends EntityLike {
    /**
     * An entity with attributes that may be edited.
     *
     * @since 1.0.0
     */
    @ApiStatus.OverrideOnly
    interface Mutable extends Entity {
        /**
         * Gets an editing utility for this entity.
         *
         * @return a new editing utility
         */
        @Contract("-> new")
        @NotNull Edits<? extends Mutable> edit();
    }

    /**
     * Gets a map view of the attributes of this entity.
     *
     * @return a map view of attributes
     * @implSpec The map view should be read-only.
     */
    @NotNull Map<String, Value<? extends @UnknownNullability Object>> attributes();

    @Override
    default @NotNull Entity asEntity() {
        return this;
    }
}
