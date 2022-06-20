package shapes;

import java.awt.Rectangle;


public class TSelection extends TShape{
	private static final long serialVersionUID = 1L;

	public TSelection() {
		this.shape = new Rectangle(); 
	}
	
	@Override
	public TShape clone() {  // 새로운 것을 만드는것이 아니라, 자신이 가진 모든 것을 전달
		return new TSelection();
	}
	
	public void prepareDrawing(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setBounds(x,y,0,0);
	}
	
	@Override
	public void keepDrawing(int x, int y) {  
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setSize(x-rectangle.x,y-rectangle.y);
	}
	
}
	
