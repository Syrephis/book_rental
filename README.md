# book_rental
Book Rental Service

This application uses a PostgreSQL database.

address: localhost:8080

Creating new user:
  POST: /customers
  {
        "firstName": "Adam",
        "lastName": "Novak",
        "account": (default is 0.0)
  }
Deleting user:
  DELETE: /customers/id
Displaying all users:
  GET: /customers
Displaying single user:
  GET: /customers/id
Add new book:
  POST: /books
  {
        "bookTitle": "Szpony i kły",
        "author": "Andrzej Sapkowski",
        "publisher": "superNOWA",
        "status": (default is UNAVAILABLE) Status {AVAILABLE, UNAVAILABLE, RENTED}
        "isbn": 9788375781557
  }
Delete book:
  DELETE /books/isbn
Displaying all books:
  GET: /books
Renting a book:
  POST: /rentals
  {
    "book": {
      "isbn": 9788375781557
    },
    "customer": {
      "id": 4
    }
  }
Returning a book:
  PUT: /rentals/bookingId
Displaying all rentals:
  GET: /rentals
