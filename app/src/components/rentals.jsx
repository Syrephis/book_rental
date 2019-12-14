import React, { Component } from "react";
import Spinner from "./spinner";
import NavBar from "./navBar";
import { FaSearch } from "react-icons/fa";

class Rentals extends Component {
  state = {
    isLoading: true,
    rentals: []
  };

  async componentDidMount() {
    await fetch("/rentals")
      .then(response => response.json())
      .then(json => {
        this.setState({ rentals: json, isLoading: false });
      });
  }

  render() {
    const { rentals, isLoading } = this.state;
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
                        class="form-control"
                        placeholder="Search for..."></input>
                      <span class="input-group-btn">
                        <button class="btn btn-primary" type="button">
                          <FaSearch />
                        </button>
                      </span>
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
                          <th scope="col">Reader</th>
                          <th scope="col">Book</th>
                          <th scope="col">Rental Date</th>
                          <th scope="col">Return Date</th>
                          <th scope="col">Actual Return</th>
                        </tr>
                      </thead>
                      <tbody>
                        {rentals.map(rental => (
                          <tr key={rental.id}>
                            <td>
                              {rental.customer.firstName}{" "}
                              {rental.customer.lastName}
                            </td>
                            <td>
                              {rental.book.author}
                              {" -"} {rental.book.title}
                            </td>
                            <td>{rental.issueDate}</td>
                            <td>{rental.predictedReturnDate}</td>
                            <td>{rental.returnDate}</td>
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

export default Rentals;
