package misc;

public class Line {

	private String content;
	private int number;

	public Line(int n, String s) {
		this.number = n;
		this.content = s;
	}

	public int getNumber() {
		return this.number;
	}

	public String getContent() {
		return this.content;
	}

}
