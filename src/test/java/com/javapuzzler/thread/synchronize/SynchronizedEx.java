package com.javapuzzler.thread.synchronize;

public class SynchronizedEx {
    public static void main(String[] args) {
        SharedPrinter p = new SharedPrinter();
        String[] engText = {
                        "Wise men say,",
                        "only fools rush in",
                        "But I can't help,",
                                "falling in love with you"

        };

        String[] korText = {
                "동해물과 백두산이 마르고 닳도록,",
                "하느님이 보우하사 우리 나라만세.",
                "무궁화 삼천리 화려강산",
                "대한 사람 대한으로 길이 보전하세요"
        };

        Thread th1 = new WorkerThread(p, engText);
        Thread th2 = new WorkerThread(p, korText);

        th1.start();
        th2.start();
    }
}

class SharedPrinter {
    synchronized void print(String text) { // synchronized를 생략하면 쓰레드끼리 섞임.

        Thread.yield(); // 쓰레드 끼리 섞임. 더 쉽게 충돌나게 함.
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
        }
        System.out.println();
    }

}

class WorkerThread extends Thread { // 쓰레드 클래스.
    SharedPrinter p;
    String[] text;

    WorkerThread(SharedPrinter p, String[] text) {
        this.p = p;
        this.text = text;
    }

    @Override
    public void run() {
        for (int i = 0; i < text.length; i++) {
            p.print(text[i]);
        }
    }
}
