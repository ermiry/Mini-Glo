import React from 'react';

export default () => {

    var style = {
        padding: "20px",
        position: "fixed",
        left: "0",
        bottom: "0",
        height: "60px",
        width: "100%",
    }
    
    var phantom = {
        display: 'block',
        padding: '20px',
        height: '60px',
        width: '100%',
    }

    return (
        <div>
            <div style={phantom} />
                <footer style={style} className="bg-dark text-white mt-5 p-4 text-center">
                    Copyright &copy; { new Date().getFullYear() } Ermiry
                </footer>
        </div>
    );

};