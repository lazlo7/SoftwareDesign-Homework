package net.requef.booklib;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static final Library library = new Library();
    private static final Map<Book, Integer> takenBooks = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean shouldExit = false;

    public static void main(String[] args) {
        System.out.printf("(hint: type /help to see all available commands)%n%n");

        while (!shouldExit) {
            System.out.print("> ");
            final String cmd = scanner.nextLine();
            processInput(cmd);
        }
    }

    private static boolean checkBookTitleArgumentCount(int got) {
        if (got < 2) {
            System.out.println("[ERROR] Empty book title");
            return false;
        }
        return true;
    }

    private static void processInput(final String input) {
        final String[] split = input.trim().split(" ");

        if (split.length < 1) {
            System.out.println("[ERROR] Empty input was provided");
            return;
        }

        final var cmd = split[0];
        switch (cmd) {
            case "/help" -> printHelp();
            case "/exit" -> shouldExit = true;
            case "/get" -> {
                if (checkBookTitleArgumentCount(split.length)) {
                    getBookFromLibrary(String.join(" ", Arrays.stream(split).skip(1).toList()));
                }
            }
            case "/put" -> {
                if (checkBookTitleArgumentCount(split.length)) {
                    putBookInLibrary(String.join(" ", Arrays.stream(split).skip(1).toList()));
                }
            }
            case "/list" -> listTakenBooks();
            case "/all" -> listBooksInLibrary();
            case "/ask" -> {
                if (checkBookTitleArgumentCount(split.length)) {
                    askForBookInLibrary(String.join(" ", Arrays.stream(split).skip(1).toList()));
                }
            }
            default -> System.out.printf("[ERROR] Unknown command: %s%n", cmd);
        }
    }

    private static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("/get <book title> - gets all books from the library that match the given title");
        System.out.println("/list - lists all books we took from the library");
        System.out.println("/put <book title> - puts a book back into the library");
        System.out.println("/all - lists all books in the library");
        System.out.println("/ask <book title> - asks the library if it contains a book with the given title");
        System.out.println("/exit - exit the application");
        System.out.println("/help - print this message");
    }

    private static void getBookFromLibrary(final String title) {
        final var fetchedBooks = library.getBook(title);

        if (fetchedBooks.isEmpty()) {
            System.out.printf("No books with title '%s' were found in the library%n", title);
            return;
        }

        Book takenBook;
        if (fetchedBooks.size() == 1) {
            System.out.printf("Found 1 book with title '%s', taking it%n", title);
            takenBook = fetchedBooks.keySet().iterator().next();
        } else {
            System.out.printf("Found %d books with title '%s'%n", fetchedBooks.size(), title);
            takenBook = chooseBook(fetchedBooks);
        }

        takenBooks.merge(takenBook, 1, Integer::sum);
        library.removeBook(takenBook);
    }

    private static void putBookInLibrary(final String title) {
        final var fetchedBooks = takenBooks.entrySet()
                .stream()
                .filter(entry -> entry.getKey().title().equals(title))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (fetchedBooks.isEmpty()) {
            System.out.printf("No books with title '%s' were found in the library%n", title);
            return;
        }

        Book book;
        if (fetchedBooks.size() == 1) {
            System.out.printf("Found 1 book with title '%s', putting it back%n", title);
            book = fetchedBooks.keySet().iterator().next();
        } else {
            System.out.printf("Found %d books with title '%s'%n", fetchedBooks.size(), title);
            book = chooseBook(fetchedBooks);
        }

        final int amount = takenBooks.getOrDefault(book, -1);
        if (amount == 1) {
            takenBooks.remove(book);
        } else {
            takenBooks.put(book, amount - 1);
        }

        library.putBook(book);
    }

    private static void listTakenBooks() {
        System.out.println("Taken books:");
        for (final var entry : takenBooks.entrySet()) {
            System.out.printf("%d x %s%n", entry.getValue(), entry.getKey());
        }
    }

    private static void listBooksInLibrary() {
        System.out.println("Books in the library:");
        for (final var entry : library.getBooks().entrySet()) {
            System.out.printf("%d x %s%n", entry.getValue(), entry.getKey());
        }
    }

    private static void askForBookInLibrary(final String title) {
        System.out.printf("The book titled '%s' is %s in the library%n",
                title,
                library.containsBook(title) ? "available" : "absent");
    }

    private static Book chooseBook(final Map<Book, Integer> books) {
        while (true) {
            int bookIndex = 0;
            for (final var entry : books.entrySet()) {
                System.out.printf("%d. %d x %s %n", bookIndex, entry.getValue(), entry.getKey());
                bookIndex++;
            }

            System.out.print("Which book do you want to use: ");
            final String bookIndexStr = scanner.nextLine();
            try {
                bookIndex = Integer.parseInt(bookIndexStr);
            } catch (NumberFormatException e) {
                System.out.printf("'%s' is not a valid number%n", bookIndexStr);
                continue;
            }

            if (bookIndex < 0 || bookIndex >= books.size()) {
                System.out.printf("'%d' is not a valid book index%n", bookIndex);
                continue;
            }

            System.out.printf("Took (%d) book%n", bookIndex);
            return (Book) books.keySet().toArray()[bookIndex];
        }
    }
}