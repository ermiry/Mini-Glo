const express = require ('express');
const router = express.Router ();
const request = require('superagent');
var columnName = "", board = "";
// @route   GET api/mini-glo/test
// @desc    Tests mini glo route
// @access  Public
router.get ('/test', (req, res) => {
    console.log("Get Request was made");      
    res.json (   
    { msg: "hola ",
      boardName: 'Mike' 
    });
});


router.post ('/test',(req,res)=>{
    let columnName = columnName.body.any;
    console.log(columnName);
    res.json({status:'Success'});
});


//DO A GET
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
  //required URL for the authorization:
  //app.gitkraken.com/oauth/authorize?response_type=code&client_id=b3rb17pn8k6y4z9hkyu4&redirect_uri=http://localhost:10000/api/mini-glo/oauth&scope=board%3Aread board%3Awrite user%3Aread user%3Awrite&state=VHVlIE1hciAxMiAyMDE5IDIxOjIxOjU1IEdNVC0wNjAwIChDZW50cmFsIFN0YW5kYXJkIFRpbWUp
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