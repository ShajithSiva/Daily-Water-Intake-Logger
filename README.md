# Daily Water Logger 💧

## Overview

Daily Water Logger is an Android application that helps users track their daily water intake.
The app allows users to register, log in, record water consumption, and view their intake history.
Each user has their own records stored in a local SQLite database.

---

## Project Repository

🔗 GitHub Repository:
https://github.com/ShajithSiva/Daily-Water-Intake-Logger.git

---

## Features

* 👤 **User Registration and Login**
* 💧 **Track Daily Water Intake**
* 🎯 **Daily Water Goal (50 glasses)**
* 📊 **Progress Bar to Visualize Daily Progress**
* ➕ **Add Water Intake**
* ➖ **Remove Glass if Added by Mistake**
* 📜 **View Water Intake History**
* 👥 **Separate Records for Each User**
* 🔄 **Automatic Daily Reset of Water Count**

---

## Technologies Used

* ☕ **Java**
* 🤖 **Android Studio**
* 🗄 **SQLite Database**
* 🎨 **XML Layouts**

---

## Project Structure

```
## Project Structure
Daily-Water-Intake-Logger/
│
├── app/
│   ├── manifests/
│   │   └── AndroidManifest.xml
│   │
│   ├── java/com/example/dailywaterlogger/
│   │   ├── Database.java
│   │   ├── HistoryActivity.java
│   │   ├── LoginActivity.java
│   │   ├── LogWaterActivity.java
│   │   ├── PasswordUtils.java
│   │   ├── RegisterActivity.java
│   │   └── SessionManager.java
│   │
│   ├── res/
│   │   ├── drawable/
│   │   │   ├── button_bg.xml
│   │   │   ├── edittext_bg.xml
│   │   │   ├── ic_launcher_background.xml
│   │   │   ├── ic_launcher_foreground.xml
│   │   │   ├── ic_water_drop.xml
│   │   │   └── water_logo.png
│   │   │
│   │   ├── layout/
│   │   │   ├── activity_login.xml
│   │   │   ├── activity_register.xml
│   │   │   ├── activity_log_water.xml
│   │   │   └── activity_history.xml
│   │   │
│   │   ├── mipmap/
│   │   │
│   │   └── values/
│   │       └── themes.xml
│   │
│   └── Gradle Scripts
│
└── README.md
```

```

### Key Components

**Activities**

* 🔐 `LoginActivity` – Handles user login
* 📝 `RegisterActivity` – Allows users to create an account
* 💧 `LogWaterActivity` – Main screen for logging water intake
* 📜 `HistoryActivity` – Displays water intake history

**Database**

* 🗄 `Database.java` manages the SQLite database
* Stores users and water intake records

**SessionManager**

* 🔑 Handles login session management for users

---

## Database Design

### users

| Column   | Description             |
| -------- | ----------------------- |
| user_id  | Unique ID for each user |
| username | Username for login      |
| password | User password           |

### water_intake

| Column    | Description                     |
| --------- | ------------------------------- |
| intake_id | Unique ID for each record       |
| user_id   | Links record to a specific user |
| date      | Date of water intake            |
| glasses   | Number of glasses logged        |

---

## How to Run the Project

1. Install **Android Studio**
2. Clone the repository

```
git clone https://github.com/ShajithSiva/Daily-Water-Intake-Logger.git
```

3. Open **Android Studio**
4. Click **Open Project**
5. Select the downloaded project folder
6. Wait for **Gradle Sync** to finish
7. Connect an **Android device** or start an **Android Emulator**
8. Click **Run ▶** in Android Studio

The application will start and you can begin tracking water intake.

---

## How the App Works

1. A user creates an account using the **Register screen**
2. The user logs in through the **Login screen**
3. The **Log Water screen** allows users to add or remove glasses of water
4. The progress bar updates according to the daily goal (50 glasses)
5. Users can view their previous records in the **History screen**
6. Each user's water intake is stored separately in the SQLite database

---

## Future Improvements

* 🔔 Water reminder notifications
* 🎨 Improved UI design
* 📈 Weekly and monthly intake charts
* ☁ Cloud database integration

---

## Project Members

* **M. K. H. K. Madushani** — ICT/2022/107
* **S. Shajith** — ICT/2022/108
* **T. M. G. C. Thennakoon** — ICT/2022/109

---

## Author

Developed as part of an Android development project.
