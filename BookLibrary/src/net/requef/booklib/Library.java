package net.requef.booklib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Library {
    // Maps books to their number of copies.
    private final Map<Book, Integer> books = new HashMap<>();

    /**
     * Creates a new library with pre-populated books.
     */
    public Library() {
        books.put(new Book("1984", "A dystopian novel", List.of("George Orwell"), 1949), 123);
        books.put(new Book("Fifty Shades of Grey", "A romance novel", List.of("E. L. James"), 2011), 3);
        books.put(new Book("The Lord of the Rings", "A fantasy novel", List.of("J. R. R. Tolkien"), 1954), 1337);
        books.put(new Book("The Hitchhiker's Guide to the Galaxy", "A science fiction comedy", List.of("Douglas Adams"), 1979), 42);
        books.put(new Book("The Hobbit", "A fantasy novel", List.of("J. R. R. Tolkien"), 1937), 2);
        books.put(new Book("The Da Vinci Code", "A mystery thriller", List.of("Dan Brown"), 2003), 1);
        books.put(new Book("The Catcher in the Rye", "A coming-of-age novel", List.of("J. D. Salinger"), 1951), 10);
        books.put(new Book("The Lion, the Witch and the Wardrobe", "A fantasy novel", List.of("C. S. Lewis"), 1950), 5);
        books.put(new Book("The Little Prince", "A philosophical novel", List.of("Antoine de Saint-Exupéry"), 1943), 25);
        books.put(new Book("Why Do I Hate Java", "A critical analysis of Java", List.of("Max Smagin, Peter Pan"), 2022), 1);
        books.put(new Book("Why Do I Hate Java", "A critical analysis of Java", List.of("Timofey Tikhonov"), 2022), 1);
        books.put(new Book("Why Do I Hate Java", "A critical analysis of Java", List.of("Angela Merkel"), 2022), 1);
        books.put(new Book("Why Do I Love Java", "My leap into insanity", List.of("<folklore>"), 1666), 100);
    }

    /**
     * Gets all books in the library.
     *
     * @return All books in the library.
     */
    public Map<Book, Integer> getBooks() {
        return books;
    }

    /**
     * Gets all books in the library which have the same title as title.
     * This does not remove books from the library.
     *
     * @param title The title of the book to search for.
     * @return All books in the library.
     */
    public Map<Book, Integer> getBook(final String title) {
        return books.entrySet()
                .stream()
                .filter(entry -> entry.getKey().title().equals(title))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Adds a book to the library.
     *
     * @param book The book to add.
     */
    public void putBook(final Book book) {
        books.merge(book, 1, Integer::sum);
    }

    /**
     * Removes a book from the library.
     *
     * @param book The book to remove.
     */
    public void removeBook(final Book book) {
        final int amount = books.getOrDefault(book, -1);

        // Book is not contained in the library.
        if (amount == -1) {
            return;
        }

        if (amount == 1) {
            books.remove(book);
        } else {
            books.put(book, amount - 1);
        }
    }

    public boolean containsBook(final String title) {
        return books.keySet().stream().anyMatch(book -> book.title().equals(title));
    }
}
