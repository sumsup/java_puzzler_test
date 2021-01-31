package com.javapuzzler.thread.timer;

import javax.swing.*;

public class TimerRunnable implements Runnable{
    JLabel timerLabel;

    public TimerRunnable(JLabel timerLabel) { // constructor.
        this.timerLabel = timerLabel;
    }

    @Override
    public void run() { // 쓰레드 인터페이스 상속받아서 run을 오버라이딩.
        int n = 0;
        while(true) { // 무한루프.
            timerLabel.setText(Integer.toString(n)); // 화면에 숫자를 표시.
            n++; // 숫자를 증가시킴.
            try {
                Thread.sleep(1000); // 1초동안 thread를 중지시킴. 1초간격으로 화면에 표시.
            }
            catch(InterruptedException e) {
                return;
            }
            if (n > 5) { // 5초가 넘어가면.
                return; // 스레드 종료.
            }
        }
    }
}

