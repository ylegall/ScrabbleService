package org.ygl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 */
class WordScoreTest {

    @Test
    void testGetScore() {
        WordScore wordScore = new WordScore();
        assertEquals(6, wordScore.getScore("hat"));
        assertEquals(7, wordScore.getScore("code"));
        assertEquals(39, wordScore.getScore("antidisestablishmenatarianism"));
        assertEquals(-1, wordScore.getScore("basic's"));
    }

}