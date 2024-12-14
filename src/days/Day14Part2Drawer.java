package days;

import tests.InputFileReader;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Day14Part2Drawer extends JPanel{

    static Day14.Bathroom bathroom;

    protected void paintComponent(Graphics grf){
        super.paintComponent(grf);
        Graphics2D graph = (Graphics2D)grf;
        graph.setPaint(Color.GREEN);

        //draw bathroom
        for(int i=0; i<bathroom.getRobots().size(); i++){
            double x1 = bathroom.getRobots().get(i).xPosition*10;
            double y1 = bathroom.getRobots().get(i).yPosition*10;
            graph.fill(new Ellipse2D.Double(x1-5, y1-5, 10, 10));
        }
    }

    public static void main(String args[]) throws InterruptedException {
        String input = InputFileReader.getInput("Day14");
        ArrayList<Day14.Robot> robots = new Day14().getRobotsFromInput(input);

        bathroom = new Day14.Bathroom(101, 103, robots);
        bathroom.passTimeSeconds(8050);

        JFrame frame = new JFrame();
        frame.add(new Day14Part2Drawer());

        // set size, layout and location for frame.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(bathroom.getWidth()*10, bathroom.getHeight()*10);
        frame.setVisible(true);

//        int seconds = 8050;
//        while(true){
//            System.out.println(seconds);
//            TimeUnit.MILLISECONDS.sleep(500);
//            bathroom.passTimeSeconds(103);
//            seconds += 103;
//            frame.repaint();
//        }
    }


}
