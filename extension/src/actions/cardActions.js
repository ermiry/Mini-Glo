import axios from 'axios';

import {
    GET_ERRORS,
	CLEAR_ERRORS,
    ADD_CARD,
    GET_CARD,
    DELETE_CARD,
    GET_CARDS,
    GET_COMMENTS,
    DELETE_COMMENT,

} from './types';

// FIXME: add action to edit a card

// Gets a list of cards for a board
export const getCards = boardID => dispatch => {

    dispatch (clearErrors ());
    axios.get ('/api/mini-glo/boards/' + boardID + '/cards')
        .then (res => {
            dispatch ({
                type: GET_CARDS,
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

// create a new card
export const addCard = (boardID, cardData) => dispatch => {

    dispatch (clearErrors ());
    axios.post ('/api/mini-glo/boards/' + boardID + '/cards', cardData)
        .then (res => {
            dispatch ({
                type: ADD_CARD,
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

// gets a card by id
// /boards/:board_id/cards/:card_id
export const getCard = (boardID, cardID) => dispatch => {

    dispatch (clearErrors ());
    axios.get ('/api/mini-glo/boards/' + boardID + '/cards/' + cardID)
        .then (res => {
            dispatch ({
                type: GET_CARD,
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

// deletes a card
export const deleteCard = (boardID, cardID) => dispatch => {

    dispatch (clearErrors ());
    axios.delete ('/api/mini-glo/boards/' + boardID + '/cards/' + cardID)
        .then (res => {
            dispatch ({
                type: DELETE_CARD,
                payload: null       // FIXME: do we need to return the ids? -> check devconnector
            })
        })
        .catch (err => {
            dispatch ({
                type: GET_ERRORS,
                payload: err.response.data
            })
        });

};

// '/boards/:board_id/columns/:column_id/cards'
// gets a list of cards for a column
export const getCards = (boardID, columnID) => dispatch => {

    dispatch (clearErrors ());
    axios.get ('/api/mini-glo/boards/' + boardID + '/columns/' + columnID + '/cards')
        .then (res => {
            dispatch ({
                type: GET_CARDS,
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

/*** COMMENTS ***/

// FIXME: add action to edit a comment

// Gets a list of comments for a card
export const getComments = (boardID, cardID) => dispatch => {

    dispatch (clearErrors ());
    axios.get ('/api/mini-glo/boards/' + boardID + '/cards/' + cardID + '/comments')
        .then (res => {
            dispatch ({
                type: GET_COMMENTS,
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

// Creates a new comment in a card
export const addComment = (boardID, cardID) => dispatch => {

    dispatch (clearErrors ());
    axios.post ('/api/mini-glo/boards/' + boardID + '/cards/' + cardID + '/comments')
        .then (res => {
            dispatch ({
                type: ADD_COMMENT,
                payload: res.data
            })
        })
        .catch (err => {
            dispatch ({
                type: GET_ERRORS,
                payload: err.response.data
            })
        });

}

// deletes a comment from a card
export const deleteComment = (boardID, cardID, commentID) => dispatch => {

    dispatch (clearErrors ());
    axios.delete ('/api/mini-glo/boards/' + boardID + '/cards/' + cardID + '/comments/' + commentID)
        .then (res => {
            dispatch ({
                type: DELETE_COMMENT,
                payload: null       // FIXME: do we need to return the ids? -> check devconnector
            })
        })
        .catch (err => {
            dispatch ({
                type: GET_ERRORS,
                payload: err.response.data
            })
        })

}; 

export const clearErrors = () => {
	return {
		type: CLEAR_ERRORS
	};
};