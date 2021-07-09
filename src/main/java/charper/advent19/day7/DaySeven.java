package charper.advent19.day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import charper.advent19.IntCodeInterpreter;
import static charper.advent19.Utils.getPermutations;

public class DaySeven {
    private static final int[] MEMORY = {3,8,1001,8,10,8,105,1,0,0,21,34,59,76,101,114,195,276,357,438,99999,3,9,1001,9,4,9,1002,9,4,9,4,9,99,3,9,102,4,9,9,101,2,9,9,102,4,9,9,1001,9,3,9,102,2,9,9,4,9,99,3,9,101,4,9,9,102,5,9,9,101,5,9,9,4,9,99,3,9,102,2,9,9,1001,9,4,9,102,4,9,9,1001,9,4,9,1002,9,3,9,4,9,99,3,9,101,2,9,9,1002,9,3,9,4,9,99,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,99,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,99};

    public DaySeven() {
        System.out.println(partOne(MEMORY));
        System.out.println(partTwo(MEMORY));
    }

    private int partOne(int[] mem) {
        Integer[] phases = {0, 1, 2, 3, 4};
        List<Integer[]> perms = new ArrayList<>();
        getPermutations(phases, 5, perms);
        int maximumOutput = 0;
        for (Integer[] perm : perms) {
            int output = computeOutput(mem, perm);
            if (output > maximumOutput) {
                maximumOutput = output;
            };
        }
        return maximumOutput;
    }

    private int partTwo(int[] mem) {
        Integer[] phases = {5, 6, 7, 8, 9};
        List<Integer[]> perms = new ArrayList<>();
        getPermutations(phases, 5, perms);
        int maximumOutput = 0;
        for (Integer[] perm : perms) {
            int output = computeOutput2(mem, perm);
            if (output > maximumOutput) {
                maximumOutput = output;
            };
        }
        return maximumOutput;
    }

    private int computeOutput(int[] mem, Integer[] phases) {
        // create amplifier sequence
        int input = 0;
        for (int i = 0; i < 5; i++) {
            input = runAmplifier(mem, input, phases[i]);
        }
        return input;
    }

    private Integer runAmplifier(int[] mem, int input, int phase) {
        int[] copy = Arrays.copyOf(mem, mem.length);
        IntCodeInterpreter amp = new IntCodeInterpreter(copy, phase, input);
        return amp.runProgram();
    }

    private int computeOutput2(int[] mem, Integer[] phases) {
        // create amplifier sequence
        int input = 0;
        int lastOutput = 0; 
        int i = 0;
        Map<Integer, IntCodeInterpreter> amplifiers = new HashMap<>();
        while (true) {
            int[] copy = Arrays.copyOf(mem, mem.length);
            IntCodeInterpreter amp;
            int output;
            if (amplifiers.containsKey(i)) {
                amp = amplifiers.get(i);
                output = amp.runInput(input);
            }
            else {
                amp = new IntCodeInterpreter(copy, phases[i], input);
                output = amp.runProgram();
                amplifiers.put(i, amp);
            }
            if (i == 4) {
                lastOutput = output;
            }
            if (amp.isHalt() == true) {
                return lastOutput;
            }
            input = output;
            i++;
            if (i == 5) { i = 0; }
        }
    }
}
