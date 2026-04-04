package columns;

import java.awt.Graphics;

import columns.model.Screen;


public class AppletScreen implements Screen {

	private Graphics g;

	public AppletScreen(Graphics g) {
		this.g = g;
	}

	@Override
	public void setColor(int color) {
		g.setColor(Columns.COLORS[color]);
	}

	@Override
	public void fillRect(int x, int y, int width, int height) {
		g.fillRect(x, y, width, height);
	}

	@Override
	public void drawRect(int x, int y, int width, int height) {
		g.drawRect(x, y, width, height);

	}

	@Override
	public void drawString(String string, int x, int y) {
		g.drawString(string, x, y);
	}

	@Override
	public void clearRect(int x, int y, int width, int height) {
		g.clearRect(x, y, width, height);
	}

	@Override
	public int Black() {
		return 0;
	}

	@Override
	public int White() {
		return Columns.COLORS.length - 1;
	}

}
