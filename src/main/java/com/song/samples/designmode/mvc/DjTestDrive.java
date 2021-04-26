package com.song.samples.designmode.mvc;

/**
 * @author: songzeqi
 * @Date: 2019-11-27 10:18 PM
 */

public class DjTestDrive {
    public static void main(String[] args) {
        BeatModelInterface beatModel = new BeatModel();
        ControllerInterface controller = new BeatController(beatModel);
    }

}
