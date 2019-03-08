package com.song.samples.triangulation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteTxt {
//	int n;
//	private double[][] t;
//	private double[][] s;
	public WriteTxt(int n, double[][] t, int[][] s){
		String fileName="/Users/songzeqi/test.txt";
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
				writer.write("output \n");
				writer.newLine();
				for (int i = 1; i < n; i++) {
					String str = "";
					for (int j = 1; j < n; j++) {
						str += t[i][j] + "\t\t\t";
					}
					writer.write(str + "\t\t\t");
					writer.newLine();
				}
				for (int i = 1; i < n; i++) {
					String str1 = "";
					for (int j = 1; j < n; j++) {
						str1 += s[i][j] + "\t\t\t";
					}
					writer.write(str1 + "\t\t\t");
					writer.newLine();
				}
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
