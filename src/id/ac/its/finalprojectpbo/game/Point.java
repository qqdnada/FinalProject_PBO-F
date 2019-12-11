package id.ac.its.finalprojectpbo.game;

//untuk posisi x dan y 
public class Point {
	//karna kolom x dan baris y, jadi bingung kalo pake normal point,
	//jd bikin point sendiri
	
	public int row;
	public int col;
	
	public Point(int row, int col) {
		this.row = row;
		this.col = col;
	}
}