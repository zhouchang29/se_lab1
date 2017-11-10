/**
 * 
 */
package se_lab1;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

import javax.swing.*;

public class Lab1_test {

	public String fileUrl;
	public String[] words;
	public Tree t;
	public int imgState = 0;
	
	public void readInFile(){
		File file = new File(fileUrl);
		String wordsStr = "";
		Scanner in;
		try {
			in = new Scanner(file);
			while(in.hasNextLine()){
				String str = in.nextLine();
				wordsStr = wordsStr.concat(replaceStr(str)+" ");
			}
			words = wordSplit(wordsStr);
			t = new Tree(words);
			//DirectedGraph.createDirectedGraph(t, fileUrl, "Verdana", 12);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Lab1_test(String fileUrl) {
		super();
		this.fileUrl = fileUrl;
		readInFile();
	}

	/** 切割字符串
	 * @param str 读入的字符串
	 * @return 切割后的String[]
	 */
	public String[] wordSplit(String str) {
		return str.split("\\s+");
	}
	
	/** 处理读入的字符串(删除标点符号，并转换为小写)
	 * @param str 读入的字符串
	 * @return 处理后的字符串
	 */
	public String replaceStr(String str){
		return str.replaceAll("[^a-zA-Z]", " ").toLowerCase();
	}
	
}