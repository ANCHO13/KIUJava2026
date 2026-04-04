package columns.model;


public interface ModelListener {

	void levelHasChanged(int level);

	void hightlightTriplet(int a, int b, int c, int d, int i, int j);

	void fieldWasUpdated(int[][] newField);

	void scoreUpdated(long score);

}
