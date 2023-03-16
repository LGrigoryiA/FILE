
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};
        File file = new File("log.csv");
        File jFile = new File("basket.json");
        //  File file = new File("objBasket.txt");
        //  Basket basket = Basket.loadFromTxtFile(file);
        // Basket basket = new Basket(products,prices);
        ClientLog basket = ClientLog.loadFromJsonFile(jFile);
        //  ClientLog basket = new ClientLog(products,prices);
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб/шт.");
        }

        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();

            if (input.equals("end")) {
                break;
            }

            String parts[] = input.split(" ");
            int inputNumber = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);
            //  basket.addToCart(inputNumber, productCount);
            //   basket.saveTxt(file);
            basket.log(inputNumber, productCount);
            basket.exportAsCSV(file);

        }

        System.out.println(Arrays.toString(basket.getSelectedProduct()));
        //    basket.printCart();
    }


}




