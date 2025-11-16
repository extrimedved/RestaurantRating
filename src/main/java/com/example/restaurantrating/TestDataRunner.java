package com.example.restaurantrating;

import com.example.restaurantrating.domain.GenderType;
import com.example.restaurantrating.domain.KitchenType;
import com.example.restaurantrating.domain.entity.*;
import com.example.restaurantrating.service.*;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataRunner implements CommandLineRunner {
    private final VisitorService visitorService;
    private final RestaurantService restaurantService;
    private final VisitorRatingService visitorRatingService;

    @Override
    public void run(String... args) {

        System.out.println("Начало тестов: ");

        Visitor v1 = visitorService.save(new Visitor(1L, "Алексей", 25, GenderType.МУЖЧИНА));
        Visitor v2 = visitorService.save(new Visitor(2L, null, 30, GenderType.ЖЕНЩИНА));
        Visitor v3 = visitorService.save(new Visitor(3L, "Иван", 19, GenderType.МУЖЧИНА));

        System.out.println("Добавлены посетители:");
        visitorService.findAll().forEach(System.out::println);

        Restaurant r1 = restaurantService.save(
                new Restaurant(1L, "Тануки", "Суши и роллы",
                        KitchenType.ЕВРОПЕЙСКАЯ, new BigDecimal("35.0"), null)
        );

        Restaurant r2 = restaurantService.save(
                new Restaurant(2L, "Братик томатик", "Пицца",
                        KitchenType.ИТАЛЬЯНСКАЯ, new BigDecimal("25.0"), null)
        );

        System.out.println("\nДобавлены рестораны:");
        restaurantService.findAll().forEach(System.out::println);

        visitorRatingService.save(new VisitorRating(v1.getId(), r1.getId(), 5, "Очень вкусно!"));
        visitorRatingService.save(new VisitorRating(v2.getId(), r1.getId(), 3, null));
        visitorRatingService.save(new VisitorRating(v3.getId(), r2.getId(), 5, "Спасибо, все идеально"));
        visitorRatingService.save(new VisitorRating(v1.getId(), r2.getId(), 4, "Еще раз зайду"));
        visitorRatingService.save(new VisitorRating(v2.getId(), r2.getId(), 5, null));

        System.out.println("\nДобавлены оценки:");
        visitorRatingService.findAll().forEach(System.out::println);

        System.out.println("\nСредние оценки ресторанов:");

        restaurantService.findAll().forEach(r ->
                System.out.println("Ресторан: " +
                        r.getName() + " средняя оценка: " + r.getUserRating())
        );

        System.out.println("\nУдаляем оценку у каждого ресторана:");

        visitorRatingService.remove(v1.getId(), r1.getId());
        visitorRatingService.remove(v2.getId(), r2.getId());

        restaurantService.findAll().forEach(r ->
                System.out.println("Ресторан: " +
                        r.getName() + " средняя оценка: " + r.getUserRating())
        );

        System.out.println("\nПроверка поиска ресторана, пользователя и оценки по id:");
        VisitorRating test1 = visitorRatingService.findById(v2.getId(), r1.getId());
        System.out.println("Оценка: " + test1);
        Visitor test2 = visitorService.findById(v2.getId());
        System.out.println("Посетитель: " + test2);
        Restaurant test3 = restaurantService.findById(r2.getId());
        System.out.println("Ресторан: " + test3);

        System.out.println("\nПроверка удаления ресторана и пользователя по id:");

        visitorService.remove(v2.getId());
        visitorService.findAll().forEach(System.out::println);
        restaurantService.remove(r2.getId());
        restaurantService.findAll().forEach(System.out::println);
    }
}
