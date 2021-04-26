package com.song.samples.designmode.mvc;

import com.google.common.collect.Lists;

import javax.sound.midi.*;
import java.util.ArrayList;

/**
 * @author: songzeqi
 * @Date: 2019-11-25 8:25 PM
 */

public class BeatModel implements BeatModelInterface, MetaEventListener {

    Sequencer sequencer;

    ArrayList<BeatObserver> beatObservers = Lists.newArrayList();

    ArrayList<BPMObserver> bpmObservers = Lists.newArrayList();

    int bpm = 90;

    // 其他实例化的变量
    Sequence sequence;
    Track track;

    /**
     * 以下方法是让控制器调用的
     * 控制器根据用户的操作而对模型做出适当的处理
     */
    @Override
    public void initialize() {
        setUpMidi();
        buildTrackAndStart();
    }

    @Override
    public void on() {
        sequencer.start();
        setBPM(90);
    }

    @Override
    public void off() {
        setBPM(0);
        sequencer.stop();
    }

    @Override
    public void setBPM(int bpm) {
        this.bpm = bpm;
        sequencer.setTempoInBPM(getBPM());
        notifyBPMObservers();
    }

    /**
     * 这些方法允许视图和控制器取得状态，并变成观察者
     */
    @Override
    public int getBPM() {
        return bpm;
    }

    void beatEvent() {
        notifyBeatObservers();
    }

    /**
     * 分两种观察者，一种希望每个节拍都被通知，另一种希望BPM改变时被通知
     *
     * @param o
     */
    @Override
    public void registerObserver(BeatObserver o) {
        beatObservers.add(o);

    }

    @Override
    public void removeObserver(BeatObserver o) {
        int i = beatObservers.indexOf(o);
        if (i > 0) {
            beatObservers.remove(o);
        }
    }

    @Override
    public void registerObserver(BPMObserver o) {
        bpmObservers.add(o);
    }

    @Override
    public void removeObserver(BPMObserver o) {
        int i = bpmObservers.indexOf(o);
        if (i > 0) {
            bpmObservers.remove(o);
        }
    }

    /**
     * Invoked when a <code>{@link Sequencer}</code> has encountered and processed
     * a <code>MetaMessage</code> in the <code>{@link Sequence}</code> it is processing.
     *
     * @param meta the meta-message that the sequencer encountered
     */
    @Override
    public void meta(MetaMessage meta) {
        if (meta.getType() == 47) {
            beatEvent();
            sequencer.start();
            setBPM(getBPM());
        }
    }

    public void notifyBeatObservers() {
        for(int i = 0; i<beatObservers.size(); i++) {
            BeatObserver observer = beatObservers.get(i);
            observer.updateBeat();

        }
    }

    public void notifyBPMObservers() {
        for(int i = 0; i<bpmObservers.size(); i++) {
            BPMObserver observer = bpmObservers.get(i);
            observer.updateBPM();

        }
    }

    public void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addMetaEventListener(this);
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(getBPM());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buildTrackAndStart() {
        int[] trackList = {35, 0, 46, 0};

        sequence.deleteTrack(null);
        track = sequence.createTrack();

        makeTracks(trackList);

        track.add(makeEvent(192, 9, 1, 0, 4));

        try {
            sequencer.setSequence(sequence);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void makeTracks(int[] list) {
        for(int i = 0; i<list.length;i++) {
            int key = list[i];

            if (key != 0) {
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i+1));
            }
        }
    }

    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return event;
    }
}
