import React, { Component } from "react";
import Spinner from "./spinner";
import NavBar from "./navBar";
import { FaSearch, FaPlus } from "react-icons/fa";

class Books extends Component {
  emptyBook = {
    title: null,
    author: null,
    publisher: null,
    isbn: null
  };

  emptyRental = {
    book: {
      id: null,
      isbn: null
    },
    customer: {
      id: null
    }
  };

  state = {
    isLoading: true,
    books: [],
    item: this.emptyBook,
    rental: this.emptyRental,
    returnISBN: null
  };

  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      books: [],
      item: this.emptyBook,
      rental: this.emptyRental,
      returnISBN: null
    };

    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleRental = this.handleRental.bind(this);
    this.handleRentalChange = this.handleRentalChange.bind(this);
    this.handleReturn = this.handleReturn.bind(this);
    this.handleReturnChange = this.handleReturnChange.bind(this);
  }

  // {
  //   "id": 1,
  //   "title": "Szpony i kły",
  //   "author": "Andrzej Sapkowski",
  //   "publisher": "superNOWA",
  //   "status": "AVAILABLE",
  //   "description": null,
  //   "isbn": "9788375781557"
  // },

  async componentDidMount() {
    /*
    const response = await fetch("/books");
    const body = await response.json();
    this.setState({ books: body, isLoading: false });
    */
    await fetch("/books")
      .then(response => response.json())
      .then(json => {
        this.setState({ books: json, isLoading: false });
      });
  }

  async remove(id) {
    await fetch("/books/" + id, {
      method: "DELETE",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
      }
    }).then(() => {
      let updatedBooks = [...this.state.books].filter(i => i.id !== id);
      this.setState({ books: updatedBooks });
    });
  }

  async handleReturn(event) {
    event.preventDefault();
    const returnISBN = this.state.returnISBN;

    console.log(returnISBN);
    await fetch("/rentals/ISBN/" + returnISBN, {
      method: "PUT",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
      }
    });
    window.location.reload();
  }

  handleReturnChange(event) {
    const target = event.target;
    const value = target.value;

    this.setState({ returnISBN: value });
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;

    let item = { ...this.state.item };
    item[name] = value;
    this.setState({ item });
  }

  async handleSubmit(event) {
    event.preventDefault();
    const item = this.state.item;

    await fetch("/books", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
      },
      body: JSON.stringify(item)
    });
    console.log(JSON.stringify(item));
    //this.props.history.push("/books");
    window.location.reload();
  }

  handleRentalChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name.split(".");

    let rental = { ...this.state.rental };
    rental[name[0]][name[1]] = value;
    this.setState({ rental });
  }

  async handleRental(event) {
    event.preventDefault();
    const rental = this.state.rental;
    await fetch("/books/ISBN/" + rental.book.isbn)
      .then(response => response.json())
      .then(json => {
        //This shouldn't be like that
        rental["book"]["id"] = json.id;
        this.setState({ rental });
      });

    await fetch("/rentals", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
      },
      body: JSON.stringify(rental)
    });
    console.log(JSON.stringify(rental));
    window.location.reload();
  }

  render() {
    const { books, isLoading } = this.state;
    if (isLoading)
      return (
        <div>
          <NavBar />
          <Spinner />;
        </div>
      );
    else
      return (
        <div>
          <NavBar />
          <div className="container-fluid">
            <div className="row">
              <div className="col-md-3">
                <div className="card my-4 border-0 shadow-sm">
                  <h5 className="card-header">Search</h5>
                  <div className="card-body">
                    <div className="input-group">
                      <input
                        type="text"
                        className="form-control"
                        placeholder="Search for..."></input>
                      <span className="input-group-btn">
                        <button className="btn btn-primary" type="submit">
                          <FaSearch />
                        </button>
                      </span>
                    </div>
                  </div>
                </div>
                <div className="card my-4 border-0 shadow-sm">
                  <h5 className="card-header">Rent</h5>
                  <div className="card-body">
                    <form onSubmit={this.handleRental}>
                      <div className="form-group">
                        <label htmlFor="exampleFormControlInput1">ISBN</label>
                        <input
                          type="text"
                          className="form-control"
                          id="book.isbn"
                          name="book.isbn"
                          onChange={this.handleRentalChange}
                          placeholder="9788375781557"></input>
                      </div>
                      <div className="form-group">
                        <label htmlFor="exampleFormControlInput1">
                          Customer ID
                        </label>
                        <input
                          type="text"
                          className="form-control"
                          id="customer.id"
                          name="customer.id"
                          onChange={this.handleRentalChange}
                          placeholder="1"></input>
                      </div>
                      <button
                        className="btn btn-primary btn-block"
                        type="submit">
                        Rent
                      </button>
                    </form>
                  </div>
                </div>

                <div className="card my-4 border-0 shadow-sm">
                  <h5 className="card-header">Return</h5>
                  <div className="card-body">
                    <form onSubmit={this.handleReturn}>
                      <div className="form-group">
                        <label htmlFor="exampleFormControlInput1">ISBN</label>
                        <input
                          type="text"
                          className="form-control"
                          id="book.isbn"
                          name="book.isbn"
                          onChange={this.handleReturnChange}
                          placeholder="9788375781557"></input>
                      </div>
                      <button
                        className="btn btn-primary btn-block"
                        type="submit">
                        Return
                      </button>
                    </form>
                  </div>
                </div>

                <button
                  type="button"
                  className="btn btn-primary btn-block"
                  data-toggle="modal"
                  data-target="#addBook">
                  Add book
                </button>
                <div
                  className="modal fade"
                  id="addBook"
                  tabIndex="-1"
                  role="dialog"
                  aria-labelledby="exampleModalLabel"
                  aria-hidden="true">
                  <div className="modal-dialog" role="document">
                    <div className="modal-content">
                      <div className="modal-header">
                        <h5 className="modal-title" id="newBook">
                          New book
                        </h5>
                        <button
                          type="button"
                          className="close"
                          data-dismiss="modal"
                          aria-label="Close">
                          <span aria-hidden="true">&times;</span>
                        </button>
                      </div>
                      <div className="modal-body">
                        <form id="newBookForm" onSubmit={this.handleSubmit}>
                          <div className="form-group">
                            <label htmlFor="exampleFormControlInput1">
                              ISBN
                            </label>
                            <input
                              type="text"
                              className="form-control"
                              id="isbn"
                              name="isbn"
                              onChange={this.handleChange}
                              placeholder="9788375781557"></input>
                          </div>
                          <div className="form-group">
                            <label htmlFor="exampleFormControlInput1">
                              Title
                            </label>
                            <input
                              type="text"
                              className="form-control"
                              id="title"
                              name="title"
                              onChange={this.handleChange}
                              placeholder="Ostatnie życzenie"></input>
                          </div>
                          <div className="form-group">
                            <label htmlFor="exampleFormControlInput1">
                              Author
                            </label>
                            <input
                              type="text"
                              className="form-control"
                              id="author"
                              name="author"
                              onChange={this.handleChange}
                              placeholder="Andrzej Sapkowski"></input>
                          </div>
                          <div className="form-group">
                            <label htmlFor="exampleFormControlInput1">
                              Publisher
                            </label>
                            <input
                              type="text"
                              className="form-control"
                              id="publisher"
                              name="publisher"
                              onChange={this.handleChange}
                              placeholder="superNOWA"></input>
                          </div>
                          <div className="form-group form-check">
                            <input
                              type="checkbox"
                              className="form-check-input"
                              id="status"
                              name="status"
                              value="AVAILABLE"
                              onChange={this.handleChange}></input>
                            <label
                              className="form-check-label"
                              htmlFor="exampleCheck1">
                              Available
                            </label>
                          </div>
                          <div className="form-group">
                            <label htmlFor="exampleFormControlTextarea1">
                              Description
                            </label>
                            <textarea
                              className="form-control"
                              id="description"
                              name="description"
                              onChange={this.handleChange}
                              rows="5"></textarea>
                          </div>
                        </form>
                      </div>
                      <div className="modal-footer">
                        <button
                          type="button"
                          className="btn btn-secondary"
                          data-dismiss="modal">
                          Cancel
                        </button>
                        <button
                          type="submit"
                          form="newBookForm"
                          className="btn btn-primary">
                          <FaPlus /> {" Add"}
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="col-md-9">
                <div className="card my-4 border-0 shadow-sm">
                  <div className="table-responsive">
                    <table className="table table-hover table-borderless">
                      <thead className="thead">
                        <tr>
                          <th scope="col">Title</th>
                          <th scope="col">Author</th>
                          <th scope="col">Publisher</th>
                          <th scope="col">ISBN</th>
                          <th scope="col">Status</th>
                          <th scope="col" />
                        </tr>
                      </thead>
                      <tbody>
                        {books.map(book => (
                          <tr key={book.id}>
                            <td>{book.title}</td>
                            <td>{book.author}</td>
                            <td>{book.publisher}</td>
                            <td>{book.isbn}</td>
                            <td>{book.status}</td>
                            <td>
                              <button
                                className="btn btn-danger"
                                onClick={() => this.remove(book.id)}>
                                Delete
                              </button>
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                    <ul></ul>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      );
  }
}

export default Books;
