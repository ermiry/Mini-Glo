const express = require ('express');
const router = express.Router ();

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

module.exports = router;