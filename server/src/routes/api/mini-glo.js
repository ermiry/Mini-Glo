const express = require ('express');
const router = express.Router ();

// const axios = require ('axios');
const request = require ('superagent');

const gloapiurl = 'https://gloapi.gitkraken.com/v1/glo/';

/*** TEST ***/

// @route   GET api/mini-glo/test
// @desc    Tests mini glo route
router.get ('/test', (req, res) => res.status (200).json ({ msg: 'Mini-Glo Works' }));

// @route   POST api/mini-glo/test
// @desc    Tests mini glo route
router.post ('/test', (req, res) => {

    let msg = req.body.msg;
    console.log (msg);
    return res.status (200).json ({ status: 'Success' });

});

/*** GITKRAKEN OAUTH ***/

// @route   GET api/mini-glo/oauth
// @desc    Handles mini glo oauth with gitkraken
router.get ('/oauth', (req, res) => {

	const { code } = req;
	if (!code) {
		res.send ({
			succes: false,
			message: 'Error: no code'
		});
	}

	request.post ('https://api.gitkraken.com/oauth/access_token')
		.set ('Accept', 'application/json')
		.send ({
			grant_type: process.env.grant_type_gk,
			client_id: process.env.client_id_gk,
			client_secret: process.env.client_secret_gk,
			code: code
		})
		.then (result => {
			res.status (200).json ({msg: 'success'});
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.board = 'Faild gitkraken oauth.';
        	return res.status (400).json (errors); 
		});

});

// @route   GET api/mini-glo/authorize
// @desc    Handles gitkraken authentication
router.get ('/authorize', (req, res) => {

	request.get ("https://app.gitkraken.com/oauth/authorize");
	console.log ("hola gitkraken");
	
});

/*** BOARDS ***/

// @route   GET /boards
// @desc    Get a list of boards
router.get ('/boards', (req, res) => {

	let token = req.query.token;

	request.get (gloapiurl + 'boards')
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.then (result => {
			if (result.status === 200) return res.status (200).json (result.body);
			else {
				let errors = {};
				errors.board = 'Failed to get boards.';
				return res.status (400).json (errors); 
			}
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.board = 'Failed to get boards.';
			return res.status (400).json (errors); 
		});

});

// @route   POST /boards
// @desc    Creates a new board
router.post ('/boards', (req, res) => {

	let token = req.body.token;

	request.post (gloapiurl + 'boards')
		.auth (req.body.token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.send ({ name: req.body.boardName })
		.then (result => {
			if (result.status === 201) return res.status (200).json (result.body);
			else {
				let errors = {};
				errors.board = 'Failed to create board.';
				return res.status (400).json (errors); 
			}
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.board = 'Failed to create board.';
        	return res.status (400).json (errors); 
		});

});

// @route   GET /boards/board_id
// @desc    Gets a board by ID
router.get ('/boards/:board_id', (req, res) => {

	let token = req.query.token;

	request.get (gloapiurl + 'boards/' + req.params.board_id)
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.send ({ fields:['name', 'columns', 'labels'] })	// we select what info we need
		.then (result => {
			if (result.status === 200) return res.status (200).json (result.body);
			else {
				let errors = {};
				errors.board = 'Failed to get board.';
				return res.status (400).json (errors); 
			}
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.board = 'Failed to get board.';
        	return res.status (400).json (errors); 
		});

});

// @route   POST /boards/board_id
// @desc    Edits a board by id
router.post ('/boards/:board_id', (req, res) => {

	let token = req.body.token;

	request.post (gloapiurl + 'boards/' + req.params.board_id)
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.send ({ name })		// we can only edit the name
		.then (result => {
			if (result.status === 200) return res.status (200).json (result.body);
			else {
				let errors = {};
				errors.board = 'Failed to get boards.';
				return res.status (400).json (errors); 
			}
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.board = 'Failed to edit board.';
        	return res.status (400).json (errors); 
		});

});

// @route   DELETE /boards/board_id
// @desc    Deletes a board by id
router.delete ('/boards/:board_id', (req, res) => {

	let token = req.query.token;

	request.delete (gloapiurl + 'boards/' + req.params.board_id)
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.then (result => {
			if (result.status === 204) return res.status (200).json ({ status: 200 });
			else {
				let errors = {};
				errors.board = 'Failed to delete board.';
				return res.status (400).json (errors); 
			}
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.board = 'Failed to delete board.';
			return res.status (400).json (errors);
		});

});

/*** COLUMNS ***/

// @route   POST /boards/board_id/columns
// @desc    Creates a new column in a board
router.post ('/boards/:board_id/columns', (req, res) => {

	let token = req.body.token;

	request.post (gloapiurl + 'boards/' + req.params.board_id + 'columns')
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.send ({ name: req.body.name, position: 0 })
		.then (result => {
			if (result.status === 201) return res.status (200).json (result.body);
			else {
				let errors = {};
				errors.board = 'Failed to create column.';
				return res.status (400).json (errors); 
			}
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.column = 'Failed to create column.';
			return res.status (400).json (errors);
		});

});

// @route   POST /boards/board_id/columns/column_id
// @desc    Edits a column by id
router.post ('/boards/:board_id/columns/:column_id', (req, res) => {

	let token = req.body.token;

	request.post (gloapiurl + 'boards/' + req.params.board_id + '/columns/' + req.params.column_id)
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.send ({ name: req.body.name, position: req.body.position })
		.then (result => {
			if (result.status === 200) return res.status (200).json (result.body);
			else {
				let errors = {};
				errors.board = 'Failed to edit column.';
				return res.status (400).json (errors); 
			}
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.column = 'Failed to edit column.';
			return res.status (400).json (errors);
		});

});

// @route   DELETE /boards/board_id/columns/column_id
// @desc    Deletes a column by id
router.delete ('/boards/:board_id/columns/:column_id', (req, res) => {

	let token = req.query.token;

	request.delete (gloapiurl + 'boards/' + req.params.board_id + '/columns/' + req.params.column_id)
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.then (result => {
			if (result.status === 204) return res.status (200).json ({ status: 200 });
			else {
				let errors = {};
				errors.board = 'Failed to delete column.';
				return res.status (400).json (errors); 
			}
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.column = 'Failed to delete column.';
			return res.status (400).json (errors);
		});

});


/*** CARDS ***/

// @route   GET /boards/{board_id}/cards
// @desc    Gets a list of cards for a board
router.get ('/boards/:board_id/cards', (req, res) => {

	let token = req.query.token;

	request.get (gloapiurl + 'boards/' + req.params.board_id + '/cards')
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.then (result => {
			if (result.status === 200) return res.status (200).json (result.body);
			else {
				let errors = {};
				errors.board = 'Failed to edit column.';
				return res.status (400).json (errors); 
			}	
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.card = 'Failed to get cards.';
			return res.status (400).json (errors);
		});

});

// @route   POST /boards/{board_id}/cards
// @desc    Creates a new card in a column
router.post ('/boards/:board_id/cards', (req, res) => {

	let token = req.body.token;

	request.post (gloapiurl + 'boards/' + req.params.board_id + '/cards')
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.send ({})		// FIXME: send the correct data
		.then (result => {
			if (result.status === 201) return res.status (200).json (result.body);
			else {
				let errors = {};
				errors.board = 'Failed to create card.';
				return res.status (400).json (errors); 
			}	
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.card = 'Failed to create card.';
			return res.status (400).json (errors);
		});

});

// @route   GET /boards/{board_id}/cards/{card_id}
// @desc    Gets a card by ID
router.get ('/boards/:board_id/cards/:card_id', (req, res) => {

	let token = req.query.token;

	request.get (gloapiurl + 'boards/' + req.params.board_id + '/cards/' + req.params.card_id)
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.then (result => {
			if (result.status === 200) return res.status (200).json (result.body);
			else {
				let errors = {};
				errors.board = 'Failed to get card.';
				return res.status (400).json (errors); 
			}
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.card = 'Failed to get card.';
			return res.status (400).json (errors);
		});

});

// @route   POST /boards/{board_id}/cards/{card_id}
// @desc    Edits a card
router.post ('/boards/:board_id/cards/:card_id', (req, res) => {

	let token = req.body.token;

	request.post (gloapiurl + 'boards/' + req.params.board_id + '/cards/' + req.params.card_id)
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.send ({})	// FIXME: send the data
		.then (result => {
			if (result.status === 200) return res.status (200).json (result.body);
			else {
				let errors = {};
				errors.board = 'Failed to edit card.';
				return res.status (400).json (errors); 
			}	
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.card = 'Failed to edit card.';
			return res.status (400).json (errors);
		});

});

// @route   DELETE /boards/{board_id}/cards/{card_id}
// @desc    Deletes a card
router.delete ('/boards/:board_id/cards/:card_id', (req, res) => {

	let token = req.query.token;

	request.delte (gloapiurl + 'boards/' + req.params.board_id + '/cards/' + req.params.card_id)
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.then (result => {
			if (result.status === 204) return res.status (200).json ({ status: 200 });
			else {
				let errors = {};
				errors.board = 'Failed to delete card.';
				return res.status (400).json (errors); 
			}
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.card = 'Failed to delete card.';
			return res.status (400).json (errors);
		});

});

// @route   GET /boards/{board_id}/columns/{column_id}/cards
// @desc    Gets a list of cards for a column
router.get ('/boards/:board_id/columns/:column_id/cards', (req, res) => {

	let token = req.query.token;

	request.get (gloapiurl + 'boards/' + req.params.board_id + '/columns/' + req.params.column_id + '/cards')	
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.then (result => {
			if (result.status === 200) return res.status (200).json (result.body);
			else {
				let errors = {};
				errors.board = 'Failed to get cards.';
				return res.status (400).json (errors); 
			}
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.card = 'Failed to get cards.';
			return res.status (400).json (errors);
		});

});

/*** ATTACHMENTS ***/

// @route   GET /boards/{board_id}/cards/{card_id}/attachments
// @desc    Gets a list of attachments for a card
router.get ('/boards/:board_id/cards/:card_id/attachments', (req, res) => {

	let token = req.query.token;

	request.get (gloapiurl + 'boards/' + req.params.board_id + '/cards/' + req.params.card_id + '/attachements')
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.then (result => {
			if (result.status === 200) return res.status (200).json (result.body);
			else {
				let errors = {};
				errors.board = 'Failed to get attachements.';
				return res.status (400).json (errors); 
			}
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.card = 'Failed to get attachements.';
			return res.status (400).json (errors);
		});

});

// FIXME:
/* After making this call, you must make another call to either update the card's description 
or add/update one of the card's comments and include the attachment url (in markdown format) in the text.
The format must be [ANY_TEXT](ATTACHMENT_URL).
If the attachment url is not in the card description or one of its comments within 1 hour of the attachment being created, it will be deleted. */

// @route   POST /boards/{board_id}/cards/{card_id}/attachments
// @desc    Creates an attachment for a card
router.post ('/boards/:board_id/cards/:card_id/attachments', (req, res) => {

	let token = req.body.token;

	request.post (gloapiurl + 'boards/' + req.params.board_id + '/cards/' + req.params.card_id + '/attachements')
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.send ({})		// FIXME: send the file
		.then (result => {
			// TODO: check errors and send back the json
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.card = 'Failed to create attachement.';
			return res.status (400).json (errors);
		});

});

/*** COMMENTS ***/

// @route   GET /boards/{board_id}/cards/{card_id}/comments
// @desc    Gets a list of comments for a card
router.get ('/boards/:board_id/cards/:card_id/comments', (req, res) => {

	let token = req.query.token;

	request.get (gloapiurl + 'boards/' + req.params.board_id + '/cards/' + req.params.card_id + '/comments')
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.then (result => {
			if (result.status === 200) return res.status (200).json (result.body);
			else {
				let errors = {};
				errors.board = 'Failed to get comments.';
				return res.status (400).json (errors); 
			}
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.card = 'Failed to get comments.';
			return res.status (400).json (errors);
		});

});

// @route   POST /boards/{board_id}/cards/{card_id}/comments
// @desc    Creates a new comment in a card
router.post ('/boards/:board_id/cards/:card_id/comments', (req, res) => {

	let token = req.body.token;

	request.post (gloapiurl + 'boards/' + req.params.board_id + '/cards/' + req.params.card_id + '/comments')
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.send ({ text })
		.then (result => {
			if (result.status === 201) return res.status (200).json (result.body);
			else {
				let errors = {};
				errors.board = 'Failed create comment.';
				return res.status (400).json (errors); 
			}
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.card = 'Failed to create comment.';
			return res.status (400).json (errors);
		});

});

// @route   POST /boards/{board_id}/cards/{card_id}/comments/{comment_id}
// @desc    Edits a comment by ID
router.post ('/boards/:board_id/cards/:card_id/comments/:comment_id', (req, res) => {

	let token = req.body.token;

	request.post (gloapiurl + 'boards/' + req.params.board_id + '/cards/' + req.params.card_id + '/comments/' + req.params.comment_id)
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.send ({ text })
		.then (result => {
			if (result.status === 200) return res.status (200).json (result.body);
			else {
				let errors = {};
				errors.board = 'Failed to edit comment.';
				return res.status (400).json (errors); 
			}
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.card = 'Failed to edit comment.';
			return res.status (400).json (errors);
		});

});

// @route   POST /boards/{board_id}/cards/{card_id}/comments/{comment_id}
// @desc    Deletes a comment by ID
router.delete ('/boards/:board_id/cards/:card_id/comments/:comment_id', (req, res) => {

	let token = req.query.token;

	request.delete (gloapiurl + 'boards/' + req.params.board_id + '/cards/' + req.params.card_id + '/comments/' + req.params.comment_id)
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.then (result => {
			if (result.status === 204) return res.status (200).json ({ status: 200 });
			else {
				let errors = {};
				errors.board = 'Failed to delete comment.';
				return res.status (400).json (errors); 
			}
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.card = 'Failed to delete comment.';
			return res.status (400).json (errors);
		});

});

/*** USERS ***/

// @route   GET /user
// @desc    Gets data about the authenticated user
router.get ('/user', (req, res) => {

	let token = req.query.token;

	request.get (gloapiurl + 'user')
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.then (result => {
			if (result.status === 200) return res.status (200).json (result.body);
			else {
				let errors = {};
				errors.board = 'Failed to get user.';
				return res.status (400).json (errors); 
			}	
		})
		.catch (err => {
			let errors = {};
			console.error (err.message);
			errors.card = 'Failed to get user.';
			return res.status (400).json (errors);
		});

});


module.exports = router;