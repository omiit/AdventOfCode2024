package days;

import tests.InputFileReader;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Day14 {

    BathroomPainter bathroomPainter;

    public long part1(String input, int wide, int tall) {

        ArrayList<Robot> robots = getRobotsFromInput(input);
        Bathroom bathroom = new Bathroom(wide, tall, robots);

        bathroom.passTimeSeconds(100);

        return bathroom.calculateSafetyFactor();
    }

    /**
     * Visualizes part2
     */
    public static void main(String args[]) throws InterruptedException {

        Day14 day14 = new Day14();
        day14.part2();
    }

    private void part2() throws InterruptedException {
        String input = InputFileReader.getInput("Day14");
        ArrayList<Day14.Robot> robots = new Day14().getRobotsFromInput(input);

        Bathroom bathroom = new Bathroom(101, 103, robots);
        bathroom.passTimeSeconds(8050);

        BathroomPainter bathroomPainter = new BathroomPainter(bathroom, bathroom.length);
        bathroomPainter.runAnimationAndOutputTime();
    }

    private static class Bathroom {
        private final int width;
        private final int length;
        private final ArrayList<Robot> robots;
        private int timeInSeconds;

        public Bathroom(int width, int length, ArrayList<Robot> robots) {
            this.width = width;
            this.length = length;
            this.robots = robots;
            this.timeInSeconds = 0;
        }

        public void passTimeSeconds(int seconds) {
            for (Robot robot : robots) {
                robot.move(seconds, width, length);
            }
            this.timeInSeconds += seconds;
        }

        public Long calculateSafetyFactor() {
            return robotsInLeftTopQuadrant() * robotsInLeftBottomQuadrant() * robotsInRightTopQuadrant() * robotsInRightBottomQuadrant();
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return length;
        }

        public ArrayList<Robot> getRobots() {
            return robots;
        }

        public int getTimeInSeconds() {
            return this.timeInSeconds;
        }

        private Long robotsInLeftTopQuadrant() {
            return robotsInArea(0, getMiddleXIndex() - 1, 0, getMiddleYIndex() - 1);
        }

        private Long robotsInLeftBottomQuadrant() {
            return robotsInArea(0, getMiddleXIndex() - 1, getMiddleYIndex() + 1, getLastYIndex());
        }

        private Long robotsInRightTopQuadrant() {
            return robotsInArea(getMiddleXIndex() + 1, getLastXIndex(), 0, getMiddleYIndex() - 1);
        }

        private Long robotsInRightBottomQuadrant() {
            return robotsInArea(getMiddleXIndex() + 1, getLastXIndex(), getMiddleYIndex() + 1, getLastYIndex());
        }

        private Long robotsInArea(int x1, int x2, int y1, int y2) {
            Long robotCount = 0L;

            for (Robot robot : robots) {
                if (robot.getxPosition() >= x1 && robot.getxPosition() <= x2) {
                    if (robot.getyPosition() >= y1 && robot.getyPosition() <= y2) {
                        robotCount++;
                    }
                }
            }

            return robotCount;
        }

        private int getMiddleXIndex() {
            return (width - 1) / 2;
        }

        private int getMiddleYIndex() {
            return (length - 1) / 2;
        }

        private int getLastXIndex() {
            return width - 1;
        }

        private int getLastYIndex() {
            return length - 1;
        }
    }

    private class Robot {

        private long xPosition;
        private long yPosition;
        private final long xVelocity;
        private final long yVelocity;

        public Robot(long xPosition, long yPosition, long xVelocity, long yVelocity) {
            this.xPosition = xPosition;
            this.yPosition = yPosition;
            this.xVelocity = xVelocity;
            this.yVelocity = yVelocity;
        }

        public void move(int seconds, int width, int length) {

            long moveX = xVelocity * seconds;
            long moveY = yVelocity * seconds;

            this.xPosition += moveX;
            this.xPosition %= width;
            this.yPosition += moveY;
            this.yPosition %= length;

            //Since % gives the remainder, compensate negative numbers to modulo
            this.xPosition += width;
            this.xPosition %= width;
            this.yPosition += length;
            this.yPosition %= length;
        }

        public long getxPosition() {
            return xPosition;
        }

        public long getyPosition() {
            return yPosition;
        }
    }

    private ArrayList<Robot> getRobotsFromInput(String input) {
        ArrayList<Robot> robots = new ArrayList<>();

        for (String line : input.lines().toList()) {
            int px = Integer.parseInt(line.substring(2, line.indexOf(",")));
            int py = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(" ")));
            int vx = Integer.parseInt(line.substring(line.indexOf("v") + 2, line.indexOf(",", line.indexOf("v"))));
            int vy = Integer.parseInt(line.substring(line.indexOf(",", line.indexOf("v")) + 1));

            Robot robot = new Robot(px, py, vx, vy);
            robots.add(robot);
        }
        return robots;
    }

    private class BathroomPainter extends JPanel {

        static Bathroom bathroom;
        private JFrame frame;
        private int secondsPerStep;
        private int scale = 10;

        public BathroomPainter(Bathroom bathroom, int secondsPerStep) {
            BathroomPainter.bathroom = bathroom;
            this.frame = getFrame();
            this.secondsPerStep = secondsPerStep;
        }

        public void runAnimationAndOutputTime() throws InterruptedException {
            while (true) {
                System.out.println(bathroom.getTimeInSeconds());
                TimeUnit.MILLISECONDS.sleep(1000);
                bathroom.passTimeSeconds(secondsPerStep);
                frame.repaint();
            }
        }

        protected void paintComponent(Graphics grf) {
            super.paintComponent(grf);
            Graphics2D graph = (Graphics2D) grf;
            graph.setPaint(Color.GREEN);

            for (int i = 0; i < bathroom.getRobots().size(); i++) {
                double x1 = bathroom.getRobots().get(i).xPosition * scale;
                double y1 = bathroom.getRobots().get(i).yPosition * scale;
                graph.fill(new Ellipse2D.Double(x1 - (double) scale / 2, y1 - (double) scale / 2, scale, scale));
            }
        }

        private JFrame getFrame() {
            JFrame frame = new JFrame();
            frame.add(this);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(bathroom.getWidth() * scale, bathroom.getHeight() * scale);
            frame.setVisible(true);
            return frame;
        }
    }
}
