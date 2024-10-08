import io.github.teamsanctum.entities.Value;
import io.github.teamsanctum.entities.util.AttributesFilter;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AttributesFilterTest {
    @Test
    void always() {
        final Map<String, Value<?>> attributesMap = new HashMap<>();
        // one Always, two OrNull
        attributesMap.put("name", Value.of("bob"));
        attributesMap.put("password", Value.empty());
        attributesMap.put("greeting", Value.orNull("welcome"));
        Map<String, Value.Always<?>> always = AttributesFilter.always(attributesMap);
        // Always - kept
        assertTrue(always.containsKey("name"));
        // OrNull - note that even non-empty wrapper is filtered out
        assertFalse(always.containsKey("password"));
        assertFalse(always.containsKey("greeting"));
    }

    @Test
    void orNull() {
        final Map<String, Value<?>> attributesMap = new HashMap<>();
        // one Always, one OrNull
        attributesMap.put("name", Value.of("test"));
        attributesMap.put("password", Value.orNull("applesauce"));
        Map<String, Value.OrNull<?>> orNull = AttributesFilter.orNull(attributesMap);
        // Always - filtered out
        assertFalse(orNull.containsKey("name"));
        // OrNull - note that even non-empty wrapper is kept
        assertTrue(orNull.containsKey("password"));
    }
}
