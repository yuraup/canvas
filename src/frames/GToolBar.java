package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import shapes.GLine;
import shapes.GOval;
import shapes.GPolygon;
import shapes.GRectangle;
import shapes.GSelect;
import shapes.GShape;

public class GToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	JRadioButton eOpenShape = null;
	ButtonGroup buttonGroup;

	public enum EShape {
		// 객체를 달아서 drawing의 코드 개선

		eSelect("Select", new GSelect()), eRectangle("Rectangle", new GRectangle()), // 변수 이름을 0번 이자 스트링 타입 가능
		eOval("Oval", new GOval()), eLine("Line", new GLine()), ePolygon("Polygon", new GPolygon()); // user가 정의하는 순서를
																										// 가진 집합을 정의

		private String name;
		private GShape gShape;

		private EShape(String name, GShape gShape) {
			this.name = name;
			this.gShape = gShape;
		}

		public String getName() {
			return this.name;
		}

		public GShape getGShape() { // 같은 주소를 계속 가지고 오고 잇음 .새로 만들어야 하는데 -> 자기 복제 하자
			return this.gShape;
		}
	}

	private EShape eSelectedShape;

	public EShape getESelectedShape() { // 도형 모양 가져오기
		return this.eSelectedShape;
	}

//	public EShape getEButtonShape() {
//		return this.eSelectedShape; // 드로잉 패널에서 그림 그릴 때마다 가지고 갈 것
//	}

	public void resetESelectedShape() {// 선택된 도형 초기화
//		JRadioButton selectButton = (JRadioButton) this.getComponent(EShape.eSelect.ordinal());
//		selectButton.doClick();
		this.buttonGroup.clearSelection(); // 버튼 그룹 좀 놓침
		this.eSelectedShape = EShape.eSelect;
	}

	public GToolBar() {
		super();
		ActionHandler actionHandler = new ActionHandler();
		buttonGroup = new ButtonGroup();
//		for (int i = 1; i<EShape.values().length; i++) {
//			
//		}

		for (EShape eButtonShape : EShape.values()) {
			if (eButtonShape != EShape.eSelect) {
				JRadioButton buttonShape = new JRadioButton(eButtonShape.getName());
				this.add(buttonShape);

				buttonShape.setActionCommand(eButtonShape.toString()); // 스트링 변환
				buttonShape.addActionListener(actionHandler);
				buttonGroup.add(buttonShape);
			}

		}
		resetESelectedShape();
//		JRadioButton selectButton = (JRadioButton) this.getComponent(EShape.eSelect.ordinal());
//		selectButton.doClick();
//		eSelectedShape = EShape.eSelect;
	}

	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			eSelectedShape = EShape.valueOf(event.getActionCommand());
		}
	}
}