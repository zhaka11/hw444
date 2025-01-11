package org.example;

import java.util.Random;

public class Cat {
    private String name;
    private int age;
    private int satiety;
    private int mood;
    private int health;
    private boolean actionPerformedToday;

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
        this.satiety = getRandomValue(20, 80);
        this.mood = getRandomValue(20, 80);
        this.health = getRandomValue(20, 80);
        this.actionPerformedToday = false;
    }

    public boolean isActionPerformedToday() {
        return actionPerformedToday;
    }

    public void resetActionPerformedToday() {
        this.actionPerformedToday = false;
    }

    public void performAction(CatActions.Action strategy) {
        if (actionPerformedToday) {
            System.out.println("Действие уже выполнено с этим котом сегодня.");
            return;
        }
        strategy.apply(this);
        this.actionPerformedToday = true;
    }


    public double getAverageLifeLevel() {
        return (satiety + mood + health) / 3.0;
    }

    public int getHealth() {
        return health;
    }

    public int getMood() {
        return mood;
    }

    public int getSatiety() {
        return satiety;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public void setSatiety(int satiety) {
        this.satiety = satiety;
    }

    public int getChangeStep() {
        if (age >= 1 && age <= 5) return 7;
        if (age >= 6 && age <= 10) return 5;
        return 4;
    }

    private static int getRandomValue(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    @Override
    public String toString() {
        return String.format("Кот{имя='%s', возраст=%d, сытость=%d, настроение=%d, здоровье=%d, действие выполнено сегодня=%s}",
                name, age, satiety, mood, health, actionPerformedToday ? "да" : "нет");
    }
}
