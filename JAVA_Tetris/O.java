package JAVA_Tetris;

public class O extends Piece {
	public O (TetrisData data) {
	      super(data);
	      c[0] = 0; r[0] = 0;
	      c[1] = 0; r[1] = 1;
	      c[2] = 1; r[2] = 1;
	      c[3] = 1; r[3] = 0;
	       
	   }

	public int getType() {    // 
		return 4;
	}

	public int roteType() {   // 회전을 했을 때 블럭 갯수의 경우의 수
		return 0;
	}
}
