# TaskReminder – Secure Task Scheduler & Reminder System

**TaskReminder** is a production-oriented task management and scheduling system focused on real-world backend engineering rather than basic CRUD. The system uses **Google OAuth2 authentication**, **background schedulers**, **distributed SQL**, and **cloud-native deployment** to deliver a secure and scalable reminder platform.

---

## Project Components

* **Backend:** Spring Boot + OAuth2 + Distributed SQL (Deployed)
* **Frontend:** Thymeleaf-based UI
* **Scheduler:** Background task reminder engine
* **Email System:** Asynchronous SMTP notifications

> **Note: No Local Backend Setup Required**
> The backend is already deployed and production-ready. Users can authenticate, manage tasks, and receive reminder emails without running anything locally.

---

## Key Features

### 🔐 Secure Google OAuth2 Authentication
* Passwordless login experience.
* **Spring Security** protected routes.
* No user credentials stored within the application database.

### 📝 Task Management
* Create, update, and delete tasks.
* Full support for **Due Dates**.
* Priority-based task organization.

### ⏰ Automated Task Scheduler
* Background scheduler runs periodically.
* Automatically detects due tasks.
* Designed specifically for production workloads.

### 📧 Email Reminder System
* **Asynchronous** email sending to prevent UI blocking.
* Reliable cloud SMTP delivery via **Brevo**.
* Ensures the UI remains responsive during high-volume notifications.

### 🗄️ Distributed SQL Database
* Powered by **TiDB Cloud**.
* Horizontally scalable and fault-tolerant architecture.
* MySQL-compatible using **JPA**.

### ☁️ Cloud-Native Deployment
* Fully **Dockerized** containerization.
* Deployed on **Render**.
* Strict environment-based configuration for security.

---

## Application Screens

### 1. OAuth Login
![Task Dashboard](https://raw.githubusercontent.com/msnawin/TaskReminder-Secure1/3465918370756780bfaebe315955ef3b906c8aa9/Screenshot%202025-12-29%20093435.png)

Secure login using Google OAuth2.

### 2. Task Dashboard

![Task Reminder Screenshot](https://raw.githubusercontent.com/msnawin/TaskReminder-Secure1/7c323e92e636e6dc8d6e2b162c4da4c8a812598f/Screenshot%202025-12-29%20093521.png)

Full Create / Read / Update / Delete (CRUD) functionality.

### 3. Reminder Email
![Scheduler Running Screenshot](https://raw.githubusercontent.com/msnawin/TaskReminder-Secure1/7c323e92e636e6dc8d6e2b162c4da4c8a812598f/Screenshot%202025-12-29%20093735.png)

![Email Sent Screenshot](https://raw.githubusercontent.com/msnawin/TaskReminder-Secure1/7c323e92e636e6dc8d6e2b162c4da4c8a812598f/Screenshot%202025-12-29%20093802.png)



Automated reminder emails delivered to the user.

---

## Task Scheduler – Execution Flow

1.  **User logs in** using Google OAuth2.
2.  **Secure session** is created by Spring Security.
3.  User **creates or updates** a task.
4.  Task data is stored in **TiDB Cloud**.
5.  **Background scheduler** runs every **60 seconds**.
6.  Due tasks trigger **reminder emails asynchronously**.

---

## Engineering Challenges and Solutions

| Challenge | Problem | Solution |
| :--- | :--- | :--- |
| **SMTP Port Restrictions** | Cloud platforms block Port 587. | Switched to **Port 2525** using Brevo SMTP. |
| **Email Delays** | Email sending blocked UI threads. | Implemented **Asynchronous** email processing. |
| **Database Scalability** | Single-node database limits. | Adopted **Distributed SQL** using TiDB Cloud. |
| **Secret Management** | Secrets exposed in source code. | Used **Environment variable injection**. |
| **Free Tier DB Timeouts** | Connection pool exhaustion. | Performed **HikariCP tuning**. |

---

## Tech Stack

### Backend
* Java 21
* Spring Boot
* Spring Security
* OAuth2
* Spring Data JPA
* Spring Scheduler
* JavaMailSender

### Database
* **TiDB Cloud** (Distributed SQL)

### Email
* **Brevo SMTP** (Port 2525)

### Deployment
* Docker
* Render

### Frontend
* Thymeleaf
* Bootstrap

---

## Project Structure

```text
TaskReminder/
├── src/
├── pom.xml
├── Dockerfile
├── .dockerignore
├── templates/
├── static/
└── README.md
## Security Highlights

* ✅ **No passwords stored** in the database.
* ✅ **OAuth2-based** authentication flow.
* ✅ **Environment-based** secret management.
* ✅ **Spring Security** route protection.

---

## Author

**Nawin M. S.**
