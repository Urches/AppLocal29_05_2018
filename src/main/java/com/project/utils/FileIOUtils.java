package com.project.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class FileIOUtils {

	/**
	 * Rewrite or create file on target file path with received content 
	 * @return This file
	 */
	public static File createFile(String filePath, String content) {
		File file = new File(filePath);
		try (FileWriter writer = new FileWriter(file, false); BufferedWriter bf = new BufferedWriter(writer)) {
			bf.write(content);
			bf.flush();
			writer.flush();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return file;
	}

	/**
	 * Read file on received file path 
	 */
	public static String readFile(String filePath) {
		StringBuilder sb = new StringBuilder();
		try (FileReader reader = new FileReader(filePath); BufferedReader br = new BufferedReader(reader)) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return sb.toString();
	}
}
