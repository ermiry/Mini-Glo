import React from 'react';

const Register = () => {

    var s = { padding: "40px" };

    return (
        <div class="jumbotron" style={s}>
            <h2>Welcome to Mini Glo</h2>
            <p>A chrome extension for Glo boards</p>
            <hr/>
            <p>You need to login to authorize Gitkraken first. Follow the next link to get your token:</p>
            <p>https://ermiry.com/mini-glo</p>
        </div>
    );
};

export default Register;
