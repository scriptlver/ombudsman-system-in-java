import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OmbudsmanTest {
    private ArrayList<User> users;
    private ArrayList<Manifestation> manifestations;

    public OmbudsmanTest() {
        this.users = new ArrayList<>();
        this.manifestations = new ArrayList<>();
    }

    public void registerUser(User user) {
        users.add(user);
    }

    public void registerManifestation(Manifestation manifestation) {
        manifestations.add(manifestation);
    }

    public ArrayList<Manifestation> listManifestationsByDay(Date date) {
        ArrayList<Manifestation> manifestationsByDay = new ArrayList<>();
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

        for (Manifestation m : manifestations) {
            String creationDateStr = fmt.format(m.retrieveDate());
            String dateStr = fmt.format(date);

            if (creationDateStr.equals(dateStr)) {
                manifestationsByDay.add(m);
            }
        }

        System.out.println("Date provided: " + fmt.format(date));
        System.out.println("Manifestations found for this date: " + manifestationsByDay.size());

        return manifestationsByDay;
    }

    public ArrayList<Manifestation> listManifestationsByUser(User user) {
        ArrayList<Manifestation> manifestationsByUser = new ArrayList<>();
        for (Manifestation m : manifestations) {
            if (m.retrieveAuthor().equals(user.getName())) {
                manifestationsByUser.add(m);
            }
        }
        return manifestationsByUser;
    }

    private boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        return fmt.format(date1).equals(fmt.format(date2));
    }

    public static void main(String[] args) {
        OmbudsmanTest system = new OmbudsmanTest();

        while (true) {
            int option = Integer.parseInt(JOptionPane.showInputDialog(
                    "Java Ombudsman System!\n" +
                            "1. Register User\n" +
                            "2. Register Manifestation\n" +
                            "3. List Manifestations by Day\n" +
                            "4. List Manifestations by User\n" +
                            "5. Exit\n" +
                            "Choose an option from 1 to 5:"));

            switch (option) {
                case 1:
                    String userType = JOptionPane.showInputDialog(
                            "Enter the user type (Student, Teacher, or Collaborator):");
                    String name = JOptionPane.showInputDialog("User's name:");
                    String phone = JOptionPane.showInputDialog("User's phone:");
                    String birthDateStr = JOptionPane.showInputDialog("Birth date (format dd/MM/yyyy):");
                    Date birthDate = null;

                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        birthDate = dateFormat.parse(birthDateStr);
                    } catch (java.text.ParseException e) {
                        JOptionPane.showMessageDialog(null, "Invalid date format. Please use dd/MM/yyyy format.");
                        continue;
                    }

                    if (userType.equalsIgnoreCase("Student")) {
                        double average = Double.parseDouble(JOptionPane.showInputDialog("Student's average:"));
                        system.registerUser(new Student(name, phone, birthDate, average));
                    } else if (userType.equalsIgnoreCase("Teacher")) {
                        int workload = Integer.parseInt(JOptionPane.showInputDialog("Teacher's workload:"));
                        system.registerUser(new Teacher(name, phone, birthDate, workload));
                    } else if (userType.equalsIgnoreCase("Collaborator")) {
                        String sector = JOptionPane.showInputDialog("Collaborator's sector:");
                        system.registerUser(new Collaborator(name, phone, birthDate, sector));
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid user type!");
                    }
                    break;
                case 2:
                    String description = JOptionPane.showInputDialog("Manifestation description:");
                    String manifestationTypeStr = JOptionPane.showInputDialog(
                            "Manifestation type (Suggestion, Praise, or Criticism):");
                    ManifestationType manifestationType = ManifestationType.valueOf(manifestationTypeStr.toUpperCase());
                    String author = JOptionPane.showInputDialog("Manifestation author's name:");
                    User authorManifestation = null;
                    for (User u : system.users) {
                        if (u.getName().equals(author)) {
                            authorManifestation = u;
                            break;
                        }
                    }
                    if (authorManifestation != null) {
                        String manifestationDateStr = JOptionPane.showInputDialog("Manifestation date (format dd/MM/yyyy):");
                        Date manifestationDate = null;
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            manifestationDate = dateFormat.parse(manifestationDateStr);
                        } catch (java.text.ParseException e) {
                            JOptionPane.showMessageDialog(null, "Invalid date format. Please use dd/MM/yyyy format.");
                            continue;
                        }
                        system.registerManifestation(new Manifestation(description, manifestationDate, authorManifestation, manifestationType));
                    } else {
                        JOptionPane.showMessageDialog(null, "User not found!");
                    }
                    break;
                case 3:
                    String dateStr = JOptionPane.showInputDialog("Enter the date to list manifestations (format dd/MM/yyyy):");
                    if (dateStr != null && !dateStr.isEmpty()) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            Date listDate = dateFormat.parse(dateStr);
                            ArrayList<Manifestation> manifestationsByDay = system.listManifestationsByDay(listDate);
                            if (!manifestationsByDay.isEmpty()) {
                                StringBuilder message = new StringBuilder("Manifestations for " + dateStr + ":\n");
                                for (Manifestation m : manifestationsByDay) {
                                    message.append("- ").append(m.getDescription()).append(" (").append(m.getType()).append(")\n");
                                }
                                JOptionPane.showMessageDialog(null, message.toString());
                            } else {
                                JOptionPane.showMessageDialog(null, "No manifestations registered for this date.");
                            }
                        } catch (java.text.ParseException e) {
                            JOptionPane.showMessageDialog(null, "Invalid date format. Please use dd/MM/yyyy format.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid date.");
                    }
                    break;
                case 4:
                    String userName = JOptionPane.showInputDialog("Enter the user's name to list manifestations:");
                    User userToList = null;
                    for (User u : system.users) {
                        if (u.getName().equalsIgnoreCase(userName)) {
                            userToList = u;
                            break;
                        }
                    }
                    if (userToList != null) {
                        ArrayList<Manifestation> manifestationsByUser = system.listManifestationsByUser(userToList);
                        if (!manifestationsByUser.isEmpty()) {
                            StringBuilder message = new StringBuilder("Manifestations for user " + userName + ":\n");
                            for (Manifestation m : manifestationsByUser) {
                                message.append("- ").append(m.getDescription()).append(" (").append(m.getType()).append(")\n");
                            }
                            JOptionPane.showMessageDialog(null, message.toString());
                        } else {
                            JOptionPane.showMessageDialog(null, "No manifestations registered for this user.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "User not found.");
                    }
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please enter a valid option (1 to 5).");
            }
        }
    }
}
