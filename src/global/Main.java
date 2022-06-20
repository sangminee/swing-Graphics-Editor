package global;

import frames.MainFrame;

public class Main {

	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame(); 
		mainFrame.setVisible(true); // visible : 메인프레임에 연결된 모든 그래픽스를 계산을 해줌 -> 위치가 매우 중요하다. -> 어느위치에 그림을 그릴지!
		mainFrame.initialize();
	}
	
	// new -> Constructor -> initialize
}
