import React, { Component } from "react";
import "./navBar.css";

class NavBar extends Component {
  state = {};
  render() {
    return (
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
        <a className="navbar-brand" href="/">
          BookRental
        </a>
        <button
          className="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ml-auto">
            <li className="nav-item">
              <a className="nav-link" href="/">
                Home
              </a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/books">
                Books
              </a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/customers">
                Customers
              </a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/rentals">
                Rentals
              </a>
            </li>
          </ul>
        </div>
      </nav>
    );
  }
}

export default NavBar;
