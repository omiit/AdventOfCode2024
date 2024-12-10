package days;

import java.util.ArrayList;

public class Day9 {

    private class MemoryBlock {
        private int size;
        private Integer id;

        public MemoryBlock(int size) {
            this.size = size;
        }

        public MemoryBlock(int totalSize, int id) {
            this.size = totalSize;
            this.id = id;
        }

        public boolean isEmpty(){
            return id == null;
        }

        public void setId(final Integer id) {
            this.id = id;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public void clear(){
            this.id = null;
        }
    }

    public Long part1(String input) {
        int[] diskMap = new int[input.length()];
        for (int i = 0; i < input.length(); i++) {
            diskMap[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
        }

        int fileBlockIndex = 0;

        int diskMapPointerTail = diskMap.length - 1; //Starts at end of array

        int currentFileIdTail = (diskMap.length - 1) / 2; //The id of the file at the end
        int currentFileId = 0;

        long sum = 0L;

        for (int diskMapPointer = 0; diskMapPointer <= diskMapPointerTail; diskMapPointer++) {

            if (isFileBlock(diskMapPointer)) {
                int fileSize = diskMap[diskMapPointer];

                for (int i = 0; i < fileSize; i++) {
                    sum += (long) fileBlockIndex * currentFileId;
                    fileBlockIndex++;
                }
                currentFileId++;
            } else {
                int emptySpaceSize = diskMap[diskMapPointer];

                //Loop over empty blocks
                for (int i = 0; i < emptySpaceSize; i++) {
                    //The file we were currently moving, has no blocks left
                    if (diskMap[diskMapPointerTail] == 0) {
                        diskMapPointerTail = diskMapPointerTail - 2;
                        currentFileIdTail--;
                    }
                    sum += (long) fileBlockIndex * currentFileIdTail;
                    //Remove fileblock from file
                    diskMap[diskMapPointerTail] = diskMap[diskMapPointerTail] - 1;
                    fileBlockIndex++;
                }
            }
        }

        return sum;
    }

    public Long part2(String input) {
        ArrayList<MemoryBlock> disk = fillDisk(input);

        //Loop through disk from the end
        for (int i = disk.size() - 1; i >= 0; i--) {
            if (disk.get(i).isEmpty()) {
                continue;
            }

            MemoryBlock fileBlockToMove = disk.get(i);

            //Loop over empty blocks from start
            for (int j = 0; j < i; j++) {
                MemoryBlock block = disk.get(j);
                if (block.isEmpty() && block.size >= fileBlockToMove.size) {
                    if (block.size > fileBlockToMove.size) {
                        MemoryBlock emptySpace = new MemoryBlock(block.size - fileBlockToMove.size);
                        disk.add(j + 1, emptySpace);
                    }
                    block.setId(fileBlockToMove.id);
                    block.setSize(fileBlockToMove.size);
                    fileBlockToMove.clear();

                    break;
                }
            }
        }

        return getCheckSum(disk);
    }

    private long getCheckSum(ArrayList<MemoryBlock> disk) {
        int blockPosition = 0;
        long sum = 0L;

        for (MemoryBlock block : disk) {
            if (block.id != null) {
                for (int j = 0; j < block.size; j++) {
                    sum += (long) block.id * (blockPosition + j);
                }
            }
            blockPosition += block.size;
        }

        return sum;
    }

    private ArrayList<MemoryBlock> fillDisk(final String input) {
        ArrayList<MemoryBlock> disk = new ArrayList<>();
        int id = 0;

        for (int i = 0; i < input.length(); i++) {
            int blockSize = Integer.parseInt(String.valueOf(input.charAt(i)));
            if (i % 2 == 0) {
                disk.add(new MemoryBlock(blockSize, id));
                id++;
            } else {
                disk.add(new MemoryBlock(blockSize)); //Empty block
            }
        }

        return disk;
    }

    private boolean isFileBlock(int diskPointer){
        return diskPointer % 2 == 0;
    }
}
