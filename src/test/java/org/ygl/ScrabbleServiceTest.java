package org.ygl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 */
class ScrabbleServiceTest {

    @Test
    void testParsePathValid() {
        assertEquals("dog", ScrabbleService.parsePath("/words/dog"));
        assertEquals("cat", ScrabbleService.parsePath("/words/CaT"));
    }

    @Test
    void testParsePathInvalid() {
        assertEquals("", ScrabbleService.parsePath("/words/"));
        assertEquals("", ScrabbleService.parsePath("/words"));
        assertEquals("", ScrabbleService.parsePath("/word/dog"));
        assertEquals("", ScrabbleService.parsePath("/"));
    }

}