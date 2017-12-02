package com.song.samples.stream;

import java.util.Date;

/**
 * manager class
 */
public class Manager extends Employee {

    private Double bonus;

    public Manager(String name, Double salary, Date hireDay, Double bonus) {
        super(name, salary, hireDay);
        this.bonus = bonus;
    }

    // overwrite getSalary
    public Double getSalary(){
        double baseSalary = super.getSalary();
        return baseSalary + bonus;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }
}
