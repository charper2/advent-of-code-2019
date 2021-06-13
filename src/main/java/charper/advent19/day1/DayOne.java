package charper.advent19.day1;

import java.lang.Math;
import java.util.List;
import java.util.stream.Collectors;

import static charper.advent19.Utils.getIntegerList;

public class DayOne {
    private static final String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2019\\src\\main\\java\\charper\\advent19\\day1\\DayOneInput.txt";

    public DayOne() {
        List<Integer> input = getIntegerList(FILE_PATH);
        System.out.println(partOne(input));
        System.out.println(partTwo(input));
    }

    private Integer partOne(List<Integer> input) {
        return input.stream()
            .collect(Collectors.summingInt(mass -> getRequiredFuel(mass)));
    }

    private Integer partTwo(List<Integer> input) {
        return input.stream()
            .collect(Collectors.summingInt(mass -> getRequiredFuelRecursive(mass)));
    }

    private Integer getRequiredFuel(Integer mass) {
        return (int) Math.floor(mass / 3) - 2;
    }

    private Integer getRequiredFuelRecursive(Integer mass) {
        Integer fuel = getRequiredFuel(mass);
        if (fuel > 0) {
            return fuel + getRequiredFuelRecursive(fuel);
        }
        else {
            return 0;
        }
    }
}