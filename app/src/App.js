import React, { Component } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import "./App.css";
import Books from "./components/books";
import Home from "./components/home";
import Customers from "./components/customers";
import Rentals from "./components/rentals";

class App extends Component {
  state = {};
  render() {
    return (
      <Router>
        <Switch>
          <Route path="/" exact={true} component={Home} />
          <Route path="/books" exact={true} component={Books} />
          <Route path="/customers" exact={true} component={Customers} />
          <Route path="/rentals" exact={true} component={Rentals} />
        </Switch>
      </Router>
    );
  }
}

export default App;
