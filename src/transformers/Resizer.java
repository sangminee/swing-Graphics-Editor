package transformers;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;

import shapes.TShape;
import shapes.TAnchors.EAnchors;

public class Resizer extends Transformer {
	
	private double xScale, yScale;
	private double cx, cy;
	
	public Resizer(TShape shape) {
		super(shape);
	}

	@Override
	public void prepare(int x, int y) {
		// 마우스 point 위치 (기본 점)
		this.px = x;
		this.py = y;
		// 원점 잡기
		Point2D resizeAnchorPoint = this.anchors.getResizeAnchorPoint(x, y); // Anchor 포지션 (땡기고 싶은 Anchor 반대편 = 원점)
		this.cx = resizeAnchorPoint.getX(); // 현재 원점
		this.cy = resizeAnchorPoint.getY();
	}

	@Override
	public void keepTransforming(int x, int y) {
		this.getResizeScale(x, y);
		
		this.affineTransform.translate(cx, cy);
		this.affineTransform.scale(this.xScale, this.yScale); // 얼마큼 움직였는지 곱해준다
		this.affineTransform.translate(-cx, -cy);
		
		this.px = x;
		this.py = y;
	}

	@Override
	public void finalize(int x, int y) {
		this.shape.finalize(x, y);
	}
	
	protected void getResizeScale(int x, int y) {
		EAnchors eResizeAnchor = this.anchors.getResizeAnchor();
		double w1 = px - cx; // 원래 도형의 width 
		double w2 = x - cx;  // 바뀐 
		
		double h1 = py - cy;
		double h2 = y - cy;

		// System.out.println(w2/w1);
		// w : 왼
		// e : 오 
		switch (eResizeAnchor) {
		case eNW: xScale = w2/w1;  yScale= h2/h1;  break;
		case eWW: xScale = w2/w1; yScale= 1;      break;
		case eSW: xScale = w2/w1;  yScale= h2/h1; break;
		case eSS: xScale = 1;      yScale= h2/h1; break;
		case eSE: xScale = w2/w1;  yScale= h2/h1;  break;
		case eEE: xScale = w2/w1;  yScale= 1;      break;
		case eNE: xScale = w2/w1; yScale= h2/h1;  break;
		case eNN: xScale = 1;      yScale= h2/h1;  break;
		default:  break;
		}
	}
	
}
