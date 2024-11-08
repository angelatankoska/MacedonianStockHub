package com.example.macedonianshubb;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataFetcher {
    private static final String URL = "https://www.mse.mk/mk/stats/symbolhistory/ALK";
    private static final String CSV_FILE_PATH = "stock_data1.csv";

    public static void fetchAndWriteData() {
        try {
            // Fetch the HTML document
            Document doc = Jsoup.connect(URL).get();

            // Extract data from the table
            Elements rows = doc.select("table#data_table tbody tr");
            List<String[]> data = new ArrayList<>();

            // Iterate over each row in the table
            for (Element row : rows) {
                Elements cols = row.select("td");
                String date = cols.get(0).text();
                String open = cols.get(1).text().replace(",", "");
                String high = cols.get(2).text().replace(",", "");
                String low = cols.get(3).text().replace(",", "");
                String close = cols.get(4).text().replace(",", "");
                String volume = cols.get(5).text().replace(",", "");

                // Add each row of data as a String array
                data.add(new String[]{date, open, high, low, close, volume});
            }

            // Write the data to a CSV file
            writeDataToCsv(data);
            System.out.println("Data successfully written to CSV.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeDataToCsv(List<String[]> data) {
        try (FileWriter fileWriter = new FileWriter(CSV_FILE_PATH);
             CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("Date", "Open", "High", "Low", "Close", "Volume"))) {

            // Write each row to the CSV file
            for (String[] rowData : data) {
                csvPrinter.printRecord((Object[]) rowData);
            }

            csvPrinter.flush();  // Ensure all data is written to the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
