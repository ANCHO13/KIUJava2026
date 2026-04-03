package columns;

import java.awt.Color;
import java.awt.Graphics;

public class View {

	private Screen screen;

	void setScreen(Screen screen) {
		this.screen = screen;
	}

	void drawBox(int x, int y, int c) {
		if (c == 0) {
			screen.setColor(Color.black);
			screen.fillRect(Columns.LEFT_BORDER + x * Columns.BOX_SIZE - Columns.BOX_SIZE, Columns.TOP_BORDER + y * Columns.BOX_SIZE - Columns.BOX_SIZE, Columns.BOX_SIZE,
					Columns.BOX_SIZE);
			screen.drawRect(Columns.LEFT_BORDER + x * Columns.BOX_SIZE - Columns.BOX_SIZE, Columns.TOP_BORDER + y * Columns.BOX_SIZE - Columns.BOX_SIZE, Columns.BOX_SIZE,
					Columns.BOX_SIZE);
		} else if (c == 8) {
			screen.setColor(Color.white);
			screen.drawRect(Columns.LEFT_BORDER + x * Columns.BOX_SIZE - Columns.BOX_SIZE + 1, Columns.TOP_BORDER + y * Columns.BOX_SIZE - Columns.BOX_SIZE + 1,
					Columns.BOX_SIZE - 2, Columns.BOX_SIZE - 2);
			screen.drawRect(Columns.LEFT_BORDER + x * Columns.BOX_SIZE - Columns.BOX_SIZE + 2, Columns.TOP_BORDER + y * Columns.BOX_SIZE - Columns.BOX_SIZE + 2,
					Columns.BOX_SIZE - 4, Columns.BOX_SIZE - 4);
			screen.setColor(Color.black);
			screen.fillRect(Columns.LEFT_BORDER + x * Columns.BOX_SIZE - Columns.BOX_SIZE + 3, Columns.TOP_BORDER + y * Columns.BOX_SIZE - Columns.BOX_SIZE + 3,
					Columns.BOX_SIZE - 6, Columns.BOX_SIZE - 6);
		} else {
			screen.setColor(Columns.COLORS[c]);
			screen.fillRect(Columns.LEFT_BORDER + x * Columns.BOX_SIZE - Columns.BOX_SIZE, Columns.TOP_BORDER + y * Columns.BOX_SIZE - Columns.BOX_SIZE, Columns.BOX_SIZE,
					Columns.BOX_SIZE);
			screen.setColor(Color.black);
			screen.drawRect(Columns.LEFT_BORDER + x * Columns.BOX_SIZE - Columns.BOX_SIZE, Columns.TOP_BORDER + y * Columns.BOX_SIZE - Columns.BOX_SIZE, Columns.BOX_SIZE,
					Columns.BOX_SIZE);
		}
		// g.setColor (Color.black);
	}

	void drawFigure(Figure f) {
		drawBox(f.x, f.y, f.c[1]);
		drawBox(f.x, f.y + 1, f.c[2]);
		drawBox(f.x, f.y + 2, f.c[3]);
	}

	void drawField(int[][] field, Graphics g) {
		for (int i = 1; i <= Columns.DEPTH; i++) {
			for (int j = 1; j <= Columns.WIDTH; j++) {
				drawBox(j, i, field[j][i]);
			}
		}
	}

	void hideFigure(Columns columns, Figure f) {
		drawBox(f.x, f.y, 0);
		drawBox(f.x, f.y + 1, 0);
		drawBox(f.x, f.y + 2, 0);
	}

	void showHelp(Graphics g) {
		screen.setColor(Color.black);
	
		screen.drawString(" Keys available:", 200 - Columns.LEFT_BORDER, 102);
		screen.drawString("Roll Box Up:     ", 200 - Columns.LEFT_BORDER, 118);
		screen.drawString("Roll Box Down:   ", 200 - Columns.LEFT_BORDER, 128);
		screen.drawString("Figure Left:     ", 200 - Columns.LEFT_BORDER, 138);
		screen.drawString("Figure Right:    ", 200 - Columns.LEFT_BORDER, 148);
		screen.drawString("Level High/Low: +/-", 200 - Columns.LEFT_BORDER, 158);
		screen.drawString("Drop Figure:   space", 200 - Columns.LEFT_BORDER, 168);
		screen.drawString("Pause:           P", 200 - Columns.LEFT_BORDER, 180);
		screen.drawString("Quit:     Esc or Q", 200 - Columns.LEFT_BORDER, 190);
	}

	void showLevel(Columns columns, Graphics g) {
		screen.setColor(Color.black);
		screen.clearRect(Columns.LEFT_BORDER + 100, 390, 100, 20);
		screen.drawString("Level: " + columns.board.Level, Columns.LEFT_BORDER + 100, 400);
	}

	void showScore(Columns columns, Graphics g) {
		screen.setColor(Color.black);
		screen.clearRect(Columns.LEFT_BORDER, 390, 100, 20);
		screen.drawString("Score: " + columns.board.Score, Columns.LEFT_BORDER, 400);
	}


}
