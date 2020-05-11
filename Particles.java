//Particles: Write a Swing program has small randomly colored particles (filled circles) appear from the center of the window.
//        The particles should start with a random velocity and proceed to move, bouncing off the walls but not each other, for
//        a fixed amount of time. When a ball's time limit is up it should be removed from the window. All of this simulation's
//        parameters (e.g. particle size, max speed, time to live, appearance rate, and so on) should be controlled by constants
//        that are chosen to be ascetically pleasing.
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Particles extends JFrame {


    private static final Random rand = new Random();
    private final DrawingPanel drawingPanel = new DrawingPanel();

    private class Ball {
        private int lifeLimit = 100;
        private static final int ballSize = 5;

        private static final int ballSpeed = 10;

        private double x;
        private double y;
        private double xVelocity;
        private double yVelocity;
        private Color color;

        public Ball(double x, double y) {
            this.x = x;
            this.y = y;

            double angle = rand.nextDouble() * 2 * Math.PI;
            xVelocity = Math.cos(angle) * ballSpeed;
            yVelocity = Math.sin(angle) * ballSpeed;
            color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        }

        public void move() {
            x += xVelocity;
            y += yVelocity;

            if (x - ballSize / 2.0 <= 0) {
                xVelocity = -1 * xVelocity;

            } else if (x + ballSize / 2.0 >= drawingPanel.getWidth()) {
                xVelocity = -1 * xVelocity;
            }
            if (y - ballSize / 2.0 <= 0) {
                yVelocity = -1 * yVelocity;


            } else if (y + ballSize / 2.0 >= drawingPanel.getHeight()) {
                yVelocity = -1 * yVelocity;

            }
            lifeLimit--;
        }
        public void draw(Graphics graphic) {
            Color oldColor = graphic.getColor();
            graphic.setColor(color);
            graphic.fillOval((int) (x - ballSize / 2), (int) (y - ballSize / 2), ballSize, ballSize);
            graphic.setColor(oldColor);
        }
    }
    private class DrawingPanel extends JPanel {

        private final ArrayList<Ball> balls = new ArrayList<>();

        public DrawingPanel() {
            setBackground(Color.gray);
            Timer animation = new Timer(40, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    balls.add(new Ball(getWidth() / 2.0, getHeight() / 2.0));
                    List<Ball> deadList = new LinkedList<Ball>();
                    for (Ball ball : balls) {
                        ball.move();
                        if (ball.lifeLimit <= 0) {
                            deadList.add(ball);
                            System.out.println();
                        }
                    }
                    balls.removeAll(deadList);
                    repaint();
                }
            });
            animation.start();
        }
        @Override
        protected void paintComponent(Graphics graphic) {
            super.paintComponent(graphic);
            for (Ball ball : balls) {
                ball.draw(graphic);
            }
        }
    }
    public Particles() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        add(drawingPanel);
    }
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Particles().setVisible(true);
            }
        });
    }
}