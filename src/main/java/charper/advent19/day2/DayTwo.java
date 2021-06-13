package charper.advent19.day2;

import java.util.Arrays;

public class DayTwo {
    private static final int[] input = {1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,10,1,19,2,9,19,23,2,13,23,27,1,6,27,31,2,6,31,35,2,13,35,39,1,39,10,43,2,43,13,47,1,9,47,51,1,51,13,55,1,55,13,59,2,59,13,63,1,63,6,67,2,6,67,71,1,5,71,75,2,6,75,79,1,5,79,83,2,83,6,87,1,5,87,91,1,6,91,95,2,95,6,99,1,5,99,103,1,6,103,107,1,107,2,111,1,111,5,0,99,2,14,0,0};

    public DayTwo() {
        System.out.println(partOne());
        System.out.println(partTwo());
    }

    private Integer partOne() {
        int[] copy = Arrays.copyOf(input, input.length);
        return runProgram(copy, 12, 2);
    }

    private Integer partTwo() {
        int inputLen = 100;
        for (int i = 0; i < inputLen; i++) {
            for (int j = 0; j < inputLen; j++) {
                int[] copy = Arrays.copyOf(input, input.length);
                Integer val = runProgram(copy, i, j);
                if (val == 19690720) {
                    return (100 * i) + j;
                }
            }
        }
        throw new RuntimeException("Ooops, out of bounds");
    }

    private Integer runProgram(int[] values, Integer noun, Integer verb) {
        values[1] = noun;
        values[2] = verb;

        for(int position = 0; position < values.length + 3; position += 4) {
            int opcode = values[position];
            switch(opcode) {
                case 1: 
                    values[values[position + 3]] = values[values[position + 1]] + values[values[position + 2]];
                    break;
                case 2:
                    values[values[position + 3]] = values[values[position + 1]] * values[values[position + 2]];
                    break;
                case 99:
                    return values[0];
                default:
                    throw new RuntimeException("Unknown OpCode");
            }
        }
        throw new RuntimeException("Out of bounds");
    }

}