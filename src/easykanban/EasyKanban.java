package easykanban;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
public class EasyKanban {

    private String name;
    private String surname;
    private String username;
    private String password;
    final List<Task> tasks;
    int totalHours;

    public EasyKanban() {
        this.tasks = new ArrayList<>();
        this.totalHours = 0;
    }

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() >= 5 && username.length() <= 10;
    }

    public boolean checkPasswordComplexity(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[^a-zA-Z0-9].*");
    }

    public String registerUser(String username, String password) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted. Please ensure it contains an underscore and is no more than 10 characters long.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted. Ensure it contains at least 8 characters, a capital letter, a number, and a special character.";
        }
        this.username = username;
        this.password = password;
        return "User successfully registered.";
    }

    public boolean loginUser(String username, String password) {
        return this.username != null && this.username.equals(username) && this.password != null && this.password.equals(password);
    }

    public String returnLoginStatus(boolean isLoginSuccessful) {
        if (isLoginSuccessful) {
            return "Welcome to EasyKanban, " + name + " " + surname + "!";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

public void startApp() {
    while (true) {
        name = JOptionPane.showInputDialog("Enter your name:");
        if (name == null) return;

        surname = JOptionPane.showInputDialog("Enter your surname:");
        if (surname == null) return;

        username = JOptionPane.showInputDialog("Enter a username:");
        if (username == null) return; 

        password = JOptionPane.showInputDialog("Enter a password:");
        if (password == null) return;

        String registrationMessage = registerUser(username, password);
        JOptionPane.showMessageDialog(null, registrationMessage);

        if (registrationMessage.equals("User successfully registered.")) {
            boolean loginSuccessful = false;
            while (!loginSuccessful) {
                String loginUsername = JOptionPane.showInputDialog("Enter your username to login:");
                if (loginUsername == null) return;

                String loginPassword = JOptionPane.showInputDialog("Enter your password to login:");
                if (loginPassword == null) return;

                loginSuccessful = loginUser(loginUsername, loginPassword);
                String loginStatus = returnLoginStatus(loginSuccessful);
                JOptionPane.showMessageDialog(null, loginStatus);
            }
            manageTasks();
            break;
        }
    }
}


void manageTasks() {
    OUTER:
    while (true) {
        String[] options = {"Add tasks", "Show report (Coming Soon)", "Quit"};
        int option = JOptionPane.showOptionDialog(null, "Select an option:", "Task Management",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (option == JOptionPane.CLOSED_OPTION) return;

        switch (option) {
            case 2 -> {
                // Quit
                return; // Exit the program
            }
            case 0 -> {
                // Add tasks
                String numTasksString = JOptionPane.showInputDialog("How many tasks do you wish to enter?");
                if (numTasksString == null) continue; // Return to menu if cancel is pressed

                int numTasks;
                try {
                    numTasks = Integer.parseInt(numTasksString);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid number. Please try again.");
                    continue;
                }

                for (int i = 0; i < numTasks; i++) {
                    Task task = createTask(i);
                    if (task == null) break; // Exit if cancel is pressed
                    tasks.add(task);
                    totalHours += task.getDuration();
                    JOptionPane.showMessageDialog(null, task.printTaskDetails());
                }
                JOptionPane.showMessageDialog(null, "Total hours across all tasks: " + returnTotalHours());
            }
            default -> JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
        }
    }
}


private Task createTask(int taskNumber) {
    String taskName = JOptionPane.showInputDialog("Enter Task Name:");
    if (taskName == null) return null; // Return null if cancel is pressed

    String taskDescription;
    do {
        taskDescription = JOptionPane.showInputDialog("Enter Task Description (max 50 characters):");
        if (taskDescription == null) return null; // Return null if cancel is pressed
    } while (!checkTaskDescription(taskDescription));

    String developerDetails = JOptionPane.showInputDialog("Enter Developer Details (Name and Surname):");
    if (developerDetails == null) return null; // Return null if cancel is pressed

    String durationString = JOptionPane.showInputDialog("Enter Task Duration (in hours):");
    if (durationString == null) return null; // Return null if cancel is pressed

    int duration = Integer.parseInt(durationString);

    String status = (String) JOptionPane.showInputDialog(null, "Select Task Status:",
            "Task Status", JOptionPane.QUESTION_MESSAGE, null, new String[]{"To Do", "Doing", "Done"}, "To Do");

    return new Task(taskName, taskNumber, taskDescription, developerDetails, duration, status);
}


    public boolean checkTaskDescription(String description) {
        return description.length() <= 50;
    }

    public int returnTotalHours() {
        return totalHours;
    }

    public static void main(String[] args) {
        EasyKanban app = new EasyKanban();
        app.startApp();
    }
}

class Task {
    private final String name;
    private final int taskNumber;
    private final String description;
    private final String developerDetails;
    private final int duration;
    private final String status;

    public Task(String name, int taskNumber, String description, String developerDetails, int duration, String status) {
        this.name = name;
        this.taskNumber = taskNumber;
        this.description = description;
        this.developerDetails = developerDetails;
        this.duration = duration;
        this.status = status;
    }

    public String printTaskDetails() {
        String taskID = createTaskID();
        return "Task Status: " + status + "\nDeveloper Details: " + developerDetails + "\nTask Number: " + taskNumber +
                "\nTask Name: " + name + "\nTask Description: " + description + "\nTask ID: " + taskID + "\nDuration: " + duration + " hours";
    }

    private String createTaskID() {
        return (name.substring(0, 2).toUpperCase() + ":" + taskNumber + ":" + developerDetails.substring(developerDetails.length() - 3).toUpperCase());
    }

    public String getName() {
        return name;
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    public String getDeveloperDetails() {
        return developerDetails;
    }

    public int getDuration() {
        return duration;
    }
}