package columns;

import java.awt.Color;
import java.awt.Graphics;


public class AppletScreen implements Screen {

	private Graphics g;

	public AppletScreen(Graphics g) {
		this.g = g;
	}

	@Override
	public void setColor(Color color) {
		g.setColor(color);
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

}
