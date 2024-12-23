package days;

import java.util.*;

public class Day22 {

    HashMap<String, Integer> sequenceWithTotalPrice = new HashMap<>();

    public long part1(String input, int iterations){

        long sum = 0;

        for(String line : input.lines().toList()){
            long number = Integer.parseInt(line);

            Secret secret = new Secret(number);
            for(int i = 0; i<iterations; i++){
                secret.evolve();
            }
            System.out.println(number + ": " + secret.getNumber());
            System.out.println(number + ": " + secret.getSequence());

            sum += secret.getNumber();
        }

        return sum;
    }

    public int part2(String input, int iterations){

        for(String line : input.lines().toList()){
            long number = Integer.parseInt(line);

            Secret secret = new Secret(number);
            for(int i = 0; i<iterations; i++){
                secret.evolve();
            }

            addSequences(secret.getSequenceWithPrice());
        }

        int highestValue = 0;

        for(Map.Entry<String, Integer> entry : sequenceWithTotalPrice.entrySet()){
            if(entry.getValue() >= highestValue){
                highestValue = entry.getValue();
            }
        }

        return highestValue;
    }

    public void addSequences(HashMap<String, Integer> sequenceWithPrice){
        for(Map.Entry<String, Integer> entry : sequenceWithPrice.entrySet()){
            int total = sequenceWithTotalPrice.getOrDefault(entry.getKey(), 0);
            total += entry.getValue();
            sequenceWithTotalPrice.put(entry.getKey(), total);
        }
    }

    private class Secret {
        private long number;
        private int lastDigit;
        private ArrayList<String> sequence;
        private HashMap<String, Integer> sequenceWithPrice;


        public Secret (long initialNumber){
            this.number = initialNumber;
            this.lastDigit = (int)(initialNumber % 10);
            sequence = new ArrayList<>();
            sequenceWithPrice = new HashMap<>();
        }

        public void evolve(){
            multiply64MixAndPrune();
            divide32RoundMixAndPrune();
            multiply2048MixAndPrune();

            setDigitAndSequence();
        }

        private void setDigitAndSequence(){
            int previousDigit = this.lastDigit;
            setLastDigit();
            int difference = this.lastDigit - previousDigit;

            addToSequence(difference);
        }

        private void addSequenceIfFirstOccurence(){
            if(!sequenceWithPrice.containsKey(sequence.toString())){
                sequenceWithPrice.put(sequence.toString(), lastDigit);
            }
        }

        private void addToSequence(int difference){
            sequence.add(Integer.toString(difference));
            if(sequence.size() > 4) {
                sequence.remove(0);
            }
            if(sequence.size() == 4){
                addSequenceIfFirstOccurence();
            }
        }

        private void setLastDigit(){
            this.lastDigit = (int)(this.number % 10);
        }

        private void multiply64MixAndPrune(){
            long result = this.number * 64;

            mixAndPrune(result);
        }

        private void divide32RoundMixAndPrune(){
            long result = this.number / 32;

            mixAndPrune(result);
        }

        private void multiply2048MixAndPrune(){
            long result = this.number * 2048;

            mixAndPrune(result);
        }

        private void mixAndPrune(long result){
            mix(result);
            prune();
        }

        private void mix(long result){
            this.number ^= result;
        }

        private void prune(){
            this.number %= 16777216;
        }

        public long getNumber(){
            return this.number;
        }

        public String getSequence(){
            return sequence.toString();
        }

        public HashMap<String, Integer> getSequenceWithPrice(){
            return sequenceWithPrice;
        }
    }
}
