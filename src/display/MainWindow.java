package display;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import misc.Line;
import misc.ListDirectory;
import misc.StringFile;

public class MainWindow extends JFrame implements ActionListener, ItemListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu = new JMenu("Info");
	private JMenuItem help;
	private JMenuItem credits;
	private Panel panelSouth = new Panel();
	private Panel panelNorth = new Panel();
	private Panel comboBoxesOne = new Panel();
	private Panel comboBoxesTwo = new Panel();
	private Panel comboBoxes = new Panel();
	private Panel mainButtons = new Panel();
	private Panel directorySelect = new Panel();
	private JTextPane currentDirectory = new JTextPane();
	private FileChooser fc = new FileChooser(currentDirectory);
	private JTextPane output = new JTextPane();
	private JScrollPane verticalOutputPane = new JScrollPane(output);
	private JTextArea input = new JTextArea();
	private JButton startButton = new JButton("Start");
	private JButton clearButton = new JButton("Clear");
	private JCheckBox caseButton = new JCheckBox("Case Sensitive");
	private JCheckBox lineButton = new JCheckBox("Display Line");
	private JCheckBox memoryButton = new JCheckBox("Memory Efficient");
	private JCheckBox subFolderButton = new JCheckBox("Search Sub Folders");
	private boolean caseSensitive = true;
	private boolean displayLine = false;
	private boolean memoryEfficient = true;
	private boolean searchSubFolder = false;
	// private StringFile[] sFiles;

	public MainWindow() {
		help = new JMenuItem("Help");
		credits = new JMenuItem("Credits");
		menu.add(help);
		menu.add(credits);
		menuBar.add(menu);
		mainButtons.setLayout(new GridLayout(2, 1));
		mainButtons.add(startButton, "North");
		mainButtons.add(clearButton, "South");
		panelSouth.setLayout(new GridLayout(1, 2));
		panelSouth.add(mainButtons, "East");
		panelSouth.add(input, "West");
		comboBoxesOne.setLayout(new GridLayout(2, 1));
		comboBoxesOne.add(caseButton, "North");
		comboBoxesOne.add(lineButton, "South");
		comboBoxesTwo.setLayout(new GridLayout(2, 1));
		comboBoxesTwo.add(memoryButton, "North");
		comboBoxesTwo.add(subFolderButton, "South");
		comboBoxes.setLayout(new GridLayout(1, 2));
		comboBoxes.add(comboBoxesOne, "West");
		comboBoxes.add(comboBoxesTwo, "East");
		directorySelect.setLayout(new GridLayout(2, 1));
		directorySelect.add(fc, "North");
		directorySelect.add(currentDirectory, "South");
		panelNorth.setLayout(new GridLayout(1, 2));
		panelNorth.add(directorySelect, "West");
		panelNorth.add(comboBoxes, "East");
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setJMenuBar(menuBar);
		this.getContentPane().add(panelNorth, "North");
		this.getContentPane().add(verticalOutputPane, "Center");
		this.getContentPane().add(panelSouth, "South");
		caseButton.setSelected(true);
		lineButton.setSelected(false);
		memoryButton.setSelected(true);
		subFolderButton.setSelected(false);
		output.setFocusable(false);
		currentDirectory.setFocusable(false);
		startButton.addActionListener(this);
		clearButton.addActionListener(this);
		caseButton.addItemListener(this);
		lineButton.addItemListener(this);
		memoryButton.addItemListener(this);
		subFolderButton.addItemListener(this);
		help.addActionListener(this);
		credits.addActionListener(this);
		currentDirectory.setText("No Selection");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton && fc.getPath() != "No Selection" && !input.getText().equals("")) {
			input.setText(input.getText().replace("\n", ""));
			workDir(fc.getPath());
		} else if (e.getSource() == clearButton) {
			output.setText("");
			input.setText("");
			// Arrays.fill(sFiles, null);
			System.gc();
		} else if (e.getSource() == credits) {
			JOptionPane.showMessageDialog(this, "© 2019 Nélson Rafael Martins All Rights Reserverd", "Credits",
					JOptionPane.PLAIN_MESSAGE);
		} else if (e.getSource() == help) {
			JOptionPane.showMessageDialog(this,
					"Choose Directory - lists available directories and allows one to be chosen.\n"
							+ "Below is the full path of the chosen directory.\n\n"
							+ "Case Sensitive - the search is case sensitive (self explanatory).\n\n"
							+ "Display Line - the lines which contain the given string are displayed.\n\n"
							+ "Memory Efficient - best option; lowest resourse usage; predefined for a reason.\n\n"
							+ "Search Sub Folders - searches folders inside given directory (self explanatory).\n\n"
							+ "Start - ... (self explanatory).\n\n"
							+ "Clear - clear the display (self explanatory).\n\n"
							+ "Input - right side of both Start and Clear buttons; string to be searched (self explanatory).\n\n\n"
							+ "This program was done to work in plain text files, take notice,and it is not meant to be misused.\n\n"
							+ "Any doubts mail me at:\n\n"
							+ "Nelson.Martins@vicaima-sgps.pt",
					"Help", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void workDir(String p) {
		// System.out.println(p);
		try {
			ListDirectory ld = new ListDirectory(p);
			for (int i = 0; i < ld.getFiles().length; i++) {
				if (ld.getFiles()[i].isFile()) {
					findInFile(i, p, ld);
				} else if (ld.getFiles()[i].isDirectory() && searchSubFolder) {
					// System.out.println(ld.getFiles()[i].getName());
					workDir(p + "\\" + ld.getFiles()[i].getName());
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		/*
		 * sFiles = new StringFile[ld.getFiles().length]; for (int i = 0; i <
		 * ld.getFiles().length; i++) { if (ld.getFiles()[i].isFile()) { findInFile(i,
		 * p, ld); } else if (ld.getFiles()[i].isDirectory() && searchSubFolder) { /* if
		 * (!(ld.getFiles()[i].getName().contains("A Minha Música") ||
		 * ld.getFiles()[i].getName().contains("As Minhas Imagens") ||
		 * ld.getFiles()[i].getName().contains("Modelos Personalizados do Office") ||
		 * ld.getFiles()[i].getName().contains("Os Meus Vídeos"))) {
		 * System.out.println(ld.getFiles()[i].getName());
		 * 
		 * System.out.println(ld.getFiles()[i].getName()); workDir(p + "\\" +
		 * ld.getFiles()[i].getName()); // } } }
		 */
	}

	private void findInFile(int i, String p, ListDirectory ld) {
		if (!this.memoryEfficient) {
			StringFile sf = new StringFile(p, ld.getFiles()[i].getName());
			sf.find(input.getText(), this.caseSensitive, this.displayLine);
			if (sf.getSize() > 0) {
				appendToPane(output, "File " + ld.getFiles()[i].getName(), Color.red, Color.yellow, true);
				output.insertComponent(sf.getButton());
				appendToPane(output, "\n", Color.black, Color.white, false);
				if (this.displayLine) {
					for (Line l : sf.getList()) {
						appendToPane(output, "Line: " + l.getNumber(), Color.blue, Color.white, true);
						appendToPane(output, "\n" + l.getContent() + "\n", Color.black, Color.white, false);
					}
					appendToPane(output, "\n", Color.black, Color.white, false);
				} else {
					appendToPane(output, "Line: ", Color.blue, Color.white, true);
					for (Line l : sf.getList()) {
						appendToPane(output, l.getNumber() + ", ", Color.black, Color.white, false);
					}
					appendToPane(output, "\n", Color.black, Color.white, false);
				}
			}
			// sFiles[i] = sf;
		} else {
			findInFileEff(ld.getFiles()[i].getName(), p);
		}
	}

	private void findInFileEff(String fn, String p) {
		File inputFile = new File(p + "\\" + fn);
		boolean exists = false;
		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
			String line;
			while ((line = br.readLine()) != null && !exists) {
				if (caseSensitive && line.contains(input.getText())) {
					StringFile sf = new StringFile(p, fn);
					exists = true;
					appendToPane(output, "File " + fn, Color.red, Color.yellow, true);
					output.insertComponent(sf.getButton());
					appendToPane(output, "\n", Color.black, Color.white, false);
				} else if (!caseSensitive && line.toLowerCase().contains(input.getText().toLowerCase())) {
					StringFile sf = new StringFile(p, fn);
					exists = true;
					appendToPane(output, "File " + fn, Color.red, Color.yellow, true);
					output.insertComponent(sf.getButton());
					appendToPane(output, "\n", Color.black, Color.white, false);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		if (source == caseButton) {
			this.caseSensitive = !this.caseSensitive;
		} else if (source == lineButton) {
			this.displayLine = !this.displayLine;
		} else if (source == memoryButton) {
			this.memoryEfficient = !this.memoryEfficient;
		} else if (source == subFolderButton) {
			this.searchSubFolder = !this.searchSubFolder;
		}
	}

	private void appendToPane(JTextPane tp, String msg, Color c, Color cb, boolean b) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		aset = sc.addAttribute(aset, StyleConstants.Background, cb);
		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Monospace");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
		aset = sc.addAttribute(aset, StyleConstants.Bold, b);
		int len = tp.getDocument().getLength();
		tp.setCaretPosition(len);
		tp.setCharacterAttributes(aset, false);
		tp.replaceSelection(msg);
	}
}
