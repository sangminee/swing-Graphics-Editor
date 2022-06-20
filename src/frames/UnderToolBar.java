package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class UnderToolBar extends JToolBar implements ChangeListener { // ToolBar : 버튼 implements ActionListener
	private static final long serialVersionUID = 1L;

	private DrawingPanel drawingPanel; 
	
	private JSlider slider;
	public static final int INIT_VAL = 15;
	
	public UnderToolBar() {
		ActionHandler actionHandler = new ActionHandler(); // 액션리슨너 - 이벤트 핸들러를 버튼에 붙임 // 이벤트 핸들러를 버튼에 직접 붙인다.
		
		slider = new JSlider(0, 100, INIT_VAL);
		
		slider.setMajorTickSpacing(10); 
		slider.setMinorTickSpacing(5); 
		
		slider.setPaintTicks(true); //눈금을 표시한다.
		slider.setPaintLabels(true); //값을 레이블로 표시한다.
		
		slider.addChangeListener((ChangeListener) this);
		this.add(slider);
	}
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel; 

	}
	

	public void initialize() {
		// TODO Auto-generated method stub
	}
	
	public void changePanelSize(int val) {
		this.drawingPanel.changePanelSize(val);	
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider selectedSlide = (JSlider) e.getSource();

		if(!selectedSlide.getValueIsAdjusting()) {
			int val = (int) ((JSlider) e.getSource()).getValue();
			
			if(val == 0) {
				val = 1;
			}
			System.out.println(val);
			changePanelSize(val);
			
//			button.setSize(val*5, val*5); //슬라이더 값에 따라 버튼 크기 조절
		}
	}
	
	private class ActionHandler implements ActionListener {
		JColorChooser chooser = new JColorChooser();

		@Override
		public void actionPerformed(ActionEvent e) {
			// getSource() : 이벤트를 발생시킨 객체의 위치값을 가져옴
			// getActionCommand() : 이벤트를 발생시킨 객체의 문자열을 가져옴

		}

	}

}
