package com.song.samples.triangulation;

import com.google.common.util.concurrent.Uninterruptibles;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Triangulation {

    private int n; // 凸三角形顶点数
    private double[][] weight; // 记录两点之间的权值
    private int[][] s = new int[n][n];
    private double[][] t = new double[n][n];
    private double[] y;// 点的横坐标
    private double[] x;// 点的纵坐标

    public Triangulation() {
    }

    public Triangulation(double[] x, double[] y, int n, double[][] weight) {
        this.n = n;
        this.weight = weight;
        this.x = x;
        this.y = y;
    }

    public Triangulation(Polygon polygon) {
        this.n = polygon.getN();
        this.weight = polygon.getWeight();
        this.x = polygon.getX();
        this.y = polygon.getY();
        this.s = new int[n][n];
        this.t = new double[n][n];
    }

    public void minWeightTriangulation() {
        for (int i = 0; i < n; i++) { // 顶点重合权值为0
            t[i][i] = 0;
        }
        for (int r = 2; r <= n; r++) { // r记录链长
            for (int i = 1; i <= n - r + 1; i++) {
                int j = (i + r - 1) % n; // j-i=r-1
                int k = i;
                t[i][j] = t[i][k] + t[(k + 1) % n][j] + weight[i - 1][k] + weight[k][j] + weight[i - 1][j];
                this.s[i][j] = k;
                for (k = i + 1; k < j; k++) {
                    double u;
                    u = t[i][k] + t[(k + 1) % 10][j] + +weight[i - 1][k] + weight[k][j] + weight[i - 1][j];
                    if (u < t[i][j]) {
                        t[i][j] = u;
                        s[i][j] = k;
                    }
                }
                System.out.println("\n");
                System.out.print(" " + i + j + " " + s[i][j] + " " + t[i][j]);
            }
        }
    }

    public void traceBack(int i, int j, Graphics g1) {
        if (i == j) {
            return;
        }

        traceBack(i, s[i][j], g1);
        g1.drawLine((int) x[i - 1] , (int) y[i - 1] , (int) x[j], (int) y[j]);
        Uninterruptibles.sleepUninterruptibly(1000L, TimeUnit.MILLISECONDS);
        traceBack(s[i][j] + 1, j, g1);
    }

    public void drawPolygon(Graphics g1) {
        for (int i = 0; i < n; i++) {
            g1.drawLine((int) x[i], (int) y[i], (int) x[(i + 1) % n],
                    (int) y[(i + 1) % n]);
            g1.drawString("(" + (int) x[i] + ", "  + (int) y[i] + ")", (int) x[i], (int) y[i]);

        }
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double[][] getWeight() {
        return weight;
    }

    public void setWeight(double[][] weight) {
        this.weight = weight;
    }

    public int[][] getS() {
        return s;
    }

    public void setS(int[][] s) {
        this.s = s;
    }

    public double[][] getT() {
        return t;
    }

    public void setT(double[][] t) {
        this.t = t;
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
}
