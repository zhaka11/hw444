package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CatManager {
    private final List<Cat> cats = new ArrayList<>();
    private static final String FILE_NAME = "src/main/resources/cats.json";
    private final Gson gson = new Gson();

    public void loadCats() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            List<Cat> loadedCats = gson.fromJson(reader, new TypeToken<List<Cat>>() {}.getType());
            if (loadedCats != null) cats.addAll(loadedCats);
        } catch (IOException e) {
            System.out.println("Не удалось загрузить котов из файла.");
        }
    }

    public void saveCats() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(cats, writer);
        } catch (IOException e) {
            System.out.println("Не удалось сохранить котов в файл.");
        }
    }

    public void addCat(String name, int age) {
        cats.add(new Cat(name, age));
    }

    public List<Cat> getCats() {
        return cats;
    }

    public void sortCats(Comparator<Cat> comparator) {
        cats.sort(comparator);
    }

    public void nextDay() {
        for (Cat cat : cats) {
            cat.resetActionPerformedToday();
        }
    }

    public void removeDeadCats() {
        cats.removeIf(cat -> cat.getHealth() <= 0);
    }
}
