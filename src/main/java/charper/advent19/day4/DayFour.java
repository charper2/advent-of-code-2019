package charper.advent19.day4;

import java.util.ArrayList;
import java.util.List;

public class DayFour {
    private static final int START = 123257;
    private static final int END = 647015;

    public DayFour() {
        System.out.println(partOne());
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
}
