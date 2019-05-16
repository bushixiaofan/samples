package com.song.samples.triangulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadTxt {
    private String fileName = "/Users/songzeqi/test.txt";
    private String line;
    private int n;
    private double[] y;// ��¼����ĺ�����
    private double[] x;// ��¼�����������
    private double[][] weight;

    public ReadTxt() {
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            n = (line.length() - 2) / 6;
            line = in.readLine();
            x = new double[n];
            y = new double[n];
            System.out.println("Problem array x and y");
            System.out.println("Problem array x and y");

            weight = new double[n][n];
            for (int i = 0; i < n; i++) {
                x[i] = (double) line.charAt(i * 6 + 4) - 48;
                y[i] = (double) line.charAt(i * 6 + 6) - 48;
            }
        } catch (IOException e) {
			System.out.println("Problem IOException" + e);
		}

    }

    public void createWeight(double[] x, double[] y, double[][] weight) {
        if (x.length == y.length) {
            for (int i = 0; i < x.length; i++) {
                System.out.println(' ');
                for (int j = 0; j < x.length; j++) {
                    weight[i][j] = Math.sqrt(Math.pow(Math.abs(x[i] - x[j]), 2) + Math.pow(Math.abs(y[i] - y[j]), 2));
                    System.out.print(" " + weight[i][j]);
                }
            }
        } else
            System.out.println("Problem array x and y");
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double[] getY() {
        return y;
    }

    public void setY(double[] y) {
        this.y = y;
    }

    public double[] getX() {
        return x;
    }

    public void setX(double[] x) {
        this.x = x;
    }

    public double[][] getWeight() {
        return weight;
    }

    public void setWeight(double[][] weight) {
        this.weight = weight;
    }
}
