package charper.advent19.day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;


public class DayThree {
    private static final String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2019\\src\\main\\java\\charper\\advent19\\day3\\DayThreeInput.txt";
    Integer shortestDistance;

    public DayThree() {
        List<List<String>> wires = getInstructions(FILE_PATH);
        Set<Pair<Integer, Integer>> wire1 = getVisited(wires.get(0));
        Set<Pair<Integer, Integer>> wire2 = getVisited(wires.get(1));
        System.out.println(getShortestManhattan(wire1, wire2));
        System.out.println(getLeastSteps(wire1, wire2));
    }

    private Set<Pair<Integer, Integer>> getVisited(List<String> instructions) {
        int steps = 0;
        Pair<Integer, Integer> currentLocation = new Pair<>(0,0);
        Set<Pair<Integer, Integer>> visited = new HashSet<>();

        for (String instruction : instructions) {
            char direction = instruction.charAt(0);
            int stepX = 0;
            int stepY = 0;
            switch (direction) {
                case 'R':
                    stepX = 1;
                    break;
                case 'L':
                    stepX = -1;
                    break;
                case 'U':
                    stepY = 1;
                    break;
                case 'D':
                    stepY = -1;
                    break;
                default:
                    break;
            }
            int move = Integer.valueOf(instruction.substring(1, instruction.length()));
            for (int i = 1; i <= move; i++) {
                steps++;
                int newX = currentLocation.getX() + stepX;
                int newY = currentLocation.getY() + stepY;
                Pair<Integer, Integer> point = new Pair<Integer, Integer>(newX, newY, steps);
                visited.add(point);
                currentLocation.setX(newX);
                currentLocation.setY(newY);
            }
        }
        return visited;
    }

    private Integer getShortestManhattan(Set<Pair<Integer, Integer>> a, Set<Pair<Integer, Integer>> b) {
        Set<Pair<Integer, Integer>> intersection = getIntersection(a, b);
        Integer shortestDistance = null;
        for (Pair<Integer, Integer> point : intersection) {
            if (shortestDistance == null || point.getManhattanDistance() < shortestDistance) {
                shortestDistance = point.getManhattanDistance();
            }
        }
        return shortestDistance;
    }

    private Integer getLeastSteps(Set<Pair<Integer, Integer>> first, Set<Pair<Integer, Integer>> second) {
        Set<Pair<Integer, Integer>> intersection = getIntersection(first, second);
        List<Pair<Integer, Integer>> a = intersection.stream().sorted(Comparator.comparingInt(Pair::getSteps)).collect(Collectors.toList());
        List<Pair<Integer, Integer>> b = second.stream().sorted(Comparator.comparingInt(Pair::getSteps)).collect(Collectors.toList());
        Integer leastSteps = null;
        for (Pair<Integer, Integer> point : a) {
            Optional<Pair<Integer, Integer>> inter = b.stream()
                .filter(x -> x.equals(point))
                .findAny();
            if (inter.isPresent()) {
                int steps = inter.get().getSteps() + point.getSteps();
                if (leastSteps == null || steps < leastSteps) {
                    leastSteps = steps;
                }
            }
            // break early so we don't have to check all intersections.
            if (leastSteps != null && point.getSteps() > leastSteps) {
                break;
            }
        }
        return leastSteps;
    }

    private Set<Pair<Integer, Integer>> getIntersection(Set<Pair<Integer, Integer>> a, Set<Pair<Integer, Integer>> b) {
        Set<Pair<Integer, Integer>> intersection = new HashSet<>(a);
        intersection.retainAll(b);
        return intersection;
    }

    public static List<List<String>> getInstructions(String filePath) {
        List<String> wires = new ArrayList<>();
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);
            while(scanner.hasNext()) {
                wires.add(scanner.next());
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
        List<List<String>> instructions = new ArrayList<>();
        for (String wire: wires) {
            instructions.add(Arrays.asList(wire.split(",")));
        }
        return instructions;
    }

    public static class Pair<T, S> {
        T x;
        S y;
        int steps;

        public Pair(T x, S y) {
            this.x = x;
            this.y = y;
        }

        public Pair(T x, S y, int steps) {
            this.x = x;
            this.y = y;
            this.steps = steps;
        }

        public T getX() {
            return x;
        }

        public S getY() {
            return y;
        }

        public int getSteps() {
            return steps;
        }

        public void setX(T x) {
            this.x = x;
        }

        public void setY(S y) {
            this.y = y;
        }

        public Integer getManhattanDistance() {
            if (x instanceof Integer && y instanceof Integer) {
                return Math.abs((Integer)x) + Math.abs((Integer)y);
            }
            throw new RuntimeException("Not an Integer Pair");
        }

        // A bit dodgy but, for part 2, we can leave the steps out of the equals since we only
        // want the first instance of an intersection and we are using a Set of pairs.
        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (object == null || object.getClass() != this.getClass()) {
                return false;
            }
            Pair<?, ?> val = (Pair<?, ?>) object; 
            if (this.getX().equals(val.getX()) && this.getY().equals(val.getY())) {
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
        return Objects.hash(x, y);
        }
    }
}
