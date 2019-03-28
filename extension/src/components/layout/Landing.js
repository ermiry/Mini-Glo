import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

// my actions
import { getUser } from '../../actions/userActions';

// my componets
import Register from '../auth/Register';

class Landing extends Component {
  
    render() {
        let content;
        if (!this.props.auth.isAuthenticated) content = <Register />;
        else content = (<h1>Hola</h1>);

        return (
            <div>
                <h1>Mini Glo!</h1>
                {content}
            </div>
        );
    }
}

Landing.propTypes = {
    auth: PropTypes.object.isRequired,
    errors: PropTypes.object.isRequired,
    getUser: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth,
    errors: state.errors,
});

export default connect (mapStateToProps, { getUser }) (Landing);