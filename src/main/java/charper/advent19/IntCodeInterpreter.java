package charper.advent19;

import static charper.advent19.Utils.getNthDigit;

import java.util.LinkedList;

public class IntCodeInterpreter {
    private final int[] memory;
    private LinkedList<Integer> input = new LinkedList<>();
    private int address = 0;

    public IntCodeInterpreter(int[] memory) {
        this.memory = memory;
    }

    public IntCodeInterpreter(int[] memory, int in) {
        this.memory = memory;
        this.input.add(in);
    }

    public IntCodeInterpreter(int[] memory, int... inputs) {
        this.memory = memory;
        for (int in : inputs) {
            this.input.add(in);
        }
    }

    public int[] getMemory() {
        return memory;
    }

    public int runProgram() {
        while(address < memory.length + 1) {
            int opcode = getOpCode(memory[address]);
            int[] modes = getModes(memory[address]);
            switch(opcode) {
                case 1: 
                    runOpCode1(modes);
                    break;
                case 2:
                    runOpCode2(modes);
                    break;
                case 3:
                    runOpCode3(modes);
                    break;
                case 4:
                    return runOpCode4(modes);
                    // break;
                case 5:
                    runOpCode5(modes);
                    break;
                case 6:
                    runOpCode6(modes);
                    break;
                case 7:
                    runOpCode7(modes);
                    break;
                case 8:
                    runOpCode8(modes);
                    break;
                case 99:
                    return memory[0];
                default:
                    throw new RuntimeException("Unknown OpCode");
            }
        }
        throw new RuntimeException("Out of bounds");
    }

    private int getOpCode(int value) {
        return getNthDigit(0, value) + getNthDigit(1, value) * 10;
    }

    private int[] getModes(int opCode) {
        int[] modes = new int[3];
        modes[0] = opCode / 100 % 10;
        modes[1] = opCode / 1000 % 10;
        modes[2] = opCode / 10000 % 10;
        return modes;
    }

    private int getValue(int mode, int loc) {
        if (mode == 0) {
            return memory[memory[loc]];
        }
        return memory[loc];
    }

    private void runOpCode1(int[] modes) {
        memory[memory[address + 3]] = getValue(modes[0], address + 1) + getValue(modes[1], address + 2);
        address += 4;
    }

    private void runOpCode2(int[] modes) {
        memory[memory[address + 3]] = getValue(modes[0], address + 1) * getValue(modes[1], address + 2);
        address += 4;
    }

    private void runOpCode3(int[] modes) {
        // will always run in position mode 0
        memory[memory[address + 1]] = input.removeFirst();
        address += 2;
    }

    private Integer runOpCode4(int[] modes) {
        int output = memory[memory[address + 1]];
        // System.out.println(output);
        address += 2;
        return output;
    }

    private void runOpCode5(int[] modes) {
        if (getValue(modes[0], address + 1) != 0) {
            address = getValue(modes[1], address + 2);
        } else {
            address += 3;
        }
    }

    private void runOpCode6(int[] modes) {
        if (getValue(modes[0], address + 1) == 0) {
            address = getValue(modes[1], address + 2);
        } else {
            address += 3;
        }
    }

    private void runOpCode7(int[] modes) {
        if (getValue(modes[0], address + 1) < getValue(modes[1], address + 2)) {
            memory[memory[address + 3]] = 1;
        } else {
            memory[memory[address + 3]] = 0;
        }
        address += 4;
    }

    private void runOpCode8(int[] modes) {
        if (getValue(modes[0], address + 1) == getValue(modes[1], address + 2)) {
            memory[memory[address + 3]] = 1;
        } else {
            memory[memory[address + 3]] = 0;
        }
        address += 4;
    }
    
}
