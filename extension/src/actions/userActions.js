import axios from 'axios';

import {
    GET_ERRORS,
	CLEAR_ERRORS,

    SET_CURRENT_USER,
    GET_USER
} from './types';

export const getUser = () => dispatch => {

    dispatch (clearErrors ());
    axios.get ('http://ermiry.com/api/mini-glo/user?token=' + localStorage.token)
        .then (res => {
            console.log (res.data);
            dispatch ({
                type: SET_CURRENT_USER,
                payload: res.data
            })
        })
        .catch (err => {
            console.log (err.response.data);
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
