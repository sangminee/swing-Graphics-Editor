package shapes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class AddImg implements Serializable{

	private BufferedImage img;
	
	public AddImg() {
		
	}
	
	public BufferedImage addImg() {
//		BufferedImage img = null;
		try {
			
			JFileChooser chooser = new JFileChooser();
			int ret = chooser.showOpenDialog(null);
			
			if(ret!=JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "��θ� �������� �ʾҽ��ϴ�.","���",
						JOptionPane.WARNING_MESSAGE);
			}
					
			String filePath = chooser.getSelectedFile().getPath();
			System.out.println(filePath);
			img = ImageIO.read(new File(filePath));
			
			img.getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH);
			
			System.out.println(img);

			return img;
		} catch (IOException e) {
		}
		
		return null;
		
	}

}
