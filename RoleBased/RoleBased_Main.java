import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Role-Based Notifications System
public class RoleBased_Main {
    
    // Abstract User class
    abstract static class User {
        protected String name;
        protected String id;
        protected String role;
        
        public User(String name, String id, String role) {
            this.name = name;
            this.id = id;
            this.role = role;
        }
        
        // Abstract method - each role implements differently
        public abstract void receiveAlert(String alertMessage);
        public abstract String getContactInfo();
        public abstract List<String> getNotificationMethods();
        
        public void displayInfo() {
            System.out.println("\n👤 " + name + " (" + role + ")");
            System.out.println("   ID: " + id);
            System.out.println("   Contact: " + getContactInfo());
            System.out.println("   Receives via: " + getNotificationMethods());
        }
        
        public String getRole() { return role; }
        public String getName() { return name; }
        public String getId() { return id; }
    }
    
    // Citizen Role
    static class Citizen extends User {
        private String phone;
        private String email;
        private String appUserId;
        
        public Citizen(String name, String id, String phone, String email) {
            super(name, id, "CITIZEN");
            this.phone = phone;
            this.email = email;
            this.appUserId = "CITIZEN_APP_" + id;
        }
        
        @Override
        public void receiveAlert(String alertMessage) {
            System.out.println("\n🚨 ALERT FOR CITIZEN: " + name);
            System.out.println("-".repeat(40));
            System.out.println("📱 SMS to " + phone + ": " + alertMessage);
            System.out.println("📱 In-App to " + appUserId + ": " + alertMessage);
            System.out.println("✅ Citizen notified successfully!");
        }
        
        @Override
        public String getContactInfo() {
            return "Phone: " + phone + ", Email: " + email;
        }
        
        @Override
        public List<String> getNotificationMethods() {
            return List.of("SMS", "In-App Notification");
        }
        
        public String getPhone() { return phone; }
        public String getEmail() { return email; }
    }
    
    // City Officer Role
    static class CityOfficer extends User {
        private String email;
        private String department;
        private String employeeId;
        
        public CityOfficer(String name, String id, String email, String department) {
            super(name, id, "CITY_OFFICER");
            this.email = email;
            this.department = department;
            this.employeeId = "OFFICER_" + id;
        }
        
        @Override
        public void receiveAlert(String alertMessage) {
            System.out.println("\n🚨 OFFICIAL ALERT FOR CITY OFFICER: " + name);
            System.out.println("-".repeat(40));
            System.out.println("📧 OFFICIAL EMAIL to " + email + ":");
            System.out.println("   Subject: City Department Alert");
            System.out.println("   Department: " + department);
            System.out.println("   Message: " + alertMessage);
            System.out.println("   Priority: Official Communication");
            System.out.println("✅ Officer notified via official channels!");
        }
        
        @Override
        public String getContactInfo() {
            return "Official Email: " + email + ", Dept: " + department;
        }
        
        @Override
        public List<String> getNotificationMethods() {
            return List.of("Official Email Only");
        }
        
        public String getEmail() { return email; }
        public String getDepartment() { return department; }
    }
    
    // Emergency Responder Role
    static class EmergencyResponder extends User {
        private String phone;
        private String email;
        private String emergencyType;
        private String responderId;
        
        public EmergencyResponder(String name, String id, String phone, String email, String emergencyType) {
            super(name, id, "EMERGENCY_RESPONDER");
            this.phone = phone;
            this.email = email;
            this.emergencyType = emergencyType;
            this.responderId = "EMERG_" + id;
        }
        
        @Override
        public void receiveAlert(String alertMessage) {
            System.out.println("\n🚨🚨 EMERGENCY ALERT FOR RESPONDER: " + name);
            System.out.println("=".repeat(50));
            System.out.println("⚠️  RESPONDER TYPE: " + emergencyType);
            System.out.println("⚠️  RESPONDER ID: " + responderId);
            System.out.println("\n📱 HIGH PRIORITY SMS to " + phone + ":");
            System.out.println("   [EMERGENCY] " + alertMessage);
            System.out.println("   Location: Dispatch Center");
            System.out.println("   Code: RED");
            
            System.out.println("\n📧 HIGH PRIORITY EMAIL to " + email + ":");
            System.out.println("   Subject: EMERGENCY RESPONSE REQUIRED");
            System.out.println("   Type: " + emergencyType);
            System.out.println("   Message: " + alertMessage);
            System.out.println("   Action Required: IMMEDIATE");
            
            System.out.println("\n✅ Emergency responder activated!");
        }
        
        @Override
        public String getContactInfo() {
            return "Emergency Phone: " + phone + ", Emergency Email: " + email + ", Type: " + emergencyType;
        }
        
        @Override
        public List<String> getNotificationMethods() {
            return List.of("High-Priority SMS", "High-Priority Email");
        }
        
        public String getPhone() { return phone; }
        public String getEmail() { return email; }
        public String getEmergencyType() { return emergencyType; }
    }
    
    // Alert Service
    static class AlertService {
        private List<User> users;
        
        public AlertService() {
            this.users = new ArrayList<>();
            initializeSampleUsers();
        }
        
        private void initializeSampleUsers() {
            users.add(new Citizen("John Doe", "C001", "+1234567890", "john@email.com"));
            users.add(new Citizen("Alice Smith", "C002", "+0987654321", "alice@email.com"));
            users.add(new CityOfficer("Robert Brown", "O001", "robert@city.gov", "Transportation"));
            users.add(new CityOfficer("Sarah Johnson", "O002", "sarah@city.gov", "Public Works"));
            users.add(new EmergencyResponder("Mike Wilson", "E001", "+1122334455", "mike@fire.gov", "Fire Department"));
            users.add(new EmergencyResponder("Lisa Chen", "E002", "+5566778899", "lisa@medical.gov", "Medical Response"));
        }
        
        public void addUser(User user) {
            users.add(user);
            System.out.println("✅ " + user.getRole() + " added: " + user.getName());
        }
        
        public void displayAllUsers() {
            System.out.println("\n=== REGISTERED USERS ===");
            System.out.println("Total Users: " + users.size());
            System.out.println("-".repeat(60));
            
            for (int i = 0; i < users.size(); i++) {
                System.out.print((i + 1) + ". ");
                users.get(i).displayInfo();
            }
        }
        
        public void sendAlertToAll(String message) {
            System.out.println("\n📢 CITY-WIDE ALERT BROADCAST");
            System.out.println("=".repeat(50));
            System.out.println("Message: " + message);
            System.out.println("Recipients: ALL " + users.size() + " USERS");
            System.out.println("-".repeat(50));
            
            for (User user : users) {
                user.receiveAlert(message);
                System.out.println();
            }
        }
        
        public void sendAlertToRole(String role, String message) {
            System.out.println("\n🎯 ROLE-SPECIFIC ALERT");
            System.out.println("=".repeat(40));
            System.out.println("Target Role: " + role);
            System.out.println("Message: " + message);
            System.out.println("-".repeat(40));
            
            boolean found = false;
            for (User user : users) {
                if (user.getRole().equalsIgnoreCase(role)) {
                    user.receiveAlert(message);
                    System.out.println();
                    found = true;
                }
            }
            
            if (!found) {
                System.out.println("No users found with role: " + role);
            }
        }
        
        public void sendAlertToUser(int userIndex, String message) {
            if (userIndex >= 0 && userIndex < users.size()) {
                User user = users.get(userIndex);
                System.out.println("\n🎯 TARGETED ALERT TO: " + user.getName());
                System.out.println("-".repeat(40));
                user.receiveAlert(message);
            }
        }
        
        public void displayRoleStatistics() {
            System.out.println("\n=== ROLE STATISTICS ===");
            
            int citizens = 0, officers = 0, responders = 0;
            for (User user : users) {
                switch (user.getRole()) {
                    case "CITIZEN": citizens++; break;
                    case "CITY_OFFICER": officers++; break;
                    case "EMERGENCY_RESPONDER": responders++; break;
                }
            }
            
            System.out.println("Citizens: " + citizens + " users");
            System.out.println("   Notification Method: SMS + In-App");
            System.out.println();
            System.out.println("City Officers: " + officers + " users");
            System.out.println("   Notification Method: Official Email Only");
            System.out.println();
            System.out.println("Emergency Responders: " + responders + " users");
            System.out.println("   Notification Method: High-Priority SMS + Email");
        }
        
        public List<User> getUsers() { return users; }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AlertService alertService = new AlertService();
        
        System.out.println("==========================================");
        System.out.println("   ROLE-BASED NOTIFICATIONS SYSTEM  ");
        System.out.println("==========================================");
        System.out.println("Different notification methods for different user roles");
        System.out.println();
        
        boolean running = true;
        while (running) {
            System.out.println("\n=== ROLE-BASED NOTIFICATIONS MENU ===");
            System.out.println("1. View All Users");
            System.out.println("2. Add New User");
            System.out.println("3. Send Alert to All Users");
            System.out.println("4. Send Alert to Specific Role");
            System.out.println("5. Send Alert to Specific User");
            System.out.println("6. View Role Statistics");
            System.out.println("7. Show Role Details");
            System.out.println("8. Exit");
            System.out.print("\nSelect option (1-8): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    alertService.displayAllUsers();
                    break;
                    
                case 2:
                    System.out.println("\n=== ADD NEW USER ===");
                    System.out.println("Select role:");
                    System.out.println("1. Citizen (SMS + In-App)");
                    System.out.println("2. City Officer (Email Only)");
                    System.out.println("3. Emergency Responder (High-Priority SMS + Email)");
                    System.out.print("Enter choice (1-3): ");
                    
                    int roleChoice = scanner.nextInt();
                    scanner.nextLine();
                    
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    
                    System.out.print("Enter ID: ");
                    String id = scanner.nextLine();
                    
                    switch (roleChoice) {
                        case 1:
                            System.out.print("Enter phone number: ");
                            String phone = scanner.nextLine();
                            System.out.print("Enter email: ");
                            String email = scanner.nextLine();
                            alertService.addUser(new Citizen(name, id, phone, email));
                            break;
                            
                        case 2:
                            System.out.print("Enter official email: ");
                            String officerEmail = scanner.nextLine();
                            System.out.print("Enter department: ");
                            String department = scanner.nextLine();
                            alertService.addUser(new CityOfficer(name, id, officerEmail, department));
                            break;
                            
                        case 3:
                            System.out.print("Enter emergency phone: ");
                            String responderPhone = scanner.nextLine();
                            System.out.print("Enter emergency email: ");
                            String responderEmail = scanner.nextLine();
                            System.out.print("Enter emergency type (Fire/Police/Medical): ");
                            String type = scanner.nextLine();
                            alertService.addUser(new EmergencyResponder(name, id, responderPhone, responderEmail, type));
                            break;
                    }
                    break;
                    
                case 3:
                    System.out.print("\nEnter broadcast alert message: ");
                    String broadcastMsg = scanner.nextLine();
                    alertService.sendAlertToAll(broadcastMsg);
                    break;
                    
                case 4:
                    System.out.println("\n=== SELECT ROLE ===");
                    System.out.println("1. CITIZEN (SMS + In-App)");
                    System.out.println("2. CITY_OFFICER (Email Only)");
                    System.out.println("3. EMERGENCY_RESPONDER (High-Priority SMS + Email)");
                    System.out.print("Enter role choice (1-3): ");
                    
                    int roleSelect = scanner.nextInt();
                    scanner.nextLine();
                    
                    String role = "";
                    switch (roleSelect) {
                        case 1: role = "CITIZEN"; break;
                        case 2: role = "CITY_OFFICER"; break;
                        case 3: role = "EMERGENCY_RESPONDER"; break;
                        default: role = "CITIZEN";
                    }
                    
                    System.out.print("Enter message for " + role + "s: ");
                    String roleMsg = scanner.nextLine();
                    alertService.sendAlertToRole(role, roleMsg);
                    break;
                    
                case 5:
                    alertService.displayAllUsers();
                    System.out.print("\nSelect user number to alert (1-" + alertService.getUsers().size() + "): ");
                    int userIndex = scanner.nextInt() - 1;
                    scanner.nextLine();
                    
                    if (userIndex >= 0 && userIndex < alertService.getUsers().size()) {
                        System.out.print("Enter alert message: ");
                        String userMsg = scanner.nextLine();
                        alertService.sendAlertToUser(userIndex, userMsg);
                    }
                    break;
                    
                case 6:
                    alertService.displayRoleStatistics();
                    break;
                    
                case 7:
                    System.out.println("\n=== ROLE DETAILS ===");
                    System.out.println("\n1. CITIZEN ROLE:");
                    System.out.println("   - Notification Methods: SMS + In-App");
                    System.out.println("   - Purpose: General public alerts");
                    System.out.println("   - Example: Traffic updates, weather warnings");
                    System.out.println("   - Priority: Normal");
                    
                    System.out.println("\n2. CITY OFFICER ROLE:");
                    System.out.println("   - Notification Methods: Official Email Only");
                    System.out.println("   - Purpose: Official communications");
                    System.out.println("   - Example: Department meetings, policy updates");
                    System.out.println("   - Priority: Official");
                    
                    System.out.println("\n3. EMERGENCY RESPONDER ROLE:");
                    System.out.println("   - Notification Methods: High-Priority SMS + Email");
                    System.out.println("   - Purpose: Emergency response activation");
                    System.out.println("   - Example: Fire alerts, medical emergencies");
                    System.out.println("   - Priority: HIGH/URGENT");
                    break;
                    
                case 8:
                    System.out.println("\nExiting Role-Based Notifications System...");
                    running = false;
                    break;
                    
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
            
            if (running) {
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        scanner.close();
    }
}
