package display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class FileChooser extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String path;
	private JButton go;
	private JFileChooser chooser;
	private String choosertitle;
	private JTextPane currentDirectory;

	public FileChooser(JTextPane cd) {
		this.path = "No Selection";
		this.currentDirectory = cd;
		go = new JButton("Choose Directory");
		go.addActionListener(this);
		add(go);
	}

	public void actionPerformed(ActionEvent e) {
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle(choosertitle);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			setPath(chooser.getSelectedFile().getPath());
		} else {
			//System.out.println("No Selection");
		}
	}

	private void setPath(String s) {
		this.path = s;
		this.currentDirectory.setText(s);
	}

	public String getPath() {
		return this.path;
	}
}