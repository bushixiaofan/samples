package com.song.samples.innerclass;

/**
 * 内部类测试
 */
public class Outter {

    private Inner inner;

    public Outter() {
        System.out.println("construct outter...");
    }

    public Inner getInnerInstance() {
        if (inner == null) {
            return new Inner();
        } else {
            return inner;
        }
    }

    private class Inner {
        public Inner() {
            System.out.println("construct inner...");
        }
    }

    public void testAnonymous(final int b) {
        final int a = 10;
        new Thread(){
            @Override
            public void run() {
                super.run();
                System.out.println(a);
                System.out.println(b);
            }
        }.start();

    }

    public static void main(String[] args) {
        Outter outter = new Outter();
        Inner inner = outter.new Inner();
    }
}
