package charper.advent19.day4;

import java.util.ArrayList;
import java.util.List;

public class DayFour {
    private static final int START = 123257;
    private static final int END = 647015;

    public DayFour() {
        System.out.println(partOne());
        System.out.println(partTwo());
    }

    private Integer partOne() {
        List<Integer> matches = new ArrayList<>();
        for (int i = START; i <= END; i++) {
            if (isDigitsIncreasing(i) && isTwoAdjacentDigitsSame(i)) {
                matches.add(i);
            }
        }
        return matches.size();
    }

    private Integer getNthDigit(int n, int number) {
        return number / (int)Math.pow(10, n) % 10;
    }

    private Integer partTwo() {
        List<Integer> matches = new ArrayList<>();
        for (int i = START; i <= END; i++) {
            if (isDigitsIncreasing(i) && exactlyTwoAdjacentDigitsSame(i)) {
                matches.add(i);
            }
        }
        return matches.size();
    }

    private boolean isDigitsIncreasing(int password) {
        if (getNthDigit(0, password) >= getNthDigit(1, password) &&
            getNthDigit(1, password) >= getNthDigit(2, password) &&
            getNthDigit(2, password) >= getNthDigit(3, password) &&
            getNthDigit(3, password) >= getNthDigit(4, password) &&
            getNthDigit(4, password) >= getNthDigit(5, password))
        {
            return true;
        }
        return false;   
    } 

    private boolean isTwoAdjacentDigitsSame(int password) {
        if (getNthDigit(0, password) == getNthDigit(1, password) ||
            getNthDigit(1, password) == getNthDigit(2, password) ||
            getNthDigit(2, password) == getNthDigit(3, password) ||
            getNthDigit(3, password) == getNthDigit(4, password) ||
            getNthDigit(4, password) == getNthDigit(5, password))
        {
            return true;
        }
        return false;   
    }

    private boolean exactlyTwoAdjacentDigitsSame(int password) {
        int ones = getNthDigit(0, password);
        int tens = getNthDigit(1, password);
        int huns = getNthDigit(2, password);
        int thous = getNthDigit(3, password);
        int ten_thous = getNthDigit(4, password);
        int hun_thous = getNthDigit(5, password);
        if ((ones == tens && tens != huns) ||
            (ones != tens && tens == huns && huns != thous) ||
            (tens != huns && huns == thous && thous != ten_thous) ||
            (huns != thous && thous == ten_thous && ten_thous != hun_thous) ||
            (thous != ten_thous && ten_thous == hun_thous))
        {
            return true;
        }
        return false;
    }
}
