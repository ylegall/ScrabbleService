package org.ygl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 */
class ScanningScrabbleIndexTest {

    private static ScanningScrabbleIndex index;

    @BeforeAll
    static void setup() throws Exception {
        index = new ScanningScrabbleIndex("wordsEn.txt");
    }

    @Test
    void testGetMatchingWords() {
        List<String> matches = index.getMatchingWords("hat");
        List<String> expected = Arrays.asList("hat","ah","ha","th","at","a");
        assertEquals(expected, matches);

        matches = index.getMatchingWords("zzz");
        expected = Collections.emptyList();
        assertEquals(expected, matches);
    }


}