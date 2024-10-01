package io.github.teamsanctum.entities;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.function.Supplier;

/**
 * Represents a wrapped property value.
 *
 * @since 1.0.0
 * @author ms5984
 * @param <T> the value type
 */
@ApiStatus.NonExtendable
public interface Value<T> extends Supplier<T> {
    /**
     * Represents a value for a property that does not accept null.
     *
     * @see #of(Object)
     * @param <T> the property value type
     */
    @ApiStatus.NonExtendable
    interface Required<T> extends Value<T> {
        /**
         * Gets the value in this wrapper.
         *
         * @return the value
         */
        @Override
        @NotNull T get();

        @Override
        default @NotNull Required<T> toNotNull() {
            return this;
        }
    }

    /**
     * Represents a value for a property that accepts nulls.
     *
     * @see #optional(Object)
     * @see #empty()
     * @param <T> the property value type
     */
    @ApiStatus.NonExtendable
    interface OrNull<T> extends Value<T> {
        /**
         * Gets the value in this wrapper, if any.
         *
         * @return the value or null
         */
        @Override
        @Nullable T get();
    }

    /**
     * Gets the value in this wrapper.
     * <p>
     * The nullability of this method is implementation-specific.
     *
     * @return the value, if any
     */
    @Override
    @UnknownNullability T get();

    /**
     * Coaxes this value wrapper to one that does not accept nulls.
     * <p>
     * If this wrapper does not have a value, this method returns {@code null}.
     * <p>
     * This method may return this object if it already does not accept nulls.
     *
     * @return a null-resistant wrapper or null
     */
    @Nullable Value.Required<T> toNotNull();

    /**
     * Coaxes this value wrapper to one that accepts nulls.
     * <p>
     * This method may return this object if it already accepts null.
     *
     * @return a null-tolerant wrapper
     */
    @NotNull Value.OrNull<T> toNullable();

    /**
     * Checks if the value in this wrapper equals that in another wrapper.
     * <p>
     * This method ignores differences in wrapper type ({@linkplain Required}
     * and {@linkplain OrNull}).
     *
     * @param wrapper another wrapper
     * @return true if the values are the same
     */
    boolean isSimilar(@NotNull Value<?> wrapper);

    /**
     * Checks if this wrapper has a value.
     *
     * @return true if the wrapper has a value
     */
    default boolean isPresent() {
        return get() != null;
    }

    /**
     * Checks if this wrapper does not have a value.
     *
     * @return true if the wrapper does not have a value
     */
    default boolean isEmpty() {
        return get() == null;
    }

    /**
     * Wraps a value for a property whose values must be nonnull.
     *
     * @param value a value
     * @param <T> the value type
     * @return a value wrapper
     */
    static <T> Value.Required<T> of(@NotNull T value) {
        return new ValueImpl.RequiredImpl<>(value);
    }

    /**
     * Wraps a value for a property that supports null values.
     *
     * @param value a value or null
     * @param <T> the value type
     * @return a value wrapper
     */
    static <T> Value.OrNull<T> optional(@Nullable T value) {
        return new ValueImpl.OrNullImpl<>(value);
    }

    /**
     * Represents an empty value.
     *
     * @param <R> the value type
     * @return a value wrapper
     */
    static <R> Value.OrNull<R> empty() {
        return optional(null);
    }
}
