package days;

import java.util.ArrayList;

public class Day13 {

    private class ClawMachine {
        private final long prizeX;
        private final long prizeY;
        private final Button buttonA;
        private final Button buttonB;

        public ClawMachine(long prizeX, long prizeY, Button a, Button b) {
            this.prizeX = prizeX;
            this.prizeY = prizeY;
            this.buttonA = a;
            this.buttonB = b;
        }

        public boolean bruteForcePrice(){
            for(int a = 0; a<=100; a++){
                for(int b = 0; b<=100; b++){
                    if(!clawAbovePrize()){
                        buttonA.setPresses(a);
                        buttonB.setPresses(b);
                    }
                    else{
                        break;
                    }
                }
            }

            return clawAbovePrize();
        }

        public boolean calculateSolution(){
            solveWithSimultaneousEquations();

            return clawAbovePrize();
        }

        /**
         * The two equations we try to solve:
         * buttonA.xPerPress * (buttonA.presses) + buttonB.xPerPress * (buttonB.presses) = prizeX;
         * buttonA.yPerPress * (buttonA.presses) + buttonB.yPerPress * (buttonB.presses) = prizeY;
         * ax + by = e
         * cx + dy = f
         */
        private void solveWithSimultaneousEquations() {
            double a = buttonA.xPerPress;
            double b = buttonB.xPerPress;
            double c = buttonA.yPerPress;
            double d = buttonB.yPerPress;
            double e = prizeX;
            double f = prizeY;

            double det = ((a) * (d) - (b) * (c));
            double x = ((d) * (e) - (b) * (f)) / det;
            double y = ((a) * (f) - (c) * (e)) / det;

            if((x % 1 == 0) && (y % 1 == 0)){
                buttonA.setPresses((long)x);
                buttonB.setPresses((long)y);
            }
        }

        private boolean clawAbovePrize(){
            return buttonA.getTotalX()+buttonB.getTotalX() == prizeX && buttonA.getTotalY()+buttonB.getTotalY() == prizeY;
        }

        public Long getUsedTokens(){
            return buttonA.getTotalTokens() + buttonB.getTotalTokens();
        }
    }

    private class Button {
        private final long tokenPerPress;
        private final long xPerPress;
        private final long yPerPress;

        private long presses;

        public Button (long x, long y, long tokenPerPress) {
            this.xPerPress = x;
            this.yPerPress = y;
            this.tokenPerPress = tokenPerPress;
            this.presses = 0;
        }

        public void setPresses(long presses){
            this.presses = presses;
        }

        public long getTotalX(){
            return xPerPress * presses;
        }

        public long getTotalY(){
            return yPerPress * presses;
        }

        public long getTotalTokens(){
            return tokenPerPress * presses;
        }
    }

    public long part1(String input){
        ArrayList<ClawMachine> claws = getMachinesFromInput(input, false);

        long tokens = 0L;

        for(ClawMachine claw : claws){
            if(claw.bruteForcePrice()){
                tokens += claw.getUsedTokens();
            }
        }

        return tokens;
    }

    public long part2(String input){
        ArrayList<ClawMachine> claws = getMachinesFromInput(input, true);

        long tokens = 0L;

        for(ClawMachine claw : claws){
            if(claw.calculateSolution()){
                tokens += claw.getUsedTokens();
            }
        }

        return tokens;
    }

    public ArrayList<ClawMachine> getMachinesFromInput(String input, boolean prizeLocationPart2){
        ArrayList<ClawMachine> machines = new ArrayList<>();

        long aXincrease = 0;
        long aYincrease = 0;
        long bXincrease = 0;
        long bYincrease = 0;
        long yPrize = 0;
        long xPrize = 0;

        for(String line : input.lines().toList()){
            if(line.startsWith("Button A:")){
                String[] numbers = line.split("\\D+");
                aXincrease = Long.parseLong(numbers[1]);
                aYincrease = Long.parseLong(numbers[2]);
            }
            else if(line.startsWith("Button B:")){
                String[] numbers = line.split("\\D+");
                bXincrease = Long.parseLong(numbers[1]);
                bYincrease = Long.parseLong(numbers[2]);
            }
            else if(line.startsWith("Prize:")){
                String[] numbers = line.split("\\D+");
                xPrize = Long.parseLong(numbers[1]);
                yPrize = Long.parseLong(numbers[2]);

                if(prizeLocationPart2){
                    xPrize += 10000000000000L;
                    yPrize += 10000000000000L;
                }

                Button a = new Button(aXincrease, aYincrease, 3);
                Button b = new Button(bXincrease, bYincrease, 1);
                ClawMachine clawmachine = new ClawMachine(xPrize, yPrize, a, b);
                machines.add(clawmachine);
            }
        }
        return machines;
    }
}
