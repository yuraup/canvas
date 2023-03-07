import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class GMenuBar extends JMenuBar {
	public GMenuBar() {
		JMenu fileMenu = new JMenu("File");
		this.add(fileMenu);
	}
}
