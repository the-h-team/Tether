package io.github.teamsanctum.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueImplTest {
    @Test
    void get() {
        final String test = "test";
        assertEquals(test, Value.of(test).get());
        assertEquals(test, Value.orNull(test).get());
        // null
        assertNull(Value.empty().get());
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void coax() {
        final String test = "test";
        final Value.Always<String> always = Value.of(test);
        final Value.OrNull<String> orNull = Value.orNull(test);
        // test identity
        assertInstanceOf(Value.OrNull.class, always.toNullable());
        assertInstanceOf(Value.Always.class, orNull.toNotNull());
        // test deep equality
        assertEquals(always.get(), always.toNullable().get());
        assertEquals(orNull.get(), orNull.toNotNull().get());
    }

    @Test
    void isSimilar() {
        final String test = "test";
        final String test2 = "test2";
        final Value.Always<String> always = Value.of(test);
        final Value.OrNull<String> orNull = always.toNullable();
        assertTrue(always.isSimilar(orNull));
        assertFalse(always.isSimilar(Value.of(test2)));
    }

    @Test
    void isPresent() {
        final String test = "test";
        final Value.Always<String> always = Value.of(test);
        final Value.OrNull<String> orNull = Value.orNull(test);
        assertTrue(always.isPresent());
        assertTrue(orNull.isPresent());
        assertFalse(Value.empty().isPresent());
    }

    @Test
    void isEmpty() {
        final String test = "test";
        final Value.Always<String> always = Value.of(test);
        final Value.OrNull<String> orNull = Value.orNull(test);
        assertFalse(always.isEmpty());
        assertFalse(orNull.isEmpty());
        assertTrue(Value.empty().isEmpty());
        assertTrue(Value.orNull(null).isEmpty());
    }

    @Test
    void eq() {
        final String test = "test";
        final Value.Always<String> always = Value.of(test);
        final Value.OrNull<String> orNull = Value.orNull(test);
        assertEquals(always, Value.of(test));
        assertEquals(orNull, Value.orNull(test));
    }
}
