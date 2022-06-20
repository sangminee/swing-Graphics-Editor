package transformers;

import shapes.TShape;

public class Mover extends Transformer {
	// move
	// -> 이전의 점을 알고 있어야 한다.
	// Matrix를 저장해 놓았다.
	public Mover(TShape shape) {
		super(shape);
	}

	@Override
	public void prepare(int x, int y) {
		// 얼마큼 이동을 해야하는지 저장을 해 놓는 것임
		this.px = x;
		this.py = y;
	}

	@Override
	public void keepTransforming(int x, int y) {
		this.affineTransform.translate(x - this.px, y - this.py); // matrix를 그저 만들어 놓고 그릴 때만 적용
		// 기존 점을 저장을 했었어야 한다. 아니면 원점을 가지고 수행을 하니까 점점 커진다.
		this.px = x;
		this.py = y;
	}

	@Override
	public void finalize(int x, int y) {
//		this.shape.finalize(x, y);

	}
}
