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
            solvePressesForEquations();

            return clawAbovePrize();
        }

        /**
         * The two equations we try to solve:
         * buttonA.xPerPress * (buttonA.presses) + buttonB.xPerPress * (buttonB.presses) = prizeX;
         * buttonA.yPerPress * (buttonA.presses) + buttonB.yPerPress * (buttonB.presses) = prizeY;
         * ax + by = e
         * cx + dy = f
         */
        private void solvePressesForEquations() {
            double determinant = ((buttonA.xPerPress) * (buttonB.yPerPress) - (buttonB.xPerPress) * (buttonA.yPerPress));
            double pressesA = ((buttonB.yPerPress) * (prizeX) - (buttonB.xPerPress) * (prizeY)) / determinant;
            double pressesB = ((buttonA.xPerPress) * (prizeY) - (buttonA.yPerPress) * (prizeX)) / determinant;

            //If the presses are whole numbers, the prize can be reached
            if((pressesA % 1 == 0) && (pressesB % 1 == 0)){
                buttonA.setPresses((long)pressesA);
                buttonB.setPresses((long)pressesB);
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

    private ArrayList<ClawMachine> getMachinesFromInput(String input, boolean prizeLocationPart2) {
        ArrayList<ClawMachine> machines = new ArrayList<>();

        Button buttonA = null;
        Button buttonB = null;

        for (String line : input.lines().toList()) {
            if (line.startsWith("Button A:")) {
                String[] numbers = line.split("\\D+");
                long xIncrease = Long.parseLong(numbers[1]);
                long yIncrease = Long.parseLong(numbers[2]);
                buttonA = new Button(xIncrease, yIncrease, 3);
            } else if (line.startsWith("Button B:")) {
                String[] numbers = line.split("\\D+");
                long xIncrease = Long.parseLong(numbers[1]);
                long yIncrease = Long.parseLong(numbers[2]);
                buttonB = new Button(xIncrease, yIncrease, 1);
            } else if (line.startsWith("Prize:")) {
                String[] numbers = line.split("\\D+");
                long prizeXLocation = Long.parseLong(numbers[1]);
                long prizeYLocation = Long.parseLong(numbers[2]);

                if (prizeLocationPart2) {
                    prizeXLocation += 10000000000000L;
                    prizeYLocation += 10000000000000L;
                }

                ClawMachine clawmachine = new ClawMachine(prizeXLocation, prizeYLocation, buttonA, buttonB);
                machines.add(clawmachine);
            }
        }
        return machines;
    }
}
