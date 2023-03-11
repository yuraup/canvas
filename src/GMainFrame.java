import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GMainFrame extends JFrame {
	private GMenuBar menuBar;
	private GToolBar toolBar;
	private GDrawingPanel drawingPanel;
	private static final long serialVersionUID = 1L;
	private String select;
	ItemHandler itemHandler;
	int frame_width = 600;
	int frame_height = 400;
	
	public void setTool(String select) {
		this.select = select;
		 this.drawingPanel.getShape(this.select);
	}
	
	public GMainFrame(){

		//attributes
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(frame_width, frame_height);
		this.setLocation(res.width / 2 - frame_width / 2, res.height / 2 - frame_height / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //닫기 버튼 누르면 터미널 죽여라 

		this.itemHandler = new ItemHandler();
		//components
		 BorderLayout flowlayout = new BorderLayout();
		 this.setLayout(flowlayout);
		 
		 this.menuBar = new GMenuBar(); //this를 쓰면 바깥에 나와있는 공통이라는 뜻이 됨 
		 this.setJMenuBar(menuBar); //특정한 자식이라서?? 얘만 add 아님 
		 
		 this.toolBar = new GToolBar(this.itemHandler);
		 this.add(this.toolBar, BorderLayout.NORTH);
	
		 this.drawingPanel = new GDrawingPanel();
		 this.add(drawingPanel, BorderLayout.CENTER);
		 
		 this.setVisible(true);
 
	}

	private void selectTool (String shape) {
		if (shape == "Rectangle") {
			this.drawingPanel.getShape("Rectangle");
		} else if (shape == "Oval") {
			this.drawingPanel.getShape("Oval");
		} else if (shape == "Line") {
			//과제 밖 범위 
			this.drawingPanel.getShape("Line");
		} else if (shape == "Ploygon") {
			//과제 밖 범위 
			this.drawingPanel.getShape("Ploygon");
		} 
	}

	public class ItemHandler implements ItemListener  {
		@Override
		public void itemStateChanged(ItemEvent e) {
			AbstractButton aButton = (AbstractButton)e.getSource();
			String label = aButton.getText();
	
			if (label.equals("Rectangle")) {
				selectTool("Rectangle");
			} else if (label.equals("Oval")) {
				selectTool("Oval");
			} else if (label.equals("Line")) {
				selectTool("Line");
			} else if (label.equals("Ploygon")) {
				selectTool("Ploygon");
			}
		}
	}
}