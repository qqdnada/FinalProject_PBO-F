package id.ac.its.finalprojectpbo.gui;

import id.ac.its.finalprojectpbo.game.GameBoard;

public class ScoreManager {
	
	// Current scores
	private int currentScore;
	private int currentTopScore;
	private int[] board = new int[GameBoard.ROWS * GameBoard.COLS];
	
	private GameBoard gBoard;
	
	// Boolean
	private boolean newGame;
	
	public ScoreManager(GameBoard gBoard) {
		this.gBoard = gBoard;
	}
	
	public void reset() {
		newGame = true;
		currentScore = 0;
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	public int[] getBoard() {
		return board;
	}

	public boolean isNewGame() {
		return newGame;
	}

	
	
}
