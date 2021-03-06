package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

import frames.DrawingPanel;
import global.Constants.EEditMenu;

public class EditMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	private DrawingPanel drawingPanel;

	public EditMenu(String title) {
		super(title);
		
		KeyStroke key;
		
		ActionHandler actionHandler = new ActionHandler();
		for (EEditMenu eEditMenu : EEditMenu.values()) {
			JMenuItem EditMenu = new JMenuItem(eEditMenu.getLabel());
			EditMenu.setActionCommand(eEditMenu.name());
			
			if(eEditMenu.equals(eEditMenu.eUndo)) {
				EditMenu.setMnemonic(KeyEvent.VK_Z);
				key = KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK);
				EditMenu.setAccelerator(key);
			}
			
			if(eEditMenu.equals(eEditMenu.eRedo)) {
				EditMenu.setMnemonic(KeyEvent.VK_Y);
				key = KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK);
				EditMenu.setAccelerator(key);
			}
			
			if(eEditMenu.equals(eEditMenu.eDeleteOne)) {
				EditMenu.setMnemonic(KeyEvent.VK_X);
				key = KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK);
				EditMenu.setAccelerator(key);
			}
			
			if(eEditMenu.equals(eEditMenu.eCopy)) {
				EditMenu.setMnemonic(KeyEvent.VK_C);
				key = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK);
				EditMenu.setAccelerator(key);
			}
			
			if(eEditMenu.equals(eEditMenu.ePaste)) {
				EditMenu.setMnemonic(KeyEvent.VK_V);
				key = KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK);
				EditMenu.setAccelerator(key);
			}
			
			if(eEditMenu.equals(eEditMenu.eGroup)) {
				EditMenu.setMnemonic(KeyEvent.VK_A);
				key = KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK);
				EditMenu.setAccelerator(key);
			}
			
			if(eEditMenu.equals(eEditMenu.eUnGroup)) {
				EditMenu.setMnemonic(KeyEvent.VK_G);
				key = KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK);
				EditMenu.setAccelerator(key);
			}

			EditMenu.addActionListener(actionHandler);
			this.add(EditMenu); // ???????? ?????????? ???? ???? ?????? ?????? ????
		}
		
	}

	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	public void initialize() {

	}

	// ????????
	public void undo() {
		this.drawingPanel.undoShape();
	}

	// ???? ????
	public void redo() {
		this.drawingPanel.redoShape();
	}

	// ???? ????????
	public void delete() {
		int result = JOptionPane.showConfirmDialog(null, "???????? ?????????????", "Confirm", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			this.drawingPanel.clearShapes();
			JOptionPane.showMessageDialog(null, "???????? ??????????.");
			this.drawingPanel.setUpdated(false);
		} else {
			JOptionPane.showMessageDialog(null, "?????????? ??????????????.");
		}
	}

	// ????????
	public void copy() {
		this.drawingPanel.copy();
	}

	public void paste() {
		this.drawingPanel.paste();
	}
	
	// ????????
	public void deleteOne() {
		this.drawingPanel.delete();		
	}
	
	public void groupAll() {
		this.drawingPanel.groupAll();	
	}
	
	public void unGroup() {
		this.drawingPanel.unGroup();		
	}
	
	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals(EEditMenu.eUndo.name())) { // Undo : ?? ???? ???? (???? ??????)
				undo();
			} else if (e.getActionCommand().equals(EEditMenu.eRedo.name())) { // ?? ?? ????
				redo();
			} else if (e.getActionCommand().equals(EEditMenu.eDelete.name())) {
				delete();
			} else if (e.getActionCommand().equals(EEditMenu.eCopy.name())) {
				copy();			
			} else if (e.getActionCommand().equals(EEditMenu.ePaste.name())) {
				paste();
			} else if (e.getActionCommand().equals(EEditMenu.eDeleteOne.name())) {
				deleteOne();
			} else if(e.getActionCommand().equals(EEditMenu.eGroup.name())) {
				groupAll();
			}else if(e.getActionCommand().equals(EEditMenu.eUnGroup.name())) {
				unGroup();
			}
		}
	}

}
