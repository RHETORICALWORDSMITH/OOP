import java.io.Serializable;

// The Book class represents a book object in the library
public class Book implements Serializable {
    private String title; // Title of the book
    private String author; // Author of the book
    private String isbn; // ISBN number of the book
    private String status; // Status of the book (e.g., Available, Issued)

    // Constructor to initialize a new book object
    public Book(String title, String author, String isbn, String status) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.status = status;
    }

    // Getters and Setters for all attributes
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Override the toString() method to provide a meaningful string representation
    // of the book
    @Override
    public String toString() {
        return "Book [Title=" + title + ", Author=" + author + ", ISBN=" + isbn + ", Status=" + status + "]";
    }
}
