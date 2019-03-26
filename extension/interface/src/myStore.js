import { createStore, applyMiddleware } from 'redux';

import thunk from 'redux-thunk';

// my reducers
import rootReducer from './reducers';

let initialState = {};

const middleware = [thunk];

const myStore = createStore (
    rootReducer, 
    initialState, 
    applyMiddleware (...middleware)    
);   

export default myStore;