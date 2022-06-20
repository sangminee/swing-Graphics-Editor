package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.awt.geom.Point2D;
import java.awt.BasicStroke;

import java.awt.Rectangle;

public class TAnchors implements Serializable  { // 9���� ���׶�̰� �����Ѵ�

	private static final long serialVersionUID = 1L;
	
	// ���׶�� ������� ������Ű��
	private final int WIDTH = 10;
	private final int HEIGHT = 10;
	
	public enum EAnchors {
		// ������ �̸��� ���� ���̴�. - ���� ���� �˾ƾ��� cursor�� �����ų �� �ִ�. 
		eNW,
		eWW,
		eSW,
		eSS,
		eSE,
		eEE,
		eNE,
		eNN,
		eRR,
		
		eMove
	}
	private Ellipse2D anchors[];
	private EAnchors eSelecetedAnchor;
	private EAnchors eResizeAnchor;
	
	public EAnchors getSelecetedAnchor() {
		return this.eSelecetedAnchor;
	}
	public void setSelectedAnchor(EAnchors eSelecetedAnchor) {
		this.eSelecetedAnchor=eSelecetedAnchor;
	}
	public EAnchors getResizeAnchor() {
		return this.eResizeAnchor;
	}
//	public void setResizeAnchor(EAnchors eResizeAnchor) {
//		this.eResizeAnchor=eResizeAnchor;
//	}
	// Constructors 
	public TAnchors() {
		// ���׶�̸� ����Ű�� ���� 9�� ���� ���̴�. - Anchor ���� �غ�� �� ���� ���� 
		// Anchor�� ������ �ΰ� �մ� ���� ������ Anchor�� ������ �ִ� ��� 2������ ���� 
		// -> ��� ������ ���ο� anchor�� ������ �ְ� �׷��� �� �� �׷��� ���� 
		this.anchors = new Ellipse2D[EAnchors.values().length-1];		
		for(int i=0; i<EAnchors.values().length-1; i++ ) {
			this.anchors[i] = new Ellipse2D.Double();
		}
	}
	
	// methods
	public boolean contains(int x, int y) {
		for(int i=0; i<EAnchors.values().length-1; i++ ) {
			if( this.anchors[i].contains(x, y) ) {
				this.eSelecetedAnchor = EAnchors.values()[i];
				return true;
			}
		} 
		return false;
	}

	
	public void draw(Graphics2D graphics2D, Rectangle boungindRectangle) { // draw(Graphics2D graphics, Shape boungindRectangle)
		
		graphics2D.setStroke(new BasicStroke((float) 1));
		
		for(int i=0; i<EAnchors.values().length-1; i++ ) {
			EAnchors eAnchor = EAnchors.values()[i];
			int x= boungindRectangle.x;
			int y= boungindRectangle.y;
			int w = boungindRectangle.width;
			int h = boungindRectangle.height;
			
			// ������ �ϸ� �����߰��ϴ� �͵� ����, ���ϸ� ã�� �� �ִ�. 
			// -> �Ѵ��� ������ �Ǹ� ��� ������ �� �� �� �� �ִ�. 
			// �����ڿ� �ִ� ���� ����. 
			// if���� switch�� �ξ� ������. 
			switch (eAnchor) {
			case eNW:                              break;
			case eWW:                y = y + h/2;  break;
			case eSW:                y = y + h;    break;
			case eSS: x = x + w/2;   y = y + h;    break;
			case eSE: x = x + w;     y = y + h;    break;
			case eEE: x = x + w;     y = y + h/2;  break;
			case eNE: x = x + w;                   break;
			case eNN: x = x + w/2;                 break;
			case eRR: x = x + w/2;   y = y - h/2;  break;
			default:                               break;
			}
			x = x - WIDTH/2;
			y = y - HEIGHT/2;
			
			this.anchors[eAnchor.ordinal()].setFrame(x,y, WIDTH,HEIGHT);
			// �ȿ��� �Ͼ�� �ٿ������ ��İ�  
			// -> ����� �Ͼ�� �Ǹ� �ȵǱ� ������ 
			// -> ����� : ����� ���� �׸��� ���̴�.
			//  �Ϲ������� �Ͼ������ ĥ�ϸ� �������ٰ� �����ϴµ� �ƴϴ�. 
			
			// ������ ������ ���ѹ����� 
			
			Color foreground = graphics2D.getColor(); // �̸� ���� �� ������. - gray 
			
//			graphics2D.setColor(graphics2D.getBackground());  
//			graphics2D.fill(this.anchors[eAnchor.ordinal()]); // �ȿ��ٰ��� ��׶��� ������ ä���.
			
			graphics2D.setColor(Color.WHITE);  
			graphics2D.fill(this.anchors[eAnchor.ordinal()]); // �ȿ��ٰ��� ��׶��� ������ ä���.
			
			graphics2D.setColor(foreground); // foreground ����� ����
			graphics2D.draw(this.anchors[eAnchor.ordinal()]);  
			
		 	// Background : ����
			// Foreground: default �� (���) 
		}
	}
	public Point2D getResizeAnchorPoint(int x, int y) {
		this.eResizeAnchor = null;
		switch (this.eSelecetedAnchor) {
		case eNW: eResizeAnchor = EAnchors.eSE; break;
		case eWW: eResizeAnchor = EAnchors.eEE; break;
		case eSW: eResizeAnchor = EAnchors.eNE; break;
		case eSS: eResizeAnchor = EAnchors.eNN; break;
		case eSE: eResizeAnchor = EAnchors.eNW; break;
		case eEE: eResizeAnchor = EAnchors.eWW; break;
		case eNE: eResizeAnchor = EAnchors.eSW; break;
		case eNN: eResizeAnchor = EAnchors.eSS; break;
		default: break;
		}
		// resize �ϱ����� ��ǥ�� ���� ���� 
		double cx = this.anchors[eResizeAnchor.ordinal()].getCenterX();
		double cy = this.anchors[eResizeAnchor.ordinal()].getCenterY();
		return new Point2D.Double(cx, cy);
	}
	
	private int width, height;
	public int getWidth() {return width;}
	public void setWidth(int width) {this.width = width;}
	
	public int getHeight() {return height;}
	public void setHeight(int height) {this.height = height;}
	
	public Point2D getRotateAnchorPoint() {
		double x1 = this.anchors[EAnchors.eNW.ordinal()].getCenterX();
		double y1 = this.anchors[EAnchors.eNW.ordinal()].getCenterY();
		
		double x2 = this.anchors[EAnchors.eNE.ordinal()].getCenterX();
		double y2 = this.anchors[EAnchors.eNE.ordinal()].getCenterY();
		
		double x3 = this.anchors[EAnchors.eSW.ordinal()].getCenterX();
		double y3 = this.anchors[EAnchors.eSW.ordinal()].getCenterY();
		
		this.width = (int) (x2 - x1);
		this.height = (int) (y3 - y1);
		
		return new Point2D.Double(x1, y1);
	}
	public void setRotateAnchorPoint() {
		
		
	}
}
