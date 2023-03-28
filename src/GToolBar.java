import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

public class GToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;

	public enum EButtonShape {
		eRectangle("Rectangle"), // 변수 이름을 0번 이자 스트링 타입 가능
		eOval("Oval"), eLine("Line"), ePolygon("Polygon"); // user가 정의하는 순서를 가진 집합을 정의

		private String name;

		private EButtonShape(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	}

	private EButtonShape eButtonShape;

	public EButtonShape getEButtonShape() {
		return this.eButtonShape; // 드로잉 패널에서 그림 그릴 때마다 가지고 갈 것
	}

	public GToolBar() {
		super();

		ActionHandler actionHandler = new ActionHandler();
		ButtonGroup buttonGroup = new ButtonGroup();

		for (EButtonShape eButtonShape : EButtonShape.values()) {
			JRadioButton buttonShape = new JRadioButton(eButtonShape.getName());
			this.add(buttonShape);
			buttonShape.setActionCommand(eButtonShape.toString()); // 스트링 변환
			buttonShape.addActionListener(actionHandler);
			buttonGroup.add(buttonShape);
		}
	}

	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			eButtonShape = EButtonShape.valueOf(event.getActionCommand());
		}
	}
}