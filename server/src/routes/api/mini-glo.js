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


module.exports = router;