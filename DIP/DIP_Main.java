import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Dependency Inversion Principle - High-level modules don't depend on low-level modules
public class DIP_Main {
    
    // Abstraction that high-level modules depend on
    interface NotificationChannel {
        void send(String recipient, String message);
        boolean isAvailable();
        String getChannelInfo();
    }
    
    // Low-level module - concrete implementation
    static class EmailChannel implements NotificationChannel {
        @Override
        public void send(String recipient, String message) {
            System.out.println("📧 Sending via Email Channel:");
            System.out.println("   SMTP Server: smtp.city.gov");
            System.out.println("   To: " + recipient);
            System.out.println("   Subject: City Alert");
            System.out.println("   Body: " + message);
        }
        
        @Override
        public boolean isAvailable() {
            return true; // Check SMTP server status
        }
        
        @Override
        public String getChannelInfo() {
            return "Email Channel (SMTP-based, supports attachments)";
        }
    }
    
    // Low-level module - concrete implementation
    static class SMSChannel implements NotificationChannel {
        @Override
        public void send(String recipient, String message) {
            System.out.println("📱 Sending via SMS Channel:");
            System.out.println("   Gateway: Twilio API");
            System.out.println("   To: " + recipient);
            System.out.println("   Message: " + (message.length() > 160 ? message.substring(0, 157) + "..." : message));
        }
        
        @Override
        public boolean isAvailable() {
            return true; // Check SMS gateway
        }
        
        @Override
        public String getChannelInfo() {
            return "SMS Channel (160 char limit, high priority)";
        }
    }
    
    // Low-level module - concrete implementation
    static class InAppChannel implements NotificationChannel {
        @Override
        public void send(String recipient, String message) {
            System.out.println("📱 Sending via In-App Channel:");
            System.out.println("   Server: Firebase Cloud Messaging");
            System.out.println("   To User ID: " + recipient);
            System.out.println("   Notification: " + message);
            System.out.println("   Type: Push notification with badge");
        }
        
        @Override
        public boolean isAvailable() {
            return true; // Check FCM connection
        }
        
        @Override
        public String getChannelInfo() {
            return "In-App Channel (Push notifications, real-time)";
        }
    }
    
    // HIGH-LEVEL MODULE: Depends on abstraction (NotificationChannel), not concrete classes
    static class AlertManager {
        private NotificationChannel channel;
        
        // Constructor injection - depends on abstraction
        public AlertManager(NotificationChannel channel) {
            this.channel = channel;
            System.out.println("✅ AlertManager created with channel: " + channel.getClass().getSimpleName());
        }
        
        // High-level business logic
        public void sendCityAlert(String alertMessage) {
            System.out.println("\n🚨 PROCESSING CITY ALERT");
            System.out.println("=".repeat(40));
            
            // Business rules
            String priority = determinePriority(alertMessage);
            String formattedMessage = formatAlertMessage(alertMessage, priority);
            
            System.out.println("   Alert Level: " + priority);
            System.out.println("   Message: " + alertMessage);
            
            // Using abstraction, not concrete implementation
            if (channel.isAvailable()) {
                System.out.println("\n📤 Sending through channel...");
                channel.send("city_residents", formattedMessage);
                logAlert(alertMessage, priority);
            } else {
                System.out.println("❌ Channel unavailable! Alert queued for retry.");
            }
        }
        
        public void sendTargetedAlert(String recipient, String message) {
            System.out.println("\n🎯 TARGETED ALERT");
            System.out.println("-".repeat(30));
            
            if (channel.isAvailable()) {
                channel.send(recipient, message);
                System.out.println("✅ Alert delivered to " + recipient);
            } else {
                System.out.println("❌ Channel not available");
            }
        }
        
        // Business logic methods
        private String determinePriority(String message) {
            if (message.toLowerCase().contains("emergency") || 
                message.toLowerCase().contains("urgent") ||
                message.toLowerCase().contains("fire") ||
                message.toLowerCase().contains("police")) {
                return "HIGH";
            } else if (message.toLowerCase().contains("maintenance") ||
                      message.toLowerCase().contains("schedule")) {
                return "MEDIUM";
            }
            return "LOW";
        }
        
        private String formatAlertMessage(String message, String priority) {
            return "[" + priority + " PRIORITY] " + message + " [City Alert System]";
        }
        
        private void logAlert(String message, String priority) {
            System.out.println("\n📝 Alert logged:");
            System.out.println("   Time: " + java.time.LocalDateTime.now());
            System.out.println("   Priority: " + priority);
            System.out.println("   Channel: " + channel.getClass().getSimpleName());
        }
        
        // Can switch channels without changing AlertManager
        public void switchChannel(NotificationChannel newChannel) {
            System.out.println("\n🔄 Switching notification channel...");
            System.out.println("   From: " + this.channel.getClass().getSimpleName());
            System.out.println("   To: " + newChannel.getClass().getSimpleName());
            this.channel = newChannel;
        }
    }
    
    // Another high-level module
    static class EmergencyBroadcastSystem {
        private List<NotificationChannel> channels;
        
        public EmergencyBroadcastSystem() {
            this.channels = new ArrayList<>();
        }
        
        public void addChannel(NotificationChannel channel) {
            channels.add(channel);
        }
        
        public void broadcastEmergency(String message) {
            System.out.println("\n🚨🚨 EMERGENCY BROADCAST SYSTEM ACTIVATED 🚨🚨");
            System.out.println("=".repeat(50));
            
            for (NotificationChannel channel : channels) {
                if (channel.isAvailable()) {
                    System.out.println("\nBroadcasting via: " + channel.getClass().getSimpleName());
                    channel.send("ALL_RESIDENTS", "[EMERGENCY] " + message);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Create concrete implementations
        NotificationChannel emailChannel = new EmailChannel();
        NotificationChannel smsChannel = new SMSChannel();
        NotificationChannel inAppChannel = new InAppChannel();
        
        // Create high-level module with dependency injection
        AlertManager alertManager = new AlertManager(emailChannel);
        EmergencyBroadcastSystem emergencySystem = new EmergencyBroadcastSystem();
        emergencySystem.addChannel(emailChannel);
        emergencySystem.addChannel(smsChannel);
        emergencySystem.addChannel(inAppChannel);
        
        System.out.println("==========================================");
        System.out.println("   DIP - DEPENDENCY INVERSION PRINCIPLE  ");
        System.out.println("==========================================");
        System.out.println("High-level modules depend on abstractions, not concrete implementations");
        System.out.println();
        
        boolean running = true;
        while (running) {
            System.out.println("\n=== DIP DEMONSTRATION MENU ===");
            System.out.println("1. Send Alert with Current Channel");
            System.out.println("2. Switch Notification Channel");
            System.out.println("3. Test All Channels");
            System.out.println("4. Emergency Broadcast (Multiple Channels)");
            System.out.println("5. Show DIP Principle Explanation");
            System.out.println("6. Exit");
            System.out.print("\nSelect option (1-6): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    System.out.print("\nEnter alert message: ");
                    String alertMsg = scanner.nextLine();
                    alertManager.sendCityAlert(alertMsg);
                    break;
                    
                case 2:
                    System.out.println("\n=== SWITCH CHANNEL ===");
                    System.out.println("1. Email Channel");
                    System.out.println("2. SMS Channel");
                    System.out.println("3. In-App Channel");
                    System.out.print("Select new channel (1-3): ");
                    
                    int channelChoice = scanner.nextInt();
                    scanner.nextLine();
                    
                    switch (channelChoice) {
                        case 1:
                            alertManager.switchChannel(emailChannel);
                            break;
                        case 2:
                            alertManager.switchChannel(smsChannel);
                            break;
                        case 3:
                            alertManager.switchChannel(inAppChannel);
                            break;
                    }
                    break;
                    
                case 3:
                    System.out.println("\n=== TESTING ALL CHANNELS ===");
                    System.out.println("Demonstrating: All channels implement NotificationChannel interface");
                    System.out.println();
                    
                    System.out.print("Enter test message: ");
                    String testMsg = scanner.nextLine();
                    
                    System.out.println("\n1. Testing Email Channel:");
                    emailChannel.send("test@email.com", testMsg);
                    
                    System.out.println("\n2. Testing SMS Channel:");
                    smsChannel.send("+1234567890", testMsg);
                    
                    System.out.println("\n3. Testing In-App Channel:");
                    inAppChannel.send("user_12345", testMsg);
                    break;
                    
                case 4:
                    System.out.print("\nEnter emergency message: ");
                    String emergencyMsg = scanner.nextLine();
                    emergencySystem.broadcastEmergency(emergencyMsg);
                    break;
                    
                case 5:
                    System.out.println("\n=== DIP PRINCIPLE EXPLANATION ===");
                    System.out.println("\nDEPENDENCY INVERSION PRINCIPLE:");
                    System.out.println("1. High-level modules should not depend on low-level modules.");
                    System.out.println("2. Both should depend on abstractions.");
                    System.out.println("3. Abstractions should not depend on details.");
                    System.out.println("4. Details should depend on abstractions.");
                    System.out.println();
                    System.out.println("✅ HIGH-LEVEL MODULES (Business Logic):");
                    System.out.println("   - AlertManager");
                    System.out.println("   - EmergencyBroadcastSystem");
                    System.out.println();
                    System.out.println("✅ ABSTRACTION:");
                    System.out.println("   - NotificationChannel interface");
                    System.out.println();
                    System.out.println("✅ LOW-LEVEL MODULES (Implementation):");
                    System.out.println("   - EmailChannel (implements NotificationChannel)");
                    System.out.println("   - SMSChannel (implements NotificationChannel)");
                    System.out.println("   - InAppChannel (implements NotificationChannel)");
                    System.out.println();
                    System.out.println("🔧 KEY BENEFITS:");
                    System.out.println("   - Can switch channels easily");
                    System.out.println("   - Can test AlertManager with mock channels");
                    System.out.println("   - Can add new channels without modifying AlertManager");
                    System.out.println("   - Business logic separated from implementation details");
                    break;
                    
                case 6:
                    System.out.println("\nExiting DIP Demonstration...");
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
