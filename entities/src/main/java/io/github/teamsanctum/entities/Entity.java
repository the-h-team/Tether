package io.github.teamsanctum.entities;

import org.jetbrains.annotations.ApiStatus;

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
    }
}
