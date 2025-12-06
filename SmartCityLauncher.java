import java.io.*;
import java.util.Scanner;

public class SmartCityLauncher {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("==========================================");
        System.out.println("    SMART CITY ALERTS - MAIN LAUNCHER    ");
        System.out.println("==========================================");
        System.out.println();
        
        // Check if programs are compiled
        checkCompilation();
        
        boolean running = true;
        while (running) {
            displayMenu();
            
            System.out.print("\nEnter your choice (1-6): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        System.out.println("\n==========================================");
                        System.out.println("   RUNNING: ISP PROGRAM");
                        System.out.println("   (Interface Segregation Principle)");
                        System.out.println("==========================================\n");
                        runProgram("ISP", "ISP_Main");
                        break;
                        
                    case 2:
                        System.out.println("\n==========================================");
                        System.out.println("   RUNNING: OCP PROGRAM");
                        System.out.println("   (Open/Closed Principle)");
                        System.out.println("==========================================\n");
                        runProgram("OCP", "OCP_Main");
                        break;
                        
                    case 3:
                        System.out.println("\n==========================================");
                        System.out.println("   RUNNING: DIP PROGRAM");
                        System.out.println("   (Dependency Inversion Principle)");
                        System.out.println("==========================================\n");
                        runProgram("DIP", "DIP_Main");
                        break;
                        
                    case 4:
                        System.out.println("\n==========================================");
                        System.out.println("   RUNNING: ROLE-BASED PROGRAM");
                        System.out.println("   (Role-Based Notifications)");
                        System.out.println("==========================================\n");
                        runProgram("RoleBased", "RoleBased_Main");
                        break;
                        
                    case 5:
                        System.out.println("\n==========================================");
                        System.out.println("   RUNNING ALL PROGRAMS SEQUENTIALLY");
                        System.out.println("==========================================\n");
                        
                        System.out.println("\n1. Starting ISP Program...");
                        System.out.println("------------------------------------------");
                        runProgram("ISP", "ISP_Main");
                        
                        System.out.println("\n2. Starting OCP Program...");
                        System.out.println("------------------------------------------");
                        runProgram("OCP", "OCP_Main");
                        
                        System.out.println("\n3. Starting DIP Program...");
                        System.out.println("------------------------------------------");
                        runProgram("DIP", "DIP_Main");
                        
                        System.out.println("\n4. Starting Role-Based Program...");
                        System.out.println("------------------------------------------");
                        runProgram("RoleBased", "RoleBased_Main");
                        break;
                        
                    case 6:
                        System.out.println("\n==========================================");
                        System.out.println("   THANK YOU FOR USING SMART CITY ALERTS!");
                        System.out.println("   Exiting... Goodbye!");
                        System.out.println("==========================================");
                        running = false;
                        break;
                        
                    default:
                        System.out.println("\nERROR: Invalid choice! Please enter a number between 1-6.");
                }
                
                if (running && choice != 5 && choice != 6) {
                    System.out.print("\nPress Enter to return to main menu...");
                    scanner.nextLine();
                }
                
            } catch (Exception e) {
                System.out.println("\nERROR: Invalid input! Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("+------------------------------------------+");
        System.out.println("|           SELECT A PROGRAM               |");
        System.out.println("+------------------------------------------+");
        System.out.println("| 1. ISP Program                           |");
        System.out.println("|    (Interface Segregation Principle)     |");
        System.out.println("|                                          |");
        System.out.println("| 2. OCP Program                           |");
        System.out.println("|    (Open/Closed Principle)               |");
        System.out.println("|                                          |");
        System.out.println("| 3. DIP Program                           |");
        System.out.println("|    (Dependency Inversion Principle)      |");
        System.out.println("|                                          |");
        System.out.println("| 4. Role-Based Program                    |");
        System.out.println("|    (Role-Based Notifications)            |");
        System.out.println("|                                          |");
        System.out.println("| 5. Run All Programs                      |");
        System.out.println("|    (Sequential Demonstration)            |");
        System.out.println("|                                          |");
        System.out.println("| 6. Exit                                  |");
        System.out.println("+------------------------------------------+");
    }
    
    private static void runProgram(String folder, String className) {
        try {
            // Compile if needed
            File classFile = new File(folder + "/" + className + ".class");
            File javaFile = new File(folder + "/" + className + ".java");
            
            if (!classFile.exists() || javaFile.lastModified() > classFile.lastModified()) {
                System.out.println("Compiling " + className + "...");
                ProcessBuilder compile = new ProcessBuilder("javac", 
                    folder + "/" + className + ".java");
                compile.inheritIO();
                Process compileProcess = compile.start();
                compileProcess.waitFor();
                
                if (compileProcess.exitValue() != 0) {
                    System.out.println("ERROR: Compilation failed for " + className);
                    return;
                }
            }
            
            // Run the program
            ProcessBuilder run = new ProcessBuilder("java", 
                "-cp", folder, 
                className);
            run.inheritIO();
            Process runProcess = run.start();
            
            // Wait for program to complete
            runProcess.waitFor();
            
        } catch (Exception e) {
            System.out.println("ERROR: Failed to run " + className);
            System.out.println("Hint: Try running manually: cd " + folder + " && javac " + className + ".java");
        }
    }
    
    private static void checkCompilation() {
        System.out.println("Checking project setup...");
        
        String[] programs = {
            "ISP/ISP_Main",
            "OCP/OCP_Main", 
            "DIP/DIP_Main",
            "RoleBased/RoleBased_Main"
        };
        
        boolean allCompiled = true;
        
        for (String program : programs) {
            File classFile = new File(program + ".class");
            File javaFile = new File(program + ".java");
            
            if (!javaFile.exists()) {
                System.out.println("ERROR: Missing file: " + program + ".java");
                allCompiled = false;
            } else if (!classFile.exists()) {
                System.out.println("WARNING: Not compiled: " + program);
                allCompiled = false;
            }
        }
        
        if (!allCompiled) {
            System.out.println("\nNote: Some programs need compilation.");
            System.out.println("They will be compiled automatically when run.");
        } else {
            System.out.println("SUCCESS: All programs are ready!");
        }
        
        System.out.println();
    }
}
