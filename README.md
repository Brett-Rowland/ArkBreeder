# Ark Breeder Tracker

Ark Breeder Tracker is a web-based application designed to help players manage and analyze breeding projects in **ARK: Survival Ascended**.  
The system focuses on accurate stat calculations, inheritance tracking, and configurable server settings.

This project is currently configured for **local development** and is actively evolving.

---

## ✨ Features
- Track breeding lines and individual creatures
- Calculate stat values based on server multipliers
- Support for mutations, imprinting, and inheritance logic
- Configurable settings for different server environments

---

## 🛠️ Tech Stack

### Backend
- Java
- Spring Boot
- JPA / Hibernate
- MySQL

### Frontend
- React
- HTML / CSS

### Tools
- GitHub
- IntelliJ IDEA

---

## 🚀 Running Locally

### Prerequisites
- Java 17+
- MySQL
- Node.js (for frontend)

---

### Backend Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/Brett-Rowland/ArkBreeder.git
   
2. Change directory to the backend
    cd ArkBreeder/backend

2. CREATE DATABASE arkbreeder;

3. Update application.properties

4. Run The application
    ./mvnw sprint-boot:run


It will start the backend on http://localhost:8787


### Frontend Setup
1. Change the directory to the front end
    cd ../ArkBreederFrontend

2. Install necessary packages from node package manager
    npm install

3. Spin up the server
    npm run dev

