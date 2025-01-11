package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CatSimulator {
    private final CatManager catManager = new CatManager();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new CatSimulator().start();
    }

    public void start() {
        catManager.loadCats();

        if (catManager.getCats().isEmpty()) {
            catManager.addCat("Мурзик", 5);
            catManager.addCat("Барсик", 8);
            catManager.addCat("Снежок", 3);
            catManager.addCat("baby", 4);
            System.out.println("Начальные коты добавлены в систему.");
        }

        while (true) {
            System.out.println("\nМеню Симулятора Котов:");
            System.out.println("1. Показать котов");
            System.out.println("2. Добавить кота");
            System.out.println("3. Выполнить действие");
            System.out.println("4. Следующий день");
            System.out.println("5. Сохранить котов в файл");
            System.out.println("6. Сортировать котов");
            System.out.println("0. Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> showCatsTable();
                case 2 -> addCat();
                case 3 -> performAction();
                case 4 -> nextDay();
                case 5 -> catManager.saveCats();
                case 6 -> sortCatsMenu();
                case 0 -> {
                    catManager.saveCats();
                    System.out.println("Выход...");
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void showCatsTable() {
        List<Cat> cats = catManager.getCats();

        if (cats.isEmpty()) {
            System.out.println("Список котов пуст. Добавьте нового кота.");
            return;
        }

        String[] headers = {"#", "ИМЯ", "Возраст", "ЗДОРОВЬЕ", "НАСТРОЕНИЕ", "СЫТОСТЬ", "СРЕДНИЙ УРОВЕНЬ"};
        List<String[]> rows = new ArrayList<>();

        for (int i = 0; i < cats.size(); i++) {
            Cat cat = cats.get(i);
            rows.add(new String[]{
                    String.valueOf(i + 1),
                    cat.getName(),
                    String.valueOf(cat.getAge()),
                    String.valueOf(cat.getHealth()),
                    String.valueOf(cat.getMood()),
                    String.valueOf(cat.getSatiety()),
                    String.format("%.2f", cat.getAverageLifeLevel())
            });
        }

        String table = TableFormatter.formatTable(headers, rows);
        System.out.println(table);
    }

    private void addCat() {
        System.out.println("Добавление нового кота:");
        String name;
        while (true) {
            System.out.print("Введите имя кота (только буквы): ");
            name = scanner.nextLine().trim();
            if (!name.isEmpty() && name.matches("[a-zA-Zа-яА-Я]+")) {
                break;
            }
            System.out.println("Некорректное имя. Имя должно содержать только буквы.");
        }

        int age = -1;
        while (age < 1 || age > 18) {
            System.out.print("Введите возраст кота (1-18): ");
            if (scanner.hasNextInt()) {
                age = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
            }
        }

        catManager.addCat(name, age);
        System.out.printf("Кот успешно добавлен: %s, возраст: %d%n", name, age);
    }

    private void performAction() {
        List<Cat> cats = catManager.getCats();
        if (cats.isEmpty()) {
            System.out.println("Нет доступных котов.");
            return;
        }

        showCatsTable();
        System.out.print("Выберите кота по номеру: ");
        int catIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (catIndex < 0 || catIndex >= cats.size()) {
            System.out.println("Неверный выбор кота.");
            return;
        }

        Cat selectedCat = cats.get(catIndex);

        if (selectedCat.isActionPerformedToday()) {
            System.out.println("Действие уже выполнено с этим котом сегодня.");
            return;
        }

        System.out.printf("Вы выбрали кота: %s, возраст: %d%n", selectedCat.getName(), selectedCat.getAge());

        System.out.println("Выберите действие:");
        System.out.println("1. Кормить");
        System.out.println("2. Играть");
        System.out.println("3. Лечить");
        int action = scanner.nextInt();
        scanner.nextLine();

        CatActions.Action strategy = switch (action) {
            case 1 -> {
                System.out.printf("Вы покормили кота: %s%n", selectedCat.getName());
                yield new CatActions.FeedAction();
            }
            case 2 -> {
                System.out.printf("Вы поиграли с котом: %s%n", selectedCat.getName());
                yield new CatActions.PlayAction();
            }
            case 3 -> {
                System.out.printf("Вы вылечили кота: %s%n", selectedCat.getName());
                yield new CatActions.HealAction();
            }
            default -> null;
        };

        if (strategy != null) {
            selectedCat.performAction(strategy);
            if (selectedCat.getHealth() <= 0) {
                System.out.println("Кот умер и был удален из списка.");
                catManager.removeDeadCats();
            }
        } else {
            System.out.println("Неверное действие.");
        }
    }

    private void nextDay() {
        catManager.nextDay();
        System.out.println("Наступил новый день.");
        catManager.removeDeadCats();
    }

    private void sortCatsMenu() {
        System.out.println("Выберите параметр для сортировки:");
        System.out.println("1. Имя");
        System.out.println("2. Возраст");
        System.out.println("3. Здоровье");
        System.out.println("4. Настроение");
        System.out.println("5. Сытость");
        System.out.println("6. Средний уровень");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Comparator<Cat> comparator = switch (choice) {
            case 1 -> Comparator.comparing(Cat::getName);
            case 2 -> Comparator.comparingInt(Cat::getAge);
            case 3 -> Comparator.comparingInt(Cat::getHealth);
            case 4 -> Comparator.comparingInt(Cat::getMood);
            case 5 -> Comparator.comparingInt(Cat::getSatiety);
            case 6 -> Comparator.comparingDouble(Cat::getAverageLifeLevel);
            default -> null;
        };

        if (comparator != null) {
            catManager.sortCats(comparator);
            showCatsTable();
        }
    }
}
