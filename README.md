# DIT 400 – Object Oriented Programming  
## Pre-OOP Assignment: Course Management System (Arrays + File I/O)

---

## Course Information
- Course Code: DIT 400  
- Assignment Title: Pre-OOP Assignment – Course Management System (C++ & Java)

---

## Team Members

| Name                | Student ID |
|---------------------|------------|
| Comfort J. Mwenya (Team lead)| 2410484    |
| Emmanuel Mulenga    | 2410480    |
| Fridah Mutunda      | 2300407    |

---

## Build and Run Instructions

### C++ (using CodeBlocks)
1. Open CodeBlocks IDE.  
2. Go to File > Import project > .  
3. Replace the default main.cpp with the provided cms.cpp file.  
4. Build and Run (press F9).  
   - The program will generate and use two files in the project directory:  
     - users.txt for usernames and passwords.  
     - courses.txt for course records.

### Java (using Eclipse)
1. Open Eclipse IDE.  
2. Create a new Java Project named `CourseManagementSystem`.  
3. Inside `src/`, create a new class file named `CourseManagementSystem.java`.  
4. Copy the provided Java code into this file.  
5. Run the program.  
   - The program will generate and use the same two files in the project directory:  
     - `users.txt`  
     - `courses.txt`  

---

## Test Credentials

Used(but not limited)`users.txt`:
    zero,2323
    mulenga,muzah510

Used(but not limited)`courses.txt`:
    DIT100,Intro to IT,3
    DIT400,object oriented programming,4


---

## Known Limitations
- Maximum of 500 courses due to fixed array size.  
- No use of structs, classes, or advanced data structures in the C++ version (as assignment requirements).  
- Passwords are stored in plain text.  
- No concurrency; only one user can interact at a time.  
- Searches are case-sensitive.  

---

## Test Cases

### Registration and Login
- Input: Register ` mulenga,muzah510`  
- Expected: User added successfully and can log in with the same credentials.  

### Add Course
- Input: Add course ` DIT400,object oriented programming,4`  
- Expected: Course appears in `courses.txt` and in the course list.  

### Prevent Duplicate Course
- Input: Add course `DIT100` again  
- Expected: Error message "Course ID already exists."  

### Delete Course
- Input: Delete course with ID `DIT400`  
- Expected: Course is removed from memory and from `courses.txt`.  

### Update Course
- Input: Update `DIT100` → Title = "Intro to information Tech", Hours = 4  
- Expected: Course record is updated in `courses.txt`.  

### Search Course
- Input: Search keyword "Programming"  
- Expected: Any course containing "Programming" in the title is displayed.  

---
