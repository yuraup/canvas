import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class GFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	public GFileMenu(String title) {
		super(title);
		
		JMenuItem itemNew = new JMenuItem("New");
		this.add(itemNew);
	}
}
