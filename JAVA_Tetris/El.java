package JAVA_Tetris;

public class El extends Piece{
	public El(TetrisData data) {
		super(data);
		c[0] = 0; r[0] = 0;
		c[1] = -1; r[1] = 0;
		c[2] = -1; r[2] = 1;
		c[3] = 1; r[3] = 0;
		 
	}
	
	public int getType() {
		return 5;
	}
	public int roteType() {
		return 4;
	}
}

