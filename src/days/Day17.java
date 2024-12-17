package days;

import java.util.Arrays;
import java.util.StringJoiner;

public class Day17 {

    public String part1(int registerA, int[] instructions){
        ChronospatialComputer chronospatialComputer = new ChronospatialComputer(registerA, 0, 0, instructions);
        chronospatialComputer.runInstructions();

        return chronospatialComputer.getOutput();
    }

    public class ChronospatialComputer {

        Integer registerA;
        Integer registerB;
        Integer registerC;
        int[] instructions;
        StringJoiner joiner = new StringJoiner(",");
        int pointer = 0;

        public ChronospatialComputer(int a, int b, int c, int[] instructions) {
            this.registerA = a;
            this.registerB = b;
            this.registerC = c;
            this.instructions = instructions;
        }

        public void runInstructions(){
            System.out.println("Starting program");

            while(pointer < instructions.length){
                System.out.println("Pointing to: " + pointer);
                runInstruction(instructions[pointer], instructions[pointer + 1]);
            }
            System.out.println("Terminating program, the output was: " + joiner.toString());
        }

        public String getOutput(){
            return joiner.toString();
        }

        private void runInstruction(int opcode, int operand){
            System.out.println("Run instruction opcode: " + opcode + " operand: " + operand + " registerA: " + registerA + " registerB: " + registerB + " registerC: " + registerC);
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

        private int dvOperation(int operand){
            int comboOperand = getComboOperand(operand);
            int divider = (int) Math.pow(2, comboOperand);
            return registerA / divider;
        }

        private void bxlOperation(int operand){
            this.registerB = registerB ^ operand;
        }

        private void bstOperation(int operand){
            int comboOperand = getComboOperand(operand);
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
            int comboOperand = getComboOperand(operand);
            int output = comboOperand % 8;
            addOutput(output);
        }

        private void movePointer(){
            this.pointer += 2;
        }

        private int getComboOperand(int operand){
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
