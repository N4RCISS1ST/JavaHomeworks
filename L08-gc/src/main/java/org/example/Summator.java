package org.example;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Summator {
    private int sum = 0;
    private int prevValue = 0;
    private int prevPrevValue = 0;
    private int sumLastThreeValues = 0;
    private int someValue = 0;
    // !!! эта коллекция должна остаться. Заменять ее на счетчик нельзя.
    private final List<Data> listValues = new ArrayList<>(100_000);
    private final SecureRandom random = new SecureRandom();

    // !!! сигнатуру метода менять нельзя
    public void calc(Data data) {
        listValues.add(data);
        if (listValues.size() == 100_000) {
            listValues.clear();
        }

        int dataValue = data.getValue();
        sum += dataValue + random.nextInt();

        sumLastThreeValues = dataValue + prevValue + prevPrevValue;

        prevPrevValue = prevValue;
        prevValue = dataValue;

        int tempSumLastThreeSquared = sumLastThreeValues * sumLastThreeValues;
        int dataValueAdjusted = dataValue + 1;
        for (int idx = 0; idx < 3; idx++) {
            someValue += (tempSumLastThreeSquared / (dataValueAdjusted) - sum);
            someValue = Math.abs(someValue) + listValues.size();
        }
    }

    public int getSum() {
        return sum;
    }

    public int getPrevValue() {
        return prevValue;
    }

    public int getPrevPrevValue() {
        return prevPrevValue;
    }

    public int getSumLastThreeValues() {
        return sumLastThreeValues;
    }

    public int getSomeValue() {
        return someValue;
    }
}
