import io.github.teamsanctum.entities.Entity;
import io.github.teamsanctum.entities.StagedUpdate;
import io.github.teamsanctum.entities.Value;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class EditsTest {
    @Test
    void edit() {
        final TestEntity test = new TestEntity();
        // test original name
        assertEquals("bob", test.getName());
        // edit
        TestEntity.Edits edit = test.edit().setName(Value.of("alice"));
        StagedUpdate stage = edit.stage(test);
        CompletableFuture<@NotNull Map<String, Object>> process = stage.process();
        // without error
        assertFalse(process.isCompletedExceptionally());
        Map<String, Object> join = process.join();
        // test output
        assertEquals("alice", join.get("name"));
        assertEquals("bob", join.get("old-name"));
    }

    static class TestEntity implements Entity.Mutable {
        private String name = "bob";

        @Override
        public @NotNull Map<String, Value<? extends @UnknownNullability Object>> attributes() {
            return Collections.singletonMap("name", Value.of(name));
        }

        @Override
        public @NotNull Edits edit() {
            return new Edits();
        }

        public @NotNull String getName() {
            return name;
        }

        static class Edits implements io.github.teamsanctum.entities.Edits {
            private final Map<String, Supplier<? extends @Nullable Object>> map = new HashMap<>();

            public Edits() {}
            public Edits(Edits copy) {
                this.map.putAll(copy.map);
            }

            public Edits setName(@NotNull Value.Required<String> name) {
                map.put("name", name);
                return this;
            }

            @Override
            public @NotNull Map<String, Supplier<? extends @Nullable Object>> getEdits() {
                return Collections.unmodifiableMap(map);
            }

            @Override
            public @NotNull StagedUpdate stage(@NotNull Mutable updating) {
                return new Staged(this, updating, UUID.randomUUID());
            }
        }

        static class Staged implements StagedUpdate {
            private final Edits edits;
            private final Mutable entity;
            private final UUID id;

            Staged(Edits edits, Mutable entity, UUID id) {
                this.edits = edits;
                this.entity = entity;
                this.id = id;
            }

            @Override
            public @NotNull Entity.Mutable getUpdatingEntity() {
                return entity;
            }

            @Override
            public @NotNull UUID getId() {
                return id;
            }

            @Override
            public @NotNull Edits staged() {
                return new Edits(edits);
            }

            @Override
            public @NotNull CompletableFuture<@NotNull Map<String, Object>> process() {
                return CompletableFuture.supplyAsync(() -> {
                    if (entity instanceof TestEntity) {
                        final TestEntity entity = (TestEntity) this.entity;
                        final String oldName = entity.name;
                        Supplier<?> name = edits.getEdits().get("name");
                        if (name instanceof Value.Required) {
                            Object n = name.get();
                            if (n instanceof String) {
                                entity.name = (String) n;
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("old-name", oldName);
                                map.put("name", n);
                                return map;
                            }
                        }
                    }
                    throw new IllegalArgumentException();
                });
            }
        }
    }
}
