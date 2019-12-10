import React, { Component } from "react";
import Spinner from "./spinner";
import NavBar from "./navBar";
import { FaSearch, FaCheck, FaChevronDown } from "react-icons/fa";

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

  toggleAlerts() {
    this.setState({
      visible: !this.state.visible
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
          <div className="container-xl">
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
                        <button className="btn btn-primary" type="button">
                          <FaSearch />
                        </button>
                      </span>
                    </div>
                  </div>
                </div>
                <div className="card my-4 border-0 shadow-sm">
                  <h5 className="card-header">Rent</h5>
                  <div className="card-body">
                    <div className="input-group">
                      <input
                        type="text"
                        className="form-control"
                        placeholder="ISBN"></input>
                      <span className="input-group-btn">
                        <button className="btn btn-primary" type="button">
                          <FaCheck />
                        </button>
                      </span>
                    </div>
                  </div>
                </div>
                <div className="card my-4 border-0 shadow-sm">
                  <h5 class="card-header">
                    <a
                      class="collapsed d-block"
                      data-toggle="collapse"
                      href="#collapse-collapsed"
                      aria-expanded="true"
                      aria-controls="collapse-collapsed"
                      id="heading-collapsed">
                      <i class="fa fa-chevron-down pull-right"></i>
                      Add a book
                    </a>
                  </h5>
                  <div
                    id="collapse-collapsed"
                    class="collapse"
                    aria-labelledby="heading-collapsed">
                    <div className="card-body">
                      <form>
                        <div class="form-group">
                          <label for="exampleFormControlInput1">ISBN</label>
                          <input
                            type="email"
                            className="form-control"
                            id="exampleFormControlInput1"
                            placeholder="9788375781557"></input>
                        </div>
                        <div className="form-group">
                          <label for="exampleFormControlInput1">Title</label>
                          <input
                            type="email"
                            className="form-control"
                            id="exampleFormControlInput1"
                            placeholder="Ostatnie życzenie"></input>
                        </div>
                        <div className="form-group">
                          <label for="exampleFormControlInput1">Author</label>
                          <input
                            type="email"
                            className="form-control"
                            id="exampleFormControlInput1"
                            placeholder="Andrzej Sapkowski"></input>
                        </div>
                        <div className="form-group">
                          <label for="exampleFormControlInput1">
                            Publisher
                          </label>
                          <input
                            type="email"
                            className="form-control"
                            id="exampleFormControlInput1"
                            placeholder="superNOWA"></input>
                        </div>
                        <div className="form-group form-check">
                          <input
                            type="checkbox"
                            className="form-check-input"
                            id="exampleCheck1"></input>
                          <label
                            className="form-check-label"
                            for="exampleCheck1">
                            Available
                          </label>
                        </div>
                        <button type="submit" className="btn btn-primary">
                          Add
                        </button>
                      </form>
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
