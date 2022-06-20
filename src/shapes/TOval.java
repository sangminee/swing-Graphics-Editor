package shapes;

import java.awt.Graphics2D;

import java.awt.geom.Ellipse2D;


public class TOval extends TShape {

	private static final long serialVersionUID = 1L;

	public TOval() {
		this.shape = new Ellipse2D.Double();
	}

	@Override
	public TShape clone() { // 새로운 것을 만드는것이 아니라, 자신이 가진 모든 것을 전달
		return new TOval();
	}

	public void prepareDrawing(int x, int y) { // 원점 setting
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		ellipse.setFrame(x, y, 0, 0);
	}

	@Override
	public void keepDrawing(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		ellipse.setFrame(ellipse.getX(), ellipse.getY(), x - ellipse.getX(), y - ellipse.getY());
	}
}
