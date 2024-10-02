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
        final Value.Always<String> always = Value.of(test);
        final Value.OrNull<String> optional = Value.optional(test);
        // test identity
        assertInstanceOf(Value.OrNull.class, always.toNullable());
        assertInstanceOf(Value.Always.class, optional.toNotNull());
        // test deep equality
        assertEquals(always.get(), always.toNullable().get());
        assertEquals(optional.get(), optional.toNotNull().get());
    }

    @Test
    void isSimilar() {
        final String test = "test";
        final String test2 = "test2";
        final Value.Always<String> always = Value.of(test);
        final Value.OrNull<String> optional = always.toNullable();
        assertTrue(always.isSimilar(optional));
        assertFalse(always.isSimilar(Value.of(test2)));
    }

    @Test
    void isPresent() {
        final String test = "test";
        final Value.Always<String> always = Value.of(test);
        final Value.OrNull<String> optional = Value.optional(test);
        assertTrue(always.isPresent());
        assertTrue(optional.isPresent());
        assertFalse(Value.empty().isPresent());
    }

    @Test
    void isEmpty() {
        final String test = "test";
        final Value.Always<String> always = Value.of(test);
        final Value.OrNull<String> optional = Value.optional(test);
        assertFalse(always.isEmpty());
        assertFalse(optional.isEmpty());
        assertTrue(Value.empty().isEmpty());
        assertTrue(Value.optional(null).isEmpty());
    }

    @Test
    void eq() {
        final String test = "test";
        final Value.Always<String> always = Value.of(test);
        final Value.OrNull<String> optional = Value.optional(test);
        assertEquals(always, Value.of(test));
        assertEquals(optional, Value.optional(test));
    }
}
