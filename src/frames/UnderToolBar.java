package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class UnderToolBar extends JToolBar implements ChangeListener { // ToolBar : ��ư implements ActionListener
	private static final long serialVersionUID = 1L;

	private DrawingPanel drawingPanel; 
	
	private JSlider slider;
	public static final int INIT_VAL = 15;
	
	public UnderToolBar() {
		ActionHandler actionHandler = new ActionHandler(); // �׼Ǹ����� - �̺�Ʈ �ڵ鷯�� ��ư�� ���� // �̺�Ʈ �ڵ鷯�� ��ư�� ���� ���δ�.
		
		slider = new JSlider(0, 100, INIT_VAL);
		
		slider.setMajorTickSpacing(10); 
		slider.setMinorTickSpacing(5); 
		
		slider.setPaintTicks(true); //������ ǥ���Ѵ�.
		slider.setPaintLabels(true); //���� ���̺�� ǥ���Ѵ�.
		
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
			
//			button.setSize(val*5, val*5); //�����̴� ���� ���� ��ư ũ�� ����
		}
	}
	
	private class ActionHandler implements ActionListener {
		JColorChooser chooser = new JColorChooser();

		@Override
		public void actionPerformed(ActionEvent e) {
			// getSource() : �̺�Ʈ�� �߻���Ų ��ü�� ��ġ���� ������
			// getActionCommand() : �̺�Ʈ�� �߻���Ų ��ü�� ���ڿ��� ������

		}

	}

}
