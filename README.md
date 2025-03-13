# 🚖 MegaCity Cabs System

## 📝 Project Overview
MegaCity Cabs is a **cab booking system** that allows users to book rides, manage drivers, and handle user authentication. The system is built using **Java (JAX-RS)** for backend services and **Microsoft SQL Server** for database management.

---

## 🚀 Features

✅ **User Management**  
- Register and login users  
- Different roles: Admin, Driver, Customer  

✅ **Driver Management**  
- Create, update, and delete driver profiles  
- Check driver availability  

✅ **Booking System**  
- Users can book a ride  
- System assigns an available driver  

✅ **Database Handling**  
- Centralized database connection using `DBHandler` (Singleton Pattern)  
- Secure user authentication  

---

## 🛠️ Technologies Used
| Technology | Purpose |
|------------|---------|
| **Java (JDK 17+)** | Backend logic & business operations |
| **JAX-RS** | REST APIs & service layers |
| **Microsoft SQL Server** | Database management |
| **JDBC (SQLServer Driver)** | Database connectivity |
| **Postman** | API testing |

---

## ⚙️ System Architecture
The system follows **object-oriented principles** with well-defined layers:

- **Controllers:** Handle API requests and responses  
- **Business Logic (BL) Classes:** Process business rules  
- **Models:** Represent data entities (User, Driver, Booking, etc.)  
- **Database Handler (DBHandler):** Manages SQL connections (Singleton Pattern)  

### 🎯 Design Patterns Implemented
- **Singleton** → `DBHandler` for managing a single database connection  
- **DAO (Data Access Object)** → `UserBL`, `DriverBL` for database operations  
- **Factory** → User role-based object creation  
- **Repository** → Abstracting database operations from business logic  
- **Observer (Potential)** → Handling driver availability updates  

---

## 🛠️ Installation Guide

### **Prerequisites**
- Install **JDK 17+**  
- Install **Microsoft SQL Server 2022**  
- Install **Netbeans latest with JAVA EE**  

### **Steps to Run Locally**

1️⃣ **Clone the Repository**
```sh
git clone https://github.com/pramodsandakelum/vehiclesystem
cd MegaCity-Cabs
```

2️⃣ **Configure the Database**  
- Open `DBHandler.java` and update database credentials:
```java
private static String JDBC_URL = "jdbc:sqlserver://YOUR_SERVER;databaseName=megacitycabs";
private static String USER = "your_db_username";
private static String PASS = "your_db_password";
```
- Import `megacitycabs.sql` (Database schema) into **SQL Server**  


4️⃣ **Test API Endpoints**  
- Open **Postman**  
- Use endpoints (e.g., `http://localhost:8080/Mega_city_cabs/api/`)  

---

## 📂 Project Structure
```
💾 MegaCity-Cabs
 ┣ 📂 src
 ┃ ┣ 📂 main
 ┃ ┃ ┣ 📂 java/com/megacitycabs
 ┃ ┃ ┃ ┣ 📂 controller
 ┃ ┃ ┃ ┣ 📂 model
 ┃ ┃ ┃ ┣ 📂 service (BL Classes)
 ┃ ┃ ┃ ┗ 📄 DBHandler.java
 ┃ ┃ ┣ 📂 resources
 ┃ ┃ ┃ ┗ 📄 application.properties
 ┣ 📄 pom.xml
 ┣ 📄 README.md
 ┗ 📄 megacitycabs.sql
```

---

## 🔍 API Endpoints (Sample)

### **User Authentication**
| Method | Endpoint | Description |
|--------|---------|-------------|
| `POST` | `/api/users/login` | User login |
| `POST` | `/api/users/register` | Create new user |

### **Driver Management**
| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET` | `/api/drivers` | Get all available drivers |
| `POST` | `/api/drivers` | Add a new driver |
| `DELETE` | `/api/drivers/{id}` | Delete driver |

### **Ride Booking**
| Method | Endpoint | Description |
|--------|---------|-------------|
| `POST` | `/api/bookings` | Create a ride booking |
| `GET` | `/api/bookings/{id}` | Get booking details |

---

## 👨‍💻 Contributors
- **Pramod Sandakelum** - Developer

🙌 Feel free to contribute! Fork the repo, create a feature branch, and submit a pull request.

---

## 🐝 License
This project is licensed under the **GPL License**.

---

### 🌟 **Support & Contact**
If you have any issues or feature requests, feel free to open an **issue** on GitHub.

---

🚀 **Happy Coding!**

