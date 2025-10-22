package com.bloggifyutils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for SlugGenerator class.
 */
public class SlugGeneratorTest {

    @Test
    public void testBasicSlugGeneration() {
        assertEquals("hello-world", SlugGenerator.generateSlug("Hello World"));
        assertEquals("test-123", SlugGenerator.generateSlug("Test 123"));
        assertEquals("my-blog-post", SlugGenerator.generateSlug("My Blog Post"));
    }

    @Test
    public void testSlugWithSpecialCharacters() {
        assertEquals("hello-world", SlugGenerator.generateSlug("Hello, World!"));
        assertEquals("test-slug", SlugGenerator.generateSlug("Test@#$%Slug"));
        assertEquals("price-99", SlugGenerator.generateSlug("Price: $99"));
    }

    @Test
    public void testSlugWithAccents() {
        assertEquals("cafe-au-lait", SlugGenerator.generateSlug("Café au Lait"));
        assertEquals("nino", SlugGenerator.generateSlug("Niño"));
        assertEquals("resume", SlugGenerator.generateSlug("Résumé"));
    }

    @Test
    public void testSlugWithMultipleSpaces() {
        assertEquals("hello-world", SlugGenerator.generateSlug("Hello    World"));
        assertEquals("test-slug", SlugGenerator.generateSlug("  Test   Slug  "));
    }

    @Test
    public void testSlugWithHyphens() {
        assertEquals("hello-world", SlugGenerator.generateSlug("Hello-World"));
        assertEquals("test-slug-2024", SlugGenerator.generateSlug("Test--Slug--2024"));
    }

    @Test
    public void testSlugWithEmptyString() {
        assertEquals("", SlugGenerator.generateSlug(""));
        assertEquals("", SlugGenerator.generateSlug(null));
        assertEquals("", SlugGenerator.generateSlug("   "));
    }

    @Test
    public void testSlugWithMaxLength() {
        String longTitle = "This is a very long blog post title that needs to be truncated";
        String slug = SlugGenerator.generateSlug(longTitle, 30);
        assertTrue(slug.length() <= 30);
        assertFalse(slug.endsWith("-")); // Should not end with hyphen
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSlugWithInvalidMaxLength() {
        SlugGenerator.generateSlug("Test", 0);
    }

    @Test
    public void testGenerateUniqueSlug() {
        String[] existing = {"my-post", "my-post-1"};
        String slug = SlugGenerator.generateUniqueSlug("My Post", existing);
        assertEquals("my-post-2", slug);
    }

    @Test
    public void testGenerateUniqueSlugWithNoConflict() {
        String[] existing = {"other-post"};
        String slug = SlugGenerator.generateUniqueSlug("My Post", existing);
        assertEquals("my-post", slug);
    }

    @Test
    public void testGenerateDatedSlug() {
        String slug = SlugGenerator.generateDatedSlug("My Post", 2024, 10, 22);
        assertEquals("2024-10-22-my-post", slug);
    }

    @Test
    public void testIsValidSlug() {
        assertTrue(SlugGenerator.isValidSlug("hello-world"));
        assertTrue(SlugGenerator.isValidSlug("test-123"));
        assertTrue(SlugGenerator.isValidSlug("mypost"));

        assertFalse(SlugGenerator.isValidSlug("Hello-World")); // Uppercase
        assertFalse(SlugGenerator.isValidSlug("hello_world")); // Underscore
        assertFalse(SlugGenerator.isValidSlug("hello world")); // Space
        assertFalse(SlugGenerator.isValidSlug("-hello")); // Starts with hyphen
        assertFalse(SlugGenerator.isValidSlug("hello-")); // Ends with hyphen
        assertFalse(SlugGenerator.isValidSlug("")); // Empty
        assertFalse(SlugGenerator.isValidSlug(null)); // Null
    }

    @Test
    public void testSlugToTitle() {
        assertEquals("Hello World", SlugGenerator.slugToTitle("hello-world"));
        assertEquals("My Blog Post", SlugGenerator.slugToTitle("my-blog-post"));
        assertEquals("Test 123", SlugGenerator.slugToTitle("test-123"));
    }

    @Test
    public void testSlugToTitleWithEmptyString() {
        assertEquals("", SlugGenerator.slugToTitle(""));
        assertEquals("", SlugGenerator.slugToTitle(null));
    }

    @Test
    public void testSlugWithUnderscores() {
        assertEquals("hello-world", SlugGenerator.generateSlug("hello_world"));
        assertEquals("test-slug", SlugGenerator.generateSlug("test___slug"));
    }

    @Test
    public void testSlugWithNumbers() {
        assertEquals("10-tips-for-2024", SlugGenerator.generateSlug("10 Tips for 2024"));
        assertEquals("version-2-0-released", SlugGenerator.generateSlug("Version 2.0 Released"));
    }
}
