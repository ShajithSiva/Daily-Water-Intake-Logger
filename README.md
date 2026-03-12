# Daily Water Logger 💧

## Overview

Daily Water Logger is an Android application that helps users track their daily water intake.
The app allows users to register, log in, record water consumption, and view their intake history.
Each user has their own records stored in a local SQLite database.

---

## Features

* User Registration and Login
* Track daily water intake
* Daily water goal (50 glasses)
* Progress bar to visualize daily progress
* Add or remove water intake
* View water intake history
* Separate records for each user
* Automatic daily reset of water count

---

## Technologies Used

* Java
* Android Studio
* SQLite Database
* XML Layouts

---

## App Structure

### Activities

* **LoginActivity** – Handles user login.
* **RegisterActivity** – Allows new users to create an account.
* **LogWaterActivity** – Main screen for logging water intake.
* **HistoryActivity** – Displays water intake history.

### Database

The app uses SQLite to store data.

Tables:

1. **users**

   * user_id
   * username
   * password

2. **water_intake**

   * intake_id
   * user_id
   * date
   * glasses

Each water record is linked to a user using `user_id`.

---

## How It Works

1. User registers a new account.
2. User logs in using their credentials.
3. User logs the number of glasses of water consumed.
4. The app stores the intake with the current date.
5. The progress bar updates according to the daily goal.
6. Users can view their past intake records in the history section.

---

## Future Improvements

* Water reminder notifications
* Better UI design
* Graphs for weekly/monthly water intake
* Cloud data storage

---

## Project Members

* ICT/2022/107
* S.Shajith- ICT/2022/108
* T.M.G.C.Thennakoon - ICT/2022/109

---

## Author

Developed as part of an Android development project.
