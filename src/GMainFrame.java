import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GMainFrame extends JFrame {
	private GMenuBar menuBar;
//	private ToolBar toolBar;
	private GDrawingPanel drawingPanel;
	private static final long serialVersionUID = 1L;
	
	int frame_width = 600;
	int frame_height = 400;
	
	public GMainFrame() {
		//attributes
		//현재 윈도우의 중앙 값을 구하는 게 과제 
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(frame_width, frame_height);
		this.setLocation(res.width / 2 - frame_width / 2, res.height / 2 - frame_height / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //닫기 버튼 누르면 터미널 죽여라 

		//components
		 this.menuBar = new GMenuBar(); //this를 쓰면 바깥에 나와있는 공통이라는 뜻이 됨 
		 this.setJMenuBar(menuBar); //특정한 자식이라서?? 얘만 add 아님 
//		 this.toolBar = new ToolBar();
		 this.drawingPanel = new GDrawingPanel();
		 this.add(drawingPanel);
		 this.setVisible(true);
	}
}
