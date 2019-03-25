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
  console.log("Get token was made");
  console.log("AcessToken: " + accessToken);
  res.json({
    token:accessToken
  });
});

router.get('/boards',(req,res)=>{
  console.log("Get Board was made");
  console.log("AT: " + accessToken);
  //const{boardName} = req.boardName;
  //const{token} = accessToken;
  request.get('https://gloapi.gitkraken.com/v1/glo/boards')
  .auth(accessToken,{type:"bearer"})
  .set('Accept','application/json')
  .then(result =>{
    const data = result.body;    
    return res.status(203).json(data);
  })
  .catch(err=>{
    console.error(err.message);
  })
});

router.post('/boards',(req,res)=>{
  
  console.log("Post Board was made");
  console.log("AT: ", req.body.accessToken)
  request.post('https://gloapi.gitkraken.com/v1/glo/boards')
  
  .auth(req.body.accessToken,{type:"bearer"})
  .send({
    name: req.body.boardName
  })
  .set('Accept','application/json')
  .then(result=>{
    const data = result.body;
    console.log(result.status);
    res.send(data);
  })
  .catch(err=>{
    console.error(err.message);
  })
})

router.get('/boards/board_id',(req,res)=>{
  console.log("Get board by id was made");
  let boardId = req.query.boardId;
  var url = universalPath + boardId; 
  console.log("URL: "+ url);
  request.get(url)
  .auth(accessToken,{type:"bearer"})
  .then(result=>{
    const data = result;
    console.log(JSON.stringify(data));
    res.send(JSON.stringify(data));
  })
  .catch(err=>{
    console.error(err.message);
  })
})

router.post('/boards/board_id/columns',(req,res)=>{
  console.log("Post column was made");
  let boardId = req.body.boardId;
  var url = universalPath + boardId + '/columns';
  console.log(url);
  request.post(url)
  .auth(accessToken,{type:"bearer"})
  .send({
    name: req.body.columnName,
    position:0
  })
  .set('Accept','application/json')
  .then(result =>{
    const data = result.body;
    console.log(JSON.stringify(data));
    res.send(data);
  })
  .catch(err=>{
    console.error(err.message);
  })

})

router.post('/boards/board_id/cards',(req,res)=>{
  console.log("Card post was made");
  let boardId = req.body.boardId;
  let cardName = req.body.cardName;
  let columnId = req.body.columnId;
  let description = req.body.description;
  var url = universalPath + boardId + '/cards';
  console.log("URL: "  + url);
  request.post(url)
  .auth(accessToken,{type:"bearer"})
  .send({
    name:cardName,
    column_id:columnId,
    description:{
      text: description
    }
  })
  .then(result=>{
    const data = result;
    console.log(JSON.stringify(data));
    res.send(data);
  })
  .catch(err=>{
    console.error(err.message);
  })
});

router.get('/boards/board_id/cards',(req,res)=>{
  let boardId = req.query.boardId;
  var url = universalPath + boardId + "/cards";
  request.get(url)
  .auth(accessToken,{type:"bearer"})
  .then(result=>{
    const data = result;
    console.log(JSON.stringify(data));
    res.send(data);
  })
  .catch(err=>{
    console.error(err.message);
  })

});

router.post('/boards/board_id/cards/card_id',(req,res)=>{
  let boardId = req.board.boardId;
  let cardId = req.board.cardId;
  var url = universalPath + boardId + "/" + cardId;
  request.post(url)
  .auth(accessToken,{type:"bearer"})
  .send({

  })
  .then(result=>{
    const data = result;
    console.log(JSON.stringify(data));
    res.send(data);
  })
  .catch(err=>{
    console.error(err.message);
  })
});


module.exports = router;