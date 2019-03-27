const express = require ('express');
const router = express.Router ();
const request = require('superagent');
const app = express();
const path = require('path');
var opn = require('opn');
var querystring = require('querystring');
var columnName = "", board = "";
var accessToken;
var url;
const universalPath = 'https://gloapi.gitkraken.com/v1/glo/boards/';
const gloapiurl = 'https://gloapi.gitkraken.com/v1/glo/';


// @route   GET api/mini-glo/test
// @desc    Tests mini glo route
// @access  Public


router.get ('/authorize', (req, res) => {
    request.get("https://app.gitkraken.com/oauth/authorize")
    console.log("Get Request was made"); 
    url = req.url;    
    res.sendFile(path.join(__dirname+'/html/index.html')); 
});

//DO A GET
router.get('/oauth',(req,res,next)=>{
  //get the query
  
  var q = querystring.parse(url);
  const{query} = req;
  // ?code=1254131h151
  //get the code 
  const{code} = query;
  if(!code){
      return res.send({
          succes:false,
          message:'Error:no code'
      });
  }  
  console.log('code',code);
  //Post
  //required URL for the authorization:
  request
  .post('https://api.gitkraken.com/oauth/access_token')
  .send({ 
    grant_type: 'authorization_code',
    client_id:'b3rb17pn8k6y4z9hkyu4',
    client_secret:'ckn1m4yxi8h64ts7dzk45ybnv35a9ckqriy03rv6',
    code:code 
  })
  .set('Accept', 'application/json')
  .then(result => {
     const data = result.body;
     var obj = JSON.parse(result.text);
     accessToken =  obj.access_token;
     console.log("AccessToken: " + accessToken);
     res.sendFile(path.join(__dirname+'/html/register.html'));
  })
});

router.get('/token',(req,res)=>{
  res.json({
    token:accessToken
  });
});


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
  console.log("AT:" +  req.body.token + "\nName:" + req.body.boardName);
	request.post (gloapiurl + 'boards')
		.auth (req.body.token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.send ({ name: req.body.boardName })
		.then (result => {
			if (result.status === 201){
        console.log("Correctly created board");
        return res.status (200).json (result.body);
      }
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

	request.delete (gloapiurl +  "boards/" + req.params.board_id)
		.auth (token, { type: "bearer" })
    .set ('Accept', 'application/json')
    
		.then (result => {
			if (result.status === 204) return res.status (200);
			else {
				let errors = {};
				errors.board = 'Failed to delete board.';
				return res.status (400).json (errors); 
			}
		})
		.catch (err => {
			let errors = {};
			console.error ("ERRORCILLO:" + err.message);
			errors.board = 'Failed to delete board.';
			return res.status (400).json (errors);
		});

});



/*** COLUMNS ***/

// @route   POST /boards/board_id/columns
// @desc    Creates a new column in a board
router.post ('/boards/:board_id/columns', (req, res) => {

	let token = req.body.token;

	request.post (gloapiurl + 'boards/' + req.params.board_id + '/columns')
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.send ({ name: req.body.name, position: 0 })
		.then (result => {
			console.log("It worked");
			if (result.status === 201){ 
				console.log("And erick es putotototototoottototote");
				return res.status (200).json (result.body);
			}
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
	let data = {};
	data.name = req.body.name;
	data.position = 0;
	data.column_id = req.body.column_id;
	if(req.body.description!=null)
		data.description = {text:req.body.description};

		
	request.post (gloapiurl + 'boards/' + req.params.board_id + '/cards')
		.auth (token, { type: "bearer" })
		.set ('Accept', 'application/json')
		.send (data)
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


// router.post('/boards/:board_id/cards',(req,res)=>{
//   console.log("Card post was made");
//   let boardId = req.params.board_id;
//   let cardName = req.body.cardName;
//   let columnId = req.body.columnId;
//   let description = req.body.description;

//   console.log("BoardId " + boardId + "\nCard Name: " + cardName + "\nColumn_Id: " + columnId + "\nDescription: " + description); 

//   var url = universalPath + boardId + '/cards';
//   console.log("URL: "  + url);
//   request.post(url)
//   .auth(accessToken,{type:"bearer"})
//   .send({
//     name:cardName,
//     column_id:columnId,
//     description:{
//     text: description
//     }
//   })
//   .then(result=>{
//     const data = result.body;
//     console.log(JSON.stringify(data));
//     res.send(data);
//   })
//   .catch(err=>{
//     console.error(err.message);
//   })
// });


// router.get('/boards/:board_id/cards',(req,res)=>{
//   let boardId = req.params.board_id;
//   var url = universalPath + boardId + "/cards";
//   request.get(url)
//   .auth(accessToken,{type:"bearer"})
//   .then(result=>{
//     const data = result.body;
//     console.log(JSON.stringify(data));
//     res.send(data);
//   })
//   .catch(err=>{
//     console.error(err.message);
//   })

// });

// router.post ('/boards/:board_id/cards/:card_id', (req, res) => {

// 	request.post (gloapiurl + 'boards/' + req.params.board_id + '/cards/' + req.params.card_id)
// 		.auth (token, { type: "bearer" })
// 		.set ('Accept', 'application/json')
// 		.send ({
// 			description: req.body.description,
// 			label: req.body.label,
// 			due_date: req.body.due_date,
// 			name: req.body.name
// 		})	// FIXME: send the data
// 		.then (result => {
//       res.send(result.body);
// 			// TODO: check errors and send back the json
// 		})
// 		.catch (err => {
// 			let errors = {};
// 			console.error (err.message);
// 			errors.card = 'Failed to edit card.';
// 			return res.status (400).json (errors);
// 		});

// });

module.exports = router;