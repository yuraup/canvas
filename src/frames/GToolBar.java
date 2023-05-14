package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import main.GConstants.EShape;

public class GToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	JRadioButton eOpenShape = null;
	ButtonGroup buttonGroup;

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