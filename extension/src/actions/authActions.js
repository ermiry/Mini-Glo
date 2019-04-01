import { SET_TOKEN } from './types';

export const authorize = (token) => dispatch => {

	localStorage.setItem ('token', token);
	dispatch (setToken ());

};

// set the current token
export const setToken = () => {

	return {
		type: SET_TOKEN,
		payload: null
	};

};