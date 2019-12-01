# book_rental
RESTful Book Rental Service

This application uses a PostgreSQL database.

**Things to be done:**
-  Responses for all of the crud operations.
-  Validation
-  Tests
-  Front-end (Probably React)
-  Documentation

**Technologies & Tools used:**
-  Spring web
-  JPA
-  Lombok
-  PostgreSQL
-  Maven
-  Java11
-  IntelliJ IDEA
-  Postman
-  Docker

address: localhost:8080

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
  }</pre></code>
- **Delete book:**  
  DELETE /books/isbn  
- **Displaying all books:**  
  GET: /books  
- **Renting a book:**  
  POST: /rentals  
  <pre><code>{
    "book": {
      "isbn": 9788375781557
    },
    "customer": {
      "id": 4
    }
  }</pre></code>
- **Returning a book:**  
  PUT: /rentals/bookingId  
- **Displaying all rentals:**  
  GET: /rentals  
