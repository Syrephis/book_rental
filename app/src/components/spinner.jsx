import React, { Component } from "react";
import "./spinner.css";

class Spinner extends Component {
  state = {};
  render() {
    return (
      <div className="d-flex justify-content-center spinner">
        <div className="spinner-border" role="status">
          <span className="sr-only">Loading...</span>
        </div>
      </div>
    );
  }
}

export default Spinner;
