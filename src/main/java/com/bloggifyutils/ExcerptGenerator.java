package com.bloggifyutils;

/**
 * Generates excerpts and summaries from blog post content.
 * Provides intelligent text truncation while preserving readability.
 *
 * @author BloggifyUtils Team
 * @version 1.0.0
 */
public class ExcerptGenerator {

    private static final int DEFAULT_EXCERPT_LENGTH = 150;
    private static final String DEFAULT_SUFFIX = "...";

    /**
     * Generates an excerpt with default length (150 characters).
     *
     * @param content the blog post content
     * @return excerpt with ellipsis
     */
    public static String generateExcerpt(String content) {
        return generateExcerpt(content, DEFAULT_EXCERPT_LENGTH);
    }

    /**
     * Generates an excerpt with specified character length.
     * Truncates at word boundary to avoid cutting words.
     *
     * @param content the blog post content
     * @param maxLength maximum length of excerpt in characters
     * @return excerpt with ellipsis
     */
    public static String generateExcerpt(String content, int maxLength) {
        return generateExcerpt(content, maxLength, DEFAULT_SUFFIX);
    }

    /**
     * Generates an excerpt with custom suffix.
     *
     * @param content the blog post content
     * @param maxLength maximum length of excerpt in characters
     * @param suffix the suffix to append (e.g., "...", "[Read more]")
     * @return excerpt with custom suffix
     */
    public static String generateExcerpt(String content, int maxLength, String suffix) {
        if (content == null || content.trim().isEmpty()) {
            return "";
        }

        if (maxLength <= 0) {
            throw new IllegalArgumentException("Max length must be greater than 0");
        }

        // Remove HTML tags
        String cleanContent = stripHtmlTags(content);

        // Remove extra whitespace
        cleanContent = cleanContent.trim().replaceAll("\\s+", " ");

        // If content is shorter than max length, return as is
        if (cleanContent.length() <= maxLength) {
            return cleanContent;
        }

        // Truncate at word boundary
        String excerpt = cleanContent.substring(0, maxLength);
        int lastSpace = excerpt.lastIndexOf(' ');

        if (lastSpace > 0) {
            excerpt = excerpt.substring(0, lastSpace);
        }

        // Add suffix
        return excerpt.trim() + (suffix != null ? suffix : "");
    }

    /**
     * Generates an excerpt based on number of words instead of characters.
     *
     * @param content the blog post content
     * @param wordCount maximum number of words
     * @return excerpt with ellipsis
     */
    public static String generateExcerptByWords(String content, int wordCount) {
        return generateExcerptByWords(content, wordCount, DEFAULT_SUFFIX);
    }

    /**
     * Generates an excerpt based on number of words with custom suffix.
     *
     * @param content the blog post content
     * @param wordCount maximum number of words
     * @param suffix the suffix to append
     * @return excerpt with custom suffix
     */
    public static String generateExcerptByWords(String content, int wordCount, String suffix) {
        if (content == null || content.trim().isEmpty()) {
            return "";
        }

        if (wordCount <= 0) {
            throw new IllegalArgumentException("Word count must be greater than 0");
        }

        // Remove HTML tags
        String cleanContent = stripHtmlTags(content);

        // Remove extra whitespace
        cleanContent = cleanContent.trim().replaceAll("\\s+", " ");

        // Split into words
        String[] words = cleanContent.split("\\s+");

        // If word count is less than requested, return all
        if (words.length <= wordCount) {
            return cleanContent;
        }

        // Build excerpt with specified word count
        StringBuilder excerpt = new StringBuilder();
        for (int i = 0; i < wordCount; i++) {
            if (i > 0) {
                excerpt.append(" ");
            }
            excerpt.append(words[i]);
        }

        // Add suffix
        return excerpt.toString() + (suffix != null ? suffix : "");
    }

    /**
     * Generates an excerpt from the first paragraph of content.
     *
     * @param content the blog post content
     * @return the first paragraph as excerpt
     */
    public static String generateExcerptFromFirstParagraph(String content) {
        if (content == null || content.trim().isEmpty()) {
            return "";
        }

        // Split by common paragraph separators FIRST (before stripping HTML which removes newlines)
        String[] paragraphs = content.split("\\n\\n|\\r\\n\\r\\n");

        if (paragraphs.length > 0) {
            // Strip HTML from the first paragraph only
            return stripHtmlTags(paragraphs[0]).trim();
        }

        // If no paragraph separator found, strip HTML from all content
        return stripHtmlTags(content).trim();
    }

    /**
     * Generates an excerpt up to the first sentence or specified length.
     *
     * @param content the blog post content
     * @return excerpt of first sentence(s)
     */
    public static String generateExcerptBySentence(String content) {
        return generateExcerptBySentence(content, 1);
    }

    /**
     * Generates an excerpt with specified number of sentences.
     *
     * @param content the blog post content
     * @param sentenceCount number of sentences to include
     * @return excerpt with specified sentences
     */
    public static String generateExcerptBySentence(String content, int sentenceCount) {
        if (content == null || content.trim().isEmpty()) {
            return "";
        }

        if (sentenceCount <= 0) {
            throw new IllegalArgumentException("Sentence count must be greater than 0");
        }

        // Remove HTML tags
        String cleanContent = stripHtmlTags(content);

        // Split by sentence endings (., !, ?)
        String[] sentences = cleanContent.split("(?<=[.!?])\\s+");

        if (sentences.length <= sentenceCount) {
            return cleanContent.trim();
        }

        // Build excerpt with specified sentence count
        StringBuilder excerpt = new StringBuilder();
        for (int i = 0; i < sentenceCount; i++) {
            if (i > 0) {
                excerpt.append(" ");
            }
            excerpt.append(sentences[i].trim());
        }

        return excerpt.toString();
    }

    /**
     * Strips HTML tags from content.
     *
     * @param content the content with HTML tags
     * @return clean text without HTML
     */
    public static String stripHtmlTags(String content) {
        if (content == null) {
            return null;
        }

        // Remove HTML tags
        String cleaned = content.replaceAll("<[^>]*>", " ");

        // Decode common HTML entities
        cleaned = cleaned.replace("&nbsp;", " ")
                        .replace("&amp;", "&")
                        .replace("&lt;", "<")
                        .replace("&gt;", ">")
                        .replace("&quot;", "\"")
                        .replace("&#39;", "'");

        // Remove extra whitespace
        cleaned = cleaned.replaceAll("\\s+", " ");

        return cleaned.trim();
    }

    /**
     * Generates a meta description suitable for SEO.
     * Typically 150-160 characters for optimal search engine display.
     *
     * @param content the blog post content
     * @return SEO-friendly meta description
     */
    public static String generateMetaDescription(String content) {
        return generateExcerpt(content, 155, "");
    }

    /**
     * Generates a Twitter card description (max 200 characters).
     *
     * @param content the blog post content
     * @return Twitter-optimized description
     */
    public static String generateTwitterDescription(String content) {
        return generateExcerpt(content, 200, "");
    }
}
