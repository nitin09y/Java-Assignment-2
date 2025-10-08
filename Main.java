import java.util.Scanner;

// Class for calculator operations with method overloading
class Calculator {
    
    // Method overloading for addition
    public int add(int num1, int num2) {
        return num1 + num2;
    }
    
    public double add(double num1, double num2) {
        return num1 + num2;
    }
    
    public int add(int num1, int num2, int num3) {
        return num1 + num2 + num3;
    }
    
    // Subtraction method
    public int subtract(int num1, int num2) {
        return num1 - num2;
    }
    
    // Multiplication method
    public double multiply(double num1, double num2) {
        return num1 * num2;
    }
    
    // Division method
    public double divide(int num1, int num2) {
        if (num2 == 0) {
            System.out.println("Error: Cannot divide by zero!");
            return 0;
        }
        return (double) num1 / num2;
    }
}

// Class for user interface
class CalculatorApp {
    private Scanner input;
    private Calculator calc;
    
    public CalculatorApp() {
        input = new Scanner(System.in);
        calc = new Calculator();
    }
    
    // Method for performing addition using switch case
    public void performAddition() {
        System.out.println("\n--- Addition ---");
        System.out.println("1. Add two integers");
        System.out.println("2. Add two decimal numbers");
        System.out.println("3. Add three integers");
        System.out.print("Enter your choice: ");
        
        int choice = input.nextInt();
        
        switch (choice) {
            case 1:
                System.out.print("Enter first number: ");
                int a1 = input.nextInt();
                System.out.print("Enter second number: ");
                int b1 = input.nextInt();
                System.out.println("Result: " + calc.add(a1, b1));
                break;
                
            case 2:
                System.out.print("Enter first number: ");
                double a2 = input.nextDouble();
                System.out.print("Enter second number: ");
                double b2 = input.nextDouble();
                System.out.println("Result: " + calc.add(a2, b2));
                break;
                
            case 3:
                System.out.print("Enter first number: ");
                int a3 = input.nextInt();
                System.out.print("Enter second number: ");
                int b3 = input.nextInt();
                System.out.print("Enter third number: ");
                int c3 = input.nextInt();
                System.out.println("Result: " + calc.add(a3, b3, c3));
                break;
                
            default:
                System.out.println("Please select valid option!");
        }
    }
    
    // Method for performing subtraction
    public void performSubtraction() {
        System.out.println("\n--- Subtraction ---");
        System.out.print("Enter first number: ");
        int a = input.nextInt();
        System.out.print("Enter second number: ");
        int b = input.nextInt();
        System.out.println("Result: " + calc.subtract(a, b));
    }
    
    // Method for performing multiplication
    public void performMultiplication() {
        System.out.println("\n--- Multiplication ---");
        System.out.print("Enter first number: ");
        double a = input.nextDouble();
        System.out.print("Enter second number: ");
        double b = input.nextDouble();
        System.out.println("Result: " + calc.multiply(a, b));
    }
    
    // Method for performing division
    public void performDivision() {
        System.out.println("\n--- Division ---");
        System.out.print("Enter first number: ");
        int a = input.nextInt();
        System.out.print("Enter second number: ");
        int b = input.nextInt();
        
        if (b == 0) {
            System.out.println("Error: Cannot divide by zero!");
        } else {
            System.out.println("Result: " + calc.divide(a, b));
        }
    }
    
    // Main menu to display options
    public void mainMenu() {
        while (true) {
            System.out.println("\n=== Calculator Application ===");
            System.out.println("1. Add Numbers");
            System.out.println("2. Subtract Numbers");
            System.out.println("3. Multiply Numbers");
            System.out.println("4. Divide Numbers");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = input.nextInt();
            
            switch (choice) {
                case 1:
                    performAddition();
                    break;
                case 2:
                    performSubtraction();
                    break;
                case 3:
                    performMultiplication();
                    break;
                case 4:
                    performDivision();
                    break;
                case 5:
                    System.out.println("Thank you for using Calculator!");
                    input.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}

// Main class with main method
public class Main {
    public static void main(String[] args) {
        CalculatorApp app = new CalculatorApp();
        System.out.println("Welcome to Calculator Application!");
        app.mainMenu();
    }
}