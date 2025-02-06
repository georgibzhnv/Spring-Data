package com.softuni.springdataadvanced.init;

import com.softuni.springdataadvanced.dao.IngredientRepository;
import com.softuni.springdataadvanced.dao.LabelRepository;
import com.softuni.springdataadvanced.dao.ShampooRepository;
import com.softuni.springdataadvanced.entity.Ingredient;
import com.softuni.springdataadvanced.entity.Shampoo;
import com.softuni.springdataadvanced.util.PrintUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static com.softuni.springdataadvanced.entity.Size.MEDIUM;

@Component
public class AppInitializer implements CommandLineRunner {
    private final ShampooRepository shampooRepository;
    private final LabelRepository labelRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public AppInitializer(ShampooRepository shampooRepository,LabelRepository labelRepository, IngredientRepository ingredientRepository) {
        this.shampooRepository = shampooRepository;
        this.labelRepository = labelRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        shampooRepository.findBySizeOrderById(MEDIUM)
//                .forEach(PrintUtils::printShampoo);
//        System.out.println("-".repeat(148)+"\n");
//
//        shampooRepository.findBySizeOrLabelOrderByPriceDesc(MEDIUM,labelRepository.findByTitle("Premium Label").get())
//                .forEach(PrintUtils::printShampoo);
//        System.out.println("-".repeat(148)+"\n");
//
//        shampooRepository.findWithIngredientsInList(Set.of("Aloe Vera","Keratin")).forEach(PrintUtils::printShampoo);

//        shampooRepository.findByPriceGreaterThanEqual(7).forEach(PrintUtils::printShampoo);
//        System.out.println("-".repeat(148)+"\n");
//
//        shampooRepository.findByPriceBetween(7,10).forEach(PrintUtils::printShampoo);
//        System.out.println("-".repeat(148)+"\n");

//        ingredientRepository.findIngredientsByNameIn(Set.of("Honey Extract","Vitamin E","AloeVera")).forEach(PrintUtils::printIngredients);
//        System.out.println("-".repeat(51 )+"\n");

//        double maxPrice = 8.50;
//        System.out.printf("Number of shampoos with price less than %5.2f is : %d%n",
//                maxPrice,shampooRepository.countShampoosByPriceLessThan(maxPrice));
//        System.out.println("-".repeat(100)+"\n");

//        shampooRepository.findByCountOfIngredientsLowerThan(2).forEach(PrintUtils::printShampoo);
//
//        String nameForDeleting = "Keratin";
//        Ingredient ingredientForDeleting = ingredientRepository.findByName(nameForDeleting).get();
//        List<Shampoo> shampoosByIngredient = shampooRepository.findByIngredient(ingredientForDeleting);
//        shampoosByIngredient.forEach(PrintUtils::printShampoo);
//        shampoosByIngredient.forEach(shampoo -> shampoo.getIngredients().remove(ingredientForDeleting));
//        System.out.printf("Number of deleted ingredients with name='%s' is: %d%n",
//                nameForDeleting,ingredientRepository.deleteAllByName(nameForDeleting));
//
//        shampooRepository.findAll().forEach(PrintUtils::printShampoo);
//        System.out.println("-".repeat(149)+"\n");

        ingredientRepository.findAll().forEach(PrintUtils::printIngredients);
        System.out.println("-".repeat(51)+"\n");

        System.out.printf("Number of ingredients updated: %d\n\nAfter update:\n",
                ingredientRepository.updatePriceOfIngredientsInList(0.1,Set.of("Aloe Vera","Vitamin E")));
        System.out.println("-".repeat(51)+"\n");

        ingredientRepository.findAll().forEach(PrintUtils::printIngredients);

    }
}
