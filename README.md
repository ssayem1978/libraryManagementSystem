# SS OPEN LIBRARY Web Application

## Description

A web application built using Spring Boot for backend and HTML frontend, designed to manage library resources efficiently.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Models](#models)
- [Database Schema](#database-schema)
- [Web Pages](#web-pages)
- [Contributors](#contributors)
- [License](#license)

## Installation

1. Clone the repository: `git clone <repository-url>`
2. Navigate to the project directory.
3. Set up backend environment:
    - Install dependencies: `mvn install`
    - Run Spring Boot application: `mvn spring-boot:run`
4. Configure database settings in `application.properties`.
5. Access application at `http://localhost:8080`, `http://localhost:8080/register`, `http://localhost:8080/login`, `http://localhost:8080/admin/dashboard`.

## Usage

- Register or log in as a user.
- Browse the catalog, borrow and return books.
- Admins can manage books, users, and notifications.

## Technologies Used

- Backend: Spring Boot, Java
- Frontend: HTML, CSS, Bootstrap
- Database: MySQL
- Other tools: Maven,

## Features

- User authentication and authorization.
- Catalog management with filter.
- Borrowing and returning books with due date tracking.
- Admin dashboard for managing books, users, categories.

## Models

1. **USER:** Manages user information, authentication, and permissions.
2. **BOOK:** Represents library inventory with details like title, author, genre, and availability.
3. **CATEGORY:** Organizes books into genres or classifications for filtering and searching.
4. **BOOK LOANS:** Records borrowing and returning activities.
5. **VERIFICATION TOKEN:** For verifying the users.

## Database Schema

- **users:** Manages user details and authentication.
- **categories:** Organizes books into categories for better management.
- **books:** Stores book details and relates to categories.
- **BOOK LOANS:** Records borrowing transactions.
- **VERIFICATION TOKEN:** Storing user verification token with expiration time.

## Web Pages

- **Home Page:** Overview and navigation to key sections.
- **User Registration & Login Pages:** Account creation and login functionality.
- **Book Catalog & Details Pages:** Lists all books and detailed information for each book.
- **User Page:** User-specific information and search results display.
- **Admin Dashboard & Management Pages:** Tools for administrators to manage books, users, categories, transactions, and notifications.

## Contributors

- Sayed Sayem (@ssayem1978)

## License

This project is licensed under the [MIT License](LICENSE).