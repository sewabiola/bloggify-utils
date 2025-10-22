package com.bloggifyutils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for ExcerptGenerator class.
 */
public class ExcerptGeneratorTest {

    private static final String SAMPLE_CONTENT = "This is a sample blog post content. " +
            "It contains multiple sentences to test the excerpt generation. " +
            "The excerpt should be properly truncated at word boundaries. " +
            "This ensures that the excerpt looks professional and readable.";

    private static final String HTML_CONTENT = "<p>This is <strong>HTML</strong> content.</p>" +
            "<p>It should be stripped properly.</p>";

    @Test
    public void testGenerateExcerptDefault() {
        String excerpt = ExcerptGenerator.generateExcerpt(SAMPLE_CONTENT);
        assertNotNull(excerpt);
        assertTrue(excerpt.length() <= 150 + 3); // 150 + "..."
        assertTrue(excerpt.endsWith("..."));
    }

    @Test
    public void testGenerateExcerptWithCustomLength() {
        String excerpt = ExcerptGenerator.generateExcerpt(SAMPLE_CONTENT, 50);
        assertNotNull(excerpt);
        assertTrue(excerpt.length() <= 50 + 3);
    }

    @Test
    public void testGenerateExcerptWithCustomSuffix() {
        String excerpt = ExcerptGenerator.generateExcerpt(SAMPLE_CONTENT, 100, " [Read More]");
        assertNotNull(excerpt);
        assertTrue(excerpt.endsWith(" [Read More]"));
    }

    @Test
    public void testGenerateExcerptShorterThanLimit() {
        String shortContent = "This is short.";
        String excerpt = ExcerptGenerator.generateExcerpt(shortContent, 100);
        assertEquals(shortContent, excerpt);
    }

    @Test
    public void testGenerateExcerptWithEmptyContent() {
        assertEquals("", ExcerptGenerator.generateExcerpt(""));
        assertEquals("", ExcerptGenerator.generateExcerpt(null));
        assertEquals("", ExcerptGenerator.generateExcerpt("   "));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGenerateExcerptWithInvalidLength() {
        ExcerptGenerator.generateExcerpt(SAMPLE_CONTENT, 0);
    }

    @Test
    public void testGenerateExcerptByWords() {
        String excerpt = ExcerptGenerator.generateExcerptByWords(SAMPLE_CONTENT, 10);
        String[] words = excerpt.replace("...", "").trim().split("\\s+");
        assertTrue(words.length <= 10);
        assertTrue(excerpt.endsWith("..."));
    }

    @Test
    public void testGenerateExcerptByWordsWithCustomSuffix() {
        String excerpt = ExcerptGenerator.generateExcerptByWords(SAMPLE_CONTENT, 5, " >>>");
        assertTrue(excerpt.endsWith(" >>>"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGenerateExcerptByWordsInvalidCount() {
        ExcerptGenerator.generateExcerptByWords(SAMPLE_CONTENT, 0);
    }

    @Test
    public void testGenerateExcerptFromFirstParagraph() {
        String multiPara = "First paragraph.\n\nSecond paragraph.";
        String excerpt = ExcerptGenerator.generateExcerptFromFirstParagraph(multiPara);
        assertEquals("First paragraph.", excerpt);
    }

    @Test
    public void testGenerateExcerptBySentence() {
        String excerpt = ExcerptGenerator.generateExcerptBySentence(SAMPLE_CONTENT);
        assertTrue(excerpt.endsWith("."));
        assertFalse(excerpt.contains("multiple sentences"));
    }

    @Test
    public void testGenerateExcerptByMultipleSentences() {
        String excerpt = ExcerptGenerator.generateExcerptBySentence(SAMPLE_CONTENT, 2);
        String[] sentences = excerpt.split("\\. ");
        assertTrue(sentences.length <= 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGenerateExcerptBySentenceInvalidCount() {
        ExcerptGenerator.generateExcerptBySentence(SAMPLE_CONTENT, 0);
    }

    @Test
    public void testStripHtmlTags() {
        String stripped = ExcerptGenerator.stripHtmlTags(HTML_CONTENT);
        assertFalse(stripped.contains("<"));
        assertFalse(stripped.contains(">"));
        assertTrue(stripped.contains("HTML"));
        assertTrue(stripped.contains("content"));
    }

    @Test
    public void testStripHtmlTagsWithEntities() {
        String htmlWithEntities = "Hello&nbsp;World &amp; Friends &lt;test&gt;";
        String stripped = ExcerptGenerator.stripHtmlTags(htmlWithEntities);
        assertTrue(stripped.contains("Hello World"));
        assertTrue(stripped.contains("&"));
        assertTrue(stripped.contains("<"));
        assertTrue(stripped.contains(">"));
    }

    @Test
    public void testStripHtmlTagsWithNull() {
        assertNull(ExcerptGenerator.stripHtmlTags(null));
    }

    @Test
    public void testGenerateMetaDescription() {
        String meta = ExcerptGenerator.generateMetaDescription(SAMPLE_CONTENT);
        assertNotNull(meta);
        assertTrue(meta.length() <= 155);
        assertFalse(meta.endsWith("...")); // Meta descriptions should not have ellipsis
    }

    @Test
    public void testGenerateTwitterDescription() {
        String twitter = ExcerptGenerator.generateTwitterDescription(SAMPLE_CONTENT);
        assertNotNull(twitter);
        assertTrue(twitter.length() <= 200);
        assertFalse(twitter.endsWith("..."));
    }

    @Test
    public void testExcerptDoesNotCutWords() {
        String content = "This is a test to ensure words are not cut in the middle when generating excerpts";
        String excerpt = ExcerptGenerator.generateExcerpt(content, 30);
        // Make sure the last word before "..." is complete
        String withoutEllipsis = excerpt.replace("...", "").trim();
        String[] words = withoutEllipsis.split("\\s+");
        String lastWord = words[words.length - 1];
        assertTrue(content.contains(lastWord));
    }

    @Test
    public void testExcerptWithHtmlContent() {
        String excerpt = ExcerptGenerator.generateExcerpt(HTML_CONTENT, 50);
        assertFalse(excerpt.contains("<"));
        assertFalse(excerpt.contains(">"));
    }
}
