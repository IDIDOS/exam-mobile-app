package com.example.weatherapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class SearchUtils {

    private final List<String> cities = new ArrayList<>(Arrays.asList(
            "Минск", "Гомель", "Могилёв", "Витебск", "Гродно", "Брест",
            "Барановичи", "Бобруйск", "Борисов", "Орша", "Новополоцк",
            "Полоцк", "Лида", "Солигорск", "Мозырь", "Пинск", "Жлобин",
            "Речица", "Светлогорск", "Кобрин", "Несвиж", "Слуцк", "Столбцы",
            "Логойск", "Клецк", "Червень", "Марьина Горка", "Вилейка",
            "Молодечно", "Ошмяны", "Ивье", "Новогрудок", "Сморгонь",
            "Волковыск", "Щучин", "Берёза", "Иваново", "Малорита",
            "Дрогичин", "Лунинец", "Ганцевичи", "Калинковичи", "Хойники",
            "Лельчицы", "Петриков", "Житковичи", "Корма", "Костюковичи",
            "Краснополье", "Климовичи", "Осиповичи", "Горки", "Дрибин",
            "Белыничи", "Шклов", "Чаусы", "Сеннен", "Толочин", "Бешенковичи",
            "Ушачи", "Глубокое", "Миоры", "Браслав"
    ));

    public List<String> getAllCities(){
        return this.cities;
    }

    public List<String> filterCitiesListByQuery(String query){
        return this.cities.stream().filter(
                city ->city.contains(query)
        ).collect(Collectors.toList());
    }

}
