TaskReminder
🔐 Secure & High-Performance Task Management System

🚀 Built with Distributed SQL, OAuth2 Security, and Cloud-Native Architecture

📌 Project Overview

TaskReminder is a production-oriented task management application built to simulate the architecture and constraints of real-world SaaS systems.

Instead of focusing only on CRUD operations, this project emphasizes security, scalability, performance, and cloud deployment challenges that backend engineers face in production environments.

Users authenticate using Google OAuth2, manage their tasks securely, and receive automated email reminders — all backed by a distributed SQL database and asynchronous background processing.

⚙️ Core Infrastructure
🚀 Backend Framework

The backend is developed using Spring Boot 3.4.x and Java 21, ensuring modern JVM performance and long-term maintainability.

This stack provides:

Faster startup times

Efficient memory management

Clean dependency handling

Production-ready configuration support

The architecture is designed to handle high request throughput while remaining simple to extend and maintain.

🔐 Authentication & Security

Authentication is implemented using Google OAuth 2.0, providing a passwordless and secure login experience.

Key security benefits:

No passwords are stored in the application

Reduced risk of credential leaks

Industry-standard authentication flow

Protected routes enforced using Spring Security

Each user interaction is tied to a secure session created after successful OAuth authentication.

☁️ Database (Distributed SQL)

Instead of relying on a traditional single-node database, TaskReminder uses TiDB Cloud, a serverless distributed SQL database.

Why this matters:

Horizontally scalable without schema changes

Fault-tolerant by design

MySQL-compatible (easy JPA integration)

Suitable for real production workloads

All task data is persisted using Spring Data JPA, ensuring clean entity mapping and transactional safety.

✉️ Email & Notification System

Automated reminders are a core feature of TaskReminder.

The system uses Brevo SMTP Relay configured on Port 2525, which avoids common cloud SMTP restrictions and ensures reliable delivery.

Design highlights:

Emails are sent asynchronously to prevent UI delays

A background scheduler checks for due tasks periodically

SMTP communication is production-tested

This approach keeps the user experience fast while ensuring timely notifications.

🐳 Containerization & Deployment

The application is fully containerized using Docker and deployed on Render.

Deployment benefits:

Consistent runtime across environments

Faster deployments using multi-stage Docker builds

Smaller image sizes

Easy scalability

All sensitive configuration values (database URLs, OAuth credentials, SMTP keys) are injected via environment variables, following security best practices.

🖼️ Visual Technical Proof
🔐 Security Layer – OAuth Login

📸 Screenshot: Google Account Selection Screen

This screenshot proves:

OAuth2 authentication is active

Routes are protected by Spring Security

🖥️ User Interface – Task Dashboard

📸 Screenshot: Main Task List UI

This demonstrates:

Full task Create / Read / Update / Delete functionality

Responsive UI built with Thymeleaf and Bootstrap

🗄️ Data Integrity – Database Verification

📸 Screenshot: TiDB Chat2Query Output

This validates:

Successful data persistence

Connectivity to a remote distributed database cluster

📧 Email Service – System Heartbeat

📸 Screenshot: Reminder Email in Gmail

This confirms:

Background scheduler execution

Successful SMTP handshake in production

🧠 Engineering Challenges & Solutions
⚠️ Challenge	✅ Solution
Cloud blocks SMTP Port 587	Migrated to Port 2525
DB timeouts on free tier	Tuned HikariCP connection pooling
Secrets in source code	Environment variable injection
Email delays UI	Asynchronous mail processing
Single-node DB limits	Distributed SQL with TiDB
🔄 Application Workflow

1️⃣ User logs in securely via Google OAuth2
2️⃣ Spring Security creates a secure session
3️⃣ User creates or updates a task
4️⃣ Task data is stored in TiDB Cloud via JPA
5️⃣ Background scheduler runs every 60 seconds
6️⃣ Due tasks trigger reminder emails via Brevo SMTP
