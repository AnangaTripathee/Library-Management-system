import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


class Book {
    public String title;
    public String author;
    public String isbn;

    /**
      initialize the Book object
      @param  The title of the book.
      @param  The author of the book.
      @param  The ISBN of the book.
     */
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    /**
     * Gets the title of the book.
     * @return The title of the book.
     */
    public String getTitle() {
        return title;
    }

    
    public String getAuthor() {
        return author;
    }

    
    public String getIsbn() {
        return isbn;
    }

    
    public String toString() {
        return String.format("%-30s %-20s %-15s", title, author, isbn);
    }
}

// Main method
public class LibraryBook {
    public static void main(String[] args) {
        ArrayList<Book> library = new ArrayList<>();
        Scanner in = new Scanner(System.in);

        // loads books and other info from the input file
        loadFromFile(library);

        boolean exit = false;
        while (!exit) {
            System.out.println("\nLibrary Management System:");
            System.out.println("1. Add a new book");
            System.out.println("2. List all books");
            System.out.println("3. Search books by title/author");
            System.out.println("4. Save to file");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

           

            
                try {
                    int choice = Integer.parseInt(in.nextLine());

                    if (choice == 1) {
                        addBook(library, in);
                    } else if (choice == 2) {
                        listBooks(library);
                    } else if (choice == 3) {
                        searchBooks(library, in);
                    } else if (choice == 4) {
                        saveToFile(library);
                    } else if (choice == 5) {
                        exit = true;
                        System.out.println("Exiting the system. Goodbye!");
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        in.close();

        } 

        
    

    /**
     * Add new book to the library
     * @param The list of books in the library.
     * @param Scanner object to read user input.
     */
    public static void addBook(ArrayList<Book> library, Scanner in) {
        try {
            // Prompt user for book details
            System.out.print("Enter book title: ");
            String title = in.nextLine();

            System.out.print("Enter book author: ");
            String author = in.nextLine();

            System.out.print("Enter book ISBN: ");
            String isbn = in.nextLine();

            // Create a new Book object and add it to the library
            Book newBook = new Book(title, author, isbn);
            library.add(newBook);

            // Append the new book's details to the file
            appendToFile(newBook);
            System.out.println("Book added successfully.");
        } catch (Exception e) {
            System.out.println("An error occurred while adding the book.");
        }
    }

    /**
     * List all books in the library
     * @param The list of books in the library.
     */
    public static void listBooks(ArrayList<Book> library) {
        if (library.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }

        // Print table headers for book listing
        System.out.println("\nBooks in Library:");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-30s %-20s %-15s\n", "Title", "Author", "ISBN");
        System.out.println("--------------------------------------------------------------------------------");

        // Print details of each book
        for (Book book : library) {
            System.out.println(book);
        }

        System.out.println("--------------------------------------------------------------------------------");
    }

    /**
     * Search for books by title or author
     * @param The list of books in the library.
     * @param Scanner object to read user input.
     */
    public static void searchBooks(ArrayList<Book> library, Scanner in) {
        System.out.print("Enter keyword to search (title/author): ");
        String keyword = in.nextLine().toLowerCase();
        boolean found = false;

        // Search for books whose title or author contains the keyword
        for (Book book : library) {
            if (book.getTitle().toLowerCase().contains(keyword) || book.getAuthor().toLowerCase().contains(keyword)) {
                System.out.println("Found: " + book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Sorry, no such books found in the library.");
        }
    }

    /**
     * Save all book details to a file.
     * @param The list of books in the library.
     */
    public static void saveToFile(ArrayList<Book> library) {
        try (PrintWriter writer = new PrintWriter(new File("Books.txt"))) {
            // Write each book's details to the file
            for (Book book : library) {
                writer.println(book.getAuthor() + "," + book.getTitle() + "," + book.getIsbn());
            }
            System.out.println("Books saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error: Unable to save to file.");
        }
    }

    /**
     * Load book details from a file into the library.
     * @param The list of books in the library.
     */
    public static void loadFromFile(ArrayList<Book> library) {
        File file = new File("Books.txt");
        if (!file.exists()) {
            System.out.println("Books.txt file not found.");
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            // Read each line from the file and parse book details
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] details = line.split(",");
                if (details.length == 3) {
                    library.add(new Book(details[1].trim(), details[0].trim(), details[2].trim()));
                } else {
                    System.out.println("Skipping invalid line in file: " + line);
                }
            }
            System.out.println("Books loaded from file successfully.");
        } catch (IOException e) {
            System.out.println("Error: Unable to load from file.");
        }
    }

    /**
     * Append new book to the input file.
     * @param The book to append.
     */
    public static void appendToFile(Book book) {
        try (FileWriter writer = new FileWriter("Books.txt", true)) {
            // Write book details to the file
            writer.write(book.getAuthor() + "," + book.getTitle() + "," + book.getIsbn() + "\n");
        } catch (IOException e) {
            System.out.println("Error: Unable to append to file.");
        }
    }
}
