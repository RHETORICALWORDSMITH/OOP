import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

public class LibraryManagementSystem {
    private JFrame frame;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private Library library;
    private final String FILE_NAME = "books.txt";

    public LibraryManagementSystem() {
        library = new Library(); // Instantiate the concrete Library class
        (library).loadBooksFromFile(FILE_NAME); // Load books from file
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // sub container(div1)
        JPanel mainPanel = new JPanel(new BorderLayout());

        String[] columnNames = { "Title", "Author", "ISBN", "Status" };
        // makes the model of the tabel i.e column, rows
        tableModel = new DefaultTableModel(columnNames, 0);
        // dislays the tabel using JTable
        bookTable = new JTable(tableModel);
        refreshTable();

        // adds scroll bars if the content is overflowing
        JScrollPane scrollPane = new JScrollPane(bookTable);

        // adding elements in mainpanel(div1)
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // another sub container (div2)
        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Add Book");
        JButton issueButton = new JButton("Issue Book");
        JButton returnButton = new JButton("Return Book");
        JButton saveButton = new JButton("Save and Exit");
        JButton deleteButton = new JButton("Delete Book");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(issueButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(saveButton);

        // adding (div2) in main body
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addBookDialog());
        deleteButton.addActionListener(e -> deleteBookDialog());
        issueButton.addActionListener(e -> issueBookDialog());
        returnButton.addActionListener(e -> returnBookDialog());
        saveButton.addActionListener(e -> saveAndExit());

        // now adds the main subconstainer to the Jframe default pane
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
        // clears previous existing rows if this is not done then they will also be
        // added again if a new book is added
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

        // returns a different int calue for OK and cancel and is stored in option
        // variable
        int option = JOptionPane.showConfirmDialog(frame, message, "Add Book", JOptionPane.OK_CANCEL_OPTION);

        // if int value of option matches int value of ok then user pressed ok
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

    private void deleteBookDialog() {
        String isbn = JOptionPane.showInputDialog(frame, "Enter ISBN of the book to delete:");
        if (isbn != null && !isbn.isEmpty()) {
            library.deleteBook(isbn); // Use the deleteBook method from the Library class
            refreshTable();
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
        // Swing has a rule that UI updates should only happen on the Event Dispatch
        // Thread (EDT). If we don't follow this rule, the program can behave strangely.
        // SwingUtilities.invokeLater() is a method that schedules a Runnable to be
        // executed on the Event Dispatch Thread (EDT).
        // Asynchronous Execution: By using invokeLater(), you allow the main thread to
        // continue its execution without being blocked while waiting for the Swing
        // components to be created or updated.
        SwingUtilities.invokeLater(LibraryManagementSystem::new);
    }
}
