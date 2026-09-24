package org.howard.edu.lsp.assignment3;

public class Employee {

    private int iD;
    private String name;
    private String department;
    private String hoursWorked;
    private String hourlyRate;
    private String grossPay;
    private String payLevel;
    private String status;

    public Employee(String[] oldFields) {

        //Validate strings and values here before assigning to new fields
        validate(oldFields);

        //Assign validated values to the new fields
        this.iD = Integer.parseInt(oldFields[0]);
        this.name = oldFields[1];
        this.department = oldFields[2];
        this.hoursWorked = String.format("%.2f", Double.parseDouble(oldFields[3]));
        this.hourlyRate = String.format("%.2f", Double.parseDouble(oldFields[4]));

        //Calculate salary and other fields based on the validated input
        calculateSalary(oldFields);
    }

    public static void validate(String[] fields){
        if (fields.length != 5) {
            throw new IllegalArgumentException("Invalid number of fields");
        }

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

    public void calculateSalary(String[] fields){
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
        }

        //Calculate pay level
        if (grossPay < 500.00) {
            this.payLevel = "Low";
        } else if (grossPay < 1000.00) {
            this.payLevel = "Standard";
        } else if (grossPay < 2000.00) {
            this.payLevel = "High";
        } else {
            this.payLevel = "Executive";
        }

        //Calculate employee status
        if (hoursWorked < 30.00) {
            this.status = "Part-Time";
        } else {
            this.status = "Full-Time";
        }

        grossPay = Math.round(grossPay * 100.0) / 100.0; 
        this.grossPay = String.format("%.2f", grossPay);
    }

    @Override
    public String toString() {
        return iD + "," + name + "," + department + "," + hoursWorked + ","
         + hourlyRate + "," + grossPay + "," + payLevel + "," + status;
    }

}
