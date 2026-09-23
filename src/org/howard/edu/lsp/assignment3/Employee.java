package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Employee {

    private int iD;
    private String name;
    private String department;
    private double hoursWorked;
    private double hourlyRate;
    private String status;

    public Employee(String[] oldFields) {

        //Validate strings and values here before assigning to newFields

        this.iD = Integer.parseInt(oldFields[0]);
        this.name = oldFields[1];
        this.department = oldFields[2];
        this.hoursWorked = Double.parseDouble(oldFields[3]);
        this.hourlyRate = Double.parseDouble(oldFields[4]);

        //Add two three new fields gross pay, pay level, and employee status.
        //Add 3 new functions to calculate gross pay, pay level, and employee status.


    }

    public static void printSummary(int rowsRead, int rowsTransformed, int rowsSkipped, String outputFilePath) {
        System.out.println("Number of rows read: " + rowsRead);
        System.out.println("Number of rows transformed: " + rowsTransformed);
        System.out.println("Number of rows skipped: " + rowsSkipped);
        System.out.println("Output file path written: " + outputFilePath);
    }

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
                    continue;
                }

                rowsRead++;

                if (line.trim().isEmpty()) {
                    rowsSkipped++;
                    continue; // Skip empty lines
                }

                String[] originalFields = line.split(",");
                String[] fields = Arrays.copyOf(originalFields, 8);
                fields[5] = ""; 
                fields[6] = ""; 
                fields[7] = ""; 

                try {
                    //write new employee object to the new file
                    Employee employee = new Employee(fields);
                    bw.write(String.join(",", fields));
                    bw.newLine();
                    rowsTransformed++;
                } catch (Exception e) {
                    rowsSkipped++;
                }
            }

            System.out.println();
            printSummary(rowsRead, rowsTransformed, rowsSkipped, outputFilePath);
            System.out.println();

        } catch (IOException e) {
            System.err.println("Error processing the CSV file: " + e.getMessage());
        }

    }

}
