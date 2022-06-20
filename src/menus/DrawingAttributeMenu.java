package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import frames.DrawingPanel;

public class DrawingAttributeMenu extends JMenu {
	
private static final long serialVersionUID = 1L;
	
	private DrawingPanel drawingPanel;

	public DrawingAttributeMenu(String title) {
		super(title);
		
		ActionHandler actionHandler = new ActionHandler();
		
		JMenuItem colorMenuItem = new JMenuItem("���� ��");
		colorMenuItem.addActionListener(actionHandler);
		this.add(colorMenuItem);
		
		JMenuItem lineSize = new JMenuItem("���� ũ��");
		lineSize.addActionListener(actionHandler);
		this.add(lineSize);
		
		JMenuItem fillShape = new JMenuItem("ä���");
		fillShape.addActionListener(actionHandler);
		this.add(fillShape);
	}

	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	public void changeShapeDraw(Color selectedColor) {
		this.drawingPanel.changeShapeDraw(selectedColor);
	}
	public void changeShapeLineSize(float f) {
		this.drawingPanel.changeShapeLineSize(f);
	}

	public void changeShapeFill(Color selectedColor) {
		this.drawingPanel.setShapefillColor(selectedColor);		
	}
	
	private class ActionHandler implements ActionListener {

		JColorChooser chooser = new JColorChooser();
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if((e.getActionCommand()).equals("���� ��")) {
				@SuppressWarnings("static-access")
				Color selectedColor = chooser.showDialog(null, "Color", Color.YELLOW); 
				System.out.println(selectedColor);
				changeShapeDraw(selectedColor);
				
				if(selectedColor == null) {
					changeShapeDraw(Color.black);
				}
			}else if((e.getActionCommand()).equals("���� ũ��")){
				JOptionPane aa=new JOptionPane();
				@SuppressWarnings("static-access")
				String numbere = aa.showInputDialog("���� ũ�⸦ �Է��ϼ���.");
				
				// �Է°��� ""�϶� ���� 
				if(numbere != null) {
					float number=Integer.parseInt(numbere);
					changeShapeLineSize(number);
				}else {
					System.out.println("�Է��� �ȵǾ����ϴ�.");
				}

			}else if((e.getActionCommand()).equals("ä���")) {
				@SuppressWarnings("static-access")
				Color selectedColor = chooser.showDialog(null, "Color", Color.YELLOW); 
				changeShapeFill(selectedColor);
			}

		}
	}
	
	
}
