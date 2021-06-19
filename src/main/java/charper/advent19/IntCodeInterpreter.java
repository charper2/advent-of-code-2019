package charper.advent19;

public class IntCodeInterpreter {
    private final int[] memory;
    private int input;
    private int address = 0;

    public IntCodeInterpreter(int[] memory) {
        this.memory = memory;
    }

    public IntCodeInterpreter(int[] memory, int input) {
        this.memory = memory;
        this.input = input;
    }

    public int[] getMemory() {
        return memory;
    }

    // For Day 2
    public int runProgram(int noun, int verb) {
        memory[1] = noun;
        memory[2] = verb;
        return runProgram();
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
                    runOpCode4(modes);
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
        return value % 10 + ((value / 10) % 10) * 10;
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
        memory[memory[address + 1]] = input;
        address += 2;
    }

    private void runOpCode4(int[] modes) {
        int output = memory[memory[address + 1]];
        System.out.println(output);
        address += 2;
    }
    
}