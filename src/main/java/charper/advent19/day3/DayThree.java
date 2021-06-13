package charper.advent19.day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;


public class DayThree {
    private static final String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2019\\src\\main\\java\\charper\\advent19\\day3\\DayThreeInput.txt";
    Integer shortestDistance;

    public DayThree() {
        System.out.println(partOne());
        // System.out.println(partTwo());
    }

    private Integer partOne() {
        List<List<String>> wires = getInstructions(FILE_PATH);
        Set<Pair<Integer, Integer>> wire1 = getVisited(wires.get(0));
        Set<Pair<Integer, Integer>> wire2 = getVisited(wires.get(1));
        return getShortestManhattan(wire1, wire2);
    }

    private Set<Pair<Integer, Integer>> getVisited(List<String> instructions) {
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
                int newX = currentLocation.getX() + stepX;
                int newY = currentLocation.getY() + stepY;
                Pair<Integer, Integer> point = new Pair<Integer, Integer>(newX, newY);
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

    private Set<Pair<Integer, Integer>> getIntersection(Set<Pair<Integer, Integer>> a, Set<Pair<Integer, Integer>> b) {
        Set<Pair<Integer, Integer>> intersection = new HashSet<>(a);
        intersection.retainAll(b);
        return intersection;
    }

    // public void moveLocations(
    //     Integer stepX, 
    //     Integer stepY, 
    //     Integer move
    // ) {
    //     for (int i = 1; i <= move; i++) {
    //         int newX = currentLocation.getX() + stepX;
    //         int newY = currentLocation.getY() + stepY;
    //         Pair<Integer, Integer> point = new Pair<Integer, Integer>(newX, newY);
    //         if (visited.contains(point)) {
    //             if (shortestDistance == null || point.getManhattanDistance() < shortestDistance) {
    //                 shortestDistance = point.getManhattanDistance();
    //             }
    //         }
    //         else {
    //             visited.add(point);
    //         }
    //         currentLocation.setX(newX);
    //         currentLocation.setY(newY);
    //     }
    // }

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

        public Pair(T x, S y) {
            this.x = x;
            this.y = y;
        }

        public T getX() {
            return x;
        }

        public S getY() {
            return y;
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
