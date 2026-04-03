package columns;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;

public class Columns extends Applet implements Runnable {

	static final int BOX_SIZE = 25, DEPTH = 15, WIDTH = 7, MAX_LEVEL = 7, TIME_SHIFT = 250, NEXT_LEVEL_THRESHOLD = 33,
			MIN_TIME_SHIFT = 200, LEFT_BORDER = 2, TOP_BORDER = 2;

	private final Color COLORS[] = { Color.black, Color.cyan, Color.blue, Color.red, Color.green, Color.yellow,
			Color.pink, Color.magenta, Color.black };

	int keyPressed;

	long tc;

	boolean isKeyPressed = false;

	Graphics graphics;

	Thread thr = null;

	Board board;

	void delay(long t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
		}
	}

	void drawBox(int x, int y, int c) {
		if (c == 0) {
			graphics.setColor(Color.black);
			graphics.fillRect(LEFT_BORDER + x * BOX_SIZE - BOX_SIZE, TOP_BORDER + y * BOX_SIZE - BOX_SIZE, BOX_SIZE,
					BOX_SIZE);
			graphics.drawRect(LEFT_BORDER + x * BOX_SIZE - BOX_SIZE, TOP_BORDER + y * BOX_SIZE - BOX_SIZE, BOX_SIZE,
					BOX_SIZE);
		} else if (c == 8) {
			graphics.setColor(Color.white);
			graphics.drawRect(LEFT_BORDER + x * BOX_SIZE - BOX_SIZE + 1, TOP_BORDER + y * BOX_SIZE - BOX_SIZE + 1,
					BOX_SIZE - 2, BOX_SIZE - 2);
			graphics.drawRect(LEFT_BORDER + x * BOX_SIZE - BOX_SIZE + 2, TOP_BORDER + y * BOX_SIZE - BOX_SIZE + 2,
					BOX_SIZE - 4, BOX_SIZE - 4);
			graphics.setColor(Color.black);
			graphics.fillRect(LEFT_BORDER + x * BOX_SIZE - BOX_SIZE + 3, TOP_BORDER + y * BOX_SIZE - BOX_SIZE + 3,
					BOX_SIZE - 6, BOX_SIZE - 6);
		} else {
			graphics.setColor(COLORS[c]);
			graphics.fillRect(LEFT_BORDER + x * BOX_SIZE - BOX_SIZE, TOP_BORDER + y * BOX_SIZE - BOX_SIZE, BOX_SIZE,
					BOX_SIZE);
			graphics.setColor(Color.black);
			graphics.drawRect(LEFT_BORDER + x * BOX_SIZE - BOX_SIZE, TOP_BORDER + y * BOX_SIZE - BOX_SIZE, BOX_SIZE,
					BOX_SIZE);
		}
		// g.setColor (Color.black);
	}

	void drawField(Graphics g) {
		for (int i = 1; i <= DEPTH; i++) {
			for (int j = 1; j <= WIDTH; j++) {
				drawBox(j, i, board.newField[j][i]);
			}
		}
	}

	void drawFigure(Figure f) {
		drawBox(f.x, f.y, f.c[1]);
		drawBox(f.x, f.y + 1, f.c[2]);
		drawBox(f.x, f.y + 2, f.c[3]);
	}

	void hideFigure(Figure f) {
		drawBox(f.x, f.y, 0);
		drawBox(f.x, f.y + 1, 0);
		drawBox(f.x, f.y + 2, 0);
	}

	@Override
	public void init() {
		board = new Board();
		board.initFields(this);
		graphics = getGraphics();
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
		showHelp(g);

		g.setColor(Color.black);

		showLevel(g);
		showScore(g);
		drawField(g);
		drawFigure(board.figure);
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
			drawFigure(board.figure);
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
			hideFigure(board.figure);
			board.figure.moveDown();
			drawFigure(board.figure);
		}
	}

	private void processEvent() {
		isKeyPressed = false;
		switch (keyPressed) {
		case Event.LEFT:
			if (board.canMoveLeft(this)) {
				hideFigure(board.figure);
				board.figure.moveLeft();
				drawFigure(board.figure);
			}
			break;
		case Event.RIGHT:
			if (board.canMoveRight(this)) {
				hideFigure(board.figure);
				board.figure.moveRight();
				drawFigure(board.figure);
			}
			break;
		case Event.UP:
			board.figure.rotateUp();
			drawFigure(board.figure);
			break;
		case Event.DOWN:
			board.figure.rotateDown();
			drawFigure(board.figure);
			break;
		case ' ':
			hideFigure(board.figure);
			board.dropFigure(this, board.figure);
			drawFigure(board.figure);
			tc = 0;
			break;
		case 'P':
		case 'p':
			while (!isKeyPressed) {
				hideFigure(board.figure);
				delay(500);
				drawFigure(board.figure);
				delay(500);
			}
			tc = System.currentTimeMillis();
			break;
		case '-':
			if (board.Level > 0) {
				board.Level = board.Level - 1;
			}
			board.figuresMatchedCounter = 0;
			showLevel(graphics);
			break;
		case '+':
			if (board.Level < MAX_LEVEL) {
				board.Level = board.Level + 1;
			}
			board.figuresMatchedCounter = 0;
			showLevel(graphics);
			break;
		}
	}

	void showHelp(Graphics g) {
		g.setColor(Color.black);

		g.drawString(" Keys available:", 200 - LEFT_BORDER, 102);
		g.drawString("Roll Box Up:     ", 200 - LEFT_BORDER, 118);
		g.drawString("Roll Box Down:   ", 200 - LEFT_BORDER, 128);
		g.drawString("Figure Left:     ", 200 - LEFT_BORDER, 138);
		g.drawString("Figure Right:    ", 200 - LEFT_BORDER, 148);
		g.drawString("Level High/Low: +/-", 200 - LEFT_BORDER, 158);
		g.drawString("Drop Figure:   space", 200 - LEFT_BORDER, 168);
		g.drawString("Pause:           P", 200 - LEFT_BORDER, 180);
		g.drawString("Quit:     Esc or Q", 200 - LEFT_BORDER, 190);
	}

	void showLevel(Graphics g) {
		g.setColor(Color.black);
		g.clearRect(LEFT_BORDER + 100, 390, 100, 20);
		g.drawString("Level: " + board.Level, LEFT_BORDER + 100, 400);
	}

	void showScore(Graphics g) {
		g.setColor(Color.black);
		g.clearRect(LEFT_BORDER, 390, 100, 20);
		g.drawString("Score: " + board.Score, LEFT_BORDER, 400);
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