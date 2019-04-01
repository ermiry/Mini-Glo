import React, { Component } from 'react';

import { connect } from 'react-redux';
import PropTypes from 'prop-types';

// my actions
import { getBoard } from '../../actions/boardActions';

// my components
import Spinner from '../common/Spinner';

class Board extends Component {

    // FIXME: check that this works!!
    componentDidMount () { this.props.getBoard (this.props.match.params.id); }

    render () {
        let { board, loading } = this.props.board;
        let boardContent;

        if (board === null || loading || Object.keys (board).length === 0)
            boardContent = <Spinner />
        else {
            boardContent = (
                <div>
                    {/* TODO: render board */}
                    <h2>Board content here!</h2>
                </div>
            )
        }

        return (
            <div>
                { boardContent }
            </div>
        );
    }

}

Board.propTypes = {
    getBoard: PropTypes.func.isRequired,
    board: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    board: state.board
});

export default connect (mapStateToProps, { getBoard }) (Board);
