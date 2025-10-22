package com.bloggifyutils;

/**
 * Calculates estimated reading time for blog posts and articles.
 * Uses industry-standard reading speeds and provides flexible output formats.
 *
 * @author BloggifyUtils Team
 * @version 1.0.0
 */
public class ReadingTimeCalculator {

    private static final int DEFAULT_WORDS_PER_MINUTE = 200;
    private static final int FAST_READER_WPM = 250;
    private static final int SLOW_READER_WPM = 150;

    /**
     * Calculates reading time in minutes using default reading speed (200 WPM).
     *
     * @param content the blog post content
     * @return estimated reading time in minutes
     */
    public static int calculateReadingTime(String content) {
        return calculateReadingTime(content, DEFAULT_WORDS_PER_MINUTE);
    }

    /**
     * Calculates reading time in minutes using custom words per minute.
     *
     * @param content the blog post content
     * @param wordsPerMinute the reading speed in words per minute
     * @return estimated reading time in minutes
     */
    public static int calculateReadingTime(String content, int wordsPerMinute) {
        if (content == null || content.trim().isEmpty()) {
            return 0;
        }

        if (wordsPerMinute <= 0) {
            throw new IllegalArgumentException("Words per minute must be greater than 0");
        }

        int wordCount = countWords(content);
        int minutes = (int) Math.ceil((double) wordCount / wordsPerMinute);
        return Math.max(1, minutes); // Minimum 1 minute
    }

    /**
     * Returns a formatted reading time string (e.g., "5 min read", "1 min read").
     *
     * @param content the blog post content
     * @return formatted reading time string
     */
    public static String getReadingTimeText(String content) {
        int minutes = calculateReadingTime(content);
        return minutes + " min read";
    }

    /**
     * Returns a detailed reading time string with word count
     * (e.g., "5 min read (1000 words)").
     *
     * @param content the blog post content
     * @return detailed reading time string
     */
    public static String getDetailedReadingTime(String content) {
        if (content == null || content.trim().isEmpty()) {
            return "0 min read (0 words)";
        }

        int minutes = calculateReadingTime(content);
        int words = countWords(content);
        return minutes + " min read (" + words + " words)";
    }

    /**
     * Calculates reading time for different reader speeds.
     *
     * @param content the blog post content
     * @return ReadingTimeEstimate object with slow, average, and fast reading times
     */
    public static ReadingTimeEstimate getReadingTimeEstimate(String content) {
        int slow = calculateReadingTime(content, SLOW_READER_WPM);
        int average = calculateReadingTime(content, DEFAULT_WORDS_PER_MINUTE);
        int fast = calculateReadingTime(content, FAST_READER_WPM);

        return new ReadingTimeEstimate(slow, average, fast);
    }

    /**
     * Counts the number of words in the content.
     *
     * @param content the text to count words in
     * @return number of words
     */
    public static int countWords(String content) {
        if (content == null || content.trim().isEmpty()) {
            return 0;
        }

        // Remove HTML tags if present
        String cleanContent = content.replaceAll("<[^>]*>", " ");

        // Split by whitespace and count non-empty words
        String[] words = cleanContent.trim().split("\\s+");
        return words.length;
    }

    /**
     * Inner class to hold reading time estimates for different reader speeds.
     */
    public static class ReadingTimeEstimate {
        private final int slowReaderMinutes;
        private final int averageReaderMinutes;
        private final int fastReaderMinutes;

        public ReadingTimeEstimate(int slow, int average, int fast) {
            this.slowReaderMinutes = slow;
            this.averageReaderMinutes = average;
            this.fastReaderMinutes = fast;
        }

        public int getSlowReaderMinutes() {
            return slowReaderMinutes;
        }

        public int getAverageReaderMinutes() {
            return averageReaderMinutes;
        }

        public int getFastReaderMinutes() {
            return fastReaderMinutes;
        }

        @Override
        public String toString() {
            return String.format("Reading Time: %d-%d min (avg: %d min)",
                    fastReaderMinutes, slowReaderMinutes, averageReaderMinutes);
        }
    }
}
