import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

public class GToolBar extends JToolBar implements ActionListener {
	private static final long serialVersionUID = 1L;

	public GToolBar () {
		ButtonGroup group = new ButtonGroup();

		
		JRadioButton btnRectangle = new JRadioButton("Rectangle");
		btnRectangle.addActionListener(this);
		group.add(btnRectangle);
		this.add(btnRectangle);
		
		JRadioButton btnOval = new JRadioButton("Oval");
		btnOval.addActionListener(this);
		group.add(btnOval);
		this.add(btnOval);
		
		JRadioButton btnLine = new JRadioButton("Line");
		btnLine.addActionListener(this);
		group.add(btnLine);
		this.add(btnLine);
		
		JRadioButton btnPloygon = new JRadioButton("Ploygon");
		btnPloygon.addActionListener(this);
		group.add(btnPloygon);
		this.add(btnPloygon);
		
	}
	
	public String selectTool (String select) {
		GDrawingPanel gDrawingPanel = new GDrawingPanel();
		gDrawingPanel.aa(select);
		System.out.println("d" + select);
		return select;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonRoute = e.getActionCommand();
		
		if (buttonRoute == "Rectangle") {
			selectTool("Rectangle");
		} else if (buttonRoute == "Oval") {
			selectTool("Oval");
		} else if (buttonRoute == "Line") {
			selectTool("Line");
		} else if (buttonRoute == "Ploygon") {
			selectTool("Ploygon");
		}
	}
	
}
