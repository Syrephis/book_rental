import React, { Component } from "react";
import Spinner from "./spinner";
import NavBar from "./navBar";

class Customers extends Component {
  state = {
    isLoading: true,
    customers: []
  };

  async componentDidMount() {
    fetch("/customers")
      .then(response => response.json())
      .then(json => {
        this.setState({ customers: json, isLoading: false });
      });
  }

  render() {
    const { customers, isLoading } = this.state;
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
                          <th scope="col">First name</th>
                          <th scope="col">Last name</th>
                          <th scope="col">Due $</th>
                        </tr>
                      </thead>
                      <tbody>
                        {customers.map(customer => (
                          <tr key={customer.id}>
                            <td>{customer.firstName}</td>
                            <td>{customer.lastName}</td>
                            <td>{customer.account}</td>
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

export default Customers;
