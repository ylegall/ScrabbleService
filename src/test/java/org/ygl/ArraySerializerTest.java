package org.ygl;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 */
class ArraySerializerTest {

    @Test
    void testToJsonArray() {
        ArraySerializer serializer = new ArraySerializer();

        List<String> words = Arrays.asList("cat", "dog", "pig");
        assertEquals("[\"cat\",\"dog\",\"pig\"]", serializer.toJsonArray(words));

        words = Collections.singletonList("cat");
        assertEquals("[\"cat\"]", serializer.toJsonArray(words));

        words = Arrays.asList(new String[] {});
        assertEquals("[]", serializer.toJsonArray(words));
    }

}