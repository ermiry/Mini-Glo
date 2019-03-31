import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

// my actions
import { authorize } from '../../actions/authActions';
import { getUser } from '../../actions/userActions';

// my componets
import Register from '../auth/Register';

class Main extends Component {

    constructor () { super (); }

    onSubmit = e => {
        e.preventDefault();
        this.props.authorize ();
    }

    render() {
        let content;
        if (!this.props.auth.isAuthenticated) {
            content = (
                <div>
                    <form onSubmit={this.onSubmit}>
                        <Register />
                            {/* <button onClick={ this.submitToken }>Submit token</button> */}
                        <input type="submit" className="btn btn-info btn-block mt-4" />
                    </form>
                </div>
                );
        }
            
        else content = (<h1>You have logged in successfully!</h1>);

        return (
            <div>
               
                {content}
                
            </div>
        );
    }
}

Main.propTypes = {
    getUser: PropTypes.func.isRequired,
    authorize: PropTypes.func.isRequired,
    auth: PropTypes.object.isRequired,
    errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth,
    errors: state.errors,
});

export default connect (mapStateToProps, { getUser, authorize }) (Main);