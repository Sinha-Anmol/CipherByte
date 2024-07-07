import java.util.*;
import java.io.*;

class User implements Serializable {
    private static final long serialVersionUID = 1L;
    String username;
    String password;
    String profile;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.profile = "";
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void updateProfile(String profile) {
        this.profile = profile;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}

class Exam {
    List<String> questions;
    List<String[]> options;
    List<Integer> answers;

    public Exam() {
        questions = new ArrayList<>();
        options = new ArrayList<>();
        answers = new ArrayList<>();
    }

    public void addQuestion(String question, String[] opts, int answer) {
        questions.add(question);
        options.add(opts);
        answers.add(answer);
    }
}

public class OnlineExam {
    private static Map<String, User> users = new HashMap<>();
    private static Exam exam = new Exam();
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser;

    public static void main(String[] args) {
        loadUsers();
        loadExam();
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Update Profile");
            System.out.println("3. Update Password");
            System.out.println("4. Take Exam");
            System.out.println("5. Logout");
            System.out.println("6. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    updateProfile();
                    break;
                case 3:
                    updatePassword();
                    break;
                case 4:
                    takeExam();
                    break;
                case 5:
                    logout();
                    break;
                case 6:
                    saveUsers();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.dat"))) {
            users = (Map<String, User>) ois.readObject();
        } catch (Exception e) {
            users = new HashMap<>();
        }
    }

    private static void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadExam() {
        exam.addQuestion("What is 2+2?", new String[]{"1", "2", "3", "4"}, 3);
        exam.addQuestion("What is the capital of France?", new String[]{"Berlin", "London", "Paris", "Rome"}, 2);
    }

    private static void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            currentUser = user;
            System.out.println("Login successful.");
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private static void updateProfile() {
        if (currentUser != null) {
            System.out.print("Enter new profile info: ");
            String profile = scanner.nextLine();
            currentUser.updateProfile(profile);
            saveUsers();
            System.out.println("Profile updated.");
        } else {
            System.out.println("Please login first.");
        }
    }

    private static void updatePassword() {
        if (currentUser != null) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            currentUser.updatePassword(newPassword);
            saveUsers();
            System.out.println("Password updated.");
        } else {
            System.out.println("Please login first.");
        }
    }

    private static void takeExam() {
        if (currentUser != null) {
            int score = 0;
            for (int i = 0; i < exam.questions.size(); i++) {
                System.out.println(exam.questions.get(i));
                String[] opts = exam.options.get(i);
                for (int j = 0; j < opts.length; j++) {
                    System.out.println((j + 1) + ". " + opts[j]);
                }
                System.out.print("Select an option: ");
                int choice = scanner.nextInt();
                if (choice - 1 == exam.answers.get(i)) {
                    score++;
                }
            }
            System.out.println("Your score is: " + score + "/" + exam.questions.size());
        } else {
            System.out.println("Please login first.");
        }
    }

    private static void logout() {
        if (currentUser != null) {
            currentUser = null;
            System.out.println("Logged out successfully.");
        } else {
            System.out.println("No user is logged in.");
        }
    }
}
