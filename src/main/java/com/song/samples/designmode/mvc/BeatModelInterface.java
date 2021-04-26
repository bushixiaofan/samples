package com.song.samples.designmode.mvc;

/**
 * BeatModel负责管理节拍
 */
public interface BeatModelInterface {
    /**
     * 以下方法是让控制器调用的
     * 控制器根据用户的操作而对模型做出适当的处理
     */
    void initialize();

    void on();

    void off();

    void setBPM(int bpm);

    /**
     * 这些方法允许视图和控制器取得状态，并变成观察者
     *
     */
    int getBPM();

    /**
     * 分两种观察者，一种希望每个节拍都被通知，另一种希望BPM改变时被通知
     */
    void registerObserver(BeatObserver o);

    void removeObserver(BeatObserver o);

    void registerObserver(BPMObserver o);

    void removeObserver(BPMObserver o);



}
