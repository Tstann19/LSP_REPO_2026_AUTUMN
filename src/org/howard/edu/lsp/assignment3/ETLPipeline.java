package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

                //Writes header to the new file with the additional fields
                if (isHeader) {
                    bw.write(line + ",GrossPay,PayLevel,EmploymentStatus");
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

                //write new employee object to the new file
                try {
                    Employee employee = new Employee(line.split(","));
                    bw.write(employee.toString());
                    bw.newLine();
                    rowsTransformed++;
                } catch (IllegalArgumentException e) {
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

    public static void printSummary(int rowsRead, int rowsTransformed, int rowsSkipped, String outputFilePath) {
        System.out.println("Number of rows read: " + rowsRead);
        System.out.println("Number of rows transformed: " + rowsTransformed);
        System.out.println("Number of rows skipped: " + rowsSkipped);
        System.out.println("Output file path written: " + outputFilePath);
    }
}
