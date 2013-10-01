package br.com.pn.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileFilterXML extends FileFilter{

	private static final String EXTENSION = "xml";

	@Override
	public boolean accept(File f) {
		
		if(f.isDirectory()){
			return true;
		}
		
		String extension = FileUtil.getExtension(f);
		
		if(extension != null && extension.equalsIgnoreCase(EXTENSION)){
			return true;
		}
		
		return false;
	}

	@Override
	public String getDescription() {
		return "Arquivo XML";
	}

}
