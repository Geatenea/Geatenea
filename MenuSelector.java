import java.io.*;
import java.util.*;

class Dish {
    String type;
    String name;
    double price;
    String recipe;
    boolean vegetarian;

    public Dish(String type, String name, double price, String recipe, boolean vegetarian) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.recipe = recipe;
        this.vegetarian = vegetarian;
    }
}

public class MenuSelector {
    static List<Dish> dishes = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static Map<String, String> userCredentials = new HashMap<>();

    public static void main(String[] args) {
        loadDishesFromCSV("menu.csv");
        
        userCredentials.put("gonzalm", "GM08");
        
        boolean isAuthenticated = authenticateUser();
        if (!isAuthenticated) {
            System.out.println("Authentication failed. Exiting...");
            return;
        }

        double budget = getUserBudget();
        List<Dish> selectedDishes = selectDishes(budget);
        displaySelectedDishes(selectedDishes);
    }

        
        
            static void loadDishesFromCSV(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0].trim();
                String name = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim());
                String recipe = parts[3].trim();
                boolean vegetarian = Boolean.parseBoolean(parts[4].trim());
                dishes.add(new Dish(type, name, price, recipe, vegetarian));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        // User authentication
        
        static boolean authenticateUser() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        
        return userCredentials.containsKey(username) && userCredentials.get(username).equals(password);
        // return username.equals("gonzalm") && password.equals("GM08");
    }
        /////

        // Get user input for budget
        static double getUserBudget() {
        double budget = 0.0;
        do {
            System.out.print("Enter your budget (minimum $3): ");
            budget = scanner.nextDouble();
        } while (budget < 3.0);
        return budget;
    }
        
        double budget = getUserBudget();

        // Select dishes based on budget and vegetarian preference
        List<Dish> selectedDishes = selectDishes(budget);

        // Display selected dishes' recipes
        displaySelectedDishes(selectedDishes);
    //}


       // Select dishes based on budget and vegetarian preference
    static List<Dish> selectDishes(double budget) {
        List<Dish> selectedDishes = new ArrayList<>();
        
        double budgetPerMeal = budget / 3; // Distribute budget evenly among meals
        
        boolean isVegetarian = askUserVegetarianPreference();
        
        String[] mealTypes = {"Breakfast", "Lunch", "Dinner"};
        
        for (String mealType : mealTypes) {
            Dish selectedDish = null;
            for (Dish dish : dishes) {
                if (dish.type.equalsIgnoreCase(mealType) &&
                    dish.price <= budgetPerMeal &&
                    (!isVegetarian || dish.vegetarian)) {
                    selectedDish = dish;
                    break; // Stop searching after finding a suitable dish for the meal
                }
            }
            if (selectedDish != null) {
                selectedDishes.add(selectedDish);
            }
        }
        
        return selectedDishes;
    }
    
    static boolean askUserVegetarianPreference() {
        System.out.print("Are you vegetarian? (y/n): ");
        String answer = scanner.next().toLowerCase();
        return answer.equals("y");
    }

       static void displaySelectedDishes(List<Dish> selectedDishes) {
        System.out.println("Your selected menu:");
        
        for (int i = 0; i < selectedDishes.size(); i++) {
            Dish dish = selectedDishes.get(i);
            String mealType = "";
            if (i == 0) {
                mealType = "Breakfast";
            } else if (i == 1) {
                mealType = "Lunch";
            } else if (i == 2) {
                mealType = "Dinner";
            }
            
            System.out.println("\n" + mealType + ": " + dish.name);
            System.out.println("Price: $" + dish.price);
            System.out.println("Recipe:");
            System.out.println(dish.recipe);
        }
    }

}
