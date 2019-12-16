# Book Rental Service
RESTful CRUD Book Rental Service

## Things to be done
-  Documentation

## Technologies & Tools used
-  Spring web
-  JPA
-  Lombok
-  PostgreSQL
-  Maven
-  Java11
-  Junit 5
-  IntelliJ IDEA
-  Postman
-  Docker
-  React
-  Javascript
-  Bootstrap
-  HTML
-  CSS

## Backend API

- **Creating new user:**  
  POST: /customers
  <pre><code>{
        "firstName": "Adam",
        "lastName": "Novak",
        "account": (default is 0.0)
  }</code></pre>
- **Deleting user:**  
  DELETE: /customers/id  
- **Displaying all users:**  
  GET: /customers  
- **Displaying single user:**  
  GET: /customers/id  
- **Add new book:**  
  POST: /books
  <pre><code>{
        "bookTitle": "Szpony i kły",
        "author": "Andrzej Sapkowski",
        "publisher": "superNOWA",
        "status": (default is UNAVAILABLE) Status {AVAILABLE, UNAVAILABLE, RENTED}
        "isbn": 9788375781557
  }</code></pre>
- **Updating a book:**  
    PUT: /books
    <pre><code>{
        "id": 1,
        "bookTitle": "Szpony i kły",
        "author": "Andrzej Sapkowski",
        "publisher": "superNOWA",
        "status": (default is UNAVAILABLE) Status {AVAILABLE, UNAVAILABLE, RENTED}
        "isbn": 9788375781557
    }</code></pre>
- **Delete book:**  
  DELETE: /books/id  
- **Displaying all books:**  
  GET: /books  
- **Renting a book:**  
  POST: /rentals  
  <pre><code>{
    "book": {
      "id": 1
    },
    "customer": {
      "id": 4
    }
  }</code></pre>
- **Returning a book:**  
  PUT: /rentals/bookingId  
- **Displaying all rentals:**  
  GET: /rentals  
