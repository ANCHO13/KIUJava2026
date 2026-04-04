package columns.model;

import columns.Columns;

public class View {

	private Screen screen;

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public void drawBox(int x, int y, int c) {
		if (c == 0) {
			screen.setColor(Black());
			screen.fillRect(Columns.LEFT_BORDER + x * Columns.BOX_SIZE - Columns.BOX_SIZE, Columns.TOP_BORDER + y * Columns.BOX_SIZE - Columns.BOX_SIZE, Columns.BOX_SIZE,
					Columns.BOX_SIZE);
			screen.drawRect(Columns.LEFT_BORDER + x * Columns.BOX_SIZE - Columns.BOX_SIZE, Columns.TOP_BORDER + y * Columns.BOX_SIZE - Columns.BOX_SIZE, Columns.BOX_SIZE,
					Columns.BOX_SIZE);
		} else if (c == 8) {
			screen.setColor(screen.White());
			screen.drawRect(Columns.LEFT_BORDER + x * Columns.BOX_SIZE - Columns.BOX_SIZE + 1, Columns.TOP_BORDER + y * Columns.BOX_SIZE - Columns.BOX_SIZE + 1,
					Columns.BOX_SIZE - 2, Columns.BOX_SIZE - 2);
			screen.drawRect(Columns.LEFT_BORDER + x * Columns.BOX_SIZE - Columns.BOX_SIZE + 2, Columns.TOP_BORDER + y * Columns.BOX_SIZE - Columns.BOX_SIZE + 2,
					Columns.BOX_SIZE - 4, Columns.BOX_SIZE - 4);
			screen.setColor(Black());
			screen.fillRect(Columns.LEFT_BORDER + x * Columns.BOX_SIZE - Columns.BOX_SIZE + 3, Columns.TOP_BORDER + y * Columns.BOX_SIZE - Columns.BOX_SIZE + 3,
					Columns.BOX_SIZE - 6, Columns.BOX_SIZE - 6);
		} else {
			screen.setColor(c);
			screen.fillRect(Columns.LEFT_BORDER + x * Columns.BOX_SIZE - Columns.BOX_SIZE, Columns.TOP_BORDER + y * Columns.BOX_SIZE - Columns.BOX_SIZE, Columns.BOX_SIZE,
					Columns.BOX_SIZE);
			screen.setColor(Black());
			screen.drawRect(Columns.LEFT_BORDER + x * Columns.BOX_SIZE - Columns.BOX_SIZE, Columns.TOP_BORDER + y * Columns.BOX_SIZE - Columns.BOX_SIZE, Columns.BOX_SIZE,
					Columns.BOX_SIZE);
		}
		// g.setColor (Color.black);
	}

	int Black() {
		return screen.Black();
	}

	public void drawFigure(Figure f) {
		drawBox(f.x, f.y, f.c[1]);
		drawBox(f.x, f.y + 1, f.c[2]);
		drawBox(f.x, f.y + 2, f.c[3]);
	}

	public void drawField(int[][] field) {
		for (int i = 1; i <= Columns.DEPTH; i++) {
			for (int j = 1; j <= Columns.WIDTH; j++) {
				drawBox(j, i, field[j][i]);
			}
		}
	}

	public void hideFigure(Figure f) {
		drawBox(f.x, f.y, 0);
		drawBox(f.x, f.y + 1, 0);
		drawBox(f.x, f.y + 2, 0);
	}

	public void showHelp() {
		screen.setColor(Black());
	
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

	public void showLevel(int level) {
		screen.setColor(Black());
		screen.clearRect(Columns.LEFT_BORDER + 100, 390, 100, 20);
		screen.drawString("Level: " + level, Columns.LEFT_BORDER + 100, 400);
	}

	public void showScore(long score) {
		screen.setColor(Black());
		screen.clearRect(Columns.LEFT_BORDER, 390, 100, 20);
		screen.drawString("Score: " + score, Columns.LEFT_BORDER, 400);
	}


}
