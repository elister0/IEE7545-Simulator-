import java.util.Scanner;

/**
 * IEEE754Simulator
 *
 * This program simulates the IEEE 754 32-bit floating point encoding,
 * addition, and multiplication of two decimal operands.
 *
 * - It converts decimal numbers into IEEE 754 binary format.
 * - It supports basic operations: addition and multiplication.
 *
 * How to Compile:
 *     javac IEEE754Simulator.java
 *
 * How to Run:
 *     java IEEE754Simulator
 *
 * Example Input:
 *     Enter operand1 (decimal): 2345.125
 *     Enter operand2 (decimal): .75
 *     Enter operator (add or mult): add
 *
 * Example Output:
 *     operand1: 0 10001010 00100101001001000000000
 *     operand2: 0 01111110 10000000000000000000000
 *     sum:      0 10001010 00100101001111000000000
 */

public class IEEE754Simulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter operand1 (decimal): ");
        float operand1 = Float.parseFloat(scanner.nextLine());

        System.out.print("Enter operand2 (decimal): ");
        float operand2 = Float.parseFloat(scanner.nextLine());

        System.out.print("Enter operator (add or mult): ");
        String operator = scanner.nextLine().trim().toLowerCase();

        float result = 0;
        if (operator.equals("add")) {
            result = operand1 + operand2;
        } else if (operator.equals("mult")) {
            result = operand1 * operand2;
        } else {
            System.out.println("Invalid operator.");
            return;
        }

        System.out.println("operand1: " + floatToIEEE754(operand1));
        System.out.println("operand2: " + floatToIEEE754(operand2));

        if (operator.equals("add")) {
            System.out.println("sum: " + floatToIEEE754(result));
        } else {
            System.out.println("product: " + floatToIEEE754(result));
        }
    }

    // Convert float to IEEE 754 binary string manually
    static String floatToIEEE754(double num) {
        int sign = (num < 0) ? 1 : 0;
        num = Math.abs(num);

        if (num == 0.0) {
            return sign + " " + "00000000" + " " + "00000000000000000000000";
        }

        int exponent = 127; // Bias for 32-bit float
        double mantissa = num;

        // Normalize mantissa to the [1.0, 2.0) range
        while (mantissa >= 2.0) {
            mantissa /= 2.0;
            exponent++;
        }
        while (mantissa < 1.0) {
            mantissa *= 2.0;
            exponent--;
        }

        // Remove the leading 1
        mantissa -= 1.0;

        // Build the 23-bit mantissa string
        String mantissaBits = "";
        for (int i = 0; i < 23; i++) {
            mantissa *= 2.0;
            if (mantissa >= 1.0) {
                mantissaBits += "1";
                mantissa -= 1.0;
            } else {
                mantissaBits += "0";
            }
        }

        String exponentBits = "";
        int tempExp = exponent;
        for (int i = 7; i >= 0; i--) {
            int bit = (tempExp >> i) & 1;
            exponentBits += bit;
        }

        return sign + " " + exponentBits + " " + mantissaBits;
    }

}
