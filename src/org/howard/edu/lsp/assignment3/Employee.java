package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Employee {

    int iD;
    String name;
    String department;
    String hoursWorked;
    String hourlyRate;
    String grossPay;
    String payLevel;
    String status;

    public Employee(String[] oldFields) {

        //Validate strings and values here before assigning to new fields
        validate(oldFields);

        //Assign validated values to the new fields
        this.iD = Integer.parseInt(oldFields[0]);
        this.name = oldFields[1];
        this.department = oldFields[2];
        this.hoursWorked = String.format("%.2f", Double.parseDouble(oldFields[3]));
        this.hourlyRate = String.format("%.2f", Double.parseDouble(oldFields[4]));

        //initialize them to empty strings for now, they will be calculated in the calculateSalary method.
        this.grossPay = "";
        this.payLevel = "";
        this.status = "";

        //Calculate salary and other fields based on the validated input
        calculateSalary(oldFields);
        this.grossPay = oldFields[5];
        this.payLevel = oldFields[6];
        this.status = oldFields[7];
    }

    public static void validate(String[] fields){
        Integer.parseInt(fields[0]); // Validate EmployeeID
        double hoursWorked = Double.parseDouble(fields[3]); // Validate HoursWorked
        double hourlyRate = Double.parseDouble(fields[4]);

        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].trim();
        }

        fields[1] = fields[1].toUpperCase();

        if (hoursWorked < 0 || hourlyRate < 0) {
          throw new IllegalArgumentException();       
        }
    }

    public static void calculateSalary(String[] fields){
        double hoursWorked = Double.parseDouble(fields[3]);
        double hourlyRate = Double.parseDouble(fields[4]);
        String department = fields[2];
        double grossPay;

        //Calculate gross pay
        if (hoursWorked <= 40.00) {
            grossPay = hoursWorked * hourlyRate;
        } else {
            grossPay = 40.00 * hourlyRate + (hoursWorked - 40.00) * hourlyRate * 1.5;
        }

        //calculate IT bonus if applicable
        if (department.equals("IT")) {
            grossPay *= 1.05;
            fields[5] = String.format("%.2f", grossPay);
        }

        //Calculate pay level
        if (grossPay < 500.00) {
            fields[6] = "Low";
        } else if (grossPay < 1000.00) {
            fields[6] = "Standard";
        } else if (grossPay < 2000.00) {
            fields[6] = "High";
        } else {
            fields[6] = "Executive";
        }

        //Calculate employee status
        if (hoursWorked < 30.00) {
            fields[7] = "Part-Time";
        } else {
            fields[7] = "Full-Time";
        }

        grossPay = Math.round(grossPay * 100.0) / 100.0; 
        fields[5] = String.format("%.2f", grossPay);
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

                //Writes header to the new file with the additional fields
                if (isHeader) {
                    bw.write(line);
                    bw.newLine();
                    isHeader = false;
                    continue;
                }

                rowsRead++;

                // Skips empty lines
                if (line.trim().isEmpty()) {
                    rowsSkipped++;
                    continue; 
                }

                try {
                    //write new employee object to the new file
                    //Employee employee = new Employee(fields);
                    //bw.write(String.join(",", employee));
                    //bw.newLine();
                    Employee employee = new Employee(line.split(","));
                    System.out.println("Employee object created: " + employee);
                    rowsTransformed++;
                } catch (NumberFormatException e) {
                    rowsSkipped++;
                }
            }

            //Prints summary to console.
            System.out.println();
            printSummary(rowsRead, rowsTransformed, rowsSkipped, outputFilePath);
            System.out.println();

        } catch (IOException e) {
            System.err.println("Error processing the CSV file: " + e.getMessage());
        }

    }

}
