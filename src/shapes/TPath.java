package shapes;

import java.awt.geom.Path2D;

public class TPath extends TShape { 

	private static final long serialVersionUID = 1L;
	
	public TPath() {
		this.shape = new Path2D.Double();	
	}
	
	@Override
	public TShape clone() {  // ���ο� ���� ����°��� �ƴ϶�, �ڽ��� ���� ��� ���� ����
		return new TPath();
	}
	
	public void prepareDrawing(int x, int y) {
		Path2D path2D = (Path2D) this.shape;	
		path2D.moveTo(x,y);
	}	
	
	@Override
	public void keepDrawing(int x, int y) { 		
		Path2D path2D = (Path2D) this.shape;			
		path2D.lineTo(x,y);
	}
	
}