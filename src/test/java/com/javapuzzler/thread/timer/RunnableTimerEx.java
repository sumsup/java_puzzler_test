package com.javapuzzler.thread.timer;

import javax.swing.*;
import java.awt.*;

public class RunnableTimerEx extends JFrame {
    public RunnableTimerEx() { // constructor.
        setTitle("Runnable을 구현한 타이머 쓰레드 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        // 타이머 값을 출력할 레이블 생성.
        JLabel timerLabel = new JLabel();
        timerLabel.setFont(new Font("Gothic", Font.ITALIC, 80));
        c.add(timerLabel);

        TimerRunnable runnable = new TimerRunnable(timerLabel); // 구현한 Runnable 객체생성.
        Thread th = new Thread(runnable); // 쓰레드 객체에 runnable을 인수로 전달.

        setSize(250, 150);
        setVisible(true);

        th.start(); // 쓰레드 시작.
    }

    public static void main(String[] args) {
        new RunnableTimerEx(); // 앞에 new는 뭐지.

        long id = Thread.currentThread().getId();
        String name = Thread.currentThread().getName();
        int priority = Thread.currentThread().getPriority();
        Thread.State s = Thread.currentThread().getState();

        System.out.println("현재 스레드 이름 = " + name);
        System.out.println("현재 스레드 ID = " + id);
        System.out.println("현재 스레드 우선순위 값 = " + priority);
        System.out.println("현재 스레드 상태 = " + s);
    }
}