package global;

import frames.MainFrame;

public class Main {

	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame(); 
		mainFrame.setVisible(true); // visible : ���������ӿ� ����� ��� �׷��Ƚ��� ����� ���� -> ��ġ�� �ſ� �߿��ϴ�. -> �����ġ�� �׸��� �׸���!
		mainFrame.initialize();
	}
	
	// new -> Constructor -> initialize
}
