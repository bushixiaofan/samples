package com.song.samples.triangulation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DrawTriangulation extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    JPanel p1 = new JPanel();
    JTextArea t1 = new JTextArea();
    Graphics g1;
    private int n;
    int[] x = new int[n];
    int[] y = new int[n];
    private int[][] s = new int[n][n];
    private double[][] t = new double[n][n];

    public double[][] getT() {
        return t;
    }

    public void setT(double[][] t) {
        this.t = t;
    }

    @SuppressWarnings("deprecation")
    public DrawTriangulation() {
        this.setTitle("Triangulation");
        this.setSize(600, 600);
        this.setLocation(0, 0);
        Container c = this.getContentPane();
        getContentPane().setLayout(null);

        p1.setBounds(0, 0, 600, 600);
        p1.setLayout(null);
        c.add(p1);

        JLabel L1 = new JLabel("the sum of the chords�� length");
        p1.add(L1);
        L1.setBounds(10, 500, 200, 50);

        p1.add(t1);
        t1.setBounds(210, 500, 100, 50);

        JButton b1 = new JButton("开始");
        b1.setBounds(400, 500, 100, 50);
        p1.add(b1);
        b1.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.show();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String cmd = e.getActionCommand();
        if (cmd.equals("开始")) {

            Graphics g1 = p1.getGraphics();
            g1.setColor(Color.black);
            for (int i = 0; i < n; i++) {
                g1.drawLine(x[i] * 50 + 10, y[i] * 50 + 10, x[(i + 1) % n] * 50 + 10, y[(i + 1) % n] * 50 + 10);
                g1.drawString("(" + x[i] + y[i] + ")", x[i] * 50 + 10, y[i] * 50 + 10);

            }
            TraceBack(1, n - 1, s, g1);
            t1.setText(Double.toString(t[1][n - 1]));

        }
    }

    public void TraceBack(int i, int j, int[][] s, Graphics g1) {
        if (i == j) {
            return;
        }

        TraceBack(i, s[i][j], s, g1);
        g1.drawLine(x[i - 1] * 50 + 10, y[i - 1] * 50 + 10, x[j] * 50 + 10, y[j] * 50 + 10);
        TraceBack(s[i][j] + 1, j, s, g1);
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setX(int[] x) {
        this.x = x;
    }

    public int[] getX1() {
        return x;
    }

    public int[] getY1() {
        return y;
    }

    public void setY(int[] y) {
        this.y = y;
    }

    public int[][] getS() {
        return s;
    }

    public void setS(int[][] s) {
        this.s = s;
    }

}
