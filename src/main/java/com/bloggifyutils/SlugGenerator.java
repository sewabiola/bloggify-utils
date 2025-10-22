package com.bloggifyutils;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Generates SEO-friendly URL slugs from blog post titles.
 * Handles special characters, accents, and provides clean, readable URLs.
 *
 * @author BloggifyUtils Team
 * @version 1.0.0
 */
public class SlugGenerator {

    private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]+");
    private static final Pattern EDGES_DASHES = Pattern.compile("(^-|-$)");
    private static final Pattern MULTIPLE_DASHES = Pattern.compile("-{2,}");

    /**
     * Generates a URL-friendly slug from a title.
     * Example: "Hello World! 2024" -> "hello-world-2024"
     *
     * @param title the blog post title
     * @return SEO-friendly slug
     */
    public static String generateSlug(String title) {
        if (title == null || title.trim().isEmpty()) {
            return "";
        }

        // Convert to lowercase
        String slug = title.toLowerCase();

        // Normalize to remove accents (é -> e, ñ -> n)
        slug = Normalizer.normalize(slug, Normalizer.Form.NFD);
        slug = slug.replaceAll("\\p{M}", "");

        // Replace underscores with hyphens
        slug = slug.replace("_", "-");

        // Replace all non-alphanumeric characters (except spaces and hyphens) with spaces
        slug = slug.replaceAll("[^a-z0-9\\s-]", " ");

        // Replace spaces with hyphens
        slug = WHITESPACE.matcher(slug).replaceAll("-");

        // Replace multiple consecutive hyphens with single hyphen
        slug = MULTIPLE_DASHES.matcher(slug).replaceAll("-");

        // Remove hyphens from start and end
        slug = EDGES_DASHES.matcher(slug).replaceAll("");

        return slug;
    }

    /**
     * Generates a slug with maximum length limit.
     * Truncates at word boundary to avoid cutting words in half.
     *
     * @param title the blog post title
     * @param maxLength maximum length of the slug
     * @return SEO-friendly slug with length limit
     */
    public static String generateSlug(String title, int maxLength) {
        if (maxLength <= 0) {
            throw new IllegalArgumentException("Max length must be greater than 0");
        }

        String slug = generateSlug(title);

        if (slug.length() <= maxLength) {
            return slug;
        }

        // Truncate at last hyphen before maxLength to avoid cutting words
        slug = slug.substring(0, maxLength);
        int lastHyphen = slug.lastIndexOf('-');

        if (lastHyphen > 0) {
            slug = slug.substring(0, lastHyphen);
        }

        return slug;
    }

    /**
     * Generates a unique slug by appending a number if needed.
     * Useful when checking against existing slugs in a database.
     *
     * @param title the blog post title
     * @param existingSlugs array of existing slugs to check against
     * @return unique SEO-friendly slug
     */
    public static String generateUniqueSlug(String title, String... existingSlugs) {
        String baseSlug = generateSlug(title);
        String slug = baseSlug;
        int counter = 1;

        // Check if slug exists
        while (slugExists(slug, existingSlugs)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }

        return slug;
    }

    /**
     * Generates a dated slug by prepending date in YYYY-MM-DD format.
     * Example: "2024-10-22-my-blog-post"
     *
     * @param title the blog post title
     * @param year the year
     * @param month the month (1-12)
     * @param day the day (1-31)
     * @return dated SEO-friendly slug
     */
    public static String generateDatedSlug(String title, int year, int month, int day) {
        String slug = generateSlug(title);
        return String.format("%04d-%02d-%02d-%s", year, month, day, slug);
    }

    /**
     * Validates if a string is a valid slug.
     *
     * @param slug the string to validate
     * @return true if valid slug, false otherwise
     */
    public static boolean isValidSlug(String slug) {
        if (slug == null || slug.isEmpty()) {
            return false;
        }

        // Valid slug should only contain lowercase letters, numbers, and hyphens
        // Should not start or end with hyphen
        return slug.matches("^[a-z0-9]+(-[a-z0-9]+)*$");
    }

    /**
     * Converts a slug back to a readable title.
     * Example: "hello-world-2024" -> "Hello World 2024"
     *
     * @param slug the slug to convert
     * @return readable title
     */
    public static String slugToTitle(String slug) {
        if (slug == null || slug.isEmpty()) {
            return "";
        }

        // Replace hyphens with spaces
        String title = slug.replace("-", " ");

        // Capitalize first letter of each word
        String[] words = title.split(" ");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                result.append(" ");
            }
            if (!words[i].isEmpty()) {
                result.append(Character.toUpperCase(words[i].charAt(0)))
                      .append(words[i].substring(1));
            }
        }

        return result.toString();
    }

    /**
     * Helper method to check if a slug exists in an array.
     */
    private static boolean slugExists(String slug, String[] existingSlugs) {
        if (existingSlugs == null) {
            return false;
        }
        for (String existing : existingSlugs) {
            if (slug.equals(existing)) {
                return true;
            }
        }
        return false;
    }
}
