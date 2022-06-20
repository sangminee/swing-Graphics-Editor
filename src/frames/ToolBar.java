package frames;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import global.Constants.EColorMenu;
import global.Constants.ETools;
import java.awt.*;

public class ToolBar extends JToolBar { // ToolBar : ��ư implements ActionListener
	private static final long serialVersionUID = 1L;

	private DrawingPanel drawingPanel; 
	
	private JRadioButton selectedColor;
//	private JTextArea jTextArea;
	
	public ToolBar() {
		ButtonGroup buttonGroup = new ButtonGroup(); 
		ActionHandler actionHandler = new ActionHandler(); // �׼Ǹ����� - �̺�Ʈ �ڵ鷯�� ��ư�� ���� // �̺�Ʈ �ڵ鷯�� ��ư�� ���� ���δ�.
		
		for(ETools eTool: ETools.values()) {
			JRadioButton drawingTool;
			if(eTool == ETools.eSelection) {
				drawingTool = new JRadioButton(eTool.getLabel());
			}else {
				drawingTool = new JRadioButton(eTool.getImg());
			}
					
			drawingTool.setActionCommand(eTool.name());  // �۾��� ����� �� �ִ�.  - String���� variable�̴�.
			drawingTool.addActionListener(actionHandler);

			drawingTool.setToolTipText(eTool.getToolTip());
			
			this.add(drawingTool);  
			buttonGroup.add(drawingTool);
		}

//		JRadioButton text = new JRadioButton("T");
//		text.addActionListener(actionHandler);
//		this.add(text);  
//		buttonGroup.add(text);
		
//		this.jTextArea = new JTextArea();
//		this.jTextArea.setBounds(50, 50, 300, 300);
//		this.add(this.jTextArea);

	}
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel; 
		JRadioButton defaultButton = (JRadioButton) this.getComponent(ETools.eSelection.ordinal());	// downCasting
		defaultButton.doClick();  
	}
	

	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	
	public void changeShapeDraw(Color selectedColor) {
		this.drawingPanel.changeShapeDraw(selectedColor);
	}
	
	public void changeButtonColor(ETools eTools) {
		JRadioButton clickedButton = (JRadioButton) this.getComponent(eTools.ordinal());
		clickedButton.setBackground(new Color(204, 204, 204));

//		if(color != null) {
//			JRadioButton button = (JRadioButton) this.selectedColor;
//			button.setBackground(new Color(230, 230, 230));		
//		}
		ETools[] arr = ETools.values();
		for(ETools rb : arr) {		
			if(rb != eTools) {
				JRadioButton button = (JRadioButton) this.getComponent(rb.ordinal());
				button.setBackground(new Color(230, 230, 230));			
			}
		}
	}
	
	private class ActionHandler implements ActionListener {
		JColorChooser chooser = new JColorChooser();

		@Override
		public void actionPerformed(ActionEvent e) {
			// getSource() : �̺�Ʈ�� �߻���Ų ��ü�� ��ġ���� ������
			// getActionCommand() : �̺�Ʈ�� �߻���Ų ��ü�� ���ڿ��� ������

			if (ETools.valueOf(e.getActionCommand()) != null) {
				changeButtonColor(ETools.valueOf(e.getActionCommand()));
				drawingPanel.setSelectedTool(ETools.valueOf(e.getActionCommand()));

			}

		}

	}

}
