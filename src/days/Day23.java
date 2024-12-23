package days;

import java.util.*;

public class Day23 {

    public int part1(String input) {

        Network network = new Network();
        for(String line : input.lines().toList()) {
            network.addConnection(line);
        }

        System.out.println("The network has: " + network.computers.size() + " computers");
        System.out.println("Removing computers without t connection");
        network.removeComputersNotConnectedToT();
        System.out.println("The network has: " + network.computers.size() + " computers");
        System.out.println("Removing computers with less than 2 connections");
        network.removeComputersNotConnectedToAtLeast2();
        System.out.println("The network has: " + network.computers.size() + " computers");

        Set<String> triples = new HashSet<>();

        for(Computer c1 : network.computers.values()) {
            if(c1.name.startsWith("t")) {
                for(Computer c2 : c1.connections.values()) {
                    for(Computer c3: c2.connections.values()) {
                        if(c3.connections.containsKey(c1.name)) {
                            ArrayList<String> triple = new ArrayList<>();
                            triple.add(c1.name);
                            triple.add(c2.name);
                            triple.add(c3.name);
                            Collections.sort(triple);
                            String tripleString = triple.toString();
                            triples.add(tripleString);
                        }
                    }

                }
            }
        }

        return (int)triples.stream().count();
    }

    public String part2(String input) {

        Network network = new Network();
        for(String line : input.lines().toList()) {
            network.addConnection(line);
        }

        System.out.println("The network has: " + network.computers.size() + " computers");
        System.out.println("Removing computers without t connection");
        network.removeComputersNotConnectedToT();
        System.out.println("The network has: " + network.computers.size() + " computers");
        System.out.println("Removing computers with less than 13 connections");
        network.removeComputersNotConnectedToAtLeastn(13);
        System.out.println("The network has: " + network.computers.size() + " computers");

        for(Computer c1 : network.computers.values()) {
            if(c1.name.startsWith("t")) {
                ArrayList<Computer> computers = new ArrayList<>();
                computers.add(c1);
                for(Computer c2 : c1.connections.values()) {
                    for(Computer c3: c2.connections.values()) {
                        boolean connectedToAll= true;
                        for(Computer computerInSet : computers){
                            if(!c3.connections.containsKey(computerInSet.name)) {
                                connectedToAll = false;
                            }
                        }
                        if(connectedToAll) {
                            computers.add(c3);
                        }
                    }
                }

                if(computers.size() > 12){
                    ArrayList<String> computerNames = new ArrayList<>();
                    for(Computer computer : computers){
                        computerNames.add(computer.name);
                    }
                    Collections.sort(computerNames);
                    return computerNames.toString().replace("[", "").replace("]", "").replace(" ", "");
                }
            }
        }

        return "";
    }


    public class Network {

        HashMap<String, Computer> computers;

        public Network() {
            computers = new HashMap<>();
        }

        public void addConnection(String connection){
            String[] names = connection.split("-");
            Computer c1 = getOrCreateComputer(names[0]);
            Computer c2 = getOrCreateComputer(names[1]);
            c1.addConnection(c2);
            c2.addConnection(c1);
        }

        public Computer getOrCreateComputer(String name){
            if(!computers.containsKey(name)){
                Computer computer = new Computer(name);
                computers.put(name, computer);
                return computer;
            } else {
                return computers.get(name);
            }
        }

        public void removeComputersNotConnectedToT(){
            computers.entrySet().removeIf(entry -> !entry.getValue().isPartOfTConnection());
        }

        public void removeComputersNotConnectedToAtLeast2(){
            computers.entrySet().removeIf(entry -> entry.getValue().connections.size() < 2);
        }

        public void removeComputersNotConnectedToAtLeastn(int n){
            computers.entrySet().removeIf(entry -> entry.getValue().connections.size() < n);
        }

    }

    public class Computer {
        String name;
        HashMap<String, Computer> connections;

        public Computer(String name) {
            connections = new HashMap<>();
            this.name = name;
        }

        public void addConnection(Computer computer){
            connections.put(computer.name, computer);
        }

        public boolean isPartOfTConnection(){
            if(name.startsWith("t")){
                return true;
            }
            for(String computerName : connections.keySet()){
                if(computerName.startsWith("t")){
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return this.name;
        }

    }

}