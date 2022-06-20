package shapes;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.BasicStroke;
import java.awt.Color;
import java.io.Serializable;
import java.awt.geom.AffineTransform;

import shapes.TAnchors.EAnchors;

abstract public class TShape implements Serializable, Cloneable { 

	// attributes 
	private static final long serialVersionUID = 1L;

	// graphics Attribute 
	private boolean bSelected;  // ���� ������ ����
	private Color shapeColor;
	private float size;
	private Color shapefillColor = null;

	// Components: ��ǰ��, ��/��/�� �� ���� ���� ���� �͵�
	protected Shape shape; // ���� �������� �׸��� �׸��� JDK���� ����� �ִ� 2���� �׸��� �׸� �� �ִ� �Ϲ����� ����
	private AffineTransform affineTransform;
	private TAnchors anchors;	
	
	// setters and getters : �Ӽ��� �ܼ� �����ϰų� �д� �͵�, ���� �������� �ʰ� ��� 	
	public boolean isSelected() {
		return this.bSelected;
	}
	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}
	public EAnchors getSelectedAnchor() {return this.anchors.getSelecetedAnchor();}
	
	public Color getShapeColor() {
		return shapeColor;
	}
	public void setShapeColor(Color shapeColor) {
		this.shapeColor = shapeColor;
	}
	public float getSize() {
		return size;
	}
	public void setSize(float size) {
		this.size = size;
	}
	public Color getShapefillColor() {
		return shapefillColor;
	}
	public void setShapefillColor(Color shapefillColor) {
		this.shapefillColor = shapefillColor;
	}
	public int getCenterX() {
		return (int) this.shape.getBounds2D().getCenterX();
	}
	public int getCenterY() {
		return (int) this.shape.getBounds2D().getCenterY();
	}
	
	// transformer�� ����ϱ� ���ؼ� 
	public AffineTransform getAffineTransform() {
		return affineTransform;
	}
	public TAnchors getAnchors() {
		return anchors;
	}
	
	// ������ : ���� �����ϰų� ���� 
	public TShape() {
		this.affineTransform = new AffineTransform();
		this.affineTransform.setToIdentity();
		
		this.anchors = new TAnchors();
		this.bSelected = false;
	}
	public abstract TShape clone();
	public void initailize() {};
	
	public TShape deepClone() throws CloneNotSupportedException {      
		TShape copy = (TShape) super.clone();
		this.affineTransform = (AffineTransform) this.affineTransform.clone();

		return copy;
    }

	// method : ���� ��ü�� �����ϴ�
	public boolean contains(int x, int y) {	
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		if(isSelected()) {
			if( this.anchors.contains(x, y)) {
				return true;
			}
		}	
		
	    if(transformedShape.contains(x, y)) {  // Anchor�� null�� �� 
	    	this.anchors.setSelectedAnchor(EAnchors.eMove);
	    	return true;
		}    
		return false;
	}	

	// draw
	public void draw(Graphics2D graphics2D) {// graphics2D : �׸��׸��� ��ǥ�� ������ ���� 
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		graphics2D.setColor(this.getShapeColor());
		graphics2D.setStroke(new BasicStroke((float) this.getSize()));
			
		if(this.getShapefillColor() != null) {
			graphics2D.setColor(this.getShapefillColor());
			graphics2D.fill(transformedShape);
		}else {
			graphics2D.draw(transformedShape);	
		}
		
		if(isSelected()) {
			graphics2D.setColor(Color.black);
			this.anchors.draw(graphics2D, transformedShape.getBounds());
		}  
	}

	public abstract void prepareDrawing(int x, int y);
	public abstract void keepDrawing(int x, int y);
	public void addPoint(int x, int y) {}
	
    public void finalize(int x, int y) {   	
    	this.shape = this.affineTransform.createTransformedShape(this.shape); 	
    	this.affineTransform.setToIdentity(); // �ʱ�ȭ 
	}   

	
}
