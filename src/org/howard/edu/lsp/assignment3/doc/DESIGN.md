1.	How was your Assignment #2 solution organized?
    - My assignment #2 was organized very simply. With a main function that hardcoded fields, 
    and helper functions that validated strings/values, and calculated every label header. All within the same file.
    So, it feels a bit crowded.

2.	What design changes did you make for Assignment #3?
    - In my assignment #3, I decided to reorganize to make the code more readable and concise. So, many functions were combined. As they were similar in what they changed so it made sense to put them together than make a function for each step. I did reuse some code for the functions as I think that were still very simple to implement. But, I probably could've reduced how hardcoded the changed code is still.

3.	What classes or abstractions did you introduce and why?
    - I introduced the Employees class with two procedural abstractions, validate and calculateSalary. I decided to continue with the Employees Class because there would be objects like name, id, department, etc that would need to be declared and defined. Since, I had to validate strings and calculate values from the CSV file it seemed correct to make functions that would assist with reformmatting the information.

4.	How did you divide responsibilities differently?
    - In assignment #3, in my Employee.java file, I also combined my functions into two big function to make everything more concise and put together. One function to validate strings and values while the other calculates gross pay, bonuses, pay level and employment status. So, that file only formats employee information, while ETLPipeline.java just reads the file then writes the new information. 

5.	Why do you believe your Assignment #3 design is an improvement?
    - I believe my assignment #3 is definitely an improvement. The readability has definitely been improved upon.
    I can understand what exactly is being changed or defined along with making it easier to find specific functions despite the fact majority is of very similar code to the first one. Also since reading the CSV file and reformatting the CSV in the Employee.java separates their functionality has been more clear.

### AI/Internet Disclosure 

Here's the link to the AI (Claude) transcript I used in the process of making my assignment #3:
https://claude.ai/chat/bea9eabb-a7ab-42e4-bd4a-1620d3fc66be

Alongside AI, I used the ppt's given in the resources in Piazza along with understanding attributes and objects from W3Schools:
https://www.w3schools.com/java/java_classes.asp
https://www.w3schools.com/java/java_class_attributes.asp
