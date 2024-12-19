package days;

import java.util.StringJoiner;

public class Day17 {

    public static void main(String[] args) {

        long first = 6; // only option
        long second = 8 * first + 1; // or 5
        long third = 8 * second + 1; // or 5
        long fourth = 8 * third + 1; // or 7
        long five = 8 * fourth + 2; //
        long sixth = 8 * five + 7; // 0,2 did not work
        long seven = 8 * sixth + 6; //
        long eight = 8 * seven + 4; //
        long nine = 8 * eight + 7; //
        long ten = 8 * nine + 4; //
        long eleven = 8 * ten + 2; //
        long twelve = 8 * eleven + 5; //
        long thirteen = 8 * twelve + 7; // 0,4 did not work
        long fourteen = 8 * thirteen + 0; // or 1 or 2
        long fifteen  = 8 * fourteen + 13; // or 5, 13?!
        long sixteen = 8 * fifteen + 5;

        long registerA = sixteen;

        System.out.println(registerA);

        for(long i = 0; i<16; i++){
            System.out.println(i + " " + new Day17().part1(registerA+i, new int[]{2,4,1,3,7,5,0,3,1,5,4,1,5,5,3,0}));
        }
    }

    public String part1(long registerA, int[] instructions){
        ChronospatialComputer chronospatialComputer = new ChronospatialComputer(registerA, 0, 0, instructions);
        chronospatialComputer.runInstructions();

        return chronospatialComputer.getOutput();
    }

    public class ChronospatialComputer {

        Long registerA;
        Long registerB;
        Long registerC;
        int[] instructions;
        StringJoiner joiner = new StringJoiner(",");
        int pointer = 0;

        public ChronospatialComputer(long a, long b, long c, int[] instructions) {
            this.registerA = a;
            this.registerB = b;
            this.registerC = c;
            this.instructions = instructions;
        }

        public void runInstructions(){
            //System.out.println("Starting program");

            while(pointer < instructions.length){
                //System.out.println("Pointing to: " + pointer);
                runInstruction(instructions[pointer], instructions[pointer + 1]);
            }
            //System.out.println("Terminating program, the output was: " + joiner.toString());
        }

        public String getOutput(){
            return joiner.toString();
        }

        private void runInstruction(int opcode, int operand){
            //System.out.println("Run instruction opcode: " + opcode + " operand: " + operand + " registerA: " + registerA + " registerB: " + registerB + " registerC: " + registerC);
            if(opcode == 0){
                advOperation(operand);
                movePointer();
            } else if(opcode == 1) {
                bxlOperation(operand);
                movePointer();
            } else if(opcode == 2) {
                bstOperation(operand);
                movePointer();
            } else if(opcode == 3) {
                jnzOperationAndMovePointer(operand);
            } else if(opcode == 4) {
                bxcOperation(operand);
                movePointer();
            } else if(opcode == 5) {
                outOperation(operand);
                movePointer();
            } else if(opcode == 6) {
                bdvOperation(operand);
                movePointer();
            } else if(opcode == 7) {
                cdvOperation(operand);
                movePointer();
            }
        }

        private void advOperation(int operand){
            this.registerA = dvOperation(operand);
        }

        private void bdvOperation(int operand){
            this.registerB = dvOperation(operand);
        }

        private void cdvOperation(int operand){
            this.registerC = dvOperation(operand);
        }

        private long dvOperation(int operand){
            long comboOperand = getComboOperand(operand);
            long divider = (int) Math.pow(2, comboOperand);
            return registerA / divider;
        }

        private void bxlOperation(int operand){
            this.registerB = registerB ^ operand;
        }

        private void bstOperation(int operand){
            long comboOperand = getComboOperand(operand);
            this.registerB = comboOperand % 8;
        }

        private void jnzOperationAndMovePointer(int operand){
            if(this.registerA != 0) {
                this.pointer = operand;
            }
            else {
                movePointer();
            }
        }

        private void bxcOperation(int operand){
            this.registerB = this.registerB ^ registerC;
        }

        private void outOperation(int operand){
            long comboOperand = getComboOperand(operand);
            int output = (int)(comboOperand % 8);
            addOutput(output);
        }

        private void movePointer(){
            this.pointer += 2;
        }

        private long getComboOperand(int operand){
            if(operand == 4){
                return registerA;
            }
            if(operand == 5){
                return registerB;
            }
            if(operand == 6){
                return registerC;
            }
            if(operand > 6 || operand < 0){
                throw new RuntimeException("Invalid operand: " + operand);
            }
            return operand;
        }

        private void addOutput(int output){
            joiner.add(String.valueOf(output));
        }

    }
}
