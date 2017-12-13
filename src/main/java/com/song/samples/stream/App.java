package com.song.samples.stream;

import com.google.common.collect.Lists;
import com.song.samples.Utils.DateUtils;

import java.io.*;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

/**
 * test class
 */
public class App {
    public static void main(String[] args) {
        List<Employee> employees = Lists.newArrayList();
        try {
            employees.add(new Employee("Cracker", 75000D, DateUtils.parseDateTime("1990-08-18 08:00:01")));
            employees.add(new Employee("Hacker", 55000D, DateUtils.parseDateTime("1991-08-18 08:00:01")));
            employees.add(new Employee("Tester", 45000D, DateUtils.parseDateTime("1992-08-18 08:00:01")));

            Iterator<Employee> iterator = employees.iterator();
            iterator.next();

            if (iterator.hasNext()) {
                System.out.println(iterator.next());
                iterator.remove();
                iterator.remove();
            }
            // write data
            PrintWriter out = new PrintWriter(new PrintWriter("employee.dat"));
            writeData(employees, out);
            out.close();

            // read data
            BufferedReader in = new BufferedReader(new FileReader("employee.dat"));
            List<Employee> employees1 = readData(in);
            for (Employee employee : employees1) {
                System.out.println(employee);
            }
        } catch (ParseException e) {
            System.out.println("Parse date error" + e);
        } catch (FileNotFoundException e) {
            System.out.println("Read file error" + e);
        } catch (IOException e) {
            System.out.println("Read file error" + e);
        }
    }

    /**
     * write data
     *
     * @param employees
     * @param out
     */
    private static void writeData(List<Employee> employees, PrintWriter out) {
        out.println(employees.size());
        for (Employee employee : employees) {
            employee.writeData(out);
        }
    }

    /**
     * read data
     *
     * @param in
     * @return
     * @throws IOException
     */
    private static List<Employee> readData(BufferedReader in) throws IOException {
        List<Employee> employees = Lists.newArrayList();
        int n = Integer.parseInt(in.readLine());
        for (int i = 0; i < n; i++) {
            Employee employee = new Employee();
            employee.readData(in);
            employees.add(employee);
        }
        return employees;
    }
}
