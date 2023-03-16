import com.google.gson.Gson;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ClientLog {
    public int[] getSelectedProduct() {
        return selectedProduct;
    }

    protected String[] products;
    protected int[] prices;
    protected int[] selectedProduct;
    protected int productNum;
    protected int amount;

    public ClientLog(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.selectedProduct = new int[products.length];
        this.productNum = 0;
        this.amount = 0;

    }

    public void log(int productNum, int amount) {
        this.productNum = productNum;
        this.amount = amount;
        this.selectedProduct[productNum] += amount;


    }

    public void exportAsCSV(File txtFile) {
        String[] log = {String.valueOf(productNum + 1), String.valueOf(amount)};
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true))) {
            writer.writeNext(log);
        } catch (IOException e) {

        }
    }



}