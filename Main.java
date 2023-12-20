import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

class Equipment {
    String name;
    double price;

    Equipment(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class UserManager {
    ArrayList<Equipment> equipmentList = new ArrayList<>();

    void addEquipment(String name, double price) {
        Equipment equipment = new Equipment(name, price);
        equipmentList.add(equipment);
    }

    void displayEquipment() {
        System.out.println("Available Equipments:");
        for (Equipment equipment : equipmentList) {
            System.out.println(equipment.name + " - $" + equipment.price);
        }
    }

    Equipment findEquipmentByName(String name) {
        for (Equipment equipment : equipmentList) {
            if (equipment.name.equalsIgnoreCase(name)) {
                return equipment;
            }
        }
        return null;
    }
}

class AdminManager {
    private UserManager userManager;
    private double totalSales;

    AdminManager(UserManager userManager) {
        this.userManager = userManager;
    }

    void addEquipment(String name, double price) {
        userManager.addEquipment(name, price);
        System.out.println("Equipment added successfully!");
    }

    void deleteEquipment(String name) {
        Equipment equipment = userManager.findEquipmentByName(name);
        if (equipment != null) {
            userManager.equipmentList.remove(equipment);
            System.out.println("Equipment deleted successfully!");
        } else {
            System.out.println("Equipment not found!");
        }
    }

    void setPrice(String name, double price) {
        Equipment equipment = userManager.findEquipmentByName(name);
        if (equipment != null) {
            equipment.price = price;
            System.out.println("Price set successfully!");
        } else {
            System.out.println("Equipment not found!");
        }
    }

    void generateSalesReport() {
	String fileName="user_bill.txt";
        try (Scanner scanner = new Scanner(new File(fileName))) {
        System.out.println("\nUser Bills from " + fileName + ":");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
        }
    } catch (Exception e) {
        System.out.println("File not found: " + fileName);
    }
    }
}

public class Main {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        AdminManager adminManager = new AdminManager(userManager);

        // Adding more sample data
        userManager.addEquipment("Football", 20.0);
        userManager.addEquipment("Basketball", 25.0);
        userManager.addEquipment("TennisRacket", 15.0);
        userManager.addEquipment("RunningShoes", 40.0);
        userManager.addEquipment("GolfClubs", 120.0);
        userManager.addEquipment("YogaMat", 15.0);
        userManager.addEquipment("SwimmingGoggles", 2.0);
        userManager.addEquipment("CyclingHelmet", 35.0);
        userManager.addEquipment("JumpRope", 7.0);
        userManager.addEquipment("Dumbbells", 50.0);
        userManager.addEquipment("Tennisball", 5.0);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n******SPORTS EQUIPMENT MANAGEMENT SYSTEM******");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter admin password:");
                    String password = scanner.next();

                    // A simple password check
                    if (password.equals("admin123")) {
                        adminMenu(scanner, adminManager);
                    } else {
                        System.out.println("Invalid password. Access denied.");
                    }
                    break;

                case 2:
                    userMenu(scanner, userManager);
                    break;

                case 3:
                    System.out.println("Exiting program. Goodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void adminMenu(Scanner scanner, AdminManager adminManager) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Equipment");
            System.out.println("2. Delete Equipment");
            System.out.println("3. Set Price");
            System.out.println("4. Generate Sales Report");
            System.out.println("5. Back to Main Menu");

            int adminChoice = scanner.nextInt();

            switch (adminChoice) {
                case 1:
                    System.out.println("Enter new equipment name:");
                    String newEquipmentName = scanner.next();
                    System.out.println("Enter new equipment price:");
                    double newEquipmentPrice = scanner.nextDouble();
                    adminManager.addEquipment(newEquipmentName, newEquipmentPrice);
                    break;

                case 2:
                    System.out.println("Enter equipment name to delete:");
                    String equipmentToDelete = scanner.next();
                    adminManager.deleteEquipment(equipmentToDelete);
                    break;

                case 3:
                    System.out.println("Enter equipment name:");
                    String equipmentName = scanner.next();
                    System.out.println("Enter new price:");
                    double newPrice = scanner.nextDouble();
                    adminManager.setPrice(equipmentName, newPrice);
                    break;

                case 4:
                    adminManager.generateSalesReport();
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void userMenu(Scanner scanner, UserManager userManager) {
        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1. Display Equipment");
            System.out.println("2. Buy Equipment");
            System.out.println("3. Back to Main Menu");

            int userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1:
                    userManager.displayEquipment();
                    break;

                case 2:
                    System.out.println("Enter equipment name to buy:");
                    String equipmentToBuy = scanner.next();
                    Equipment equipment = userManager.findEquipmentByName(equipmentToBuy);

                    if (equipment != null) {
                        System.out.println("Enter quantity:");
                        int quantity = scanner.nextInt();

                        // Calculate total price
                        double totalPrice = equipment.price * quantity;

                        // Display and save the bill
                        System.out.println("Item: " + equipment.name + " - Quantity: " + quantity + " - Total: $" + totalPrice);
                        saveBillToFile("user_bill.txt", equipment.name, equipment.price, quantity, totalPrice);
                    } else {
                        System.out.println("Equipment not found!");
                    }
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void saveBillToFile(String fileName, String itemName, double price, int quantity, double totalPrice) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(itemName + " - Quantity: " + quantity + " - Total: $" + totalPrice + "\n");
            System.out.println("Bill saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}