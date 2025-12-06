import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Open/Closed Principle - Open for extension, closed for modification
public class OCP_Main {
    
    // Base interface - Closed for modification
    interface NotificationService {
        void sendNotification(String recipient, String message);
        String getServiceType();
        String getServiceDetails();
    }
    
    // Existing implementation - Don't modify this
    static class EmailNotification implements NotificationService {
        @Override
        public void sendNotification(String recipient, String message) {
            System.out.println("📧 [Email Service]");
            System.out.println("   To: " + recipient);
            System.out.println("   Message: " + message);
            System.out.println("   Status: Sent via SMTP server");
        }
        
        @Override
        public String getServiceType() {
            return "EMAIL";
        }
        
        @Override
        public String getServiceDetails() {
            return "Standard email delivery service";
        }
    }
    
    // Existing implementation - Don't modify this
    static class SMSNotification implements NotificationService {
        @Override
        public void sendNotification(String recipient, String message) {
            System.out.println("📱 [SMS Service]");
            System.out.println("   To: " + recipient);
            System.out.println("   Message: " + (message.length() > 160 ? message.substring(0, 157) + "..." : message));
            System.out.println("   Status: Delivered via SMS gateway");
        }
        
        @Override
        public String getServiceType() {
            return "SMS";
        }
        
        @Override
        public String getServiceDetails() {
            return "Text message service (160 char limit)";
        }
    }
    
    // Existing implementation - Don't modify this
    static class InAppNotification implements NotificationService {
        @Override
        public void sendNotification(String recipient, String message) {
            System.out.println("📱 [In-App Service]");
            System.out.println("   To User ID: " + recipient);
            System.out.println("   Message: " + message);
            System.out.println("   Status: Pushed to mobile application");
        }
        
        @Override
        public String getServiceType() {
            return "IN_APP";
        }
        
        @Override
        public String getServiceDetails() {
            return "Mobile app push notifications";
        }
    }
    
    // NEW SERVICE: Can be added without modifying existing code
    static class PushNotification implements NotificationService {
        @Override
        public void sendNotification(String recipient, String message) {
            System.out.println("🔔 [Push Notification Service]");
            System.out.println("   To Device: " + recipient);
            System.out.println("   Message: " + message);
            System.out.println("   Status: Sent as mobile push notification");
            System.out.println("   Features: Badge, Sound, Alert");
        }
        
        @Override
        public String getServiceType() {
            return "PUSH";
        }
        
        @Override
        public String getServiceDetails() {
            return "Mobile device push notifications with rich features";
        }
    }
    
    // NEW SERVICE: Can be added without modifying existing code
    static class VoiceCallNotification implements NotificationService {
        @Override
        public void sendNotification(String recipient, String message) {
            System.out.println("📞 [Voice Call Service]");
            System.out.println("   Calling: " + recipient);
            System.out.println("   Message: " + message);
            System.out.println("   Status: Automated voice call initiated");
            System.out.println("   Duration: " + (message.length() / 10) + " seconds");
        }
        
        @Override
        public String getServiceType() {
            return "VOICE_CALL";
        }
        
        @Override
        public String getServiceDetails() {
            return "Automated voice call notifications";
        }
    }
    
    // Notification Manager - Works with any NotificationService
    static class NotificationManager {
        private List<NotificationService> services = new ArrayList<>();
        
        public void addService(NotificationService service) {
            services.add(service);
            System.out.println("✅ Added service: " + service.getServiceType());
        }
        
        public void displayAllServices() {
            System.out.println("\n=== AVAILABLE NOTIFICATION SERVICES ===");
            System.out.println("Total Services: " + services.size());
            System.out.println("-".repeat(60));
            for (int i = 0; i < services.size(); i++) {
                NotificationService service = services.get(i);
                System.out.println((i + 1) + ". " + service.getServiceType());
                System.out.println("   " + service.getServiceDetails());
                System.out.println();
            }
        }
        
        public void testService(int index, String recipient, String message) {
            if (index >= 0 && index < services.size()) {
                services.get(index).sendNotification(recipient, message);
            } else {
                System.out.println("Invalid service index!");
            }
        }
        
        public void broadcastMessage(String message) {
            System.out.println("\n📢 BROADCASTING MESSAGE VIA ALL SERVICES");
            System.out.println("=".repeat(50));
            for (NotificationService service : services) {
                service.sendNotification("broadcast_recipient", message);
                System.out.println();
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NotificationManager manager = new NotificationManager();
        
        // Add existing services
        manager.addService(new EmailNotification());
        manager.addService(new SMSNotification());
        manager.addService(new InAppNotification());
        
        System.out.println("==========================================");
        System.out.println("   OCP - OPEN/CLOSED PRINCIPLE  ");
        System.out.println("==========================================");
        System.out.println("Demonstration: Add new services WITHOUT modifying existing code");
        System.out.println();
        
        boolean running = true;
        while (running) {
            System.out.println("\n=== OCP DEMONSTRATION MENU ===");
            System.out.println("1. View All Notification Services");
            System.out.println("2. Test Specific Service");
            System.out.println("3. Add New Service Type");
            System.out.println("4. Broadcast Test Message");
            System.out.println("5. Show OCP Principle Explanation");
            System.out.println("6. Exit");
            System.out.print("\nSelect option (1-6): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    manager.displayAllServices();
                    break;
                    
                case 2:
                    manager.displayAllServices();
                    System.out.print("\nSelect service to test (1-" + manager.services.size() + "): ");
                    int serviceIndex = scanner.nextInt() - 1;
                    scanner.nextLine();
                    
                    System.out.print("Enter recipient: ");
                    String recipient = scanner.nextLine();
                    
                    System.out.print("Enter message: ");
                    String message = scanner.nextLine();
                    
                    System.out.println();
                    manager.testService(serviceIndex, recipient, message);
                    break;
                    
                case 3:
                    System.out.println("\n=== ADD NEW SERVICE TYPE ===");
                    System.out.println("Demonstrating OCP: Adding new services without modifying existing code");
                    System.out.println("\nAvailable new service types to add:");
                    System.out.println("1. Push Notification Service (Already implemented)");
                    System.out.println("2. Voice Call Service (Already implemented)");
                    System.out.println("3. Social Media Service (To be implemented)");
                    System.out.print("\nSelect service to add (1-2): ");
                    
                    int newServiceType = scanner.nextInt();
                    scanner.nextLine();
                    
                    switch (newServiceType) {
                        case 1:
                            manager.addService(new PushNotification());
                            System.out.println("\n✅ PushNotification added WITHOUT modifying:");
                            System.out.println("   - EmailNotification class");
                            System.out.println("   - SMSNotification class");
                            System.out.println("   - InAppNotification class");
                            System.out.println("   - NotificationManager class");
                            break;
                            
                        case 2:
                            manager.addService(new VoiceCallNotification());
                            System.out.println("\n✅ VoiceCallNotification added WITHOUT modifying:");
                            System.out.println("   - Any existing notification class");
                            System.out.println("   - Any existing method signatures");
                            System.out.println("   - Any existing logic");
                            break;
                            
                        case 3:
                            System.out.println("\n💡 To add SocialMediaService:");
                            System.out.println("1. Create new class: SocialMediaNotification");
                            System.out.println("2. Implement NotificationService interface");
                            System.out.println("3. Use manager.addService(new SocialMediaNotification())");
                            System.out.println("✅ No need to modify existing code!");
                            break;
                    }
                    break;
                    
                case 4:
                    System.out.print("\nEnter broadcast message: ");
                    String broadcastMsg = scanner.nextLine();
                    manager.broadcastMessage(broadcastMsg);
                    break;
                    
                case 5:
                    System.out.println("\n=== OCP PRINCIPLE EXPLANATION ===");
                    System.out.println("\nOPEN/CLOSED PRINCIPLE:");
                    System.out.println("Objects should be OPEN for EXTENSION");
                    System.out.println("but CLOSED for MODIFICATION");
                    System.out.println();
                    System.out.println("✅ What's CLOSED (Not modified):");
                    System.out.println("   - NotificationService interface");
                    System.out.println("   - EmailNotification class");
                    System.out.println("   - SMSNotification class");
                    System.out.println("   - InAppNotification class");
                    System.out.println("   - NotificationManager class");
                    System.out.println();
                    System.out.println("✅ What's OPEN (Can be extended):");
                    System.out.println("   - Add PushNotification");
                    System.out.println("   - Add VoiceCallNotification");
                    System.out.println("   - Add SocialMediaNotification");
                    System.out.println("   - Add any new NotificationService");
                    System.out.println();
                    System.out.println("🔧 How it works:");
                    System.out.println("   New services just need to implement");
                    System.out.println("   the NotificationService interface");
                    System.out.println("   No changes to existing code needed!");
                    break;
                    
                case 6:
                    System.out.println("\nExiting OCP Demonstration...");
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
