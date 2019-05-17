package com.song.samples.triangulation;

import java.io.*;

public class Polygon {
    private int n;
    private double[] y;// 点的横坐标
    private double[] x;// 点的纵坐标
    private double[][] weight;

    public Polygon() {
    }

    public Polygon(java.util.List<Integer> xList, java.util.List<Integer> yList) {
        n = xList.size();
        x = new double[n];
        y = new double[n];
        for(int i = 0; i< n; i++) {
            x[i] = xList.get(i);
            y[i] = yList.get(i);
        }
        System.out.println("\nx=" + x + ", y=" + y + '\n');
        weight = new double[n][n];
        this.createWeight();
    }

    public Polygon(String fileName) {
        System.out.println(fileName);
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            String line = in.readLine();
            String[] xArray = line.split(", ");
            line = in.readLine();
            String[] yArray = line.split(",");
            n = xArray.length;
            x = new double[n];
            y = new double[n];

            weight = new double[n][n];
            for (int i = 0; i < n; i++) {
                x[i] = Double.valueOf(xArray[i]);
                y[i] = Double.valueOf(yArray[i]);
            }
            this.createWeight();
        } catch (IOException e) {
            System.out.println("Problem IOException" + e);
        }

    }

    public void createWeight() {
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

    public void writeTxt(String fileName, int n, double[][] t, int[][] s) {
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
