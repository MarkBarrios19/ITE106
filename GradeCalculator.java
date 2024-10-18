import java.util.Scanner;

public class GradeCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

       
        System.out.print("Enter the number of students: ");
        int numStudents = scanner.nextInt();
        scanner.nextLine();  // Consume newline

       
        double classTotal = 0;
        double highestAverage = 0;
        double lowestAverage = 100;

        
        String[] studentNames = new String[numStudents];
        double[] averageScores = new double[numStudents];

       
        for (int i = 0; i < numStudents; i++) {
            System.out.println("Student " + (i + 1));

           
            System.out.print("Enter student's name: ");
            studentNames[i] = scanner.nextLine();

            
            System.out.print("Enter the number of assignments: ");
            int numAssignments = scanner.nextInt();

            double totalScore = 0;

           
            for (int j = 0; j < numAssignments; j++) {
                System.out.print("Enter score for assignment " + (j + 1) + ": ");
                while (!scanner.hasNextDouble()) {  // Error handling for non-numeric input
                    System.out.println("Invalid input. Please enter a valid score.");
                    scanner.next();  // Clear the invalid input
                }
                totalScore += scanner.nextDouble();
            }
            scanner.nextLine();  // Consume newline

           
            double averageScore = totalScore / numAssignments;
            averageScores[i] = averageScore;
            classTotal += averageScore;

           
            if (averageScore > highestAverage) {
                highestAverage = averageScore;
            }
            if (averageScore < lowestAverage) {
                lowestAverage = averageScore;
            }
        }

        
        System.out.println("\n--- Grade Report ---");
        for (int i = 0; i < numStudents; i++) {
            String grade = calculateGrade(averageScores[i]);
            System.out.printf("%s - Average Score: %.2f, Grade: %s%n", studentNames[i], averageScores[i], grade);
        }

        
        System.out.printf("\nClass Average: %.2f%n", classTotal / numStudents);
        System.out.printf("Highest Average: %.2f%n", highestAverage);
        System.out.printf("Lowest Average: %.2f%n", lowestAverage);

        scanner.close();
    }

   
    public static String calculateGrade(double average) {
        if (average >= 90) {
            return "A";
        } else if (average >= 80) {
            return "B";
        } else if (average >= 70) {
            return "C";
        } else if (average >= 60) {
            return "D";
        } else {
            return "F";
        }
    }
}