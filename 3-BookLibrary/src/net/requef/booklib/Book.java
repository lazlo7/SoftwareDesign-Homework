package net.requef.booklib;

import java.util.List;

public record Book(String title, String description, List<String> authors, int publishingYear) {
    @Override
    public String toString() {
        return String.format("'%s' (%d) by %s [%s]",
                title,
                publishingYear,
                String.join(", ", authors),
                description);
    }
}
