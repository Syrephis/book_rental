import React, { Component } from "react";
import Spinner from "./spinner";
import NavBar from "./navBar";

class Rentals extends Component {
  state = {
    isLoading: true,
    rentals: []
  };

  async componentDidMount() {
    fetch("/rentals")
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
          <div className="container border rounded entity-table">
            <div className="table-responsive">
              <table className="table">
                <thead className="thead-light">
                  <tr>
                    <th scope="col">Reader</th>
                    <th scope="col">Book</th>
                    <th scope="col">Rented on</th>
                    <th scope="col">Return date</th>
                    <th scope="col">Actuall return date</th>
                  </tr>
                </thead>
                <tbody>
                  {rentals.map(rental => (
                    <tr key={rental.id}>
                      <td>
                        {rental.customer.firstName} {rental.customer.lastName}
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
      );
  }
}

export default Rentals;
