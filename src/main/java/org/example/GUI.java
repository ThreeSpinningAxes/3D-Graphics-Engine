package org.example;

import Objects.Triangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

public class GUI extends Canvas implements Runnable, KeyListener {

    private int windowPixelWidth;
    private int windowPixelHeight;

    private String windowTitle;

    JFrame frame;

    private Screen screen;

    private Thread backendThread;

    private boolean running = false;

    BufferedImage bufferedImage;


    ArrayList<Float> input = new ArrayList<>();

    //used to perform calculations on the pixels for the current frame of the screen
    private int[] pixelsOfFrameToBeDisplayed;

    public GUI(int windowPixelWidth, int windowPixelHeight) {
        this.windowPixelWidth = windowPixelWidth;
        this.windowPixelHeight = windowPixelHeight;
        this.screen = new Screen(this.windowPixelWidth, this.windowPixelHeight);
        setCanvasSize();
        initJFrame();
        this.bufferedImage = new BufferedImage(windowPixelWidth, windowPixelHeight, BufferedImage.TYPE_INT_RGB);
        this.pixelsOfFrameToBeDisplayed = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();

        input.add(0.0f);
        input.add(0.0f);
        input.add(0.0f);
    }

    private void initJFrame() {
        this.frame = new JFrame();
        this.frame.setVisible(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.add(this);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.addKeyListener(this);
    }
    private void setCanvasSize() {
        this.setSize(this.windowPixelWidth, this.windowPixelHeight);
        this.setPreferredSize(new Dimension(windowPixelWidth, windowPixelHeight));
    }

    public synchronized void start() {
        this.backendThread = new Thread(this);
        this.backendThread.start();
        this.running = true;
    }

    public synchronized void stop()  {
        try {
            if (this.backendThread.isAlive()) {
                this.backendThread.join();
                this.running = false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (running) {
            update();
           // long time = System.currentTimeMillis();
            renderFrame();
            //System.out.println(1000 / (System.currentTimeMillis() - time +0.00000001));
        }
    }
    public void renderFrame() {
        long t = System.currentTimeMillis();
        //bstrat is a way of managing data across two systems. 1 buffer shows the frame, 1 buffer loads the next frame,
        // 1 buffer is used to construct a frame.
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            //create 3 buffers for triple buffering
            this.createBufferStrategy(3);
            return;
        }

        //we set the pixels of this frame to the screens frame
        for (int i = 0; i < this.pixelsOfFrameToBeDisplayed.length; i++) {
            this.pixelsOfFrameToBeDisplayed[i] = this.screen.getPixel(i);
        }
        Graphics graphics = bufferStrategy.getDrawGraphics();
        //this.screen.clearScreen();
        this.screen.renderFrame(input);

        graphics.setColor(Color.black);
        graphics.fillRect(0,0, windowPixelWidth, windowPixelHeight);
        graphics.drawImage(this.bufferedImage, 0, 0, getWidth(), getHeight(), null);

        t = System.currentTimeMillis() - t;
        graphics.setFont(new Font("Seqoe UI", Font.PLAIN, 16));
        graphics.setColor(Color.WHITE);
        graphics.drawString("FPS: " + (int) (1000 / (t+0.01)), 10, 20);
        graphics.dispose();
        bufferStrategy.show();
    }

    public void update() {
    }

    public static void main(String[] args) {
        GUI gui = new GUI(600, 400);
        gui.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            input.set(0, input.get(0) + 0.05f);
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            input.set(0, input.get(0) - 0.05f);
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            input.set(1, input.get(1) - 0.05f);
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            input.set(1, input.get(1) + 0.05f);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

