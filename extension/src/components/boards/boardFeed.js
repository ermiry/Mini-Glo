import React, { Component } from 'react';

import PropTypes from 'prop-types';

// my components
import BoardItem from './BoardItem';

class BoardFeed extends Component {

    render () {
        let { boards } = this.props;

        return boards.map (board => <BoardItem key={ board._id } board={ board } />);
    }

}

BoardFeed.propTypes = {
    boards: PropTypes.array.isRequired
}

export default BoardFeed;
