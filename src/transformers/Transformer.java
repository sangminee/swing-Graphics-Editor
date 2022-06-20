package transformers;

import shapes.TAnchors;
import shapes.TShape;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public abstract class Transformer { // mover, resizer, rotater, Drawer 
	// shape�� ��ǥ�� 

	protected TShape shape;
	
	// Association
	protected AffineTransform affineTransform;
	protected TAnchors anchors;
	
	protected int px, py;
	
	public Transformer(TShape shape) {
		this.shape = shape;
		this.affineTransform = this.shape.getAffineTransform();
		this.anchors = this.shape.getAnchors();
	}
	
	public abstract void prepare(int x, int y);
	public abstract void keepTransforming(int x, int y); // ��ǥ ����
	public abstract void finalize(int x, int y);

}
