import axios from 'axios';

import {
    GET_ERRORS,
	CLEAR_ERRORS,

    SET_CURRENT_USER,
    GET_USER
} from './types';

export const getUser = () => dispatch => {

    dispatch (clearErrors ());
    axios.get ('/api/mini-glo/user')
        .then (res => {
            dispatch ({
                type: GET_USER,
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

export const clearErrors = () => {
	return {
		type: CLEAR_ERRORS
	};
};
