import React, { Component } from "react";
import Spinner from "./spinner";
import NavBar from "./navBar";
import { FaSearch, FaCheck } from "react-icons/fa";

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
                  <h5 class="card-header">
                    <a
                      class="collapsed d-block"
                      data-toggle="collapse"
                      href="#collapse-collapsed"
                      aria-expanded="true"
                      aria-controls="collapse-collapsed"
                      id="heading-collapsed">
                      <i class="fa fa-chevron-down pull-right"></i>
                      Add a customer
                    </a>
                  </h5>
                  <div
                    id="collapse-collapsed"
                    class="collapse"
                    aria-labelledby="heading-collapsed">
                    <div className="card-body">
                      <form>
                        <div class="form-group">
                          <label for="exampleFormControlInput1">
                            First Name
                          </label>
                          <input
                            type="email"
                            className="form-control"
                            id="exampleFormControlInput1"
                            placeholder="Adam"></input>
                        </div>
                        <div className="form-group">
                          <label for="exampleFormControlInput1">
                            Last Name
                          </label>
                          <input
                            type="email"
                            className="form-control"
                            id="exampleFormControlInput1"
                            placeholder="Smith"></input>
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
                          <th scope="col">First name</th>
                          <th scope="col">Last name</th>
                          <th scope="col">Balance $</th>
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
