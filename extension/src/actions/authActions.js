import axios from 'axios';
import { SET_TOKEN } from './types';

export const authorize = () => dispatch => {

	localStorage.setItem ('token', 'test');
	dispatch (setToken ());

};

// set the current token
export const setToken = () => {

	return {
		type: SET_TOKEN,
		payload: null
	};

};