package charper.advent19.day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DayEight {
    private static final String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2019\\src\\main\\java\\charper\\advent19\\day8\\DayEightInput.txt";

    public DayEight() {
        System.out.println(partOne());
        // System.out.println(partTwo());
    }

    // private int partTwo() {
    // }

    private int partOne() {
        int width = 25;
        int height = 6;
        List<Integer> leastZeros = new ArrayList<>();
        int numZeros = Integer.MAX_VALUE;
        String input = "";
        try {
            File file = new File(FILE_PATH);
            Scanner scanner = new Scanner(file);
            if(scanner.hasNextLine()) {
                input = scanner.nextLine();
            }
            scanner.close();
        }
        catch (FileNotFoundException fnfe) {
            System.out.println("oops no file here...");
        }
        int startIndex = 0;
        while (startIndex < input.length()) {
            List<Integer> line = Arrays.stream(input.substring(startIndex, startIndex + (width * height)).split(""))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
            int zeros = Collections.frequency(line, 0);
            if (zeros < numZeros) {
                numZeros = zeros;
                leastZeros = line;
            }  
            startIndex += (width * height);
        }
              
        int ones = Collections.frequency(leastZeros, 1);
        int twos = Collections.frequency(leastZeros, 2);
        return ones * twos;
    }
}

    