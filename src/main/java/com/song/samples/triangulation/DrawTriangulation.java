package com.song.samples.triangulation;

import com.google.common.collect.Lists;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class DrawTriangulation extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel cards = new JPanel(new CardLayout());
    private JPanel p0 = new JPanel(); // 面板1
    private JPanel p1 = new JPanel(); // 面板2
    private JPanel p2 = new JPanel(); // 面板3
    private java.util.List<Integer> xList = Lists.newLinkedList();
    private java.util.List<Integer> yList = Lists.newLinkedList();

    private Polygon polygon;
    private Triangulation triangulation;



    public DrawTriangulation() {
    }

    public void doDrawTriangulation() {
        this.setTitle("Triangulation");
        this.setSize(600, 600);
        this.setLocation(0, 0);
        this.add(cards);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cards.add(p0, "card0");
        cards.add(p1, "card1");
        cards.add(p2, "card2");

        JLabel l1 = new JLabel("最优权重和");
        l1.setBounds(480, 50, 100, 40);

        JTextArea t1 = new JTextArea();
        t1.setBounds(480, 150, 100, 100);

        JLabel l2 = new JLabel("文件路径");
        l2.setBounds(20, 20, 100, 40);

        JTextArea t2 = new JTextArea();
        t2.setBounds(120, 20, 200, 40);
        t2.setText("/Users/songzeqi/test1.txt");


        JButton b11 = new JButton("选择文件");
        b11.setBounds(50, 520, 100, 40);
        b11.addActionListener(e -> {
            String path = t2.getText();
            polygon = new Polygon(path);
            triangulation = new Triangulation(polygon);
            triangulation.minWeightTriangulation();

        });

        JButton b12 = new JButton("完成绘制");
        b12.setBounds(50, 520, 100, 40);
        b12.addActionListener(e -> {
            Graphics g1 = p2.getGraphics();
            System.out.println("\nx=" + xList.toString() + ", y=" + yList.toString() + '\n');
            for(int i = xList.size() - 1; i > 1; i --) {
                g1.drawLine(xList.get(i), yList.get(i), xList.get(i-1), yList.get(i-1));
            }
            g1.drawLine(xList.get(xList.size() -1), yList.get(xList.size() - 1), xList.get(0), yList.get(0));
            polygon = new Polygon(xList, yList);
            triangulation = new Triangulation(polygon);
            triangulation.minWeightTriangulation();

        });


        JButton b2 = new JButton("开始处理");
        b2.setBounds(250, 520, 100, 40);
        b2.addActionListener(e -> {
            int n = triangulation.getN();
            double[][] t = triangulation.getT();
            Graphics g2 = p2.getGraphics();
            g2.setColor(Color.black);
            triangulation.drawPolygon(g2);
            triangulation.traceBack(1, n - 1, g2);
            t1.setText(Double.toString(t[1][n - 1]));

        });

        JButton b3 = new JButton("返回");
        b3.setBounds(450, 520, 100, 40);
        b3.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "card0");
        });

        // 首页面板
        p0.setLayout(new GridLayout(1, 2, 5, 5));
        JButton b00 = new JButton("从文件中读取");
        p0.add(b00);
        b00.addActionListener(e -> {
            // 从文件中读取
            p1.setLayout(null);
            p1.add(t2);
            p1.add(l2);
            p1.add(l1);
            p1.add(t1);
            p1.add(b11);
            p1.add(b2);
            p1.add(b3);
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "card1");
        });


        p2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Graphics g1 = p2.getGraphics();
                xList.add(e.getX());
                yList.add(e.getY());
                System.out.println("\nx=" + e.getX() + ", y=" + e.getY() + '\n');
                for(int i = xList.size() - 1; i >= 1; i --) {
                    g1.drawLine(xList.get(i), yList.get(i), xList.get(i-1), yList.get(i-1));
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        JButton b01 = new JButton("绘制多边形");
        p0.add(b01);
        b01.addActionListener(e -> {
            // 三角剖分展示页
            p2.setLayout(null); // 使用GridBagLayout布局管理器
            p2.add(l1);
            p2.add(t1);
            p2.add(b12);
            p2.add(b2);
            p2.add(b3);
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "card2");
        });


        CardLayout cl = (CardLayout) (cards.getLayout());

        cl.show(cards, "card0"); // 调用show()方法显示面板0
        this.setVisible(true);

    }

    public static void main(String[] args) {
        DrawTriangulation drawTriangulation = new DrawTriangulation();
        drawTriangulation.doDrawTriangulation();
    }
}
