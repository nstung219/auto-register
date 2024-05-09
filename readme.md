Automated language test registration
====================================
[Requirements](#requirements)
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)

[Data instruction](#data-instruction)
- Data for registration is stored in an Excel file in following format:
  - Column 1: username
  - Column 2: password
  - Column 3: test event registration information
    separated by `::`(double colon) including 5 fields
    - first field: day of event
    - second field: place of event (uni)
    - third field: time slot of event
    - fourth field: language band (Band A, Band B, Band C)
    - fifth field: language type (Traditional, Simplified)
  - Any following columns: additional test event information, the program will prioritize test event information in order
- Each row represents a student registration
- If Excel cell has value started with `0`, add `'` before the value to prevent Excel from removing the leading `0`
- Example line: Username, Password, 2024/06/23::越南_越南城東大學 越南_越南城東大學::( 08:00 - 10:00 )::Band A::Traditional

[Program Instruction](#program-instruction)
- Run `mvn -B clean test -DdataPath="Auto-login.xlsx"` to execute registration for all users in the Excel file. Replace `Auto-login.xlsx` with the path to the excel file
  - Add `-Ddataproviderthreadcount=number` parameter to set the number of concurrent users to be registered
- After the program has completed, check `succeeded` and `failed` folders inside `target` folder for register result