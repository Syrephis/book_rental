import React, { Component } from "react";
import Spinner from "./spinner";
import NavBar from "./navBar";

class Books extends Component {
  //private
  state = {
    isLoading: true,
    books: []
  };

  async componentDidMount() {
    /*
    const response = await fetch("/books");
    const body = await response.json();
    this.setState({ books: body, isLoading: false });
    */
    fetch("/books")
      .then(response => response.json())
      .then(json => {
        this.setState({ books: json, isLoading: false });
      });
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
          <div className="container">
            <div className="row">
              <div className="col-md-4">
                <div className="card my-4">
                  <h5 className="card-header">Search</h5>
                  <div className="card-body">
                    <div className="input-group">
                      <input
                        type="text"
                        class="form-control"
                        placeholder="Search for..."></input>
                      <span class="input-group-btn">
                        <button class="btn btn-secondary" type="button">
                          Go!
                        </button>
                      </span>
                    </div>
                  </div>
                </div>
              </div>
              <div className="col-md-8">
                <div className="card my-4">
                  <div className="table-responsive">
                    <table className="table">
                      <thead className="thead-light">
                        <tr>
                          <th scope="col">Title</th>
                          <th scope="col">Author</th>
                          <th scope="col">Publisher</th>
                          <th scope="col">ISBN</th>
                          <th scope="col">Status</th>
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
