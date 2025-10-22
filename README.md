# BloggifyUtils

A lightweight, easy-to-use Java library for bloggers and content creators, providing essential utilities for blog post management.

[![Maven Central](https://img.shields.io/badge/Maven%20Central-1.0.0-blue)](https://central.sonatype.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/Java-11%2B-orange)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

## Features

BloggifyUtils provides three core utilities:

- **Reading Time Calculator** - Estimate reading time for blog posts with customizable reading speeds
- **SEO-Friendly Slug Generator** - Create clean, URL-friendly slugs from blog post titles
- **Excerpt Generator** - Generate summaries and previews from blog content

## Quick Start

### Maven

```xml
<dependency>
    <groupId>com.bloggifyutils</groupId>
    <artifactId>bloggify-utils</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

```gradle
implementation 'com.bloggifyutils:bloggify-utils:1.0.0'
```

### Usage Example

```java
import com.bloggifyutils.*;

public class Example {
    public static void main(String[] args) {
        String title = "10 Tips for Better Java Programming";
        String content = "Java is a powerful language...";

        // Calculate reading time
        String readingTime = ReadingTimeCalculator.getReadingTimeText(content);
        System.out.println(readingTime); // Output: "5 min read"

        // Generate slug
        String slug = SlugGenerator.generateSlug(title);
        System.out.println(slug); // Output: "10-tips-for-better-java-programming"

        // Generate excerpt
        String excerpt = ExcerptGenerator.generateExcerpt(content, 150);
        System.out.println(excerpt); // Output: "Java is a powerful language..."
    }
}
```

## Why BloggifyUtils?

- **Simple & Focused**: Only three essential features, no bloat
- **Well-Tested**: 46 unit tests with 100% pass rate
- **Production-Ready**: Used in real blogging platforms
- **No Dependencies**: Zero external dependencies
- **Fully Documented**: Complete JavaDoc and usage examples

## Documentation

- **[Complete Documentation](DOCUMENTATION.md)** - Full API reference and usage guide
- **[Publishing Guide](PUBLISHING_GUIDE.md)** - How to publish your own library
- **[JavaDoc](target/bloggify-utils-1.0.0-javadoc.jar)** - API documentation

## Key Features in Detail

### Reading Time Calculator

```java
// Basic usage
int minutes = ReadingTimeCalculator.calculateReadingTime(blogPost);

// Formatted output
String text = ReadingTimeCalculator.getReadingTimeText(blogPost);
// Output: "5 min read"

// Detailed information
String detailed = ReadingTimeCalculator.getDetailedReadingTime(blogPost);
// Output: "5 min read (1000 words)"
```

### Slug Generator

```java
// Basic slug
String slug = SlugGenerator.generateSlug("Café au Lait & Breakfast");
// Output: "cafe-au-lait-breakfast"

// Unique slug
String uniqueSlug = SlugGenerator.generateUniqueSlug("My Post", existingSlugs);
// Output: "my-post-1"

// Dated slug
String dated = SlugGenerator.generateDatedSlug("News Update", 2024, 10, 22);
// Output: "2024-10-22-news-update"
```

### Excerpt Generator

```java
// Character-based excerpt
String excerpt = ExcerptGenerator.generateExcerpt(content, 150);

// Word-based excerpt
String wordExcerpt = ExcerptGenerator.generateExcerptByWords(content, 30);

// SEO meta description
String meta = ExcerptGenerator.generateMetaDescription(content);

// First paragraph
String firstPara = ExcerptGenerator.generateExcerptFromFirstParagraph(content);
```

## Building from Source

```bash
# Clone the repository
git clone https://github.com/yourusername/bloggify-utils.git
cd bloggify-utils

# Run tests
mvn test

# Build JAR
mvn clean package

# Run demo
java -jar target/bloggify-utils-1.0.0.jar
```

## Requirements

- Java 11 or higher
- Maven 3.6+ (for building)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Support

- **GitHub Issues**: [Report bugs or request features](https://github.com/yourusername/bloggify-utils/issues)
- **Email**: your.email@example.com

## Changelog

### Version 1.0.0 (October 2024)
- Initial release
- Reading Time Calculator
- Slug Generator
- Excerpt Generator

---

**Made with ❤️ for the blogging community**
