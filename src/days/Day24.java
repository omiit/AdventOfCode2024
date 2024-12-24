package days;

import java.util.HashMap;

public class Day24 {

    public long part1(String input){

        Device device = new Device();

        for(String line : input.lines().toList()){
            device.addGate(line);
        }

        String x = getBinaryNumberOf("x", device);
        String y = getBinaryNumberOf("y", device);

        System.out.println("x:  " + x + " which is: " + Long.parseLong(x, 2));
        System.out.println("y:  " + y + " which is: " + Long.parseLong(y, 2));

        String z = getBinaryNumberOf("z", device);

        System.out.println("z: " + z + " which is: " + Long.parseLong(z, 2));

        return Long.parseLong(z, 2);
    }

    public String getBinaryNumberOf(String letter, Device device){
        String outputBits = "";

        int i = 0;
        while(device.hasGate(letter + ((i<10) ? "0" + i : i ))){
            outputBits = device.getGateOutBit(letter + ((i<10) ? "0" + i : i )) + outputBits;
            i++;
        }

        return outputBits;
    }

    public class Device {

        HashMap<String, Gate> gates = new HashMap<>();

        public void addGate(String line){

            if(line.contains(": ")){
                String[] splits = line.split(": ");
                gates.put(splits[0], new InputGate(splits[0], splits[1].equals("1"), this));
            }

            if(line.contains("->")){
                String[] splits = line.split(" ");
                String in1 = splits[0];
                String in2 = splits[2];
                String out = splits[4];
                switch (splits[1]) {
                    case "XOR" -> gates.put(out, new XORGate(out, in1, in2, this));
                    case "AND" -> gates.put(out, new ANDGate(out, in1, in2, this));
                    case "OR" -> gates.put(out, new ORGate(out, in1, in2, this));
                }
            }
        }

        public boolean hasGate(String input){
            return gates.containsKey(input);
        }

        public Gate getGate(String wire){
            return gates.get(wire);
        }

        public boolean getGateOutput(String wire){
            return gates.get(wire).getOutPut();
        }

        public String getGateOutBit(String wire){
            return gates.get(wire).getOutBit();
        }

    }

    public abstract class Gate {
        Device device;
        String outWire;
        String in1;
        String in2;
        Boolean out = null;

        public Gate(String outWire, String in1, String in2, Device device) {
            this.device = device;
            this.outWire = outWire;
            this.in1 = in1;
            this.in2 = in2;
        }

        public boolean getInput1(){
            return this.device.getGateOutput(in1);
        }

        public boolean getInput2(){
            return this.device.getGateOutput(in2);
        }

        public String getOutBit(){
            if(getOutPut()){
                return "1";
            } else {
                return "0";
            }
        }

        public abstract boolean getOutPut();
    }

    public class InputGate extends Gate {

        boolean value;

        public InputGate(final String outWire, boolean value, final Device device) {
            super(outWire, null, null, device);
            this.value = value;
        }

        @Override
        public boolean getOutPut() {
            return value;
        }
    }

    public class XORGate extends Gate {

        public XORGate(final String outWire, final String in1, final String in2, final Device device) {
            super(outWire, in1, in2, device);
        }

        @Override
        public boolean getOutPut() {
            return getInput1() ^ getInput2();
        }
    }

    public class ANDGate extends Gate {

        public ANDGate(final String outWire, final String in1, final String in2, final Device device) {
            super(outWire, in1, in2, device);
        }

        @Override
        public boolean getOutPut() {
            return getInput1() & getInput2();
        }
    }

    public class ORGate extends Gate {

        public ORGate(final String outWire, final String in1, final String in2, final Device device) {
            super(outWire, in1, in2, device);
        }

        @Override
        public boolean getOutPut() {
            return getInput1() | getInput2();
        }
    }

}
