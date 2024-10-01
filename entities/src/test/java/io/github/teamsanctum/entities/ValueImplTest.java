package io.github.teamsanctum.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueImplTest {
    @Test
    void get() {
        final String test = "test";
        assertEquals(test, Value.of(test).get());
        assertEquals(test, Value.optional(test).get());
        // null
        assertNull(Value.empty().get());
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void coax() {
        final String test = "test";
        final Value.Required<String> required = Value.of(test);
        final Value.OrNull<String> optional = Value.optional(test);
        // test identity
        assertInstanceOf(Value.OrNull.class, required.toNullable());
        assertInstanceOf(Value.Required.class, optional.toNotNull());
        // test deep equality
        assertEquals(required.get(), required.toNullable().get());
        assertEquals(optional.get(), optional.toNotNull().get());
    }

    @Test
    void isSimilar() {
        final String test = "test";
        final String test2 = "test2";
        final Value.Required<String> required = Value.of(test);
        final Value.OrNull<String> optional = required.toNullable();
        assertTrue(required.isSimilar(optional));
        assertFalse(required.isSimilar(Value.of(test2)));
    }

    @Test
    void isPresent() {
        final String test = "test";
        final Value.Required<String> required = Value.of(test);
        final Value.OrNull<String> optional = Value.optional(test);
        assertTrue(required.isPresent());
        assertTrue(optional.isPresent());
        assertFalse(Value.empty().isPresent());
    }

    @Test
    void isEmpty() {
        final String test = "test";
        final Value.Required<String> required = Value.of(test);
        final Value.OrNull<String> optional = Value.optional(test);
        assertFalse(required.isEmpty());
        assertFalse(optional.isEmpty());
        assertTrue(Value.empty().isEmpty());
        assertTrue(Value.optional(null).isEmpty());
    }

    @Test
    void eq() {
        final String test = "test";
        final Value.Required<String> required = Value.of(test);
        final Value.OrNull<String> optional = Value.optional(test);
        assertEquals(required, Value.of(test));
        assertEquals(optional, Value.optional(test));
    }
}
