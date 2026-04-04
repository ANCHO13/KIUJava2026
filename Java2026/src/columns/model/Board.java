package columns.model;

import columns.Columns;

public class Board {

	public int newField[][];
	int oldField[][];
	public int level;
	public long Score;
	public long DScore;
	public int figuresMatchedCounter;
	public boolean noChanges = true;
	public Figure figure;

	private ModelListener listener;

	public void setModelListener(ModelListener listener) {
		this.listener = listener;
	}

	public void initBoard() {
		for (int c = 0; c < Columns.WIDTH + 1; c++) {
			for (int r = 0; r < Columns.DEPTH + 1; r++) {
				newField[c][r] = 0;
				oldField[c][r] = 0;
			}
		}
		level = 0;
		Score = 0;
		figuresMatchedCounter = 0;
	}

	void copyFieldsNew2Old() {
		int i;
		int j;
		for (i = 1; i <= Columns.DEPTH; i++) {
			for (j = 1; j <= Columns.WIDTH; j++) {
				oldField[j][i] = newField[j][i];
			}
		}
	}

	public void dropFigure(Figure f) {
		int zz;
		if (f.y < Columns.DEPTH - 2) {
			zz = Columns.DEPTH;
			while (newField[f.x][zz] > 0) {
				zz--;
			}
			DScore = (((level + 1) * (Columns.DEPTH * 2 - f.y - zz) * 2) % 5) * 5;
			f.y = zz - 2;
		}
	}

	public void initFields() {
		newField = new int[Columns.WIDTH + 2][Columns.DEPTH + 2];
		oldField = new int[Columns.WIDTH + 2][Columns.DEPTH + 2];
	}

	public void pasteFigure(Figure f) {
		newField[f.x][f.y] = f.c[1];
		newField[f.x][f.y + 1] = f.c[2];
		newField[f.x][f.y + 2] = f.c[3];
	}

	void changeLevelIfNeeded() {
		if (figuresMatchedCounter >= Columns.NEXT_LEVEL_THRESHOLD) {
			figuresMatchedCounter = 0;
			if (level < Columns.MAX_LEVEL) {
				level = level + 1;
			}
			listener.levelHasChanged(level);
		}
	}

	void packField() {
		int i, j, n;
		for (i = 1; i <= Columns.WIDTH; i++) {
			n = Columns.DEPTH;
			for (j = Columns.DEPTH; j > 0; j--) {
				if (oldField[i][j] > 0) {
					newField[i][n] = oldField[i][j];
					n--;
				}
			}
			;
			for (j = n; j > 0; j--) {
				newField[i][j] = 0;
			}
		}
	}

	void checkNeighbours(int a, int b, int c, int d, int i, int j) {
		if ((newField[j][i] == newField[a][b]) && (newField[j][i] == newField[c][d])) {
			oldField[a][b] = 0;
			oldField[j][i] = 0;
			oldField[c][d] = 0;
			listener.hightlightTriplet(a, b, c, d, i, j);
			noChanges = false;
			Score = Score + (level + 1) * 10;
			figuresMatchedCounter = figuresMatchedCounter + 1;
		}
		;
	}

	public void collapse() {
		packField();
		listener.fieldWasUpdated(newField);
		Score = Score + DScore;
		listener.scoreUpdated(Score);
		changeLevelIfNeeded();
	}

	public void findMatches() {
		int i, j;
		copyFieldsNew2Old();
		for (i = 1; i <= Columns.DEPTH; i++) {
			for (j = 1; j <= Columns.WIDTH; j++) {
				if (newField[j][i] > 0) {
					checkNeighbours(j, i - 1, j, i + 1, i, j);
					checkNeighbours(j - 1, i, j + 1, i, i, j);
					checkNeighbours(j - 1, i - 1, j + 1, i + 1, i, j);
					checkNeighbours(j + 1, i - 1, j - 1, i + 1, i, j);
				}
			}
		}
	}

	public boolean figureMayMoveDown() {
		return (figure.y < Columns.DEPTH - 2) && (newField[figure.x][figure.y + 3] == 0);
	}

	public boolean canMoveLeft() {
		return (figure.x > 1) && (newField[figure.x - 1][figure.y + 2] == 0);
	}

	public boolean canMoveRight() {
		return (figure.x < Columns.WIDTH) && (newField[figure.x + 1][figure.y + 2] == 0);
	}

}
