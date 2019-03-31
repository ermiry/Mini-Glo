import React, { Component } from 'react';

// Redux
import { Provider } from 'react-redux';
import myStore from './myStore';

// Layout components
import Header from './components/layout/Header';
import Main from './components/layout/Main';
import Footer from './components/layout/Footer';

// my actions
import { setToken } from './actions/authActions';

import './App.css';

// check for token
if (localStorage.token) {
	myStore.dispatch(setToken ());
}

class App extends Component {

  render() {
	return (
		<Provider store= { myStore }>
			<div className="App">
				<Header />
				
				<div className="container">
					<Main />	
				</div>

				<Footer />
			</div>
		</Provider>
	);
  }

}

export default App;