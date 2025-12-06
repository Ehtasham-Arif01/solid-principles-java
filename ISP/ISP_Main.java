import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Interface Segregation Principle - Separate interfaces for different capabilities
public class ISP_Main {
    
    // Interface for users who can receive notifications
    interface NotificationReceiver {
        void receiveNotification(String message);
        String getUserInfo();
    }
    
    // Separate interfaces for specific capabilities
    interface EmailCapable {
        void sendViaEmail(String message);
        String getEmail();
    }
    
    interface SMSCapable {
        void sendViaSMS(String message);
        String getPhone();
    }
    
    interface InAppCapable {
        void sendViaInApp(String message);
        String getAppId();
    }
    
    // Citizen class - implements all 3 capabilities
    static class Citizen implements NotificationReceiver, EmailCapable, SMSCapable, InAppCapable {
        private String name;
        private String email;
        private String phone;
        private String appId;
        
        public Citizen(String name, String email, String phone) {
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.appId = "APP_" + name.replaceAll(" ", "").toUpperCase();
        }
        
        @Override
        public void receiveNotification(String message) {
            System.out.println("\n📨 Notifying Citizen: " + name);
            System.out.println("   Capabilities: Email, SMS, In-App");
            sendViaEmail(message);
            sendViaSMS(message);
            sendViaInApp(message);
        }
        
        @Override
        public String getUserInfo() {
            return "Citizen: " + name + " | Email: " + email + " | Phone: " + phone;
        }
        
        @Override
        public void sendViaEmail(String message) {
            System.out.println("   📧 Email to " + email + ": " + message);
        }
        
        @Override
        public String getEmail() { return email; }
        
        @Override
        public void sendViaSMS(String message) {
            System.out.println("   📱 SMS to " + phone + ": " + message);
        }
        
        @Override
        public String getPhone() { return phone; }
        
        @Override
        public void sendViaInApp(String message) {
            System.out.println("   📱 In-App to " + appId + ": " + message);
        }
        
        @Override
        public String getAppId() { return appId; }
    }
    
    // City Officer class - implements only Email capability
    static class CityOfficer implements NotificationReceiver, EmailCapable {
        private String name;
        private String email;
        private String department;
        
        public CityOfficer(String name, String email, String department) {
            this.name = name;
            this.email = email;
            this.department = department;
        }
        
        @Override
        public void receiveNotification(String message) {
            System.out.println("\n📨 Notifying City Officer: " + name);
            System.out.println("   Department: " + department);
            System.out.println("   Capability: Email only");
            sendViaEmail(message);
        }
        
        @Override
        public String getUserInfo() {
            return "Officer: " + name + " | Dept: " + department + " | Email: " + email;
        }
        
        @Override
        public void sendViaEmail(String message) {
            System.out.println("   📧 Official Email to " + email + ": " + message);
        }
        
        @Override
        public String getEmail() { return email; }
    }
    
    // Emergency Responder class - implements Email and SMS capabilities
    static class EmergencyResponder implements NotificationReceiver, EmailCapable, SMSCapable {
        private String name;
        private String email;
        private String phone;
        private String emergencyType;
        
        public EmergencyResponder(String name, String email, String phone, String emergencyType) {
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.emergencyType = emergencyType;
        }
        
        @Override
        public void receiveNotification(String message) {
            System.out.println("\n🚨 EMERGENCY ALERT for Responder: " + name);
            System.out.println("   Type: " + emergencyType);
            System.out.println("   Capabilities: Email, SMS (Priority)");
            sendViaEmail("[URGENT] " + message);
            sendViaSMS("[URGENT] " + message);
        }
        
        @Override
        public String getUserInfo() {
            return "Responder: " + name + " | Type: " + emergencyType + " | Email: " + email + " | Phone: " + phone;
        }
        
        @Override
        public void sendViaEmail(String message) {
            System.out.println("   📧 Emergency Email to " + email + ": " + message);
        }
        
        @Override
        public String getEmail() { return email; }
        
        @Override
        public void sendViaSMS(String message) {
            System.out.println("   📱 Emergency SMS to " + phone + ": " + message);
        }
        
        @Override
        public String getPhone() { return phone; }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<NotificationReceiver> users = new ArrayList<>();
        
        // Add some sample users
        users.add(new Citizen("John Doe", "john@email.com", "+1234567890"));
        users.add(new Citizen("Alice Smith", "alice@email.com", "+0987654321"));
        users.add(new CityOfficer("Robert Brown", "robert@city.gov", "Transportation"));
        users.add(new EmergencyResponder("Sarah Wilson", "sarah@rescue.org", "+1122334455", "Fire"));
        users.add(new EmergencyResponder("Mike Johnson", "mike@medical.gov", "+5566778899", "Medical"));
        
        System.out.println("==========================================");
        System.out.println("   ISP - INTERFACE SEGREGATION PRINCIPLE  ");
        System.out.println("==========================================");
        System.out.println("Demonstration: Each user implements ONLY the interfaces they need");
        System.out.println();
        
        boolean running = true;
        while (running) {
            System.out.println("\n=== ISP DEMONSTRATION MENU ===");
            System.out.println("1. View All Users and Their Capabilities");
            System.out.println("2. Add New User");
            System.out.println("3. Test Notification for Specific User");
            System.out.println("4. Send Alert to All Users");
            System.out.println("5. Show Interface Structure");
            System.out.println("6. Exit");
            System.out.print("\nSelect option (1-6): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    System.out.println("\n=== REGISTERED USERS ===");
                    System.out.println("Total Users: " + users.size());
                    System.out.println("-".repeat(60));
                    for (int i = 0; i < users.size(); i++) {
                        System.out.println((i + 1) + ". " + users.get(i).getUserInfo());
                        // Show capabilities
                        if (users.get(i) instanceof EmailCapable) System.out.println("   ✓ Email Capable");
                        if (users.get(i) instanceof SMSCapable) System.out.println("   ✓ SMS Capable");
                        if (users.get(i) instanceof InAppCapable) System.out.println("   ✓ In-App Capable");
                        System.out.println();
                    }
                    break;
                    
                case 2:
                    System.out.println("\n=== ADD NEW USER ===");
                    System.out.println("Select user type:");
                    System.out.println("1. Citizen (Email + SMS + In-App)");
                    System.out.println("2. City Officer (Email only)");
                    System.out.println("3. Emergency Responder (Email + SMS)");
                    System.out.print("Enter choice (1-3): ");
                    
                    int userType = scanner.nextInt();
                    scanner.nextLine();
                    
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    
                    switch (userType) {
                        case 1:
                            System.out.print("Enter phone number: ");
                            String phone = scanner.nextLine();
                            users.add(new Citizen(name, email, phone));
                            System.out.println("✅ Citizen added successfully!");
                            break;
                            
                        case 2:
                            System.out.print("Enter department: ");
                            String dept = scanner.nextLine();
                            users.add(new CityOfficer(name, email, dept));
                            System.out.println("✅ City Officer added successfully!");
                            break;
                            
                        case 3:
                            System.out.print("Enter phone number: ");
                            String responderPhone = scanner.nextLine();
                            System.out.print("Enter emergency type (Fire/Police/Medical): ");
                            String type = scanner.nextLine();
                            users.add(new EmergencyResponder(name, email, responderPhone, type));
                            System.out.println("✅ Emergency Responder added successfully!");
                            break;
                    }
                    break;
                    
                case 3:
                    System.out.println("\n=== TEST NOTIFICATION ===");
                    for (int i = 0; i < users.size(); i++) {
                        System.out.println((i + 1) + ". " + users.get(i).getUserInfo());
                    }
                    System.out.print("\nSelect user to test (1-" + users.size() + "): ");
                    int userIndex = scanner.nextInt() - 1;
                    scanner.nextLine();
                    
                    if (userIndex >= 0 && userIndex < users.size()) {
                        System.out.print("Enter test message: ");
                        String testMsg = scanner.nextLine();
                        users.get(userIndex).receiveNotification(testMsg);
                    }
                    break;
                    
                case 4:
                    System.out.print("\nEnter alert message for all users: ");
                    String alertMsg = scanner.nextLine();
                    System.out.println("\n🚨 SENDING ALERT TO ALL " + users.size() + " USERS");
                    System.out.println("=".repeat(50));
                    for (NotificationReceiver user : users) {
                        user.receiveNotification(alertMsg);
                    }
                    break;
                    
                case 5:
                    System.out.println("\n=== INTERFACE STRUCTURE ===");
                    System.out.println("1. NotificationReceiver (Base Interface)");
                    System.out.println("   ├── receiveNotification()");
                    System.out.println("   └── getUserInfo()");
                    System.out.println();
                    System.out.println("2. EmailCapable (Separate Interface)");
                    System.out.println("   ├── sendViaEmail()");
                    System.out.println("   └── getEmail()");
                    System.out.println();
                    System.out.println("3. SMSCapable (Separate Interface)");
                    System.out.println("   ├── sendViaSMS()");
                    System.out.println("   └── getPhone()");
                    System.out.println();
                    System.out.println("4. InAppCapable (Separate Interface)");
                    System.out.println("   ├── sendViaInApp()");
                    System.out.println("   └── getAppId()");
                    System.out.println();
                    System.out.println("✅ ISP Benefit: Users implement ONLY what they need!");
                    break;
                    
                case 6:
                    System.out.println("\nExiting ISP Demonstration...");
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
