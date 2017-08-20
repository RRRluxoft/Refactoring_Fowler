package people.intellij.refactoring;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Date: 20.08.2017
 */
public class Customer {
    private final String name;
    private List<Rental> rentals;

    public Customer(String name) {
        this.name = name;
    }

    public void addRental(Rental rental) {
        Preconditions.checkArgument(Objects.nonNull(rental), "Rental cannot be NULL");
        if (Objects.isNull(rental)) {
            rentals = new ArrayList<>();
        }
        rentals.add(rental);
    }

    public String getName() {
        return name;
    }

    private List<Rental> getRentals() {
        if (rentals == null) {
            rentals = new ArrayList<>();
        }
        return rentals;
    }

    public String statement() {
        double totalAmount = 0;
        double thisAmount = 0;
        int frequentRenterPoints = 0;

        String result = "Учет аренды для" + getName() + "\n";

//        rentals.stream().map(rental ->
//        {
//            if (rental.getMovie().getPriceCode() == Movie.NEW_RELEASE) {
//                return (rental.getDaysRented() - 2) * 15;
//            }
//        } return );

        while (rentals.iterator().hasNext()) {
            Rental rental = rentals.iterator().next();

            //определить сумму для каждой строки
            switch (rental.getMovie().getPriceCode()) {
                case Movie.REGULAR:
                    thisAmount += 2;
                    if (rental.getDaysRented() > 2) {
                        thisAmount += (rental.getDaysRented() - 2) * 15;
                    }
                    break;

                case Movie.NEW_RELEASE:
                    thisAmount += rental.getDaysRented() * 3;
                    break;

                case Movie.CHILDRENS:
                    thisAmount += 15;
                    if (rental.getDaysRented() > 3) {
                        thisAmount += (rental.getDaysRented() - 3) * 15;
                    }
                    break;
            }

            // добавить очки для активного арендатора
            frequentRenterPoints++;

            // бонус за аренду новинки на два дня
            if ((rental.getMovie().getPriceCode() == Movie.NEW_RELEASE) &&
                rental.getDaysRented() > 1) {
                frequentRenterPoints++;
            }

            //показать результаты для этой аренды
            result += "\t" + rental.getMovie().getTitle() + "\t" +
                String.valueOf(thisAmount) + "\n";
            totalAmount += thisAmount;
        }

        //добавить нижний колонтитул
        result += "Сумма задолженности составляет" +
            String.valueOf(totalAmount) + "\n";
        result += "Вы заработали " + String.valueOf(frequentRenterPoints) +
            "очков за активность";
        return result;
    }

}
