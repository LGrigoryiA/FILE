import java.io.*;
import java.util.Arrays;

public class Basket {

    protected String[] products;
    protected int[] prices;
    protected int[] selectedProduct;

    public Basket(String[] products, int[] prices) throws IOException {
        this.products = products;
        this.prices = prices;
        File f = new File("basket.txt");

        if (f.exists()) {

            try (BufferedReader reader = new BufferedReader(new FileReader(f));) {

                String line = reader.readLine();
                String lprices[] = line.split(" ");
                this.selectedProduct = Arrays.stream(lprices).mapToInt(Integer::parseInt).toArray();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else this.selectedProduct = new int[products.length];

    }

    public void addToCart(int productNum, int amount) {
        selectedProduct[productNum] += amount;
        try (PrintWriter out = new PrintWriter("basket.txt");) {

            for (int e : selectedProduct)
                out.print(e + " ");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void printCart() {
        System.out.println("Ваша корзина:");
        for (int i = 0; i < selectedProduct.length; i++) {
            if (selectedProduct[i] == 0) {
                continue;
            }

            System.out.println(products[i] + " " + selectedProduct[i] + " шт "
                    + prices[i] + " руб/шт " + (selectedProduct[i] * prices[i]) +
                    " руб в сумме");
        }
    }

    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile);) {
            for (String e : products)
                out.print(e + " ");
            out.println();
            for (int e : prices)
                out.print(e + " ");
            out.println();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    static Basket loadFromTxtFile(File textFile) throws IOException {
        String[] loadProducts = new String[0];

        int[] loadPrices = new int[0];
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile));) {

            String line1 = reader.readLine();
            String line2 = reader.readLine();
            loadProducts = line1.split(" ");
            String lprices[] = line2.split(" ");
            loadPrices = Arrays.stream(lprices).mapToInt(Integer::parseInt).toArray();


        } catch (IOException e) {
            System.out.println(e.getMessage());


        }

        return new Basket(loadProducts, loadPrices);
    }


}
