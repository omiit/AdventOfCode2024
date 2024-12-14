package days;

import tests.InputFileReader;

import javax.swing.*;
import java.util.ArrayList;

public class Day14 {

    public long part1(String input, int wide, int tall){

        ArrayList<Robot> robots = getRobotsFromInput(input);
        Bathroom bathroom = new Bathroom(wide, tall, robots);

        bathroom.passTimeSeconds(100);

        return bathroom.calculateSafetyFactor();
    }

    public static class Bathroom {
        private final int width;
        private final int length;
        private ArrayList<Robot> robots;

        public Bathroom(int width, int length, ArrayList<Robot> robots) {
            this.width = width;
            this.length = length;
            this.robots = robots;
        }

        public void passTimeSeconds(int seconds) {
            for(Robot robot : robots){
                robot.move(seconds, width, length);
            }
        }

        public Long calculateSafetyFactor() {
            return robotsInLeftTopQuadrant() * robotsInLeftBottomQuadrant() * robotsInRightTopQuadrant() * robotsInRightBottomQuadrant();
        }

        private Long robotsInLeftTopQuadrant(){
            return robotsInArea(0, getMiddleXIndex()-1, 0, getMiddleYIndex()-1);
        }

        private Long robotsInLeftBottomQuadrant(){
            return robotsInArea(0, getMiddleXIndex()-1, getMiddleYIndex()+1, getLastYIndex());
        }

        private Long robotsInRightTopQuadrant(){
            return robotsInArea(getMiddleXIndex()+1, getLastXIndex(), 0, getMiddleYIndex()-1);

        }

        private Long robotsInRightBottomQuadrant(){
            return robotsInArea(getMiddleXIndex()+1, getLastXIndex(), getMiddleYIndex()+1, getLastYIndex());
        }

        private Long robotsInArea(int x1, int x2, int y1, int y2){
            Long robotCount = 0L;

            for(Robot robot : robots){
                if(robot.getxPosition() >= x1 && robot.getxPosition() <= x2) {
                    if(robot.getyPosition() >= y1 && robot.getyPosition() <= y2) {
                        robotCount++;
                    }
                }
            }

            return robotCount;
        }

        private int getMiddleXIndex(){
            return (width-1)/2;
        }

        private int getMiddleYIndex(){
            return (length-1)/2;
        }

        private int getLastXIndex(){
            return width-1;
        }

        private int getLastYIndex(){
            return length-1;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return length;
        }

        public ArrayList<Robot> getRobots(){
            return robots;
        }
    }

    public class Robot {

        long xPosition;
        long yPosition;
        long xVelocity;
        long yVelocity;

        public Robot(long xPosition, long yPosition, long xVelocity, long yVelocity){
            this.xPosition = xPosition;
            this.yPosition = yPosition;
            this.xVelocity = xVelocity;
            this.yVelocity = yVelocity;
        }

        public void move(int seconds, int width, int length){

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

    public ArrayList<Robot> getRobotsFromInput(String input) {
        ArrayList<Robot> robots = new ArrayList<>();

        for (String line : input.lines().toList()) {
            int px = Integer.parseInt(line.substring(2, line.indexOf(",")));
            int py = Integer.parseInt(line.substring(line.indexOf(",")+1, line.indexOf(" ")));
            int vx = Integer.parseInt(line.substring(line.indexOf("v")+2, line.indexOf(",", line.indexOf("v"))));
            int vy = Integer.parseInt(line.substring(line.indexOf(",", line.indexOf("v"))+1));

            Robot robot = new Robot(px, py, vx, vy);
            robots.add(robot);
        }
        return robots;
    }
}
