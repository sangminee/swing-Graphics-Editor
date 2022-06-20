package frames;

import javax.swing.JMenuBar;

import menus.DrawingAttributeMenu;
import menus.EditMenu;
import menus.FileMenu;

import java.awt.*;

public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	private FileMenu fileMenu; 
	private EditMenu editMenu;
	private DrawingAttributeMenu colorMenu;

	private DrawingPanel drawingPanel;
	
	public MenuBar() {
		this.fileMenu = new FileMenu("����");
		this.add(this.fileMenu);
			
		this.editMenu = new EditMenu("����");
		this.add(this.editMenu);
		
		this.colorMenu = new DrawingAttributeMenu("�Ӽ�");
		this.add(this.colorMenu);
		
	}

	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel; 	
		this.fileMenu.associate(this.drawingPanel);
		this.editMenu.associate(this.drawingPanel);
		this.colorMenu.associate(this.drawingPanel);
	}

	public void initialize() {
		this.fileMenu.initialize();
		this.editMenu.initialize();
		
	}

}
