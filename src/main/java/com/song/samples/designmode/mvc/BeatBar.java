package com.song.samples.designmode.mvc;

import javax.swing.*;

/**
 * @author: songzeqi
 * @Date: 2019-11-26 3:17 PM
 */

public class BeatBar extends JProgressBar implements Runnable {
    private static final long serialVersionUID = 2L;
    JProgressBar progressBar;
    Thread thread;

    public BeatBar() {
        thread = new Thread(this);
        setMaximum(100);
        thread.start();
    }

    public void run() {
        Long i = 0L;
        while (i < 100000000000L) {
            int value = getValue();
            value = (int) (value * .75);
            setValue(value);
            repaint();
            try {
                Thread.sleep(50);
            } catch (Exception e) {
            }
            i++;
        }
    }

}

