package JAVA_Tetris;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TetrisCanvas extends JPanel implements Runnable, KeyListener {
		protected Thread worker;
		protected Color colors[];
		protected int w = 25;
		protected TetrisData data;
		protected int margin = 20;
		protected boolean stop, makeNew;
		protected Piece current1;
		protected Piece current2;
		protected Piece tempcurrent;
		protected int interval = 2000;
		protected int level = 2;
		
		public TetrisCanvas() {
			data = new TetrisData();
			
			addKeyListener(this);
			colors = new Color[8];  // 테트리스 배경 및 조각 색
			colors[0] = new Color(80, 80, 80);  // 배경색 (검은회색)
			colors[1] = new Color(255, 0, 0);  // 빨간색
			colors[2] = new Color(0, 255, 0);  // 녹색
			colors[3] = new Color(0, 200, 255);  // 하늘식
			colors[4] = new Color(255, 255, 0);  // 노란색 
			colors[5] = new Color(255, 150, 0);  // 황토색 
			colors[6] = new Color(210, 0, 240); // 보라색
			colors[7] = new Color(40, 0, 240);  // 파란색
	}
	
	public void start() {  // 게임 시작
		data.clear();
		worker = new Thread(this);
		worker.start();
		makeNew = true;
		stop = false;
		requestFocus();
		repaint();
	}
	
	public void stop() {
		stop = true;
		current1 = null;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		for (int i = 0; i < TetrisData.ROW; i++) {  // 쌓인 조각들 그리기
			
			for(int k = 0; k < TetrisData.COL; k++) {
				if(data.getAt(i, k) == 0) {
					g.setColor(colors[data.getAt(i, k)]);
					g.draw3DRect(margin/2 + w * k, margin/2 + w * i, w, w, true);
				} else {
					g.setColor(colors[data.getAt(i, k)]);
					g.fill3DRect(margin/2 + w * k, margin/2 + w * i, w, w, true);
				}
			}
		}
		
		if(current1 != null) {  // 현재 내려오고 있는 테트리스 조각 그리기
			for(int i = 0; i < 4; i++) {
				g.setColor(colors[current1.getType()]);
				g.fill3DRect(margin/2 + w * (current1.getX() + current1.c[i]), 
						margin/2 + w * (current1.getY() + current1.r[i]), w, w, true);
			}
		}
		if(current2 != null) {
			for(int i = 0; i<4;i++) {
				g.setColor(colors[current2.getType()]);
				g.fill3DRect(margin*11+w*(current2.getX() + current2.c[i]), margin*23+w*(current2.getY() + current2.r[i]),w, w, true);
			}
		}
	}
	
	public Dimension getPreferredSize() {  // 테트리스 판의 크기 지정
		int tw = w * TetrisData.COL + margin;
		int th = w * TetrisData.ROW + margin;
		return new Dimension(tw, th);
	}
	
	
	
	public void run() {
		while(!stop) {
			try {
				if(makeNew) {  // 새로운 테트리스 조각 만들기
					int random = (int) (Math.random() * Integer.MAX_VALUE) % 7;;
					
					switch(random) {
						case 0:
							current2 = new Bar(data);
							break;
						case 1:
							current2 = new Tee(data);
							break;
						case 2:
							current2 = new El(data);
							break;
						case 3:
							current2 = new Z(data);
							break;
						case 4:
							current2 = new O(data);
							break;
						case 5:
							current2 = new S(data);
							break;
						case 6:
							current2 = new J(data);
							break;
						default:
							if(random % 2 == 0)
								current2 = new Tee(data);
							else current2 = new El(data);
					}
					if(current1 == null) {
						current1 = current2;
						continue;
					}
					makeNew = false;
				} else {  // 현재 만들어진 테트리스 조각 아래로 이동
					if(current1.moveDown()) {
						makeNew = true;
						if (current1.copy()) {
							stop();
							int score = data.getLine() * 175 * level;
							JOptionPane.showMessageDialog(this, "게임끝\n점수 : " + score);
						}
						current1 = current2;
					}
					data.removeLines();
				}
				repaint();
				Thread.sleep(interval/level);
			}catch(Exception e) { }
		}
	}

	// 키보드를 이용해서 테트리스 조각 제어
	public void keyPressed(KeyEvent e) {
		if (current1 == null) return;
		
	     switch(e.getKeyCode()) {
	     	case 32 : // 스페이스 바
	     		current1.spacebar();
	     		repaint();
	     		break;
			case 37:  // 왼쪽 화살표
				current1.moveLeft();
				repaint();
				break;
			case 39:  // 오른쪽 화살표
				current1.moveRight();
				repaint();
				break;
			case 38:  // 윗쪽 화살표
				current1.rotate();
				repaint();
				break;
			case 40:  // 아랫쪽 화살표
				boolean temp = current1.moveDown();
				if(temp) {
					makeNew = true;
					if(current1.copy()) {
						stop();
						int score = data.getLine() * 175 * level;
						JOptionPane.showMessageDialog(this, "게임끝\n점수 : " + score);
					}
				}
				break;

		}
	}
	public void keyReleased(KeyEvent e) { }
	public void keyTyped(KeyEvent e) { }
}
