package com.javapuzzler.thread.vibe;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class VibratingFrame extends JFrame implements Runnable {
    Thread th;

    public VibratingFrame() {
        setTitle("진동하는 프레임 만들기");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200, 200);
        setLocation(300, 300);
        setVisible(true);

        getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!th.isAlive()) return; // 스레드가 이미 종료되 있으면 return;
                th.interrupt(); // 진동중인 스레드 강제 종료.
            }
        });

        th = new Thread(this); // this는 현재 클래스 객체를 의미. runnable을 구현했기 때문에 this를 전달해도 가능.
        th.start();

    }

    @Override
    public void run() { // 프레임에 진동을 일으키기 위해 20ms마다 프레임의 위치를 랜덤하게 이동.
        Random r = new Random(); // 진동할 위치를 랜덤하게 발생시킬 랜덤 객체 생성.
        while (true) {
            try {
                Thread.sleep(20);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }

            int x = getX() + r.nextInt()%5; // -4에서 4까지의 임의의 정수 리턴. getX()는 프레임의 현재 위치 x값.
            int y = getY() + r.nextInt()%5; // 새 위치 y.
            setLocation(x, y);
        }
    }

    public static void main(String[] args) {
        new VibratingFrame();
    }


}
