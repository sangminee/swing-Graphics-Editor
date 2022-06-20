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
		
		// px, py : 마우스 위치 (전점)
		this.px = x;
		this.py = y;
//
//		// 높이 길이 계산
//		Point2D rotateAnchorPoint = this.anchors.getRotateAnchorPoint(); // Anchor 포지션 (땡기고 싶은 Anchor 반대편 = 원점)
//		this.cx = rotateAnchorPoint.getX(); // 현재 원점
//		this.cy = rotateAnchorPoint.getY();
//
//		this.h = this.anchors.getHeight(); // 현재 원점
//		this.w = this.anchors.getWidth();
	}

	@Override
	public void keepTransforming(int x, int y) {
		// 원점이 틀린 것 같음 
		// 1. 오른쪽 왼쪽이 바꼈음
		// 2. 원점을 중심으로 돌지 않고, 중심이 안잡혔움
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
