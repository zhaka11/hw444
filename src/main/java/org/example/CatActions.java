package org.example;

public class CatActions {

    public interface Action {
        void apply(Cat cat);
    }

    public static class FeedAction implements Action {
        @Override
        public void apply(Cat cat) {
            int step = cat.getChangeStep();
            cat.setSatiety(Math.min(100, cat.getSatiety() + step));
            cat.setMood(Math.min(100, cat.getMood() + step));
            if (Math.random() < 0.2) {
                System.out.println("Кот отравился! Настроение и здоровье сильно упали.");
                cat.setMood(Math.max(0, cat.getMood() - 15));
                cat.setHealth(Math.max(0, cat.getHealth() - 20));
            }
        }
    }

    public static class PlayAction implements Action {
        @Override
        public void apply(Cat cat) {
            int step = cat.getChangeStep();
            cat.setMood(Math.min(100, cat.getMood() + step));
            cat.setHealth(Math.min(100, cat.getHealth() + step));
            cat.setSatiety(Math.max(0, cat.getSatiety() - step));
            if (Math.random() < 0.2) {
                System.out.println("Кот получил травму! Настроение и здоровье сильно упали.");
                cat.setMood(Math.max(0, cat.getMood() - 10));
                cat.setHealth(Math.max(0, cat.getHealth() - 15));
            }
        }
    }

    public static class HealAction implements Action {
        @Override
        public void apply(Cat cat) {
            int step = cat.getChangeStep();
            cat.setHealth(Math.min(100, cat.getHealth() + step));
            cat.setMood(Math.max(0, cat.getMood() - step));
            cat.setSatiety(Math.max(0, cat.getSatiety() - step));
        }
    }
}
