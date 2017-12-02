package com.song.samples.stream;

import com.google.common.base.Splitter;
import com.song.samples.Utils.DateUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * class
 *
 * 过多的更能封装到类里不好
 *
 */
public class Employee {

    private final Splitter splitter = Splitter.on("|");

    private String name;

    private Double salary;

    private Date hireDay;

    public Employee() {
    }

    public Employee(String name, Double salary, Date hireDay) {
        this.name = name;
        this.salary = salary;
        this.hireDay = hireDay;
    }

    public void raiseSalary(Double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", hireDay=" + hireDay +
                '}';
    }

    /**
     * Write employee data to a print write
     * @param out
     */
    public void writeData(PrintWriter out) {
        out.println(name + "|" + salary + "|" + DateUtils.formatDateTime(hireDay));
    }

    public void readData(BufferedReader in) throws IOException {
        String s = in.readLine();
        if (s == null) {
            return;
        }
        List<String> employeeList = splitter.splitToList(s);
        if (employeeList.size() != 3) {
            return;
        }
        name = employeeList.get(0);
        salary = Double.parseDouble(employeeList.get(1));
        try {
            hireDay = DateUtils.parseDateTime(employeeList.get(2));
        } catch (ParseException e) {
            hireDay = new Date();
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getHireDay() {
        return hireDay;
    }

    public void setHireDay(Date hireDay) {
        this.hireDay = hireDay;
    }
}
