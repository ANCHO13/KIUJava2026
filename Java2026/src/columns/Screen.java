package columns;

import java.awt.Color;

public interface Screen {

	void setColor(Color color);

	void fillRect(int x, int y, int width, int height);

	void drawRect(int x, int y, int width, int height);

	void drawString(String string, int x, int y);

	void clearRect(int x, int y, int width, int height);

}
