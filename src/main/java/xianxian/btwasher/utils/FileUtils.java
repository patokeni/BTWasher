package xianxian.btwasher.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
	public static byte[] readFileToByteArray(String file) throws IOException {
		return readFileToByteArray(new File(file));
	}
	public static byte[] readFileToByteArray(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int pos = 0;
		while ((pos = fis.read(buff,0,100)) > 0) {
			baos.write(buff, 0, pos);
		}
		fis.close();
		return baos.toByteArray();
	}
	public static void writeByteArrayToFile(byte[] byteArray,String file) throws IOException {
		writeByteArrayToFile(byteArray, new File(file));
	}
	public static void writeByteArrayToFile(byte[] byteArray,File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(byteArray);
		fos.close();
	}
	public static String getFileExtension(String filePath) {
		int index = filePath.lastIndexOf(".");
		String extension = filePath.substring(index);
		if (extension.contains(" ")) {
			return "";
		}
		return extension;
	}
	public static String getFilePathWithoutExtension(String filePath){
		int index = filePath.lastIndexOf(".");
		String file = filePath.substring(0,index);
		return file;
	}
}
