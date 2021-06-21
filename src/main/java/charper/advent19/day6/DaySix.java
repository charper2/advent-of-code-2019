package charper.advent19.day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class DaySix {
    private static final String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2019\\src\\main\\java\\charper\\advent19\\day6\\DaySixInput.txt";
    private Map<String, String> tree = new HashMap<>();

    public DaySix() {
        System.out.println(partOne());
    }
    
    private int partOne() {
        int orbits = 0;
        buildTree();
        Set<String> outerOrbits = getOuterOrbits();
        Set<String> visited = new HashSet<>();
        for (String outer : outerOrbits) {
            orbits += getOrbitsInPath(0, visited, outer);
        }
        return orbits;
    }

    private void buildTree() {
        try {
            File input = new File(FILE_PATH);
            Scanner scanner = new Scanner(input);
            while (scanner.hasNextLine()) {
                String vertex = scanner.nextLine();
                tree.put(vertex.split("\\)")[1], vertex.split("\\)")[0]);
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
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
