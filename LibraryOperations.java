import java.io.IOException;
import java.util.ArrayList;

public abstract class LibraryOperations {
    protected ArrayList<Book> books; // List to store books

    // Constructor
    public LibraryOperations() {
        books = new ArrayList<>();
    }

    // Abstract methods for operations
    public abstract void addBook(String title, String author, String isbn);

    public abstract void issueBook(String isbn);

    public abstract void returnBook(String isbn);

    public abstract void saveBooksToFile(String fileName) throws IOException;

    public abstract ArrayList<Book> getBooks();
}
