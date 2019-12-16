import React, { Component } from "react";
import Spinner from "./spinner";
import NavBar from "./navBar";
import { FaSearch, FaPlus } from "react-icons/fa";

class Customers extends Component {
  emptyCustomer = {
    firstName: null,
    lastName: null
  };

  state = {
    isLoading: true,
    customers: [],
    customer: this.emptyCustomer
  };

  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      customers: [],
      customer: this.emptyCustomer
    };

    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  async componentDidMount() {
    await fetch("/customers")
      .then(response => response.json())
      .then(json => {
        this.setState({ customers: json, isLoading: false });
      });
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;

    let customer = { ...this.state.customer };
    customer[name] = value;
    this.setState({ customer });
  }

  async handleSubmit(event) {
    event.preventDefault();
    const customer = this.state.customer;

    await fetch("/customers", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
      },
      body: JSON.stringify(customer)
    });
    console.log(JSON.stringify(customer));
    //this.props.history.push("/books");
    window.location.reload();
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
                        <button className="btn btn-primary" type="button">
                          <FaSearch />
                        </button>
                      </span>
                    </div>
                  </div>
                </div>
                <button
                  type="button"
                  class="btn btn-primary btn-block"
                  data-toggle="modal"
                  data-target="#exampleModal">
                  Add customer
                </button>
                <div
                  className="modal fade"
                  id="exampleModal"
                  tabindex="-1"
                  role="dialog"
                  aria-labelledby="exampleModalLabel"
                  aria-hidden="true">
                  <div className="modal-dialog" role="document">
                    <div className="modal-content">
                      <div className="modal-header">
                        <h5 className="modal-title" id="exampleModalLabel">
                          New customer
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
                        <form id="newCustomerForm" onSubmit={this.handleSubmit}>
                          <div className="form-group">
                            <label htmlFor="exampleFormControlInput1">
                              First name
                            </label>
                            <input
                              type="text"
                              className="form-control"
                              id="firstName"
                              name="firstName"
                              onChange={this.handleChange}
                              placeholder="Adam"></input>
                          </div>
                          <div className="form-group">
                            <label htmlFor="exampleFormControlInput1">
                              Last name
                            </label>
                            <input
                              type="text"
                              className="form-control"
                              id="lastName"
                              name="lastName"
                              onChange={this.handleChange}
                              placeholder="Nowak"></input>
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
                          form="newCustomerForm"
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
                            <td>{customer.account.toFixed(2)}</td>
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
