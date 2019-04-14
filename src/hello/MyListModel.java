package hello;

import javax.swing.AbstractListModel;
import hello.judge;
class MyListModel extends AbstractListModel<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] contents = judge.top8Word;
	
	public String getElementAt(int x) {
		if (x < contents.length)
			return contents[x++];
		else
			return null;
	}
	
	public int getSize() {
		return contents.length;
	}
}
