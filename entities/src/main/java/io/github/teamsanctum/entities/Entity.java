package io.github.teamsanctum.entities;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An entity which may have attributes.
 *
 * @since 1.0.0
 * @author ms5984
 */
@ApiStatus.OverrideOnly
public interface Entity {
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
        @NotNull Edits edit();
    }
}
