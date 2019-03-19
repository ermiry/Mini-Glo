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
// @route   GET api/mini-glo/test
// @desc    Tests mini glo route
// @access  Public
router.get ('/authorize', (req, res) => {
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
//https://app.gitkraken.com/oauth/authorize?response_type=code&client_id=b3rb17pn8k6y4z9hkyu4&redirect_uri=https://18540928.ngrok.io/api/mini-glo/oauth&scope=board%3Aread board%3Awrite user%3Aread user%3Awrite&state=VHVlIE1hciAxMiAyMDE5IDIxOjIxOjU1IEdNVC0wNjAwIChDZW50cmFsIFN0YW5kYXJkIFRpbWUp
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
     //opn('https://pitangui.amazon.com/api/skill/link/M1IE0AW91STLMJ' + '?state=' + q.state + '&code=' + code);
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
  //const{boardName} = req.boardName;
  //const{token} = accessToken;
  request.get('https://gloapi.gitkraken.com/v1/glo/boards')
  .auth(accessToken,{type:"bearer"})
  .set('Accept','application/json')
  .then(result =>{
    const data = result.body;
    console.log(result.status + "\n" + result.header + "\n" + data);
    res.send(data);
  })
  .catch(err=>{
    console.error(err.message);
  })
});

router.post('/boardPost',(req,res)=>{
  console.log("Post Board was made");
  request.post('https://gloapi.gitkraken.com/v1/glo/boards')
  .auth(accessToken,{type:"bearer"})
  .send({
    name:"ErickEsjoto"
  })
  .set('Accept','application/json')
  .then(result=>{
    const data = result.body;
    res.send(data);
  })
  .catch(err=>{
    console.error(err.message);
  })
})

module.exports = router;