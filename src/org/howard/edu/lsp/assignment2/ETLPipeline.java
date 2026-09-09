package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


public class ETLPipeline {

    public static void main(String[] args) {
        String filepath = "data/employees.csv";
        String outputFilePath = "data/transformed_employees.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filepath));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            boolean isHeader = true;
            int rowsRead = 0;
            int rowsTransformed = 0;
            int rowsSkipped = 0;

            while ((line = br.readLine()) != null) {

                if (isHeader) {
                    bw.write(line + ",GrossPay,PayLevel,EmploymentStatus");
                    bw.newLine();
                    isHeader = false;
                    System.out.println(line + ",GrossPay,PayLevel,EmploymentStatus");
                    System.out.println();
                    continue;
                }

                rowsRead++;

                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }

                String[] originalFields = line.split(",");
                String[] fields = Arrays.copyOf(originalFields, originalFields.length + 3);

                try {
                    normalizeStrings(fields);
                    validateValues(fields);
                    calculateGrossPay(fields);
                    applyITBonus(fields);
                    roundGrossPay(fields);
                    determinePayLevel(fields);
                    determineEmploymentStatus(fields);
                    bw.write(String.join(",", fields));
                    bw.newLine();
                    rowsTransformed++;
                    System.out.println(String.join(",", fields));
                } catch (Exception e) {
                    rowsSkipped++;
                }
            }

            printSummary(rowsRead, rowsTransformed, rowsSkipped, outputFilePath);

        } catch (IOException e) {
            System.err.println("Error processing the CSV file: " + e.getMessage());
        }
    }


    // Normalize fields: Trim leading and trailing whitespace from every field. 
    // Convert the employee Name to UPPERCASE.
    // Department names otherwise remain unchanged after trimming.
    public static void normalizeStrings(String[] fields){
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].trim();
        }
        fields[1] = fields[1].toUpperCase();
        //System.out.println(String.join(",", fields));
    }

    // Validate numeric values: EmployeeID must be an integer. 
    // HoursWorked and HourlyRate must be valid decimal numbers and may not be negative. 
    // Invalid rows are skipped as described below.
    public static void validateValues(String[] fields){
        Integer.parseInt(fields[0]); // Validate EmployeeID
        double hoursWorked = Double.parseDouble(fields[3]); // Validate HoursWorked
        double hourlyRate = Double.parseDouble(fields[4]); // Validate HourlyRate

        if (hoursWorked < 0 || hourlyRate < 0) {
          throw new IllegalArgumentException();       
        }
    }

    // Calculate base/overtime pay: For HoursWorked up to and including 40.00, pay all hours at the normal HourlyRate.
    // If HoursWorked is greater than 40.00, pay the first 40 hours at the normal rate and all hours above 40 at 1.5 times the normal rate.
    public static void calculateGrossPay(String[] fields){
        double hoursWorked = Double.parseDouble(fields[3]);
        double hourlyRate = Double.parseDouble(fields[4]);
        double grossPay;

        if (hoursWorked <= 40.00) {
            grossPay = hoursWorked * hourlyRate;
        } else {
            grossPay = 40.00 * hourlyRate + (hoursWorked - 40.00) * hourlyRate * 1.5;
        }

        fields[5] = String.format("%.2f", grossPay);
    }

    // Apply the IT bonus: If the trimmed Department is exactly "IT", add a 5% bonus to the pay calculated in Step 3. 
    // The bonus is applied after overtime.
    public static void applyITBonus(String[] fields){
        String department = fields[2].trim();
        if (department.equals("IT")) {
            double grossPay = Double.parseDouble(fields[5]);
            grossPay *= 1.05; // Apply 5% bonus
            fields[5] = String.format("%.2f", grossPay);
        }
    }

    // Round GrossPay: Round the resulting GrossPay to exactly two decimal places using round-half-up.
    public static void roundGrossPay(String[] fields){
        double grossPay = Double.parseDouble(fields[5]);
        grossPay = Math.round(grossPay * 100.0) / 100.0; 
        fields[5] = String.format("%.2f", grossPay);
    }

    // Determine PayLevel: Using the final rounded GrossPay: < $500.00 → Low; 
    // $500.00–$999.99 → Standard; 
    // $1000.00–$1999.99 → High; >= $2000.00 → Executive.

    public static void determinePayLevel(String[] fields){
        double grossPay = Double.parseDouble(fields[5]);
        if (grossPay < 500.00) {
            fields[7] = "Low";
        } else if (grossPay < 1000.00) {
            fields[7] = "Standard";
        } else if (grossPay < 2000.00) {
            fields[7] = "High";
        } else {
            fields[7] = "Executive";
        }
    }

    // Determine EmploymentStatus: HoursWorked < 30.00 → Part-Time; HoursWorked >= 30.00 → Full-Time.
    public static void determineEmploymentStatus(String[] fields){
        double HoursWorked = Double.parseDouble(fields[3]);
        if (HoursWorked < 30.00) {
            fields[6] = "Part-Time";
        } else {
            fields[6] = "Full-Time";
        }
    }

    /*
    Important numeric rule: use the parsed HoursWorked and HourlyRate values for payroll calculations. Do not round HourlyRate before calculating GrossPay. 
    Formatting HourlyRate to two decimal places is an output requirement only.
    */

    //Print summary.
    public static void printSummary(int rowsRead, int rowsTransformed, int rowsSkipped, String outputFilePath) {
        System.out.println("Number of rows read: " + rowsRead);
        System.out.println("Number of rows transformed: " + rowsTransformed);
        System.out.println("Number of rows skipped: " + rowsSkipped);
        System.out.println("Output file path written: " + outputFilePath);
    }
}

/*
Expected Output:
EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus
101,ALICE JOHNSON,HR,40.00,25.00,1000.00,High,Full-Time
102,BOB SMITH,IT,45.00,30.00,1496.25,High,Full-Time
107,EVAN LEE,Sales,20.00,18.50,370.00,Low,Part-Time
108,FATIMA BROWN,IT,40.00,50.00,2100.00,Executive,Full-Time
109,GEORGE WHITE,Finance,50.00,40.00,2200.00,Executive,Full-Time
110,HELEN DAVIS,IT,30.00,80.00,2520.00,Executive,Full-Time
113,KEVIN YOUNG,Marketing,30.00,16.67,499.98,Low,Full-Time
*/
