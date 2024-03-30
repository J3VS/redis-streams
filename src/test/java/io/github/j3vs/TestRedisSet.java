package io.github.j3vs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRedisSet {
    private static RedisSet set;

    @BeforeAll
    public static void initSet() {
        set = new RedisSet("localhost", 6379, "test-set");
    }

    @AfterEach
    public void clearSet() {
        set.clear();
    }

    @Test
    public void testAddAndRetrieve() {
        set.add("one");
        set.add("two");
        set.add("two");

        assertEquals(2, set.size());
    }

    @Test
    public void testIterator() {
        set.add("one");
        set.add("two");
        set.add("three");
        set.add("four");
        set.add("five");

        List<String> asList = new ArrayList<>(set);
        List<String> sorted = asList.stream().sorted().toList();

        assertEquals(5, sorted.size());
        assertEquals("five", sorted.get(0));
        assertEquals("four", sorted.get(1));
        assertEquals("one", sorted.get(2));
        assertEquals("three", sorted.get(3));
        assertEquals("two", sorted.get(4));
    }

}
