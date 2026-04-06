package columns;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;

import columns.model.Board;
import columns.model.Figure;
import columns.model.GameConfig;
import columns.model.ModelListener;
import columns.model.View;

public class Columns extends Applet implements Runnable, ModelListener {

	int keyPressed;

	long tc;

	boolean isKeyPressed = false;

	Graphics graphics;

	Thread thr = null;

	Board board;

	public View view = new View();

	void delay(long t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void init() {
		board = new Board();
		board.setModelListener(this);
		board.initFields();
		graphics = getGraphics();
		view.setScreen(new AppletScreen(graphics));
		setFocusable(true);
	}

	@Override
	public boolean keyDown(Event e, int k) {
		isKeyPressed = true;
		keyPressed = e.key;
		return true;
	}

	@Override
	public boolean lostFocus(Event e, Object w) {
		isKeyPressed = true;
		keyPressed = 'P';
		return true;
	}

	@Override
	public void paint(Graphics g) {
		view.showHelp();

		g.setColor(Color.black);

		view.showLevel(board.level);
		view.showScore(board.Score);
		view.drawField(board.newField);
		view.drawFigure(board.figure);
		requestFocus();
	}

	@Override
	public void run() {
		board.initBoard();
		graphics.setColor(Color.black);
		requestFocus();

		do {
			tc = System.currentTimeMillis();
			board.figure = new Figure();
			view.drawFigure(board.figure);
			while (board.figureMayMoveDown()) {
				checkTimeAndMoveDownIfNeeded();
				board.DScore = 0;
				processUserEventsIfAny();
			}
			board.pasteFigure(board.figure);
			do {
				board.noChanges = true;
				board.findMatches();
				if (foundMatches()) {
					delay(500);
					board.collapse();
				}
			} while (foundMatches());
		} while (!board.isFieldFull());
	}

	private boolean foundMatches() {
		return !board.noChanges;
	}

	private void processUserEventsIfAny() {
		do {
			delay(50);
			if (isKeyPressed) {
				processEvent();
			}
		} while ((int) (System.currentTimeMillis() - tc) <= (GameConfig.MAX_LEVEL - board.level) * GameConfig.TIME_SHIFT
				+ GameConfig.MIN_TIME_SHIFT);
	}

	private void checkTimeAndMoveDownIfNeeded() {
		if ((int) (System.currentTimeMillis() - tc) > (GameConfig.MAX_LEVEL - board.level) * GameConfig.TIME_SHIFT
				+ GameConfig.MIN_TIME_SHIFT) {
			tc = System.currentTimeMillis();
			view.hideFigure(board.figure);
			board.figure.moveDown();
			view.drawFigure(board.figure);
		}
	}

	private void processEvent() {
		isKeyPressed = false;
		switch (keyPressed) {
		case Event.LEFT:
			if (board.canMoveLeft()) {
				view.hideFigure(board.figure);
				board.figure.moveLeft();
				view.drawFigure(board.figure);
			}
			break;
		case Event.RIGHT:
			if (board.canMoveRight()) {
				view.hideFigure(board.figure);
				board.figure.moveRight();
				view.drawFigure(board.figure);
			}
			break;
		case Event.UP:
			board.figure.rotateUp();
			view.drawFigure(board.figure);
			break;
		case Event.DOWN:
			board.figure.rotateDown();
			view.drawFigure(board.figure);
			break;
		case ' ':
			view.hideFigure(board.figure);
			board.dropFigure(board.figure);
			view.drawFigure(board.figure);
			tc = 0;
			break;
		case 'P':
		case 'p':
			while (!isKeyPressed) {
				view.hideFigure(board.figure);
				delay(500);
				view.drawFigure(board.figure);
				delay(500);
			}
			tc = System.currentTimeMillis();
			break;
		case '-':
			if (board.level > 0) {
				board.level = board.level - 1;
			}
			board.figuresMatchedCounter = 0;
			view.showLevel(board.level);
			break;
		case '+':
			if (board.level < GameConfig.MAX_LEVEL) {
				board.level = board.level + 1;
			}
			board.figuresMatchedCounter = 0;
			view.showLevel(board.level);
			break;
		}
	}

	@Override
	public void start() {
		graphics.setColor(Color.black);

		// setBackground (new Color(180,180,180));

		if (thr == null) {
			thr = new Thread(this);
			thr.start();
		}
		requestFocus();
	}

	@Override
	public void stop() {
		if (thr != null) {
			thr.stop();
			thr = null;
		}
	}

	@Override
	public void levelHasChanged(int level) {
		view.showLevel(level);
	}

	@Override
	public void tripletDetected(int a, int b, int c, int d, int i, int j) {
		view.drawBox(a, b, 8);
		view.drawBox(j, i, 8);
		view.drawBox(c, d, 8);
	}

	@Override
	public void fieldWasUpdated(int[][] field) {
		view.drawField(field);
	}

	@Override
	public void scoreUpdated(long score) {
		view.showScore(score);
	}
}
