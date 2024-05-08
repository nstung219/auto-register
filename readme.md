Automated language test registration
====================================
[Data instruction](#data-instruction)
- Data for registration is stored in an excel file in following format:
  - Column 1: username
  - Column 2: password
  - Column 3: test event registration information
    separated by `::`(double colon) including 5 fields
    - first field: day of event
    - second field: place of event (uni)
    - third field: time slot of event
    - fourth field: language band (Band A, Band B, Band C)
    - fifth field: language type (Traditional, Simplified)
  - any following columns: additional test event information, the program will prioritize test event information in order
- Each row represents a student registration
- If excel cell has value started with `0`, add `'` before the value to prevent excel from removing the `0`

[Program integration](#program-integration)
- Run `mvn -B clean test -DdataPath="Auto-login.xlsx"` to execute registration for all users in the excel file. Replace `Auto-login.xlsx` with the path to the excel file
- Add `-Ddataproviderthreadcount=1` parameter to limit the number of concurrent users to be registered (for weak machine)
