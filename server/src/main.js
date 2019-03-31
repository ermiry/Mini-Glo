const result = require ('dotenv').config ({ path: './config/keys.env' });

if (result.error) throw result.error

const express = require ('express');
// const mongoose = require ('mongoose');
const bodyParser = require ('body-parser');
// const passport = require ('passport');

const app = express ();

// Body parser middleware
app.use (bodyParser.urlencoded ({ extended: false }));
app.use (bodyParser.json ());

/*** ROUTES ***/

const miniglo = require ('./routes/api/mini-glo');

app.use ('/api/mini-glo', miniglo);

// catch all, for now redirect to the home page
// app.get ("*", (req, res) => { res.redirect ("/"); });

const port = process.env.port;

app.listen (port, () => console.log (`Server running on port ${port}`));