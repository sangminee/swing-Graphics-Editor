package shapes;

import java.awt.geom.Arc2D;

public class TArcOpen extends TShape {
	// Shape : �������̽� Ŭ���� 
	private static final long serialVersionUID = 1L;

	public TArcOpen() {
		this.shape = new Arc2D.Double(); 
	}
	
	@Override
	public TShape clone() {  // ���ο� ���� ����°��� �ƴ϶�, �ڽ��� ���� ��� ���� ����
		return new TArcOpen();
	}
	
	public void prepareDrawing(int x, int y) {
		Arc2D arc2D = (Arc2D) this.shape;
		// x, y, w, h, angSt(���� ����), angExt(���� ����)
		arc2D.setArc(x, y, 0, 0, 60, -120, Arc2D.OPEN);
	}
	
	@Override
	public void keepDrawing(int x, int y) {  
		Arc2D arc2D = (Arc2D) this.shape;
		arc2D.setArc(arc2D.getX(), arc2D.getY(), x-arc2D.getX(), y-arc2D.getY(), 60, -120, Arc2D.OPEN);
	}
	
}
