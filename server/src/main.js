const express = require ('express');
// const mongoose = require ('mongoose');
const bodyParser = require ('body-parser');
// const passport = require ('passport');

const app = express ();

// Body parser middleware
app.use (bodyParser.urlencoded ({ extended: false }));
app.use (bodyParser.json ());

// DB Config
// TODO:
// const db = require('./config/keys').mongoURI;

// Connect to MongoDB
/* mongoose
    .connect ('mongodb://localhost/test', { useNewUrlParser: true })
    .then (() => console.log ('MongoDB Connected'))
    .catch (err => console.log (err)); */

// passport
// app.use (passport.initialize ());
// require ('../config/passport')(passport);

// serve static files from our public dir
// app.use (express.static ("./public"));

/*** ROUTES ***/

app.use ('/api/mini-glo', mini-glo);

// catch all, for now redirect to the home page
// app.get ("*", (req, res) => { res.redirect ("/"); });

const port = process.env.PORT || 8080;

app.listen (port, () => console.log (`Server running on port ${port}`));