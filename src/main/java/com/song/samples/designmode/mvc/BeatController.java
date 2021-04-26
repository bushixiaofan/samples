package com.song.samples.designmode.mvc;

/**
 * @author: songzeqi
 * @Date: 2019-11-26 2:59 PM
 */

public class BeatController implements ControllerInterface{
    BeatModelInterface model;//MVC中，控制器在中间，所以要同时持有模型以及视图的引用。
    DJView view;
    public BeatController(BeatModelInterface model) {
        this.model = model;
        view = new DJView(this, model);//控制器创建视图
        view.createView();
        view.createControls();
        view.disableStopMenuItem();
        view.enableStartMenuItem();
        model.initialize();
    }
    public void start() {//控制器在得到start指令时去操纵模型和视图，下边的几个动作同理。
        model.on();
        view.disableStartMenuItem();//注意，控制器这时在帮视图做决定，视图只知道如何将菜单项变成开或者关而不知道在何时该这么做
        view.enableStopMenuItem();
    }
    public void stop() {
        model.off();
        view.disableStopMenuItem();
        view.enableStartMenuItem();
    }
    public void increaseBPM() {//控制器扩展了模型的动作
        int bpm = model.getBPM();
        model.setBPM(bpm + 1);
    }
    public void decreaseBPM() {
        int bpm = model.getBPM();
        model.setBPM(bpm - 1);
    }
    public void setBPM(int bpm) {
        model.setBPM(bpm);
    }
}
