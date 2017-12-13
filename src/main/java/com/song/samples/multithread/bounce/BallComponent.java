package com.song.samples.multithread.bounce;

import com.google.common.collect.Lists;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * the component that draws the balls
 */
public class BallComponent extends JPanel {
    private ArrayList<Ball> balls = Lists.newArrayList();

    public void add(Ball ball) {
        balls.add(ball);
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;
        for (Ball ball : balls) {
            g2.fill(ball.getShape());
        }
    }
}
