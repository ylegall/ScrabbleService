package org.ygl;

import java.io.File;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * scans the list of words to find matches, sorting by score.
 */
public class ScanningScrabbleIndex implements ScrabbleIndex {

    private List<ScrabbleWord> words;

    /**
     * parses the specified input file, filtering out words with an
     * apostrophe as those are prohibited by the official scrabble rules.
     * example file: http://www-01.sil.org/linguistics/wordlists/english/wordlist/wordsEn.txt
     * @param filename file containing valid words, one per line
     * @throws Exception
     */
    ScanningScrabbleIndex(String filename) throws Exception {
        WordScore wordScore = new WordScore();

        File file = new File(filename);
        words = Files.lines(file.toPath())
                .filter(line -> !line.isEmpty() && !line.contains("'"))
                .map(String::toLowerCase)
                .map(word -> new ScrabbleWord(word, wordScore.getScore(word), getLetterCounts(word)))
                .sorted(Comparator.comparingInt(ScrabbleWord::getScore).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Get a list of scrabble words that can be made with the specified letters,
     * sorted by their scrabble score from highest to lowest.
     * @param letters A String of letters used to make the words
     * @return A list of matching words
     */
    @Override
    public List<String> getMatchingWords(String letters) {
        int[] counts = getLetterCounts(letters);

        return words.stream()
                .filter(word -> canMakeWord(counts, word))
                .map(ScrabbleWord::getWord)
                .collect(Collectors.toList());
    }

    private boolean canMakeWord(int[] counts, ScrabbleWord word) {
        int[] required = word.getCounts();
        for (int i = 0; i < required.length; ++i) {
            if (counts[i] < required[i]) return false;
        }
        return true;
    }

    private int[] getLetterCounts(String word) {
        int[] counts = new int[26];
        for (char c : word.toCharArray()) {
            counts[(c - 'a')] += 1;
        }
        return counts;
    }
}

/**
 * in memory representation of a valid scrabble word, including
 * its score and letter counts
 */
class ScrabbleWord {
    private int score;
    private int[] counts;
    private String word;

    public ScrabbleWord(String word, int score, int[] counts) {
        this.score = score;
        this.word = word;
        this.counts = counts;
    }

    public int getScore() {
        return score;
    }

    public int[] getCounts() {
        return counts;
    }

    public String getWord() {
        return word;
    }
}