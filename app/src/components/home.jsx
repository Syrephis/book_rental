import React, { Component } from "react";
import NavBar from "./navBar";
import "./home.css";
class Home extends Component {
  state = {};
  render() {
    return (
      <div>
        <NavBar />
        <div>
          <h1 className="welcome">Welcome to the Book Rental System</h1>
        </div>
      </div>
    );
  }
}

export default Home;
