package charper.advent19.day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class DaySix {
    private static final String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2019\\src\\main\\java\\charper\\advent19\\day6\\DaySixInput.txt";
    private Map<String, String> tree = new HashMap<>();
    private Map<String, List<String>> graph = new HashMap<>();

    public DaySix() {
        buildTreeAndGraph();
        System.out.println(partOne());
        System.out.println(partTwo());
    }

    private int partTwo() {
        int shortestPath = Integer.MAX_VALUE;
        // minus 1 because the first step doesn't count.
        return getShortestPath("YOU", 0, shortestPath, null) - 1;
    }

    private Integer getShortestPath(String startNode, int currentSteps, int shortestPath, String previous) {
        List<String> orbits = graph.get(startNode);
        if (orbits.size() == 0) {
            return null;
        }
        for (String orbit: orbits) {
            if (orbit.equals(previous)) {
                continue;
            }
            if (orbit.equals("SAN")) {
                // you have reached SANTA !
                if (currentSteps < shortestPath) {
                    shortestPath = currentSteps;
                }
            } else {
                Integer path = getShortestPath(orbit, currentSteps + 1, shortestPath, startNode);
                if (path != null && path < shortestPath) {
                    shortestPath = path;
                }
            }
        }
        return shortestPath;
    }
    
    private int partOne() {
        int orbits = 0;
        Set<String> outerOrbits = getOuterOrbits();
        Set<String> visited = new HashSet<>();
        for (String outer : outerOrbits) {
            orbits += getOrbitsInPath(0, visited, outer);
        }
        return orbits;
    }

    private void buildTreeAndGraph() {
        try {
            File input = new File(FILE_PATH);
            Scanner scanner = new Scanner(input);
            while (scanner.hasNextLine()) {
                String vertex = scanner.nextLine();
                String nodeA = vertex.split("\\)")[1];
                String nodeB = vertex.split("\\)")[0];
                // part 1
                tree.put(nodeA, nodeB);
                // part 2
                checkAndAddBidirectionalVertices(nodeA, nodeB);
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
    }

    private void checkAndAddVertex(String nodeA, String nodeB) {
        if (graph.containsKey(nodeA)) {
            List<String> vertices = graph.get(nodeA);
            vertices.add(nodeB);
            graph.put(nodeA, vertices);
        }
        else {
            List<String> vertices = new ArrayList<>();
            vertices.add(nodeB);
            graph.put(nodeA, vertices);
        }
    }

    private void checkAndAddBidirectionalVertices(String nodeA, String nodeB) {
        checkAndAddVertex(nodeA, nodeB);
        checkAndAddVertex(nodeB, nodeA);
    }

    // find the ends of the tree
    private Set<String> getOuterOrbits() {
        Set<String> keys = new HashSet<String>(tree.keySet());
        keys.removeAll(tree.values());
        return keys;
    }

    // tail recursive
    private int getOrbitsInPath(int existingOrbits, Set<String> visited, String startVertex) {
        visited.add(startVertex);
        if (startVertex.equals("COM")) {
            return existingOrbits;
        }
        String inner = tree.get(startVertex);
        while (!inner.equals("COM")) {
            existingOrbits += 1;
            inner = tree.get(inner);
        }
        // add the 1 for COM
        existingOrbits++;
        if (visited.contains(tree.get(startVertex))) {
            return existingOrbits;
        }
        return getOrbitsInPath(existingOrbits, visited, tree.get(startVertex));
    }
}
