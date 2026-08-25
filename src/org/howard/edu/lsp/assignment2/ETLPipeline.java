package org.howard.edu.lsp.assignment2;

public static void main(String[] args){

}

/*
1. Normalize fields: Trim leading and trailing whitespace from every field. Convert the employee Name to UPPERCASE. Department names otherwise remain unchanged after trimming.
2. Validate numeric values: EmployeeID must be an integer. HoursWorked and HourlyRate must be valid decimal numbers and may not be negative. Invalid rows are skipped as described below.
3. Calculate base/overtime pay: For HoursWorked up to and including 40.00, pay all hours at the normal HourlyRate. If HoursWorked is greater than 40.00, pay the first 40 hours at the normal rate and all hours above 40 at 1.5 times the normal rate.
4. Apply the IT bonus: If the trimmed Department is exactly "IT", add a 5% bonus to the pay calculated in Step 3. The bonus is applied after overtime.
5. Round GrossPay: Round the resulting GrossPay to exactly two decimal places using round-half-up.
6. Determine PayLevel: Using the final rounded GrossPay: < $500.00 → Low; $500.00–$999.99 → Standard; $1000.00–$1999.99 → High; >= $2000.00 → Executive.
7. Determine EmploymentStatus: HoursWorked < 30.00 → Part-Time; HoursWorked >= 30.00 → Full-Time.
Important numeric rule: use the parsed HoursWorked and HourlyRate values for payroll calculations. Do not round HourlyRate before calculating GrossPay. Formatting HourlyRate to two decimal places is an output requirement only.

The rows containing badid, abc, too few fields, too many fields, the blank line, negative HoursWorked, 
and bad HourlyRate must be skipped.
*/


/*
At the end of normal execution, print a summary that includes:
•	Number of rows read (all non-header lines encountered, including blank and malformed lines)
•	Number of rows transformed (written to output)
•	Number of rows skipped
•	Output file path written

*/