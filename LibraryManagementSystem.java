import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

public class LibraryManagementSystem {
    private JFrame frame;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private LibraryOperations library; // Use the abstract class
    private final String FILE_NAME = "books.txt";

    public LibraryManagementSystem() {
        library = new Library(); // Instantiate the concrete Library class
        ((Library) library).loadBooksFromFile(FILE_NAME); // Load books from file
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());

        String[] columnNames = { "Title", "Author", "ISBN", "Status" };
        tableModel = new DefaultTableModel(columnNames, 0);
        bookTable = new JTable(tableModel);
        refreshTable();

        JScrollPane scrollPane = new JScrollPane(bookTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Book");
        JButton issueButton = new JButton("Issue Book");
        JButton returnButton = new JButton("Return Book");
        JButton saveButton = new JButton("Save and Exit");

        buttonPanel.add(addButton);
        buttonPanel.add(issueButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(saveButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addBookDialog());
        issueButton.addActionListener(e -> issueBookDialog());
        returnButton.addActionListener(e -> returnBookDialog());
        saveButton.addActionListener(e -> saveAndExit());

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void saveAndExit() {
        try {
            library.saveBooksToFile(FILE_NAME); // Save books
            System.exit(0); // Exit if save is successful
        } catch (IOException e) {
            // Show error message in case of failure
            JOptionPane.showMessageDialog(frame, "Error saving data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Book book : library.getBooks()) {
            tableModel.addRow(new Object[] { book.getTitle(), book.getAuthor(), book.getIsbn(), book.getStatus() });
        }
    }

    private void addBookDialog() {
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField isbnField = new JTextField();

        Object[] message = {
                "Title:", titleField,
                "Author:", authorField,
                "ISBN:", isbnField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Add Book", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();

            if (!title.isEmpty() && !author.isEmpty() && !isbn.isEmpty()) {
                library.addBook(title, author, isbn); // Use the addBook method from the Library class
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void issueBookDialog() {
        String isbn = JOptionPane.showInputDialog(frame, "Enter ISBN of the book to issue:");
        if (isbn != null && !isbn.isEmpty()) {
            library.issueBook(isbn); // Use the issueBook method from the Library class
            refreshTable();
        }
    }

    private void returnBookDialog() {
        String isbn = JOptionPane.showInputDialog(frame, "Enter ISBN of the book to return:");
        if (isbn != null && !isbn.isEmpty()) {
            library.returnBook(isbn); // Use the returnBook method from the Library class
            refreshTable();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LibraryManagementSystem::new);
    }
}
