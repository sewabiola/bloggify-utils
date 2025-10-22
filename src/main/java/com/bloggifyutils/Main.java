package com.bloggifyutils;

/**
 * Demo application showcasing BloggifyUtils library functionality.
 * Demonstrates the three core features: Reading Time Calculator, Slug Generator, and Excerpt Generator.
 *
 * @author BloggifyUtils Team
 * @version 1.0.0
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("   BloggifyUtils Library Demo");
        System.out.println("====================================\n");

        // Sample blog post content
        String blogTitle = "10 Amazing Tips for Web Development in 2024!";
        String blogContent = "<h1>Introduction</h1>" +
                "<p>Web development has evolved significantly over the years. " +
                "In this comprehensive guide, we'll explore the latest trends, " +
                "best practices, and essential tools that every web developer " +
                "should know in 2024. Whether you're a beginner or an experienced " +
                "developer, these tips will help you build better, faster, and " +
                "more secure web applications.</p>" +
                "<p>From modern JavaScript frameworks to cutting-edge CSS techniques, " +
                "we've got you covered with practical examples and real-world scenarios. " +
                "Let's dive into the world of modern web development and discover what " +
                "makes today's websites so powerful and user-friendly.</p>";

        demonstrateReadingTimeCalculator(blogContent);
        demonstrateSlugGenerator(blogTitle);
        demonstrateExcerptGenerator(blogContent);

        System.out.println("\n====================================");
        System.out.println("   Demo Complete!");
        System.out.println("====================================");
    }

    /**
     * Demonstrates Reading Time Calculator features.
     */
    private static void demonstrateReadingTimeCalculator(String content) {
        System.out.println("=== READING TIME CALCULATOR ===\n");

        // Basic reading time
        int readingTime = ReadingTimeCalculator.calculateReadingTime(content);
        System.out.println("Reading Time: " + readingTime + " minutes");

        // Formatted reading time
        String readingTimeText = ReadingTimeCalculator.getReadingTimeText(content);
        System.out.println("Formatted: " + readingTimeText);

        // Detailed reading time
        String detailedTime = ReadingTimeCalculator.getDetailedReadingTime(content);
        System.out.println("Detailed: " + detailedTime);

        // Word count
        int wordCount = ReadingTimeCalculator.countWords(content);
        System.out.println("Word Count: " + wordCount + " words");

        // Reading time estimate for different speeds
        ReadingTimeCalculator.ReadingTimeEstimate estimate =
                ReadingTimeCalculator.getReadingTimeEstimate(content);
        System.out.println("Estimate: " + estimate);

        // Custom reading speed
        int customSpeed = ReadingTimeCalculator.calculateReadingTime(content, 250);
        System.out.println("Custom Speed (250 WPM): " + customSpeed + " minutes");

        System.out.println();
    }

    /**
     * Demonstrates Slug Generator features.
     */
    private static void demonstrateSlugGenerator(String title) {
        System.out.println("=== SLUG GENERATOR ===\n");

        System.out.println("Original Title: " + title);

        // Basic slug generation
        String slug = SlugGenerator.generateSlug(title);
        System.out.println("Generated Slug: " + slug);

        // Slug with length limit
        String shortSlug = SlugGenerator.generateSlug(title, 30);
        System.out.println("Short Slug (30 chars): " + shortSlug);

        // Unique slug generation
        String[] existingSlugs = {"web-dev-tips", "amazing-tips"};
        String uniqueSlug = SlugGenerator.generateUniqueSlug("Amazing Tips", existingSlugs);
        System.out.println("Unique Slug: " + uniqueSlug);

        // Dated slug
        String datedSlug = SlugGenerator.generateDatedSlug(title, 2024, 10, 22);
        System.out.println("Dated Slug: " + datedSlug);

        // Validate slug
        boolean isValid = SlugGenerator.isValidSlug(slug);
        System.out.println("Is Valid Slug: " + isValid);

        // Convert slug back to title
        String reconstructedTitle = SlugGenerator.slugToTitle(slug);
        System.out.println("Slug to Title: " + reconstructedTitle);

        // Handle special characters
        String specialTitle = "Café au Lait & Crème Brûlée: A Guide";
        String specialSlug = SlugGenerator.generateSlug(specialTitle);
        System.out.println("\nSpecial Characters Demo:");
        System.out.println("Title: " + specialTitle);
        System.out.println("Slug: " + specialSlug);

        System.out.println();
    }

    /**
     * Demonstrates Excerpt Generator features.
     */
    private static void demonstrateExcerptGenerator(String content) {
        System.out.println("=== EXCERPT GENERATOR ===\n");

        // Default excerpt (150 characters)
        String excerpt = ExcerptGenerator.generateExcerpt(content);
        System.out.println("Default Excerpt (150 chars):");
        System.out.println(excerpt);
        System.out.println();

        // Custom length excerpt
        String customExcerpt = ExcerptGenerator.generateExcerpt(content, 100);
        System.out.println("Custom Excerpt (100 chars):");
        System.out.println(customExcerpt);
        System.out.println();

        // Custom suffix
        String readMoreExcerpt = ExcerptGenerator.generateExcerpt(content, 120, " [Read more]");
        System.out.println("With Custom Suffix:");
        System.out.println(readMoreExcerpt);
        System.out.println();

        // Word-based excerpt
        String wordExcerpt = ExcerptGenerator.generateExcerptByWords(content, 20);
        System.out.println("Word-based Excerpt (20 words):");
        System.out.println(wordExcerpt);
        System.out.println();

        // First paragraph
        String firstPara = ExcerptGenerator.generateExcerptFromFirstParagraph(content);
        System.out.println("First Paragraph:");
        System.out.println(firstPara);
        System.out.println();

        // First sentence
        String firstSentence = ExcerptGenerator.generateExcerptBySentence(content);
        System.out.println("First Sentence:");
        System.out.println(firstSentence);
        System.out.println();

        // Meta description for SEO
        String metaDesc = ExcerptGenerator.generateMetaDescription(content);
        System.out.println("Meta Description (SEO):");
        System.out.println(metaDesc);
        System.out.println();

        // Twitter description
        String twitterDesc = ExcerptGenerator.generateTwitterDescription(content);
        System.out.println("Twitter Description:");
        System.out.println(twitterDesc);
        System.out.println();

        // Strip HTML tags
        String stripped = ExcerptGenerator.stripHtmlTags(content);
        System.out.println("Stripped HTML (first 100 chars):");
        System.out.println(stripped.substring(0, Math.min(100, stripped.length())) + "...");

        System.out.println();
    }
}
