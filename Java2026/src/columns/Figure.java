package columns;

import java.util.Random;


class Figure {

	static Random r = new Random();

	int x = Columns.WIDTH / 2 + 1, y = 1, c[] = new int[4];

	Figure()
	{
		x = Columns.WIDTH / 2 + 1;
		y = 1;
		c[0] = 0;
		c[1] = Math.abs(r.nextInt())%7+1;
		c[2] = Math.abs(r.nextInt())%7+1;
		c[3] = Math.abs(r.nextInt())%7+1;
	}

	void moveRight() {
		x++;
	}

	void moveLeft() {
		x--;
	}

	void rotateUp() {
		int t = c[1];
		c[1] = c[2];
		c[2] = c[3];
		c[3] = t;
	}

	void rotateDown() {
		int t = c[1];
		c[1] = c[3];
		c[3] = c[2];
		c[2] = t;
	}

	void moveDown() {
		y++;
	}
}