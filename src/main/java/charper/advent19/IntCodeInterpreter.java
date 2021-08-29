package charper.advent19;

import static charper.advent19.Utils.getNthDigit;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.IntStream;

public class IntCodeInterpreter {
    private final IntCodeMemory memory;
    private LinkedList<Integer> input = new LinkedList<>();
    private int address = 0;
    private boolean halt = false;

    public IntCodeInterpreter(long[] memory) {
        this.memory = new IntCodeMemory(memory);
    }
    
    public IntCodeInterpreter(int[] memory) {
        this(IntStream.of(memory).mapToLong(i -> Long.valueOf(i)).toArray());
    }

    public IntCodeInterpreter(int[] memory, int in) {
        this(IntStream.of(memory).mapToLong(i -> Long.valueOf(i)).toArray());
        this.input.add(in);
    }

    public IntCodeInterpreter(int[] memory, int... inputs) {
        this(IntStream.of(memory).mapToLong(i -> Long.valueOf(i)).toArray());
        for (int in : inputs) {
            this.input.add(in);
        }
    }

    public long runInput(int input) {
        this.input.add(input);
        return runProgram();
    }

    public boolean isHalt() {
        return this.halt;
    }

    public long[] getMemory() {
        return memory.getMemory();
    }

    public long runProgram() {
        while(true) {
            int opcode = getOpCode(memory.get(address));
            int[] modes = getModes(memory.get(address));
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
                case 9:
                    runOpCode9(modes);
                    break;
                case 99:
                    halt = true;
                    return memory.get(0);
                default:
                    throw new RuntimeException("Unknown OpCode");
            }
        }
    }

    private int getOpCode(long value) {
        return getNthDigit(0, value) + getNthDigit(1, value) * 10;
    }

    private int[] getModes(long opCode) {
        int[] modes = new int[3];
        modes[0] = (int)opCode / 100 % 10;
        modes[1] = (int)opCode / 1000 % 10;
        modes[2] = (int)opCode / 10000 % 10;
        return modes;
    }

    private long getValue(long mode, int loc) {
        if (mode == 0) {
            return memory.get(memory.get(loc));
        } 
        else if (mode == 2) {
            return memory.get(memory.get(loc) + memory.getRelativeBase());
        }
        return memory.get(loc);
    }

    private void runOpCode1(int[] modes) {
        memory.set(modes[2], address + 3, getValue(modes[0], address + 1) + getValue(modes[1], address + 2));
        address += 4;
    }

    private void runOpCode2(int[] modes) {
        memory.set(modes[2], address + 3, getValue(modes[0], address + 1) * getValue(modes[1], address + 2));
        address += 4;
    }

    private void runOpCode3(int[] modes) {
        // will always run in position mode 0
        memory.set(modes[0], address + 1, input.removeFirst());
        // memory.set(0, address + 1, input.removeFirst());
        address += 2;
    }

    private long runOpCode4(int[] modes) {
        long output = getValue(modes[0], address + 1);
        // System.out.println(output);
        address += 2;
        return output;
    }

    private void runOpCode5(int[] modes) {
        if (getValue(modes[0], address + 1) != 0) {
            address = (int)getValue(modes[1], address + 2);
        } else {
            address += 3;
        }
    }

    private void runOpCode6(int[] modes) {
        if (getValue(modes[0], address + 1) == 0) {
            address = (int)getValue(modes[1], address + 2);
        } else {
            address += 3;
        }
    }

    private void runOpCode7(int[] modes) {
        if (getValue(modes[0], address + 1) < getValue(modes[1], address + 2)) {
            memory.set(modes[2], address + 3, 1);
        } else {
            memory.set(modes[2], address + 3, 0);
        }
        address += 4;
    }

    private void runOpCode8(int[] modes) {
        if (getValue(modes[0], address + 1) == getValue(modes[1], address + 2)) {
            memory.set(modes[2], address + 3, 1);
        } else {
            memory.set(modes[2], address + 3, 0);
        }
        address += 4;
    }
    
    private void runOpCode9(int[] modes) {
        memory.adjustRelativeBase(getValue(modes[0], address + 1));
        address += 2;
    }

    private static class IntCodeMemory {
        private long[] memory;
        private long relativeBase = 0;

        public IntCodeMemory(long[] memory) {
            this.memory = memory;
        }

        public void adjustRelativeBase(long adjustment) {
            relativeBase += adjustment;
        }

        public long getRelativeBase() {
            return relativeBase;
        }

        public long get(long index) {
            int intIndex = (int) index;
            if (intIndex >= memory.length) {
                return 0;
            }
            return memory[intIndex];
        }

        public void set(long mode, long index, long value) {
            int intIndex = (int)index;
            if (mode == 0) {
                set(get(intIndex), value);
                return;
            }
            else if (mode == 2) {
                set(get(intIndex) + relativeBase, value);
                return;
            }
            throw new IllegalArgumentException("Invalid mode for WRITE value");
        }

        private void set(long index, long value) {
            if (index >= memory.length) {
                // make the new memory double the largest index, just coz
                long [] largerMemory = Arrays.copyOf(memory, (int)index * 2);
                memory = largerMemory;
            }
            memory[(int)index] = value;
        }

        public long[] getMemory() {
            return this.memory;
        }
    }
}
