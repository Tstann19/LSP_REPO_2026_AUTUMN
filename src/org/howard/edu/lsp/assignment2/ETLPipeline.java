package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class ETLPipeline {

    public static void main(String[] args) {
        String filepath = "data/employees.csv";
        String outputFilePath = "data/transformed_employees.csv";
        int rowsRead = 0;
        int rowsTransformed = 0;
        int rowsSkipped = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filepath));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    bw.write(line);
                    bw.newLine();
                    isHeader = false;
                    continue;
                }
                
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                rowsRead++;

                String[] fields = line.split(",");

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
        
    }

    // Validate numeric values: EmployeeID must be an integer. 
    // HoursWorked and HourlyRate must be valid decimal numbers and may not be negative. 
    // Invalid rows are skipped as described below.
    public static void validateValues(String[] fields){

    }
    // Calculate base/overtime pay: For HoursWorked up to and including 40.00, pay all hours at the normal HourlyRate.
    // If HoursWorked is greater than 40.00, pay the first 40 hours at the normal rate and all hours above 40 at 1.5 times the normal rate.
    public static void calculateGrossPay(String[] fields){

    }

    // Apply the IT bonus: If the trimmed Department is exactly "IT", add a 5% bonus to the pay calculated in Step 3. 
    // The bonus is applied after overtime.
    public static void applyITBonus(String[] fields){

    }

    // Round GrossPay: Round the resulting GrossPay to exactly two decimal places using round-half-up.
    public static void roundGrossPay(String[] fields){

    }

    // Determine PayLevel: Using the final rounded GrossPay: < $500.00 → Low; 
    // $500.00–$999.99 → Standard; 
    // $1000.00–$1999.99 → High; >= $2000.00 → Executive.

    public static void determinePayLevel(String[] fields){

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
