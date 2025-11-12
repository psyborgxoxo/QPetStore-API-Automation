# 🧪 QPetStore API Automation Framework

This is a **Java-based API Automation Framework** built for testing the **QPetStore QA backend APIs**.  
The framework uses **Rest Assured**, **TestNG**, **Maven**, **Extent Reports**, and **Log4j2** to deliver a powerful and maintainable automated testing solution.

---

## 🌐 API Under Test

**Base URL:**
**https://github.com/psyborgxoxo/QPetStore-API-Automation/**


**Modules Covered:**
| Module | Endpoint | Description |
|---------|-----------|-------------|
| User | `/api/v1/register` | Register new users |
| User | `/api/v1/login` | Login and authentication |
| City | `/api/v1/cities` | Fetch city list and details |
| Adventure | `/api/v1/adventures` | Fetch adventure listings |
| Reservation | `/api/v1/reservations` | Manage adventure bookings |

---

## ⚙️ Tech Stack

| Category | Tool |
|-----------|------|
| Programming Language | Java 21 |
| Build Tool | Maven 3.9.11 |
| Testing Framework | TestNG |
| API Testing Library | Rest Assured |
| Reporting | Extent Reports |
| Logging | Log4j2 |
| Data Generation | Java Faker |

---

## 🧩 Project Structure

QPetStore-API-Automation/
├── pom.xml # Maven dependency manager
├── testng.xml # Test suite definition
├── /src
│ ├── /main/java/api
│ │ ├── /endpoints
│ │ │ ├── Routes.java # All API endpoints
│ │ │ └── UserEndpoints.java # Endpoint logic for CRUD operations
│ │ ├── /payload
│ │ │ └── User.java # POJO for User data
│ │ └── /utilities
│ │ ├── ExtentReportManager.java # Handles Extent report lifecycle
│ │ ├── DataProviders.java # Supplies data to TestNG tests
│ │ └── ExcelUtility.java # Utility for reading Excel data
│ └── /test/java/api
│ └── /test
│ ├── UserTests.java # CRUD tests for User module
│ ├── DDTest.java # Data-driven test class
│ └── ReservationTests.java # Tests for reservation APIs
└── /reports
└── ExtentReport.html # Auto-generated test execution report


---

## 🧱 Prerequisites

Before setup, ensure the following are installed and configured on your system:

| Software | Version | Description |
|-----------|----------|-------------|
| Java | 17 or higher (You’re using 21 ✅) | Required for running the tests |
| Maven | 3.8+ (You’re using 3.9.11 ✅) | For dependency management and build |
| IDE | IntelliJ IDEA / Eclipse | Recommended for development |
| Git | Latest | For version control |

