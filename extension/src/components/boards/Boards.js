import React, { Component } from 'react';

import PropTypes from 'prop-types';
import { connect } from 'react-redux';

// my components
import BoardFeed from './BoardFeed';

// my actions
import { getBoards, getBoard } from '../../actions/boardActions';

// FIXME: add a spinner for loading actions
// import Spinner from '../common/Spinner';

class Boards extends Component {

	// load a list of boards as soon as we enter the extension
	componentDidMount () { this.props.getBoards (); }

	render () {
		return (
			<h1>BOARDS</h1>
			// FIXME:
			// const { posts, loading } = this.props.post;
			// let postContent;

			// if (posts === null || loading) {
			// postContent = <Spinner />;
			// } else {
			// postContent = <PostFeed posts={posts} />;
			// }

			// return (
			// <div className="feed">
			// 	<div className="container">
			// 	<div className="row">
			// 		<div className="col-md-12">
			// 		<PostForm />
			// 		{postContent}
			// 		</div>
			// 	</div>
			// 	</div>
			// </div>
			// );
		);
		
	}

}

Boards.propTypes = {
	getBoards: PropTypes.func.isRequired,
	board: propTypes.object.isRequired
};

const mapStateToProps = state => ({
	board: state.board
});

export default connect (mapStateToProps, { getBoards}) (Boards);