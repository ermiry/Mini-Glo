import isEmpty from '../validation/is-empty';

import { SET_CURRENT_USER, SET_TOKEN } from '../actions/types';

const initialState = {
	isAuthenticated: false,
	user: {}
};

export default function (state = initialState, action) {
	switch (action.type) {
		case SET_TOKEN:
			return {
				...state,
				isAuthenticated: true
			};

		case SET_CURRENT_USER:
			return {
				...state,
				isAuthenticated: !isEmpty (action.payload),
				user: action.payload
			};

		default: return state;
	}
}
