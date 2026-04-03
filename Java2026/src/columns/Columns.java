package columns;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;

public class Columns extends Applet implements Runnable {

	static final int BOX_SIZE = 25, DEPTH = 15, WIDTH = 7, MAX_LEVEL = 7, TIME_SHIFT = 250, NEXT_LEVEL_THRESHOLD = 33,
			MIN_TIME_SHIFT = 200, LEFT_BORDER = 2, TOP_BORDER = 2;

	static final Color COLORS[] = { Color.black, Color.cyan, Color.blue, Color.red, Color.green, Color.yellow,
			Color.pink, Color.magenta, Color.black };

	int keyPressed;

	long tc;

	boolean isKeyPressed = false;

	Graphics graphics;

	Thread thr = null;

	Board board;

	View view = new View();

	void delay(long t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void init() {
		board = new Board();
		board.initFields(this);
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
		view.showHelp(g);

		g.setColor(Color.black);

		view.showLevel(this, g);
		view.showScore(this, g);
		view.drawField(board.newField, g);
		view.drawFigure(board.figure);
		requestFocus();
	}

	@Override
	public void run() {
		board.initBoard(this);
		graphics.setColor(Color.black);
		requestFocus();

		do {
			tc = System.currentTimeMillis();
			board.figure = new Figure();
			view.drawFigure(board.figure);
			while (board.figureMayMoveDown(this)) {
				checkTimeAndMoveDownIfNeeded();
				board.DScore = 0;
				processUserEventsIfAny();
			}
			board.pasteFigure(this, board.figure);
			do {
				board.noChanges = true;
				board.findMatches(this);
				if (foundMatches()) {
					delay(500);
					board.collapse(this);
				}
			} while (foundMatches());
		} while (!board.fullField(this));
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
		} while ((int) (System.currentTimeMillis() - tc) <= (MAX_LEVEL - board.Level) * TIME_SHIFT + MIN_TIME_SHIFT);
	}

	private void checkTimeAndMoveDownIfNeeded() {
		if ((int) (System.currentTimeMillis() - tc) > (MAX_LEVEL - board.Level) * TIME_SHIFT + MIN_TIME_SHIFT) {
			tc = System.currentTimeMillis();
			view.hideFigure(this, board.figure);
			board.figure.moveDown();
			view.drawFigure(board.figure);
		}
	}

	private void processEvent() {
		isKeyPressed = false;
		switch (keyPressed) {
		case Event.LEFT:
			if (board.canMoveLeft(this)) {
				view.hideFigure(this, board.figure);
				board.figure.moveLeft();
				view.drawFigure(board.figure);
			}
			break;
		case Event.RIGHT:
			if (board.canMoveRight(this)) {
				view.hideFigure(this, board.figure);
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
			view.hideFigure(this, board.figure);
			board.dropFigure(this, board.figure);
			view.drawFigure(board.figure);
			tc = 0;
			break;
		case 'P':
		case 'p':
			while (!isKeyPressed) {
				view.hideFigure(this, board.figure);
				delay(500);
				view.drawFigure(board.figure);
				delay(500);
			}
			tc = System.currentTimeMillis();
			break;
		case '-':
			if (board.Level > 0) {
				board.Level = board.Level - 1;
			}
			board.figuresMatchedCounter = 0;
			view.showLevel(this, graphics);
			break;
		case '+':
			if (board.Level < MAX_LEVEL) {
				board.Level = board.Level + 1;
			}
			board.figuresMatchedCounter = 0;
			view.showLevel(this, graphics);
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
}