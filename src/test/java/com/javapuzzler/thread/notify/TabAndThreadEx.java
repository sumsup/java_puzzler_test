package com.javapuzzler.thread.notify;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class MyLabel extends JLabel {
    int barSize = 0;
    int maxBarSize;

    MyLabel(int maxBarSize) {
        this.maxBarSize = maxBarSize;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.MAGENTA);
        int width = (int) (((double) getWidth()) / maxBarSize * barSize);
        if (width == 0) return; // 크기가 0 이기 때문에 bar를 그릴 필요 없음.
        g.fillRect(0, 0, width, this.getHeight()); // width만큼 MAGENTA 색으로 칠함.
    }

    synchronized void fill() {
        if (barSize >= maxBarSize) {
            try {
                wait(); // 바의 크기가 최대면, ConsumerThread에 의해 바의 크기가 줄어들 때 까지 대기.
            }
            catch (InterruptedException e) {
                return;
            }
        }
        if (barSize < maxBarSize) {
            barSize++;
        }
        System.out.println("barSize : " + barSize);
        repaint();
        notify(); // 기다리는 ConsumerThread 스레드 깨우기.
    }

    synchronized void consume() {
        if (barSize == 0) {
            try {
                wait(); // wait() 내부로 들어가면 throws InterruptedException 이 있다. 호출한 곳으로 처리를 던진다는 의미.
            }
            catch(InterruptedException e) {
                return;
            }
        }
        barSize--;
        repaint();
        notify(); // 기다리는 키 이벤트 리스너(스레드) 꺠우기.
    }
}

class ConsumerThreaed extends Thread {
    MyLabel bar;

    ConsumerThreaed(MyLabel bar) {
        this.bar = bar;
    }

    public void run() {
        while(true) {
            try {
                sleep(100);
                bar.consume(); // 0.1초마다 barSize를 1씩 줄인다. 0이면 wait(); 0이 아니면 notify();
            }
            catch (InterruptedException e) {
                return;
            }
        }
    }
}

// public class는 클래스파일당 하나만.
public class TabAndThreadEx extends JFrame {
    MyLabel bar = new MyLabel(100);

    TabAndThreadEx(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(null);
        bar.setBackground(Color.ORANGE);
        bar.setOpaque(true);
        bar.setLocation(100, 100);
        bar.setSize(300,50);
        c.add(bar);

        c.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                bar.fill();
            }
        });
        setSize(500, 300);
        setVisible(true);

        c.requestFocus();
        ConsumerThreaed th = new ConsumerThreaed(bar);
        th.start(); // start 하면 ConsumnerThread내의 오버라이딩된 run()을 실행 시킨다.
    }

    public static void main(String[] args) {
        new TabAndThreadEx("아무키나 빨리 눌러 바 채우기");
    }
}
