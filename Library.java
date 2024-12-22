import java.io.*;
import java.util.ArrayList;

public class Library extends LibraryOperations {

    // Add a new book to the library
    public void addBook(String title, String author, String isbn) {
        books.add(new Book(title, author, isbn, "Available"));
    }

    // Delete a book from the library
    public void deleteBook(String isbn) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(isbn)) {
                books.remove(i);
                return;
            }
        }
    }

    // Issue a book (change status to 'Issued')
    public void issueBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                if (book.getStatus().equals("Available")) {
                    book.setStatus("Issued");
                    return;
                }
            }
        }
    }

    // Return a book (change status to 'Available')
    public void returnBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                if (book.getStatus().equals("Issued")) {
                    book.setStatus("Available");
                    return;
                }
            }
        }
    }

    // Save the list of books to a file
    public void saveBooksToFile(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(books); // Write the books to the file
        } catch (IOException e) {
            // Handle the exception manually here
            System.err.println("Error saving the books to file: " + e.getMessage());
        }
    }

    // Return the list of books
    public ArrayList<Book> getBooks() {
        return books;
    }

    // Load books from a file (if exists)
    @SuppressWarnings("unchecked")
    public void loadBooksFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            books = (ArrayList<Book>) ois.readObject();
        } catch (IOException e) {
            // Handle IO exceptions, such as file not found or read error
            System.out.println("Error reading from file. The file might not exist or there was an issue accessing it.");
            e.printStackTrace(); // print stack trace for debugging
        } catch (ClassNotFoundException e) {
            // Handle the case where the Book class is not found during deserialization
            System.out.println("Error deserializing data: The Book class was not found.");
            e.printStackTrace(); // print stack trace for debugging
        }
    }

}
