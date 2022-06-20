package shapes;

import java.awt.geom.Arc2D;

public class TArcOpen extends TShape {
	// Shape : 인터페이스 클래스 
	private static final long serialVersionUID = 1L;

	public TArcOpen() {
		this.shape = new Arc2D.Double(); 
	}
	
	@Override
	public TShape clone() {  // 새로운 것을 만드는것이 아니라, 자신이 가진 모든 것을 전달
		return new TArcOpen();
	}
	
	public void prepareDrawing(int x, int y) {
		Arc2D arc2D = (Arc2D) this.shape;
		// x, y, w, h, angSt(시작 각도), angExt(각도 범위)
		arc2D.setArc(x, y, 0, 0, 60, -120, Arc2D.OPEN);
	}
	
	@Override
	public void keepDrawing(int x, int y) {  
		Arc2D arc2D = (Arc2D) this.shape;
		arc2D.setArc(arc2D.getX(), arc2D.getY(), x-arc2D.getX(), y-arc2D.getY(), 60, -120, Arc2D.OPEN);
	}
	
}
