package com.song.samples.plastic;

import com.google.common.collect.Lists;
import com.song.samples.Utils.DateUtils;
import com.song.samples.stream.Employee;
import com.song.samples.stream.Manager;

import java.text.ParseException;
import java.util.List;

/**
 * test class
 */
public class App {

    public static void main(String[] args) {
        try {
            Manager ceo = new Manager("Greedy", 80000D, DateUtils.parseDateTime("1990-08-18 08:00:01"), 15D);
            Manager cfo = new Manager("Sneaky", 60000D, DateUtils.parseDateTime("1991-08-18 08:00:01"), 13D);
            Pair<Manager> managerPair = new Pair<Manager>(ceo, cfo);
            List<Manager> employees = Lists.newArrayList();
            employees.add(ceo);
            employees.add(cfo);
            printBuddies(managerPair);

            Pair<Employee> result = new Pair<Employee>();
            minMaxBonus(employees, result);
            System.out.println("first: " + result.getFirst().getName() + ", second: " + result.getSecond().getName());

            maxMinBonus(employees, result);
            System.out.println("first: " + result.getFirst().getName() + ", second: " + result.getSecond().getName());

        } catch (ParseException e) {
            System.out.println("DateFormat error.");
        }
    }

    public static void printBuddies(Pair<? extends Manager> pair) {
        Employee first = pair.getFirst();
        Employee second = pair.getSecond();
        System.out.println(first.getName() + " and " + second.getName());
    }

    public static void minMaxBonus(List<Manager> managers, Pair<? super Manager> result) {
        if (managers == null || managers.size() == 0) {
            return;
        }
        Manager min = managers.get(0);
        Manager max = managers.get(0);
        for (Manager manager : managers) {
            if (min.getBonus() > manager.getBonus()) {
                min = manager;
            }
            if (max.getBonus() < manager.getBonus()) {
                max = manager;
            }
        }

        result.setFirst(min);
        result.setSecond(max);
    }

    public static void maxMinBonus(List<Manager> managers, Pair<? super Manager> result) {
        minMaxBonus(managers, result);
        PairAlg.swap(result);
    }

}
