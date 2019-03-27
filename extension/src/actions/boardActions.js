import axios from 'axios';

import {
    GET_ERRORS,
	CLEAR_ERRORS,

	GET_BOARDS,
	GET_BOARD,
	ADD_BOARD,
	DELETE_BOARD,
	BOARD_LOADING
} from './types';

// FIXME: we also need to edit a board!!

export const getBoards = () => dispatch => {

	dispatch (setBoardLoading ());
	axios.get ('/api/mini-glo/boards')
		.then (res => 
			dispatch ({
				type: GET_BOARDS,
				payload: res.data
			})
		)
		.catch (err => {
			dispatch ({
				type: GET_ERRORS,
				payload: null
			})
		});

};

export const addBoard = boardData => dispatch => {

	dispatch (clearErrors ());
	axios.post ('/api/mini-glo/boards', boardData)
		.then (res => {
			dispatch ({
				type: ADD_BOARD,
				payload: res.data
			})
		})
		.catch (err => {
			dispatch ({
				type: GET_ERRORS,
				payload: err.response.data
			})
		});

};

export const getBoard = id => dispatch => {

	dispatch (setBoardLoading ());
	axios.get ('/api/mini-glo/boards/' + id)
		.then (res => 
			dispatch ({
				type: GET_BOARD,
				payload: res.data
			})
		)
		.catch (err => 
			dispatch ({
				type: GET_BOARD,
				payload: null
			})
		);

};

export const deleteBoard = id => dispatch => {

	axios.delete ('/api/mini-glo/boards/' + id)
		.then (res =>
			dispatch ({
				type: DELETE_BOARD,
				payload: null
			})
		)
		.catch (err => 
			dispatch ({
				type: GET_ERRORS,
				payload: err.response.data
			})
		);

};

export const clearErrors = () => {
	return {
		type: CLEAR_ERRORS
	};
};

export const setBoardLoading = () => {
	return {
		type: BOARD_LOADING
	};
};
