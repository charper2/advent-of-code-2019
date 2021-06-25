package charper.advent19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Utils {
    
    private Utils() {}

    public static Integer getNthDigit(int n, int number) {
        return number / (int)Math.pow(10, n) % 10;
    }
    
    public static List<Integer> getIntegers() {
        List<Integer> integers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            integers.add(scanner.nextInt());
        }
        scanner.close();
        return integers;
    }

    public static List<Long> getLongs(String filePath) {
        List<Long> numbers = new ArrayList<>();
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);
            while(scanner.hasNext()) {
                numbers.add(scanner.nextLong());
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
        return numbers;
    }

    public static List<Integer> getIntegerList(String filePath) {
        List<Integer> numbers = new ArrayList<>();
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);
            while(scanner.hasNext()) {
                numbers.add(scanner.nextInt());
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
        return numbers;
    }

    public static List<String> getLinesFromFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);

            while(scanner.hasNext()) {
                String instruction = scanner.nextLine();
                lines.add(instruction);
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
        return lines;
    }

    public static List<List<Character>> getCharList(String filePath) {
        List<List<Character>> lines = new ArrayList<>();
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);

            while(scanner.hasNext()) {
                String str = scanner.nextLine();
                str = str.replace(" ", "");
                List<Character> chars = str.chars().mapToObj(e -> (char)e).collect(Collectors.toList());
                lines.add(chars);
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
        return lines;
    }

    public static Map<Integer, char[]> getCharMap(String filePath) {
        Map<Integer, char[]> charMap = new HashMap<>();
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);

            Integer row = 0;
            while(scanner.hasNext()) {
                String instruction = scanner.nextLine();
                char chars[] = instruction.toCharArray();
                charMap.put(row, chars);
                row++;
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
        return charMap;
    }
}