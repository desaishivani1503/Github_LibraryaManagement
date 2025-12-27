import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

class Book {
    String name;
    String status;
    String borrower;

    Book(String name) {
        this.name = name;
        this.status = "Available";
        this.borrower = "";
    }
}

public class LibraryManagementSystem extends JFrame {

    ArrayList<Book> bookList = new ArrayList<>();
    JTable table;
    DefaultTableModel model;
    JTextField bookField, borrowerField;

    public LibraryManagementSystem() {

        setTitle("Library Management System");
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel();
        model.addColumn("Book Name");
        model.addColumn("Status");
        model.addColumn("Borrower");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));

        bookField = new JTextField();
        borrowerField = new JTextField();

        JButton addBtn = new JButton("Add Book");
        JButton removeBtn = new JButton("Remove Book");
        JButton issueBtn = new JButton("Issue Book");
        JButton returnBtn = new JButton("Return Book");

        inputPanel.add(new JLabel("Book Name"));
        inputPanel.add(bookField);
        inputPanel.add(addBtn);
        inputPanel.add(removeBtn);

        inputPanel.add(new JLabel("Borrower Name"));
        inputPanel.add(borrowerField);
        inputPanel.add(issueBtn);
        inputPanel.add(returnBtn);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        addBtn.addActionListener(e -> {
            String bookName = bookField.getText().trim();
            if (!bookName.isEmpty()) {
                Book b = new Book(bookName);
                bookList.add(b);
                model.addRow(new Object[]{b.name, b.status, b.borrower});
                bookField.setText("");
            }
        });

        removeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                bookList.remove(row);
                model.removeRow(row);
            }
        });

        issueBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            String borrower = borrowerField.getText().trim();
            if (row >= 0 && !borrower.isEmpty()) {
                Book b = bookList.get(row);
                if (b.status.equals("Available")) {
                    b.status = "Issued";
                    b.borrower = borrower;
                    model.setValueAt("Issued", row, 1);
                    model.setValueAt(borrower, row, 2);
                    borrowerField.setText("");
                }
            }
        });

        returnBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Book b = bookList.get(row);
                b.status = "Available";
                b.borrower = "";
                model.setValueAt("Available", row, 1);
                model.setValueAt("", row, 2);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LibraryManagementSystem().setVisible(true);
        });
    }
}
