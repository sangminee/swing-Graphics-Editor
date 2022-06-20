package transformers;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import shapes.TShape;

public class Rotator extends Transformer {

	private double cx, cy;
	
//	private int px, py;
//	private double cx, cy;
//	private int h, w;

	public Rotator(TShape shape) {
		super(shape);
	}

	@Override
	public void prepare(int x, int y) {
		this.cx = this.shape.getCenterX();
		this.cy = this.shape.getCenterY();
		
		// px, py : ���콺 ��ġ (����)
		this.px = x;
		this.py = y;
//
//		// ���� ���� ���
//		Point2D rotateAnchorPoint = this.anchors.getRotateAnchorPoint(); // Anchor ������ (����� ���� Anchor �ݴ��� = ����)
//		this.cx = rotateAnchorPoint.getX(); // ���� ����
//		this.cy = rotateAnchorPoint.getY();
//
//		this.h = this.anchors.getHeight(); // ���� ����
//		this.w = this.anchors.getWidth();
	}

	@Override
	public void keepTransforming(int x, int y) {
		// ������ Ʋ�� �� ���� 
		// 1. ������ ������ �ٲ���
		// 2. ������ �߽����� ���� �ʰ�, �߽��� ��������
		double startAngle = Math.atan2(px-cx, py-cy);
		double endAngle = Math.atan2(x-cx, y-cy);
		double angle = endAngle - startAngle;
		
		this.affineTransform.rotate(angle);
		
		this.px = x;
		this.py = y;
//		double angle = Math.atan2(this.h / 2 - y, this.w / 2 - x) - Math.PI / 2;
//		
//		this.affineTransform.translate(cx, cy);
//		this.affineTransform.rotate(angle, this.w / 2, this.h / 2);
//		this.affineTransform.translate(-cx, -cy);
//		
//		this.anchors.setRotateAnchorPoint();
	}

	@Override
	public void finalize(int x, int y) {
		this.shape.finalize(x, y);
	}
}
