package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.services.RecipeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeService recipeService;

    public DataLoader(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeService recipeService) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeService = recipeService;
    }

    @Override
    public void run(String... args) throws Exception {

        UnitOfMeasure tableSpoon = unitOfMeasureRepository.findByDescription("Tablespoon").get();

        Optional<Category> guacCategory = categoryRepository.findByDescription("American");

        Ingredient avocado = new Ingredient();
        avocado.setAmount(BigDecimal.valueOf(2));

        Ingredient salt = new Ingredient();
        salt.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").get());
        salt.setAmount(BigDecimal.valueOf(0.5));

        Ingredient limeJuice = new Ingredient();
        limeJuice.setUom(tableSpoon);
        limeJuice.setAmount(BigDecimal.valueOf(1));

        Ingredient cilantro = new Ingredient();
        cilantro.setAmount(BigDecimal.valueOf(2));
        cilantro.setUom(tableSpoon);

        Ingredient blackPepper = new Ingredient();
        blackPepper.setAmount(BigDecimal.valueOf(1));
        blackPepper.setUom(unitOfMeasureRepository.findByDescription("Dash").get());

        Recipe guacamole = new Recipe();
        guacamole.getIngredients().add(avocado);
        guacamole.getIngredients().add(salt);
        guacamole.getIngredients().add(limeJuice);
        guacamole.getIngredients().add(cilantro);
        guacamole.getIngredients().add(blackPepper);
        guacamole.setDescription("Guacamole!!");

        guacamole.getCategories().add(guacCategory.get());

        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setCookTime(0);
        guacamole.setPrepTime(10);
        guacamole.setDirections("Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.");

        recipeService.save(guacamole);
    }
}