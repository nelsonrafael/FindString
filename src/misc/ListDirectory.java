package misc;

import java.io.File;

public class ListDirectory {

	private String path;
	private File[] listOfFiles;
	private File folder;

	public ListDirectory(String s) {
		this.path = s;
		folder = new File(path);
		listOfFiles = folder.listFiles();
	}

	public File[] getFiles() {
		return listOfFiles;
	}
}