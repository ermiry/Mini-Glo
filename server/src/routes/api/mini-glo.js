const express = require ('express');
const router = express.Router ();

const axios = require('axios');

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

/* FIXME: can we use passport outh?? */

// @route   GET api/mini-glo/boards
// @desc    Get a list of boards
// @access  Private

// @route   POST api/mini-glo/boards
// @desc    Creates a new board
// @access  Private

// @route   GET api/mini-glo/boards/:board_id
// @desc    Gets a board by ID
// @access  Private

// @route   POST api/mini-glo/boards/:board_id
// @desc    Edits a board by id
// @access  Private

// @route   DELETE api/mini-glo/boards/:board_id
// @desc    Deletes a board by id
// @access  Private

// https://app.gitkraken.com/oauth/authorize?response_type=code&client_id=b3rb17pn8k6y4z9hkyu4&redirect_uri=http://localhost:10000/api/mini-glo/oauth&scope=board%3Aread%20board%3Awrite%20user%3Aread%20user%3Awrite&state=V2VkIE1hciAxMyAyMDE5IDExOjA1OjAyIEdNVC0wNjAwIChDZW50cmFsIFN0YW5kYXJkIFRpbWUp

// FIXME: add super agent
router.get('/oauth',(req,res,next)=>{
    //get the query
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
       res.send(data);
    });
  });


module.exports = router;