package misc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;

public class StringFile implements ActionListener {

	private JButton openButton = new JButton("Open");
	private String fileName;
	private String filePath;
	private ArrayList<Line> linesList;
	private int size;

	public StringFile(String fp, String fn) {
		this.fileName = fn;
		this.filePath = fp;
		this.linesList = new ArrayList<Line>();
		openButton.addActionListener(this);
	}

	public void find(String fString, boolean caseSensitive, boolean displayLine) {
		File inputFile = new File(this.filePath + "\\" + this.fileName);
		int i = 1, j = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
			String line;
			Line l;
			while ((line = br.readLine()) != null) {
				if (caseSensitive && line.contains(fString)) {
					if(displayLine)
						l = new Line(i, line.trim());
					else
						l = new Line(i, null);
					linesList.add(l);
					j++;
				} else if (!caseSensitive && line.toLowerCase().contains(fString.toLowerCase())) {
					if(displayLine)
						l = new Line(i, line.trim());
					else
						l = new Line(i, null);
					linesList.add(l);
					j++;
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.size = j;
	}

	public int getSize() {
		return this.size;
	}

	public ArrayList<Line> getList() {
		return this.linesList;
	}
	
	public JButton getButton() {
		return this.openButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == openButton) {
			ProcessBuilder pb = new ProcessBuilder("Notepad.exe", this.filePath + "\\" + this.fileName);
			try {
				pb.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
