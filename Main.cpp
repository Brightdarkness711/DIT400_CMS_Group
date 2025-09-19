#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
using namespace std;

const int MAX_COURSES = 500;
string courseIds[MAX_COURSES];
string titles[MAX_COURSES];
int creditHours[MAX_COURSES];
int courseCount = 0;

string currentUser;

// function for File Handling -
void loadCourses() {
    ifstream in("courses.txt");
    courseCount = 0;
    string line;
    while (getline(in, line) && courseCount < MAX_COURSES) {
        stringstream ss(line);
        string id, title, creditStr;
        if (getline(ss, id, ',') && getline(ss, title, ',') && getline(ss, creditStr)) {
            courseIds[courseCount] = id;
            titles[courseCount] = title;
            creditHours[courseCount] = stoi(creditStr);
            courseCount++;
        }
    }
    in.close();
}

void saveCourses() {
    ofstream out("courses.txt", ios::trunc);
    for (int i = 0; i < courseCount; i++) {
        out << courseIds[i] << "," << titles[i] << "," << creditHours[i] << "\n";
    }
    out.close();
}

// Authentication funtion--
bool registerUser() {
    string username, password;
    cout << "Enter new username: ";
    cin >> username;
    cout << "Enter new password: ";
    cin >> password;

    ifstream in("users.txt");
    string line;
    while (getline(in, line)) {
        stringstream ss(line);
        string u, p;
        if (getline(ss, u, ',') && getline(ss, p)) {
            if (u == username) {
                cout << "Username already exists.\n";
                return false;
            }
        }
    }
    in.close();

    ofstream out("users.txt", ios::app);
    out << username << "," << password << "\n";
    out.close();
    cout << "Registration successful.\n";
    return true;
}

bool loginUser() {
    string username, password;
    cout << "Username: ";
    cin >> username;
    cout << "Password: ";
    cin >> password;

    ifstream in("users.txt");
    string line;
    while (getline(in, line)) {
        stringstream ss(line);
        string u, p;
        if (getline(ss, u, ',') && getline(ss, p)) {
            if (u == username && p == password) {
                currentUser = username;
                cout << "Login successful. Welcome " << username << "!\n";
                return true;
            }
        }
    }
    cout << "Invalid credentials.\n";
    return false;
}

// Course Operations --

void listCourses() {
    if (courseCount == 0) {
        cout << "No courses available.\n";
        return;
    }
    cout << "\n--- Course List ---\n";
    for (int i = 0; i < courseCount; i++) {
        cout << courseIds[i] << " | " << titles[i] << " | " << creditHours[i] << " hrs\n";
    }
}

void addCourse() {
    if (courseCount >= MAX_COURSES) {
        cout << "Course limit reached.\n";
        return;
    }
    string id, title;
    int hours;
    cout << "Enter course ID: ";
    cin >> id;

    for (int i = 0; i < courseCount; i++) {
        if (courseIds[i] == id) {
            cout << "Course ID already exists.\n";
            return;
        }
    }
    cin.ignore();
    cout << "Enter title: ";
    getline(cin, title);

    cout << "Enter credit hours (1-6): ";
    cin >> hours;
    if (hours < 1 || hours > 6) {
        cout << "Invalid credit hours. Must be 1–6.\n";
        return;
    }

    courseIds[courseCount] = id;
    titles[courseCount] = title;
    creditHours[courseCount] = hours;
    courseCount++;
    saveCourses();
    cout << "Course added successfully.\n";
}

void deleteCourse() {
    string id;
    cout << "Enter course ID to delete: ";
    cin >> id;

    for (int i = 0; i < courseCount; i++) {
        if (courseIds[i] == id) {
            for (int j = i; j < courseCount - 1; j++) {
                courseIds[j] = courseIds[j + 1];
                titles[j] = titles[j + 1];
                creditHours[j] = creditHours[j + 1];
            }
            courseCount--;
            saveCourses();
            cout << "Course deleted.\n";
            return;
        }
    }
    cout << "Course not found.\n";
}

void updateCourse() {
    string id;
    cout << "Enter course ID to update: ";
    cin >> id;

    for (int i = 0; i < courseCount; i++) {
        if (courseIds[i] == id) {
            cin.ignore();
            string newTitle;
            int newHours;
            cout << "Enter new title: ";
            getline(cin, newTitle);
            cout << "Enter new credit hours (1-6): ";
            cin >> newHours;

            if (newHours < 1 || newHours > 6) {
                cout << "Invalid credit hours.\n";
                return;
            }
            titles[i] = newTitle;
            creditHours[i] = newHours;
            saveCourses();
            cout << "Course updated.\n";
            return;
        }
    }
    cout << "Course not found.\n";
}

void searchCourse() {
    string keyword;
    cin.ignore();
    cout << "Enter course ID or title keyword: ";
    getline(cin, keyword);

    bool found = false;
    for (int i = 0; i < courseCount; i++) {
        if (courseIds[i] == keyword || titles[i].find(keyword) != string::npos) {
            cout << courseIds[i] << " | " << titles[i] << " | " << creditHours[i] << " hrs\n";
            found = true;
        }
    }
    if (!found) cout << "No matching course found.\n";
}

// - Menu--
void menu() {
    int choice;
    do {
        cout << "\n--- Course Management Menu ---\n";
        cout << "1. Add Course\n";
        cout << "2. Delete Course\n";
        cout << "3. Search Course\n";
        cout << "4. Update Course\n";
        cout << "5. List All Courses\n";
        cout << "6. Logout\n";
        cout << "Choose option: ";
        cin >> choice;

        switch (choice) {
            case 1: addCourse(); break;
            case 2: deleteCourse(); break;
            case 3: searchCourse(); break;
            case 4: updateCourse(); break;
            case 5: listCourses(); break;
            case 6: cout << "Logging out...\n"; break;
            default: cout << "Invalid choice.\n";
        }
    } while (choice != 6);
}

// Main function
int main() {
    loadCourses();
    int option;
    do {
        cout << "\n===|| Welcome to the Course Management System ||===\n";
        cout << "1. Register\n";
        cout << "2. Login\n";
        cout << "3. Exit\n";
        cout << "Choose and option: ";
        cin >> option;

        switch (option) {
            case 1: registerUser(); break;
            case 2: if (loginUser()) menu(); break;
            case 3: cout << "Exiting...\n"; break;
            default: cout << "Invalid option.\n";
        }
    } while (option != 3);



    return 0;
     /*
    code by Comfort. J Mwenya 2410484
    Emmanuel Mulenga 2410480
    Fridah Mutunda 2300407
    */
}
