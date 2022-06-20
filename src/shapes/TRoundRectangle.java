package shapes;

import java.awt.geom.RoundRectangle2D;

public class TRoundRectangle extends TShape { 

	private static final long serialVersionUID = 1L;
	
	public TRoundRectangle() {
		this.shape = new RoundRectangle2D.Double();	
	}
	
	@Override
	public TShape clone() {  // 새로운 것을 만드는것이 아니라, 자신이 가진 모든 것을 전달
		return new TRoundRectangle();
	}
	
	public void prepareDrawing(int x, int y) {
		RoundRectangle2D roundRectangle2D = (RoundRectangle2D) this.shape;	
		roundRectangle2D.setRoundRect(x,y,0,0, 70, 70);
	}
	
	
	@Override
	public void keepDrawing(int x, int y) { 
		RoundRectangle2D roundRectangle2D = (RoundRectangle2D) this.shape;
		
		roundRectangle2D.setFrame(roundRectangle2D.getX(), roundRectangle2D.getY()
				,x-roundRectangle2D.getX(),y- roundRectangle2D.getY());
	}
	
}