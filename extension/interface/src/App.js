import React, { Component } from 'react';

// react router
import { BrowserRouter as Router, Route, Switch }  from 'react-router-dom';

// Redux
import { Provider } from 'react-redux';
import myStore from './myStore';

// Layout components
import Header from './components/layout/Header';
import Landing from './components/layout/Landing';
import Footer from './components/layout/Footer';

import './App.css';

class App extends Component {

  render() {
    return (
		<Provider store= { myStore }>
		<Router>
			<div className="App">
				<Header />
				{/* <Route exact path='/' component={ Landing } /> */}
				<Landing />

				<div className="container">
					{/* <Route exact path="/register" component={ Register } />
					<Route exact path="/login" component={ Login } /> */}

					{/* <Switch>
						<PrivateRoute exact path="/profile" component={ Profile } />
					</Switch> */}
				</div>

				<Footer />
			</div>
		</Router>
	</Provider>
    );
  }

}

export default App;