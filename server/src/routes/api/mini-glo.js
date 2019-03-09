const express = require ('express');
const router = express.Router ();

// @route   GET api/mini-glo/test
// @desc    Tests mini glo route
// @access  Public
router.get ('/test', (req, res) => res.json ({ msg: 'Mini Glo Works' }));

module.exports = router;