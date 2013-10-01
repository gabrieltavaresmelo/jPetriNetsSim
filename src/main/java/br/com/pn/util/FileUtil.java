package br.com.pn.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static List<String> readTxt(String filename){
		File fileTxt = new File(filename);
		
		if(!fileTxt.exists()){
			System.out.println("Erro ao abrir arquivo:\n" + fileTxt.getAbsolutePath());
			return null;
		}
		
		try {
			BufferedReader bufferReader = new BufferedReader(new FileReader(fileTxt));
			List<String> bufferList = new ArrayList<String>();
			
			String line = "";			
			while((line = bufferReader.readLine()) != null){
				bufferList.add(line);
			}
						
			
			bufferReader.close();
			return bufferList;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static byte[] readBytes(String filename){
		File fileTxt = new File(filename);
		
		if(!fileTxt.exists()){
			System.out.println("Erro ao abrir arquivo:\n" + fileTxt.getAbsolutePath());
			return null;
		}
				
		try {
			int size = (int) fileTxt.length();
			byte[] bytes = new byte[size];
			int read = 0;
			int numRead = 0;
			
			DataInputStream dis = new DataInputStream(new FileInputStream(fileTxt));
			
			while (read < bytes.length
					&& (numRead = dis.read(bytes, read, bytes.length - read)) >= 0) {
				read = read + numRead;
			}

			if (read < bytes.length) {
				System.out.println("Erro ao ler: " + fileTxt.getName());
			}
			
			dis.close();
			
			return bytes;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void saveTxt(String line, String filename, boolean isEndSave){
        try {
			FileWriter file = new FileWriter(filename, isEndSave);
			BufferedWriter writer = new BufferedWriter(file);
			
			writer.write(line);
			writer.newLine();
			
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void save(byte[] bytes, File path) throws FileNotFoundException, IOException {
		if(bytes != null){
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(bytes);
			fos.close();
		}
	}
	
	public static String getExtension(File path){
		try {
			if(path != null){
				String filename = path.getName();
				
				if(filename != null){
					filename = filename.trim();
					int idx = filename.lastIndexOf(".");
					
					if((idx > -1) && (idx+1 <= filename.length())){
						String extension = filename.substring(idx+1);
						
						return extension.toLowerCase();
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		
		return null;
	}
}
