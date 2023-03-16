
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};

        File fileTxt = new File("objBasket.txt");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("shop.xml"));
        Scanner scanner = new Scanner(System.in);
        XPath xPath = XPathFactory.newInstance().newXPath();
        boolean loadEnabled = Boolean.parseBoolean(xPath
                .compile("config/load/enabled")
                .evaluate(doc));
        String loadFileName = xPath
                .compile("config/load/fileName")
                .evaluate(doc);
        String loadFormat = xPath
                .compile("config/load/format")
                .evaluate(doc);
        Basket basket = null;
        if (loadEnabled) {
            File loadFile = new File(loadFileName);
            switch (loadFormat){
                case "json" : basket = Basket.loadFromJsonFile(loadFile); break;
                case "text" : basket = Basket.loadFromTxtFile(loadFile); break;
                default:
                    System.out.println("Что-то пошло не так");
            }
    }else {
            basket = new Basket(products,prices);
        }
        boolean saveEnabled = Boolean.parseBoolean(xPath
                .compile("config/save/enabled")
                .evaluate(doc));
        String saveFileName = xPath
                .compile("config/save/fileName")
                .evaluate(doc);
        String saveFormat = xPath
                .compile("config/save/format")
                .evaluate(doc);
        if (saveEnabled) {
            switch (loadFormat) {
                case "json":
                    basket.saveJson(new File("basket.json"));
                    break;
                case "text":
                    basket.saveTxt(new File("objBasket.txt"));
                    break;
                default:
                    System.out.println("Что-то пошло не так");
            }
        }
        File file = new File("client.csv");
        ClientLog clientLog = new ClientLog(products,prices);
        boolean logEnabled = Boolean.parseBoolean(xPath
                .compile("config/log/enabled")
                .evaluate(doc));
        String logFileName = xPath
                .compile("config/log/fileName")
                .evaluate(doc);
if (logEnabled){
    clientLog.exportAsCSV(file);
}

        for(int i = 0; i<products.length;i++)

    {
        System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб/шт.");
    }

        while(true)

    {
        System.out.println("Выберите товар и количество или введите `end`");
        String input = scanner.nextLine();

        if (input.equals("end")) {
            break;
        }

        String parts[] = input.split(" ");
        int inputNumber = Integer.parseInt(parts[0]) - 1;
        int productCount = Integer.parseInt(parts[1]);
         basket.addToCart(inputNumber, productCount);
        clientLog.log(inputNumber, productCount);
        clientLog.exportAsCSV(file);

    }


        basket.printCart();
}


}




