import axios from 'axios';

import {
    GET_ERRORS,
	CLEAR_ERRORS,

    COLUMN_LOADING,
    ADD_COLUMN,
    DELETE_COLUMN
} from './types';

// FIXME: we also need to edit a column

export const addColumn = (boradID, columnData) => dispatch => {

    dispatch (clearErrors ());
    axios.post ('/api/mini-glo/boards/' + boradID + '/columns', columnData)
        .then (res => {
            dispatch ({
                type: ADD_COLUMN,
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

export const deleteColumn = (boardID, columnID) => dispatch => {

    axios.delete ('/api/mini-glo/boards/' + boardID + '/columns/' + columnID)
        .then (res => {
            dispatch ({
                type: DELETE_COLUMN,
                payload: null
            })
        })
        .catch (err => {
            dispatch ({
                type: GET_ERRORS,
                payload: err.response.data
            })
        });

};

export const clearErrors = () => {
	return {
		type: CLEAR_ERRORS
	};
};