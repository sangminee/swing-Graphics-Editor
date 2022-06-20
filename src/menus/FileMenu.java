package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import frames.DrawingPanel;
import global.Constants.EFileMenu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	private File file; 
	private DrawingPanel drawingPanel;

	public FileMenu(String title) {
		super(title);

		this.file = null; // ���� null�� �� ������ ���������� 
		
		KeyStroke key;
		
		ActionHandler actionHandler = new ActionHandler();
		for (EFileMenu eMenuItem : EFileMenu.values()) {
			JMenuItem MenuItem = new JMenuItem(eMenuItem.getLabel());
			MenuItem.setActionCommand(eMenuItem.name());
			
			if(eMenuItem.equals(eMenuItem.eNew)) {
				MenuItem.setMnemonic(KeyEvent.VK_N);
				key = KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK);
				MenuItem.setAccelerator(key);
			}
			if(eMenuItem.equals(eMenuItem.eSave)) {
				MenuItem.setMnemonic(KeyEvent.VK_S);
				key = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK);
				MenuItem.setAccelerator(key);
			}
			if(eMenuItem.equals(eMenuItem.eSaveAs)) {
				MenuItem.setMnemonic(KeyEvent.VK_F12);
				key = KeyStroke.getKeyStroke(KeyEvent.VK_F12, ActionEvent.ALT_MASK);
				MenuItem.setAccelerator(key);
			}
			if(eMenuItem.equals(eMenuItem.eOpen)) {
				MenuItem.setMnemonic(KeyEvent.VK_F);
				key = KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK);
				MenuItem.setAccelerator(key);
			}
			if(eMenuItem.equals(eMenuItem.eQuit)) {
				MenuItem.setMnemonic(KeyEvent.VK_F4);
				key = KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK);
				MenuItem.setAccelerator(key);
			}
			
			MenuItem.addActionListener(actionHandler);
			this.add(MenuItem); 
		}
	}

	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	private void load(File file) {
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			ObjectInputStream objectOutputStream = new ObjectInputStream(fileInputStream); // object�� byte ��Ʈ������ �ٲ��ִ�

			Object object = objectOutputStream.readObject();
			
			// ���� â �ݰ� ���� â ����
			this.drawingPanel.saveShapes();
			this.drawingPanel.setShapes(object);
			objectOutputStream.close();

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void store(File file) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream); // object�� byte ��Ʈ������ �ٲ��ִ�
			
			objectOutputStream.writeObject(this.drawingPanel.getShapes());
			
			objectOutputStream.writeObject(this.drawingPanel.getImges());
			objectOutputStream.close();

			JOptionPane.showMessageDialog(null, "����Ǿ����ϴ�!");
			this.drawingPanel.setUpdated(false);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void newPanel() {
		// ��â�� ���� ���� ������ ������
		if (this.drawingPanel.isUpdated() == false) {
			int result0 = JOptionPane.showConfirmDialog(null, "��â�� ���ðڽ��ϱ�?", "Confirm", JOptionPane.YES_NO_OPTION);
			if (result0 == JOptionPane.YES_OPTION) {		
				this.drawingPanel.closeFile();
				this.file = null;
			}
		} else {
			// �̹� �׸��� ������(isUpdated�� true �� ��)
			int result1 = JOptionPane.showConfirmDialog(null, "�׸��� �����մϴ�. �����Ͻðڽ��ϱ�?", "Confirm",
					JOptionPane.YES_NO_OPTION);
			if (result1 == JOptionPane.YES_OPTION) {
				save();
				this.drawingPanel.setUpdated(false);
				newPanel();
			}else if(result1 == JOptionPane.NO_OPTION) {
				int result0 = JOptionPane.showConfirmDialog(null,
						"�������� �ʰ� ��â�� ���ðڽ��ϱ�?", "Confirm", JOptionPane.YES_NO_OPTION);
				if (result0 == JOptionPane.YES_OPTION) {		
					this.drawingPanel.closeFile();
					this.drawingPanel.setUpdated(false);
					this.file = null;
				}
			}
		}
	}

	private void open() {
		if(this.drawingPanel.isUpdated() == false) {
			JFileChooser chooser = new JFileChooser();
			String path = "C:\\ECLIPSE\\ShapeSave";
			File Folder = new File(path);
			chooser.setCurrentDirectory(Folder);
			int returnVal = chooser.showOpenDialog(this.drawingPanel);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.file = chooser.getSelectedFile();
				this.load(this.file);
			}
		}else {
			// �׸��� ������ �� 
			JOptionPane.showMessageDialog(null, "�׸��� �����մϴ�.");
		}
	}

	public void save() {
		if (this.drawingPanel.isUpdated() == false) {
			JOptionPane.showMessageDialog(null, "������ �׸��� �����ϴ�.");
		} else {
			// �̹� �׸��� ������(isUpdated�� true �� ��)
			int result = JOptionPane.showConfirmDialog(null, 
					"�����Ͻðڽ��ϱ�?", "Confirm",
					JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.CLOSED_OPTION) {
			} else if (result == JOptionPane.YES_OPTION) {
		
				if (this.file == null) {
					this.saveAs();				
				} else {
					this.store(this.file);
				}
			}
		}
	}

	private void saveAs() {
		String path = "C:\\ECLIPSE\\ShapeSave";
		File Folder = new File(path);
		
		if(!Folder.exists()) {
			Folder.mkdir();
			System.out.println("������ �����Ǿ����ϴ�.");
		}else {
//			System.out.println("������ �����մϴ�.");
		}
		
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(Folder);
		int returnVal = chooser.showSaveDialog(this.drawingPanel);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.file = chooser.getSelectedFile();
			this.store(this.file);
		}
	}

	// �׸��� ���� ����
	private void quit() {
		int result = JOptionPane.showConfirmDialog(null, 
				"�׸����� �����Ͻðڽ��ϱ�?", "Confirm", JOptionPane.YES_NO_OPTION);
		if(this.drawingPanel.isUpdated() == true) {
			int result1 = JOptionPane.showConfirmDialog(null, 
					"�׸��� �����մϴ�.�׷��� �����Ͻðڽ��ϱ�?", "Confirm", JOptionPane.YES_NO_OPTION);
			if (result1 == JOptionPane.CLOSED_OPTION) {
			} else if (result1 == JOptionPane.YES_OPTION) {
				System.exit(0);
			} else {
				JOptionPane.showMessageDialog(null, "��ҵǾ����ϴ�.");
			}
			
		}else {
			if (result == JOptionPane.CLOSED_OPTION) {
			} else if (result == JOptionPane.YES_OPTION) {
				System.exit(0);
			} else {
				JOptionPane.showMessageDialog(null, "��ҵǾ����ϴ�.");
			}
		}
	}

	// �̹��� �߰� 
	public void addImg() {
		this.drawingPanel.addImg();
	}
	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals(EFileMenu.eNew.name())) {
				newPanel();
			} else if (e.getActionCommand().equals(EFileMenu.eOpen.name())) {
				open();
			}else if (e.getActionCommand().equals(EFileMenu.eSave.name())) {
				save();
			} else if (e.getActionCommand().equals(EFileMenu.eSaveAs.name())) {
				saveAs();
			} else if (e.getActionCommand().equals(EFileMenu.eQuit.name())) {
				quit();
			} else if (e.getActionCommand().equals(EFileMenu.eImg.name())) {
				addImg();
			}
		}

	}

}
