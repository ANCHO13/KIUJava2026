package columns;


public class Board {

	int newField[][];
	int oldField[][];
	int Level;
	long Score;
	long DScore;
	int figuresMatchedCounter;
	boolean noChanges = true;
	Columns columns;
	Figure figure;

	void initBoard(Columns columns) {
		this.columns = columns;
		for (int c = 0; c < Columns.WIDTH + 1; c++) {
			for (int r = 0; r < Columns.DEPTH + 1; r++) {
				newField[c][r] = 0;
				oldField[c][r] = 0;
			}
		}
		Level = 0;
		Score = 0;
		figuresMatchedCounter = 0;
	}

	void copyFieldsNew2Old(Columns columns) {
		int i;
		int j;
		for (i = 1; i <= Columns.DEPTH; i++) {
			for (j = 1; j <= Columns.WIDTH; j++) {
				oldField[j][i] = newField[j][i];
			}
		}
	}

	void dropFigure(Columns columns, Figure f) {
		int zz;
		if (f.y < Columns.DEPTH - 2) {
			zz = Columns.DEPTH;
			while (newField[f.x][zz] > 0) {
				zz--;
			}
			DScore = (((Level + 1) * (Columns.DEPTH * 2 - f.y - zz) * 2) % 5) * 5;
			f.y = zz - 2;
		}
	}

	boolean fullField(Columns columns) {
		int i;
		for (i = 1; i <= Columns.WIDTH; i++) {
			if (newField[i][3] > 0) {
				return true;
			}
		}
		return false;
	}

	void initFields(Columns columns) {
		newField = new int[Columns.WIDTH + 2][Columns.DEPTH + 2];
		oldField = new int[Columns.WIDTH + 2][Columns.DEPTH + 2];
	}

	void pasteFigure(Columns columns, Figure f) {
		newField[f.x][f.y] = f.c[1];
		newField[f.x][f.y + 1] = f.c[2];
		newField[f.x][f.y + 2] = f.c[3];
	}

	void changeLevelIfNeeded(Columns columns) {
		if (figuresMatchedCounter >= Columns.NEXT_LEVEL_THRESHOLD) {
			figuresMatchedCounter = 0;
			if (Level < Columns.MAX_LEVEL) {
				Level = Level + 1;
			}
			columns.view.showLevel(columns, columns.graphics);
		}
	}

	void packField(Columns columns) {
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

	void checkNeighbours(Columns columns, int a, int b, int c, int d, int i, int j) {
		if ((newField[j][i] == newField[a][b]) && (newField[j][i] == newField[c][d])) {
			oldField[a][b] = 0;
			columns.view.drawBox(a, b, 8);
			oldField[j][i] = 0;
			columns.view.drawBox(j, i, 8);
			oldField[c][d] = 0;
			columns.view.drawBox(c, d, 8);
			noChanges = false;
			Score = Score + (Level + 1) * 10;
			figuresMatchedCounter = figuresMatchedCounter + 1;
		}
		;
	}

	void collapse(Columns columns) {
		packField(columns);
		columns.view.drawField(newField, columns.graphics);
		Score = Score + DScore;
		columns.view.showScore(columns, columns.graphics);
		changeLevelIfNeeded(columns);
	}

	void findMatches(Columns columns) {
		int i, j;
		copyFieldsNew2Old(columns);
		for (i = 1; i <= Columns.DEPTH; i++) {
			for (j = 1; j <= Columns.WIDTH; j++) {
				if (newField[j][i] > 0) {
					checkNeighbours(columns, j, i - 1, j, i + 1, i, j);
					checkNeighbours(columns, j - 1, i, j + 1, i, i, j);
					checkNeighbours(columns, j - 1, i - 1, j + 1, i + 1, i, j);
					checkNeighbours(columns, j + 1, i - 1, j - 1, i + 1, i, j);
				}
			}
		}
	}

	boolean figureMayMoveDown(Columns columns) {
		return (figure.y < Columns.DEPTH - 2) && (newField[figure.x][figure.y + 3] == 0);
	}

	boolean canMoveLeft(Columns columns) {
		return (figure.x > 1) && (newField[figure.x - 1][figure.y + 2] == 0);
	}

	boolean canMoveRight(Columns columns) {
		return (figure.x < Columns.WIDTH) && (newField[figure.x + 1][figure.y + 2] == 0);
	}

}
