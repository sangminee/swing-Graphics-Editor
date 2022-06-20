package shapes;

import java.awt.geom.Line2D;

public class TLine extends TShape { 

	private static final long serialVersionUID = 1L;
	
	public TLine() {
		this.shape = new Line2D.Double();	
	}
	
	@Override
	public TShape clone() {  // 새로운 것을 만드는것이 아니라, 자신이 가진 모든 것을 전달
		return new TLine();
	}
	
	public void prepareDrawing(int x, int y) {
		Line2D line = (Line2D) this.shape;
		line.setLine(x,y,x,y);
	}
	
	@Override
	public void keepDrawing(int x, int y) { 
		Line2D line = (Line2D) this.shape;
		line.setLine(line.getX1(),line.getY1(),x,y);
	}
	
}