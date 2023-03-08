import javax.swing.JRadioButton;
import javax.swing.JToolBar;

public class GToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;

	public GToolBar () {
		JRadioButton btnRectangle = new JRadioButton("Rectangle");
		this.add(btnRectangle);
		
		JRadioButton btnOval = new JRadioButton("Oval");
		this.add(btnOval);
		
		JRadioButton btnLine = new JRadioButton("Line");
		this.add(btnLine);
		
		JRadioButton btnPloygon = new JRadioButton("Ploygon");
		this.add(btnPloygon);
	}
}
