import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

// my actions
import { authorize } from '../../actions/authActions';
import { getUser } from '../../actions/userActions';

// my componets
import Register from '../auth/Register';
import TextField from '../common/TextField';

class Main extends Component {

    constructor () { 
        super (); 
        this.state = {
            token: '',
            errors: {}
        };
    }

    onChange = e => { this.setState ({ [e.target.name]: e.target.value }); }

    onSubmit = e => {
        e.preventDefault();
        this.props.authorize (this.state.token);
    }

    render() {
        let { errors } = this.state;

        let content;
        if (!this.props.auth.isAuthenticated) {
            content = (
                <div>
                    <form onSubmit={ this.onSubmit }>
                        <Register />
                        <TextField
                            placeholder="Token"
                            name="token"
                            value={ this.state.token }
                            onChange={ this.onChange }
                            error={ errors.token }
                        />
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