package global;

import java.awt.Image;

import javax.swing.ImageIcon;

import shapes.TArcOpen;
import shapes.TArcPie;
import shapes.TLine;
import shapes.TOval;
import shapes.TPath;
import shapes.TPolygon;
import shapes.TRectangle;
import shapes.TRoundRectangle;
import shapes.TSelection;
import shapes.TShape;

import java.awt.Color;

public class Constants {

	public enum ETransformationStyle{
		e2point, 
		eNpoint
	}
	
	public enum ETools{
		eSelection("����",new TSelection(),"Selection Tool",new ImageIcon("./Image/rectangle.png"),ETransformationStyle.e2point),  
		eRectanble("�׸�",new TRectangle(),"�׸�",new ImageIcon("./Image/rectangle.png"),ETransformationStyle.e2point),    
		eRoundRectangle("�����簢��",new TRoundRectangle(),"�����簢��",new ImageIcon("./Image/roundRectangle.png"),ETransformationStyle.e2point),
		eOval("���׶��" ,new TOval(),"���׶��",new ImageIcon("./Image/oval.png"),ETransformationStyle.e2point),  
		eLine("��",new TLine(),"��",new ImageIcon("./Image/line.png"),ETransformationStyle.e2point),  
		ePolygon("�ٰ���",new TPolygon(),"�ٰ���",new ImageIcon("./Image/polygon.png"),ETransformationStyle.eNpoint),
		ePath("��",new TPath(),"��",new ImageIcon("./Image/pen.png"),ETransformationStyle.e2point),

		eArcOpen("���� ȣ",new TArcOpen(),"���� ȣ",new ImageIcon("./Image/arcOpen.png"),ETransformationStyle.e2point),
		eArcPie("���� ȣ",new TArcPie(),"���� ȣ",new ImageIcon("./Image/arcPie.png"),ETransformationStyle.e2point);
		
		private String label;
		private TShape tool;
		private String toolTip;
		private ImageIcon img;
		private ETransformationStyle eTransformationStyle;

		private ETools(String lable, TShape tool,String toolTip,ImageIcon img,ETransformationStyle eTransformationStyle) {
			this.label = lable;
			this.tool = tool;		
			this.toolTip = toolTip;
			this.img = img;
			this.eTransformationStyle = eTransformationStyle;
		}
		
		public TShape newShape() {
			return this.tool.clone();  
		}
		
		public String getLabel() {
			return this.label;
		}
		
		public String getToolTip() {
			return this.toolTip;
		}
		
		public ImageIcon getImg() {
			Image image = this.img.getImage();  //ImageIcon�� Image�� ��ȯ 
			Image newImage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
			ImageIcon newImageIcon = new ImageIcon(newImage); 
			return newImageIcon;			
		}
		
		public ETransformationStyle getETransformationStyle() {
			return this.eTransformationStyle;
		}
	}
	
	public enum EFileMenu{
		eNew("���θ����"), 
		eOpen("����"),
		eSave("����"), 
		eSaveAs("�ٸ��̸����� �����ϱ�"),
		eQuit("����"),
		eImg("�̹��� �߰�");
		
		private String getLabel;

		private EFileMenu(String lable) {
			this.getLabel = lable;
		}
		
		public String getLabel() {
			return this.getLabel;
		}

	}
	
	public enum EEditMenu{
		eUndo("���� ���"), 
		eRedo("�ٽ� ����"),
		eDelete("��ü ����"),
		eDeleteOne("�����ϱ�"),
		eCopy("�����ϱ�"),		
		 ePaste("�ٿ��ֱ�"),
		 eGroup("��ü �׷칭��"),
		 eUnGroup("�׷�����ϱ�");
		
		private String getLabel;

		private EEditMenu(String lable) {
			this.getLabel = lable;
		}
		
		public String getLabel() {
			return this.getLabel;
		}
	}
	
	public enum EColorMenu{
		Red("RED",Color.RED,new ImageIcon("./Image/red.png")),
		ORANGE("ORANGE", Color.ORANGE,new ImageIcon("./Image/orange.png")), 
		YELLOW("YELLOW", Color.YELLOW,new ImageIcon("./Image/yellow.png")), 
		GREEN("GREEN", Color.GREEN,new ImageIcon("./Image/green.png")), 
		Blue("BLUE",Color.BLUE,new ImageIcon("./Image/blue.png")),
		PINK("PINK", Color.PINK,new ImageIcon("./Image/pink.png")), 
		BLACK("BLACK", Color.BLACK,new ImageIcon("./Image/black.png")),
		WHITE("WHITE", Color.WHITE,new ImageIcon("./Image/white.png"));
		
		private String getLabel;
		private Color getColor;
		private ImageIcon img;
		
		private EColorMenu(String lable, Color color,ImageIcon img) {
			this.getLabel = lable;
			this.getColor = color;
			this.img = img;
		}
		
		public String getLabel() {
			return this.getLabel;
		}
		
		public Color getColor() {
			return this.getColor;
		}
		
		public ImageIcon getImg() {
			Image image = this.img.getImage();  //ImageIcon�� Image�� ��ȯ 
			Image newImage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
			ImageIcon newImageIcon = new ImageIcon(newImage); 
			return newImageIcon;			
		}
	}
	
}
