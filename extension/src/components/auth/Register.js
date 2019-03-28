import React from 'react';

// FIXME: change the path to ermiry
const Register = () => {
    var s = { padding: "40px" };

    return (
        <div class="jumbotron" style={s}>
            <h1>WELCOME TO MINI-GLO</h1>
            <p>You have to register to mini-glo to continue</p>
            <p>
                <a class="btn btn-primary btn-lg" 
                href="https://app.gitkraken.com/oauth/authorize?response_type=code&client_id=b3rb17pn8k6y4z9hkyu4&redirect_uri=https://ea52f4e7.ngrok.io/api/mini-glo/oauth&scope=board%3Aread board%3Awrite user%3Aread user%3Awrite&state=VHVlIE1hciAxMiAyMDE5IDIxOjIxOjU1IEdNVC0wNjAwIChDZW50cmFsIFN0YW5kYXJkIFRpbWUp" 
                role="button">
                Register
                </a>
            </p>
        </div>
    );
};

export default Register;
