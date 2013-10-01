package br.com.pn.view;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GridBagUtil {

	
	public static GridBagConstraints getGridBagConstraints(int x, int y) {
		return getGridBagConstraints(x, y, 0, 0, GridBagConstraints.CENTER);
	}
	
	public static GridBagConstraints getGridBagConstraints(int x, int y, int weightx, int weighty) {
		return getGridBagConstraints(x, y, 0, 0, GridBagConstraints.CENTER);
	}
	
	public static GridBagConstraints getGridBagConstraints(int x, int y, int weightx, int weighty, int position) {
		return getGridBagConstraints(x, y, 0, 0, position, 1, 1, GridBagConstraints.HORIZONTAL);
	}
	
	public static GridBagConstraints getGridBagConstraints(int x, int y, int weightx, int weighty, int position, int colspan, int linespan) {
		return getGridBagConstraints(x, y, 0, 0, position, colspan, linespan, GridBagConstraints.HORIZONTAL);
	}
	public static GridBagConstraints getGridBagConstraints(int x, int y,
			int weightx, int weighty, int position, int colspan, int linespan,
			int fill){
		return getGridBagConstraints(x, y, 0, 0, position, colspan, linespan, fill, new Insets(5, 5, 5, 5));
	}
	
	public static GridBagConstraints getGridBagConstraints(int x, int y,
			int weightx, int weighty, int position, int colspan, int linespan,
			int fill, Insets inserts) {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = inserts;
		gridBagConstraints.anchor = position;
		gridBagConstraints.fill = fill;
		gridBagConstraints.gridx = x;
		gridBagConstraints.gridy = y;
		gridBagConstraints.weightx = weightx;
		gridBagConstraints.weighty = weighty;
		gridBagConstraints.gridwidth = colspan;
		gridBagConstraints.gridheight = linespan;
		
		return gridBagConstraints;
	}
}
