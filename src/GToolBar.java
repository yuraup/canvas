import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;


public class GToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	
	private JRadioButton btnRectangle;
	private JRadioButton btnOval;
	private JRadioButton btnLine;
	private JRadioButton btnPloygon;
	
	public GToolBar (GMainFrame.ItemHandler itemHandler) {
		ButtonGroup group = new ButtonGroup();

		this.btnRectangle = new JRadioButton("Rectangle");
		this.btnRectangle.addItemListener(itemHandler);
		group.add(this.btnRectangle);
		this.add(this.btnRectangle);
		
		this.btnOval = new JRadioButton("Oval");
		this.btnOval.addItemListener(itemHandler);
		
		group.add(this.btnOval);
		this.add(this.btnOval);
		
		this.btnLine = new JRadioButton("Line");
		this.btnLine.addItemListener(itemHandler);
		group.add(this.btnLine);	
		this.add(this.btnLine);
		
		this.btnPloygon = new JRadioButton("Ploygon");
		this.btnPloygon.addItemListener(itemHandler);
		group.add(this.btnPloygon);
		this.add(this.btnPloygon);
	}
}