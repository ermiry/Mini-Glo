import { combineReducers } from 'redux';

// my reducers
import errorReducer from './errorReducer';

export default combineReducers ({

    errors: errorReducer

});