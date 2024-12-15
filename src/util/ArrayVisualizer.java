package util;

import javax.swing.*;
import java.awt.*;

public class ArrayVisualizer extends JPanel {

    private final JFrame frame;
    private final int width;
    private final int height;
    private final char[][] grid;

    public ArrayVisualizer(char[][] grid){
        this.frame = getFrame();
        this.height = grid.length;
        this.width = grid[0].length;
        setBackground(Color.black);
        frame.setSize(1920, 1080);

        this.grid = grid;
    }

    public void refresh() throws InterruptedException {
            frame.repaint();
    }

    protected void paintComponent(Graphics grf) {
        super.paintComponent(grf);
        Graphics2D graph = (Graphics2D) grf;
        graph.setPaint(Color.white);
        graph.scale(2,2);
        for (int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++){
                if(grid[i][j] == '@'){
                    graph.setPaint(Color.red);
                } else if(grid[i][j] == 'O' || grid[i][j] == '[' || grid[i][j] == ']'){
                    graph.setPaint(Color.green);
                } else {
                    graph.setPaint(Color.white);
                }

                graph.drawString(String.valueOf(grid[i][j]), (j+1)*10, (i+1)*10);
            }
        }
    }

    private JFrame getFrame() {
        JFrame frame = new JFrame();
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int scale = 80;
        frame.setSize(width * scale, height * scale);
        frame.setVisible(true);
        return frame;
    }

}
