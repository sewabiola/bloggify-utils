package com.bloggifyutils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for ReadingTimeCalculator class.
 */
public class ReadingTimeCalculatorTest {

    @Test
    public void testCalculateReadingTimeWithDefaultSpeed() {
        String content = "This is a sample text with exactly twenty words to test the reading time calculation function properly works as expected.";
        int readingTime = ReadingTimeCalculator.calculateReadingTime(content);
        assertEquals(1, readingTime); // 20 words / 200 WPM = 0.1 min, rounds up to 1
    }

    @Test
    public void testCalculateReadingTimeWithCustomSpeed() {
        String content = generateContent(500); // 500 words
        int readingTime = ReadingTimeCalculator.calculateReadingTime(content, 250);
        assertEquals(2, readingTime); // 500 words / 250 WPM = 2 minutes
    }

    @Test
    public void testCalculateReadingTimeWithEmptyContent() {
        assertEquals(0, ReadingTimeCalculator.calculateReadingTime(""));
        assertEquals(0, ReadingTimeCalculator.calculateReadingTime(null));
        assertEquals(0, ReadingTimeCalculator.calculateReadingTime("   "));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateReadingTimeWithInvalidSpeed() {
        ReadingTimeCalculator.calculateReadingTime("Sample text", 0);
    }

    @Test
    public void testGetReadingTimeText() {
        String content = generateContent(400); // 400 words
        String result = ReadingTimeCalculator.getReadingTimeText(content);
        assertEquals("2 min read", result); // 400/200 = 2 minutes
    }

    @Test
    public void testGetDetailedReadingTime() {
        String content = generateContent(300); // 300 words
        String result = ReadingTimeCalculator.getDetailedReadingTime(content);
        assertTrue(result.contains("min read"));
        assertTrue(result.contains("300 words"));
    }

    @Test
    public void testCountWords() {
        assertEquals(5, ReadingTimeCalculator.countWords("This is a simple test"));
        assertEquals(0, ReadingTimeCalculator.countWords(""));
        assertEquals(0, ReadingTimeCalculator.countWords(null));
    }

    @Test
    public void testCountWordsWithHtml() {
        String htmlContent = "<p>This is <strong>HTML</strong> content</p>";
        int wordCount = ReadingTimeCalculator.countWords(htmlContent);
        assertEquals(4, wordCount); // "This", "is", "HTML", "content"
    }

    @Test
    public void testGetReadingTimeEstimate() {
        String content = generateContent(300);
        ReadingTimeCalculator.ReadingTimeEstimate estimate =
                ReadingTimeCalculator.getReadingTimeEstimate(content);

        assertNotNull(estimate);
        assertTrue(estimate.getSlowReaderMinutes() >= estimate.getAverageReaderMinutes());
        assertTrue(estimate.getAverageReaderMinutes() >= estimate.getFastReaderMinutes());
    }

    @Test
    public void testMinimumReadingTime() {
        String content = "Just five words here now";
        int readingTime = ReadingTimeCalculator.calculateReadingTime(content);
        assertEquals(1, readingTime); // Minimum should be 1 minute
    }

    // Helper method to generate content with specific word count
    private String generateContent(int wordCount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordCount; i++) {
            sb.append("word ");
        }
        return sb.toString().trim();
    }
}
