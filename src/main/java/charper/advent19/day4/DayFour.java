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
        if (password % 10 >= password /10 % 10 &&
            password / 10 % 10 >= password / 100 % 10 &&
            password / 100 % 10 >= password/ 1000 % 10 &&
            password / 1000 % 10 >= password / 10000 % 10 &&
            password / 10000 % 10 >= password / 100000) 
        {
            return true;
        }
        return false;   
    } 

    private boolean isTwoAdjacentDigitsSame(int password) {
        if (password % 10 == password /10 % 10 ||
            password / 10 % 10 == password / 100 % 10 ||
            password / 100 % 10 == password/ 1000 % 10 ||
            password / 1000 % 10 == password / 10000 % 10 ||
            password / 10000 % 10 == password / 100000) 
        {
            return true;
        }
        return false;   
    }

    private boolean exactlyTwoAdjacentDigitsSame(int password) {
        int ones = password % 10;
        int tens = password / 10 % 10;
        int huns = password / 100 % 10;
        int thous = password / 1000 % 10;
        int ten_thous = password / 10000 % 10;
        int hun_thous = password / 100000;
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
