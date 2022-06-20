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
		eSelection("선택",new TSelection(),"Selection Tool",new ImageIcon("./Image/rectangle.png"),ETransformationStyle.e2point),  
		eRectanble("네모",new TRectangle(),"네모",new ImageIcon("./Image/rectangle.png"),ETransformationStyle.e2point),    
		eRoundRectangle("원형사각형",new TRoundRectangle(),"원형사각형",new ImageIcon("./Image/roundRectangle.png"),ETransformationStyle.e2point),
		eOval("동그라미" ,new TOval(),"동그라미",new ImageIcon("./Image/oval.png"),ETransformationStyle.e2point),  
		eLine("선",new TLine(),"선",new ImageIcon("./Image/line.png"),ETransformationStyle.e2point),  
		ePolygon("다각형",new TPolygon(),"다각형",new ImageIcon("./Image/polygon.png"),ETransformationStyle.eNpoint),
		ePath("펜",new TPath(),"펜",new ImageIcon("./Image/pen.png"),ETransformationStyle.e2point),

		eArcOpen("열린 호",new TArcOpen(),"열린 호",new ImageIcon("./Image/arcOpen.png"),ETransformationStyle.e2point),
		eArcPie("파이 호",new TArcPie(),"파이 호",new ImageIcon("./Image/arcPie.png"),ETransformationStyle.e2point);
		
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
			Image image = this.img.getImage();  //ImageIcon을 Image로 변환 
			Image newImage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
			ImageIcon newImageIcon = new ImageIcon(newImage); 
			return newImageIcon;			
		}
		
		public ETransformationStyle getETransformationStyle() {
			return this.eTransformationStyle;
		}
	}
	
	public enum EFileMenu{
		eNew("새로만들기"), 
		eOpen("열기"),
		eSave("저장"), 
		eSaveAs("다른이름으로 저장하기"),
		eQuit("종료"),
		eImg("이미지 추가");
		
		private String getLabel;

		private EFileMenu(String lable) {
			this.getLabel = lable;
		}
		
		public String getLabel() {
			return this.getLabel;
		}

	}
	
	public enum EEditMenu{
		eUndo("실행 취소"), 
		eRedo("다시 실행"),
		eDelete("전체 삭제"),
		eDeleteOne("삭제하기"),
		eCopy("복사하기"),		
		 ePaste("붙여넣기"),
		 eGroup("전체 그룹묶기"),
		 eUnGroup("그룹취소하기");
		
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
			Image image = this.img.getImage();  //ImageIcon을 Image로 변환 
			Image newImage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
			ImageIcon newImageIcon = new ImageIcon(newImage); 
			return newImageIcon;			
		}
	}
	
}
