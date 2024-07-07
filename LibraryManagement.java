import java.sql.*;
import java.util.Scanner;

class DatabaseConnection {
    private Connection conn;

    public DatabaseConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }
}

class Admin {
    private Connection conn;

    public Admin(Connection conn) {
        this.conn = conn;
    }

    public void addBook(String title, String author) {
        try {
            String query = "INSERT INTO books (title, author) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeBook(int bookId) {
        try {
            String query = "DELETE FROM books WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBook(int bookId, String title, String author) {
        try {
            String query = "UPDATE books SET title = ?, author = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setInt(3, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class User {
    private Connection conn;

    public User(Connection conn) {
        this.conn = conn;
    }

    public void viewBooks() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Title: " + rs.getString("title") + ", Author: " + rs.getString("author"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void issueBook(int bookId, int userId) {
        try {
            String query = "INSERT INTO issued_books (book_id, user_id) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bookId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook(int bookId, int userId) {
        try {
            String query = "DELETE FROM issued_books WHERE book_id = ? AND user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bookId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class LibraryManagement {
    private static Scanner scanner = new Scanner(System.in);
    private static DatabaseConnection dbConn = new DatabaseConnection();
    private static Admin admin = new Admin(dbConn.getConnection());
    private static User user = new User(dbConn.getConnection());

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Admin - Add Book");
            System.out.println("2. Admin - Remove Book");
            System.out.println("3. Admin - Update Book");
            System.out.println("4. User - View Books");
            System.out.println("5. User - Issue Book");
            System.out.println("6. User - Return Book");
            System.out.println("7. Exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = scanner.next();
                    System.out.print("Enter author: ");
                    String author = scanner.next();
                    admin.addBook(title, author);
                    break;
                case 2:
                    System.out.print("Enter book ID: ");
                    int bookId = scanner.nextInt();
                    admin.removeBook(bookId);
                    break;
                case 3:
                    System.out.print("Enter book ID: ");
                    bookId = scanner.nextInt();
                    System.out.print("Enter title: ");
                    title = scanner.next();
                    System.out.print("Enter author: ");
                    author = scanner.next();
                    admin.updateBook(bookId, title, author);
                    break;
                case 4:
                    user.viewBooks();
                    break;
                case 5:
                    System.out.print("Enter book ID: ");
                    bookId = scanner.nextInt();
                    System.out.print("Enter user ID: ");
                    int userId = scanner.nextInt();
                    user.issueBook(bookId, userId);
                    break;
                case 6:
                    System.out.print("Enter book ID: ");
                    bookId = scanner.nextInt();
                    System.out.print("Enter user ID: ");
                    userId = scanner.nextInt();
                    user.returnBook(bookId, userId);
                    break;
                case 7:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
