package main;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import frames.GDrawingPanel;
import frames.GMenuBar;
import frames.GToolBar;

public class GMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private GMenuBar menuBar;
	private GToolBar toolBar;
	private GDrawingPanel drawingPanel;

//	int frame_width = 600;
//	int frame_height = 400;

	public GMainFrame() {

		// attributes
//		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(GConstants.CMainFrame.w, GConstants.CMainFrame.h);
		this.setLocation(GConstants.CMainFrame.x, GConstants.CMainFrame.y);
//		this.setSize(frame_width, frame_height);
//		this.setLocation(res.width / 2 - frame_width / 2, res.height / 2 - frame_height / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 누르면 터미널 죽여라

		// components
		BorderLayout flowlayout = new BorderLayout();
		this.setLayout(flowlayout);

		this.menuBar = new GMenuBar(); // this를 쓰면 바깥에 나와있는 공통이라는 뜻이 됨
		this.setJMenuBar(menuBar); // 특정한 자식이라서?? 얘만 add 아님

		this.toolBar = new GToolBar();
		this.add(this.toolBar, BorderLayout.NORTH);

		this.drawingPanel = new GDrawingPanel();
		this.add(drawingPanel, BorderLayout.CENTER);

		this.drawingPanel.setToolBar(toolBar); // 자식 연결
		this.setVisible(true);
	}
}