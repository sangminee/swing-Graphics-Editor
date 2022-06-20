package frames;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import global.Constants.ETools;
import global.Constants.ETransformationStyle;
import shapes.TShape;
import transformers.Drawer;
import transformers.Mover;
import transformers.Resizer;
import transformers.Rotator;
import transformers.Transformer;
import shapes.AddImg;
import shapes.TAnchors;
import shapes.TAnchors.EAnchors;
import shapes.TSelection;

import java.awt.Cursor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.awt.BasicStroke;

import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class DrawingPanel extends JPanel { 
	
	// attributes
	private static final long serialVersionUID = 1L;
	
	// components 
	private Vector<TShape> shapes;
	private BufferedImage bufferedImage;  
	private Graphics2D graphics2DBufferedImage; 
	private boolean bUpdated;
	
	private Vector<TShape> deleteShape; // 삭제 한 도형들 
	private Vector<TShape> closedShape; // 닫기 전 도형들
	
	// association attribute 
	private ETools selectedTool; 
	private TShape selectedShape;
	private TShape currentShape;
	private Transformer transformer ;
	
	// working variables 
	private enum EDrawingState {
		eIdle, e2PointTransformation, eNPointTransformation, 
	}
	EDrawingState eDrawingState;

	// Constructor 
	public DrawingPanel() { 
		this.setBackground(Color.WHITE); 
		this.eDrawingState = EDrawingState.eIdle; // 초기 값
		this.bUpdated = false; 
		
		this.shapes = new Vector<TShape>();
		
		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		this.addMouseWheelListener(mouseHandler);
		
		// 추가
		this.deleteShape = new Vector<TShape>();
		this.closedShape = new Vector<TShape>();
		this.imges = new Vector<Image>();
	}
	
	public void initialize() {
		if(this.getPanelWidth() != 0 && this.getPanelHeight() != 0 ){
			this.bufferedImage = (BufferedImage) this.createImage(this.getPanelWidth(), this.getPanelHeight()); // 내가 현재 가진 이미지 크기 만큼
		}else {
			this.bufferedImage = (BufferedImage) this.createImage(this.getWidth(), this.getHeight()); // 내가 현재 가진 이미지 크기 만큼
		}
		this.graphics2DBufferedImage = (Graphics2D) this.bufferedImage.getGraphics();
	}
		
	// bUpdated true  : 저장하기 전 그림 존재 
	// bUpdated false : 저장 완료 
	public boolean isUpdated() {
		return this.bUpdated;
	}
	public void setUpdated(boolean bUpdated) {
		this.bUpdated = bUpdated;  // finishDrawing 할 때 true됨 
	}
	
	// file Open/Save
	@SuppressWarnings("unchecked")
	public void setShapes(Object shapes) {
		this.shapes = (Vector<TShape>) shapes; // 다운캐스팅
		this.repaint();  // 다시 그리기 
	}
	public Object getShapes() {
		return this.shapes;
	}
	
	public void setSelectedTool(ETools selectedTool) { // 남에게 영향을 주는 속성들
		this.selectedTool = selectedTool; 
	}
	
	// Overriding
	public void paint(Graphics graphics) { 
		super.paint(graphics); 
		this.graphics2DBufferedImage.clearRect(0,0,this.getWidth(),this.getHeight()); // 새로운 그림을 홀라당 다시 그린다.				
		for (TShape shape : this.shapes) {
			shape.draw(this.graphics2DBufferedImage); // 전체를 다시 그림 
		}
		for(Image img : this.imges) {
			this.graphics2DBufferedImage.drawImage(img, 0, 0, this);
			this.getGraphics().drawImage(img, 0, 0, this);
		}
		graphics.drawImage(this.bufferedImage,0,0,this);
	}

	private void prepareTransformation(int x, int y) { // Transformer : 좌표만 움직이는 아이
		if (selectedTool == ETools.eSelection) {
			currentShape = onShape(x, y);
			if (currentShape != null) {
				EAnchors eAnchor = currentShape.getSelectedAnchor();
				if (eAnchor == EAnchors.eMove) { // move : 3단계 필요 (press->drag ->release)
					this.transformer = new Mover(this.currentShape);
				} else if (eAnchor == EAnchors.eRR) {
					this.transformer = new Rotator(this.currentShape);
				} else {
					this.transformer = new Resizer(this.currentShape);
				}
			} else {
				this.currentShape = this.selectedTool.newShape();

				if (this.currentShape != null) {
					this.currentShape.setShapeColor(this.getShapColor());
					this.graphics2DBufferedImage.setColor(this.currentShape.getShapeColor());

					this.currentShape.setSize(this.getShapeSize());
					this.graphics2DBufferedImage.setStroke(new BasicStroke((float) this.currentShape.getSize()));
				}

				this.transformer = new Drawer(this.currentShape);
			}
		} else {
			this.currentShape = this.selectedTool.newShape();
			if (this.currentShape != null) {
				this.currentShape.setShapeColor(this.getShapColor());
				this.graphics2DBufferedImage.setColor(this.currentShape.getShapeColor());

				this.currentShape.setSize(this.getShapeSize());
				this.graphics2DBufferedImage.setStroke(new BasicStroke((float) this.currentShape.getSize()));
			}
			this.transformer = new Drawer(this.currentShape);
		}
		this.setUpdated(true);
		this.transformer.prepare(x, y);
		this.graphics2DBufferedImage.setXORMode(this.getBackground()); // XORMode: 없으면 그리고, 있으면 지운다
	}

	private void keepTransformation(int x, int y) { 
		// erase
		this.currentShape.draw(this.graphics2DBufferedImage);
		// draw
		this.transformer.keepTransforming(x, y);
		this.currentShape.draw(this.graphics2DBufferedImage);
		this.getGraphics().drawImage(this.bufferedImage, 0, 0, this);
	}

	private void continueTransformation(int x, int y) {
		this.currentShape.addPoint(x, y);
	}
	
	private void finishTransformation(int x, int y) {
		this.graphics2DBufferedImage.setPaintMode();  // 그림모드 - paintMode : 어떤 그림이 있던지 그냥 그리는 
		this.transformer.finalize(x, y);
		
		// 이전 도형 Anchor 취소하기  
		if(this.selectedShape != null) {
			this.selectedShape.setSelected(false);
		}
		if(!(this.currentShape instanceof TSelection)) {
			this.shapes.add(this.currentShape); // Vector add
			this.selectedShape = this.currentShape;
			this.selectedShape.setSelected(true);
		}	
		this.repaint();	// 전체 그림을 다시 그린다
	}
	
	private TShape onShape(int x, int y) { 
		for (TShape shape : this.shapes) {
			if (shape.contains(x, y)) { // contains() :커서 안에 도형 존재 유무 알려줌
				return shape; 
			}
		}
		return null;
	}
	
	private void changeSelection(int x, int y) {
		// erase previous selection 
		if (this.selectedShape != null) {
			this.selectedShape.setSelected(false);
		}
		this.repaint();	
		
		// draw anchors
		this.selectedShape = this.onShape(x, y); // 상태를 유지하고 있어야 하기 때문에 밖으로 빼내야 한다. 
		if(this.selectedShape != null) {
			this.selectedShape.setSelected(true);
//			this.selectedShape.draw(this.graphics2DBufferedImage);
		}
		if(this.getShapefillColor() != null) {
			this.selectedShape.setShapefillColor(this.getShapefillColor());
			this.setShapefillColor(null);
		}		
	}
	
	private void changeCursor(int x, int y) {	
		Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		if(this.selectedTool == ETools.eSelection) {
			cursor = new Cursor(Cursor.DEFAULT_CURSOR);
			
			this.currentShape = onShape(x, y);
			if (this.currentShape != null) {  // 마우스 아래에 도형이 있으면
				if(this.currentShape.isSelected()) {
					cursor = new Cursor(Cursor.MOVE_CURSOR);
					EAnchors eAnchor = this.currentShape.getSelectedAnchor(); 
					switch(eAnchor) {
					case eRR:cursor = new Cursor(Cursor.HAND_CURSOR);    break;
					
					case eNW: cursor = new Cursor(Cursor.NW_RESIZE_CURSOR); break;
					case eWW: cursor = new Cursor(Cursor.W_RESIZE_CURSOR);  break;
					case eSW: cursor = new Cursor(Cursor.SW_RESIZE_CURSOR); break;
					case eSS: cursor = new Cursor(Cursor.S_RESIZE_CURSOR);  break;
					case eSE: cursor = new Cursor(Cursor.SE_RESIZE_CURSOR); break;
					case eEE: cursor = new Cursor(Cursor.E_RESIZE_CURSOR);  break;
					case eNE: cursor = new Cursor(Cursor.NE_RESIZE_CURSOR); break;
					case eNN: cursor = new Cursor(Cursor.N_RESIZE_CURSOR);  break;
					
					default: break;
					}
				}
			}
		}

		this.setCursor(cursor);
	}


	/////////////////////////////////////////////////////////
	////////////////////// 추가 기능 //////////////////////////
	/////////////////////////////////////////////////////////
	
	// attribute
	private int panelWidth;
	private int panelHeight;
	private Color shapefillColor;
	private Color shapColor = Color.black;
	private float shapeSize = 1;
	private TShape copyShape;
	private Vector<Image> imges;
	
	// getters & setters
	public int getPanelWidth() {return panelWidth;}
	public void setPanelWidth(int panelWidth) {this.panelWidth = panelWidth;}

	public int getPanelHeight() {return panelHeight;}
	public void setPanelHeight(int panelHeight) {this.panelHeight = panelHeight;}
	
	public Color getShapColor() {return shapColor;}
	public void setShapColor(Color shapColor) {this.shapColor = shapColor;}
	
	public Color getShapefillColor() {return shapefillColor;}
		
	public float getShapeSize() {return shapeSize;}
	public void setShapeSize(float shapeSize) {this.shapeSize = shapeSize;}

	public Vector<Image> getImges() {return imges;}
	public void setImges(Vector<Image> imges) {this.imges = imges;}
	
	// method
	// 패널 사이즈 변경
	public void changePanelSize(int number) {
		this.setPanelHeight(this.getHeight() / 50 * number);
		this.setPanelWidth(this.getWidth() / 50 * number);
		initialize();
	}
	
	// 도형 채우기
	public void setShapefillColor(Color shapefillColor) {
		this.shapefillColor = shapefillColor;
	}

	// 도형 속성 변경
	public void changeShapeDraw(Color selectedColor) { // 도형 색깔 변경
		this.setShapColor(selectedColor);
	}
	
	public void changeShapeLineSize(float f) { // 도형 크기 변경
		this.setShapeSize(f);		
	}

	// 현재 창 닫고 기존 창 열기
	public void saveShapes() {
		for (TShape shape : this.shapes) {
			this.closedShape.add(shape);
		}
	}

	public void closeFile() {
		this.closedShape = new Vector<TShape>();
		this.shapes.clear();
		for (TShape shape : this.closedShape) {
			this.shapes.add(shape);
		}
		this.repaint();
	}

	// 실행취소
	public void undoShape() {
		if (this.shapes.size() - 1 > -1) {
			this.deleteShape.add(this.shapes.get(this.shapes.size() - 1));
			this.shapes.remove(this.shapes.size() - 1);
			this.repaint(); // 다시 그리기
		} else {
			JOptionPane.showMessageDialog(null, "모두 실행 취소되었습니다.");
		}
	}

	// 다시 실행
	public void redoShape() {
		if (this.deleteShape.size() - 1 > -1) {
			this.shapes.add(this.deleteShape.get(this.deleteShape.size() - 1));
			this.deleteShape.remove(this.deleteShape.size() - 1);
			this.repaint(); // 다시 그리기
		} else {
			JOptionPane.showMessageDialog(null, "모두 실행되었습니다.");
		}
	}

	// 전체 삭제하기
	public void clearShapes() {
		this.deleteShape = new Vector<TShape>();
		for (TShape shape : this.shapes) {
			this.deleteShape.add(shape);
		}
		this.shapes.clear();
		this.repaint();
	}
	
	// 선택된 도형 삭제하기
	public void delete() {
		for (TShape shape : this.shapes) {
			if(shape.isSelected() == true) {
				this.deleteShape.add(shape);
				this.shapes.remove(shape);
				break;
			}
		}
		this.repaint();
	}
	
	// 마우스 왼쪽 버튼 누르면 특정 도형 삭제
	public void deletedShape(int x, int y) {
		int index = 0;
		for (TShape shape : this.shapes) {
			if (shape.contains(x, y)) {
				break;
			}
			index++;
		}
		int result = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			this.deleteShape.add(this.shapes.get(index));
			this.shapes.remove(index);
			this.repaint(); // 다시 그리기
		} else {
			JOptionPane.showMessageDialog(null, "삭제 취소되었습니다.");
		}
	}

	// 붙여넣기 
	public void paste() {
		if(copyShape != null) {
			this.setUpdated(true);
			this.shapes.add(this.copyShape); // Vector add
			
			this.selectedShape = this.copyShape;		
			this.selectedShape.setSelected(true);	
			
		}	
		copyShape = null;
		this.repaint();
	}
	
	// 도형 복사하기	
	public void copy() {
		for (TShape shape : this.shapes) {
			if (shape.isSelected() == true) {
				try {	
					this.copyShape = shape.deepClone();				
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}		
				break;
			}
		}
	}
	
	// 전체 그룹 묶기
	public void groupAll() {
		for (TShape shape : this.shapes) {
			shape.setSelected(true);
			shape.draw(this.graphics2DBufferedImage); // 전체를 다시 그림 
		}
		this.repaint();
	}
	
	// 그룹취소하기
	public void unGroup() {
		for (TShape shape : this.shapes) {
			if(shape.isSelected() == true) {
				shape.setSelected(false);
				shape.draw(this.graphics2DBufferedImage); // 전체를 다시 그림 
			}		
		}
		this.repaint();
	}

	// 이미지 추가
	public void addImg() {
		AddImg addImg = new AddImg();
		BufferedImage img = addImg.addImg();
		
		this.graphics2DBufferedImage.drawImage(img, 0, 0, this);
		this.getGraphics().drawImage(img, 0, 0, this);
		this.imges.add(img);
		this.setUpdated(true);
	}
	
	private class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {
		@Override
		public void mouseClicked(MouseEvent e) { // e.getButton() : 몇번 버튼인지 - 좌클릭:1/우클릭:3
			if (e.getButton() == MouseEvent.BUTTON1) { // MouseEvent.BUTTON1 : 왼쪽 버튼
				if (e.getClickCount() == 1) {
					this.lButtonClicked(e);			
				} else if (e.getClickCount() == 2) {
					this.lButtonDoubleClicked(e);
				}
			}			
			if(e.getButton() == MouseEvent.BUTTON3) {
				deletedShape(e.getX(), e.getY());
			}
		}

		private void lButtonClicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) { // 아무것도 안하고 있으면 그림을 그리기 시작해야 함
				// 도형 클릭 시 anchor 그리기 ( Anchor 그리기 : selection이 되어야 한다. 그림을 그리는 것은 2번째 이다.)
				changeSelection(e.getX(), e.getY()); 
				// polygon 그리기
				if (selectedTool.getETransformationStyle() == ETransformationStyle.eNpoint) {
					prepareTransformation(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNPointTransformation;
				}

			} else if (eDrawingState == EDrawingState.eNPointTransformation) {
				continueTransformation(e.getX(), e.getY());
			}
		}

		private void lButtonDoubleClicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPointTransformation) {
				finishTransformation(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPointTransformation) {
				keepTransformation(e.getX(), e.getY()); 
			} else if (eDrawingState == EDrawingState.eIdle) {
				changeCursor(e.getX(), e.getY()); 
			}
		}

		@Override
		public void mousePressed(MouseEvent e) { 
			if (eDrawingState == EDrawingState.eIdle) {
				if (selectedTool.getETransformationStyle() == ETransformationStyle.e2point) {
					prepareTransformation(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2PointTransformation; // 그림그리기, move 다 포함 - 어떤 도구인지 확인을 해야함 
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) { // 드레그: Press의 무브 // 지우고 그리고를 반복한다. (제일 많이 사용됨)
			if (eDrawingState == EDrawingState.e2PointTransformation) { // 상태를 갇혀 높은 것
				keepTransformation(e.getX(), e.getY()); // 현재좌표와 새로운 좌표 둘다 필요함
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointTransformation) {
				finishTransformation(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) { // 마우스가 drawingPanel을 들어왔을 때
		}

		@Override
		public void mouseExited(MouseEvent e) { // 마우스가 drawingPanel 밖으로 떠났을 때
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) { // 역방향인지 정방향인지를 파악
			System.out.println("[mouseWheelMoved]  " + e.getWheelRotation());
		}
	}



}
