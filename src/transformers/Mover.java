package transformers;

import shapes.TShape;

public class Mover extends Transformer {
	// move
	// -> ������ ���� �˰� �־�� �Ѵ�.
	// Matrix�� ������ ���Ҵ�.
	public Mover(TShape shape) {
		super(shape);
	}

	@Override
	public void prepare(int x, int y) {
		// ��ŭ �̵��� �ؾ��ϴ��� ������ �� ���� ����
		this.px = x;
		this.py = y;
	}

	@Override
	public void keepTransforming(int x, int y) {
		this.affineTransform.translate(x - this.px, y - this.py); // matrix�� ���� ����� ���� �׸� ���� ����
		// ���� ���� ������ �߾���� �Ѵ�. �ƴϸ� ������ ������ ������ �ϴϱ� ���� Ŀ����.
		this.px = x;
		this.py = y;
	}

	@Override
	public void finalize(int x, int y) {
//		this.shape.finalize(x, y);

	}
}
