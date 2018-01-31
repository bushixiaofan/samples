package com.song.samples.concurrent.sycn;

public class AlternatePrint {

    public static class Num{
        // 数字
        public int i = 0;
        // false：打印奇数，true：打印偶数
        boolean flag = false;

        void printNum() {
            System.out.println(String.valueOf(flag) + " " + i);
        }

    }

    /**
     * 打印奇数
     */
    public static class PrintQi implements Runnable{

        Num num;

        public PrintQi(Num num) {
            this.num = num;
        }

        @Override
        public void run() {
            while (num.i < 100) {
                synchronized (num) {
                    if (num.flag) {
                        try {
                            num.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    num.printNum();
                    num.i++;
                    num.flag = true;
                    num.notify();
                }
            }
        }
    }

    /**
     * 打印偶数
     */
    public static class PrintOu implements Runnable{

        Num num;

        public PrintOu(Num num) {
            this.num = num;
        }

        @Override
        public void run() {
            while (num.i < 100) {
                synchronized (num) {
                    while (!num.flag) {
                        try {
                            // 线程释放掉当前锁，进入等待
                            num.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    num.printNum();
                    num.i++;
                    num.flag = false;
                    // 唤醒等待线程
                    num.notify();
                }
            }
        }
    }

    public static void main(String[] args) {
        Num num = new Num(); //声明一个资源

        PrintQi pQi = new PrintQi(num);
        PrintOu pOu = new PrintOu(num);

        Thread aThread = new Thread(pQi);
        Thread bThread = new Thread(pOu);

        aThread.start();
        bThread.start();


    }
}
