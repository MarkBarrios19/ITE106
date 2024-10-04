import java.util.Scanner;

public class ScannerSampleProgram
{
	public static void main(String[]args) {
		System getData = new Scanner (System.in);
		System.out.print ("Enter your age: ");
		int age = getData.nextInt();
		System.out.println ("Your age is: " + age);
		System.out.print("Enter your name : ");
		String name = getData.next();
		System.out.println (" ");
		System.out.println ("Your name is: " + name);
	}
}