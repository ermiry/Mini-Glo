const express = require ('express');
const router = express.Router ();

const axios = require ('axios');
const request = require ('superagent');

/*** TEST ***/

// @route   GET api/mini-glo/test
// @desc    Tests mini glo route
// @access  Public
router.get ('/test', (req, res) => res.json ({ msg: 'Mini-Glo Works' }));

// @route   POST api/mini-glo/test
// @desc    Tests mini glo route
// @access  Public
router.post ('/test', (req, res) => {

    let msg = req.body.msg;

    console.log (msg);

    res.json ({ status: 'Success' });

});

/*** OAUTH ***/

// @route   GET api/mini-glo/oauth
// @desc    Handles mini glo oauth
// @access  Public
router.get ('/oauth', (req, res) => {

	const { code } = req;
	if (!code) {
		res.send ({
			succes: false,
			message: 'Error: no code'
		});
	}

	// FIXME: move this to a protected file
	request.post ('https://api.gitkraken.com/oauth/access_token')
		.set ('Accept', 'application/json')
		.send ({
			grant_type: 'authorization_code',
			client_id: 'b3rb17pn8k6y4z9hkyu4',
			client_secret: 'ckn1m4yxi8h64ts7dzk45ybnv35a9ckqriy03rv6',
			code: code 
		})
		.then (result => {
			/*** TODO: check this later ***/
			res.send (result.body);
		})
		.catch (err => {
			let { errors };
			console.error (err.message);
			errors.board = 'Faild oauth.';
        	return res.status (400).json (errors); 
		});

});

/*** BOARDS ***/

// @route   GET /boards
// @desc    Get a list of boards
// @access  Private
router.get ('/boards', (req, res) => {

});

// @route   POST /boards
// @desc    Creates a new board
// @access  Private
router.post ('/boards', (req, res) => {

	request.post ('https://gloapi.gitkraken.com/v1/glo/boards')
		.auth (req.body.token, { type: "bearer"})
		.set ('Accept', 'application/json')
		.send ({ name: req.body.boardName })
		.then (result => {
			// res.send (result);
			let { board };
			board.id = result.body.id;
			board.name = result.body.name;
			return res.status (200).json (board);
		})
		.catch (err => {
			let { errors };
			console.error (err.message);
			errors.board = 'Failed to create board.';
        	return res.status (400).json (errors); 
		});

});

/***** */
// TODO: req.body.id!!!
/***** */

// @route   GET /boards/board_id
// @desc    Gets a board by ID
// @access  Private
router.get ('/boards/board_id', (req, res) => {

});

// @route   POST /boards/board_id
// @desc    Edits a board by id
// @access  Private
router.post ('/boards/board_id', (req, res) => {

});

// @route   DELETE /boards/board_id
// @desc    Deletes a board by id
// @access  Private
router.delete ('/boards/board_id', (req, res) => {

});

/*** COLUMNS ***/

// @route   POST /boards/board_id/columns
// @desc    Creates a new column in a board
router.post ('/boards/board_id/columns', (req, res) => {

});

// @route   POST /boards/board_id/columns/column_id
// @desc    Edits a column by id
router.delete ('/boards/board_id/columns/column_id', (req, res) => {

});

// @route   DELETE /boards/board_id/columns/column_id
// @desc    Deletes a column by id
router.delete ('/boards/board_id/columns/column_id', (req, res) => {

});


/*** CARDS ***/

// @route   GET /boards/{board_id}/cards
// @desc    Gets a list of cards for a board
router.get ('/boards/board_id/cards', (req, res) => {

});

// @route   POST /boards/{board_id}/cards
// @desc    Creates a new card in a column
router.post ('/boards/board_id/cards', (req, res) => {

});

// @route   GET /boards/{board_id}/cards/{card_id}
// @desc    Gets a card by ID
router.get ('/boards/board_id/cards/card_id', (req, res) => {

});

// @route   POST /boards/{board_id}/cards/{card_id}
// @desc    Edits a card
router.post ('/boards/board_id/cards/card_id', (req, res) => {

});

// @route   DELETE /boards/{board_id}/cards/{card_id}
// @desc    Deletes a card
router.delete ('/boards/board_id/cards/card_id', (req, res) => {

});

// @route   GET /boards/{board_id}/columns/{column_id}/cards
// @desc    Gets a list of cards for a column
router.get ('/boards/board_id/columns/column_id/cards', (req, res) => {

});

/*** ATTACHMENTS ***/

// @route   GET /boards/{board_id}/cards/{card_id}/attachments
// @desc    Gets a list of attachments for a card
router.get ('/boards/board_id/cards/card_id/attachments', (req, res) => {

});

// @route   POST /boards/{board_id}/cards/{card_id}/attachments
// @desc    Creates an attachment for a card
router.post ('/boards/board_id/cards/card_id/attachments', (req, res) => {

});

/*** COMMENTS ***/

// @route   GET /boards/{board_id}/cards/{card_id}/comments
// @desc    Gets a list of comments for a card
router.get ('/boards/board_id/cards/card_id/comments', (req, res) => {

});

// @route   POST /boards/{board_id}/cards/{card_id}/comments
// @desc    Creates a new comment in a card
router.post ('/boards/board_id/cards/card_id/comments', (req, res) => {

});

// @route   POST /boards/{board_id}/cards/{card_id}/comments/{comment_id}
// @desc    Edits a comment by ID
router.post ('/boards/board_id/cards/card_id/comments/comment_id', (req, res) => {

});

// @route   POST /boards/{board_id}/cards/{card_id}/comments/{comment_id}
// @desc    Deletes a comment by ID
router.delete ('/boards/board_id/cards/card_id/comments/comment_id', (req, res) => {

});

/*** USERS ***/

// @route   GET /user
// @desc    Gets data about the authenticated user
router.get ('/user', (req, res) => {

});


module.exports = router;