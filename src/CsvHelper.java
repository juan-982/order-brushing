import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvHelper {
	
	public static ArrayList<Shop> readFile(String filename) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        
        HashMap<String, Shop> shopMap = new HashMap<>();

        try {

            br = new BufferedReader(new FileReader(filename));
            
            int i = 0;
            
            while ((line = br.readLine()) != null) {            	
            	i++;
            	
            	if (i == 1) continue;

                // use comma as separator
                String[] orderDetails = line.split(cvsSplitBy);
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
                
                String shopid = orderDetails[1];
                
                Shop shop = shopMap.get(shopid);
                
                if (shop == null) {
                	shop = new Shop(shopid);
                	shopMap.put(shopid, shop);
                }
                
                Order order = new Order(orderDetails[0], shop, Long.parseLong(orderDetails[2]), LocalDateTime.parse(orderDetails[3], formatter));
                
                shop.addOrder(order);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ArrayList<Shop>(shopMap.values());
    }

	public static void writeFile(ArrayList<Shop> shops, String filename) throws FileNotFoundException {
		ArrayList<String[]> dataLines = new ArrayList<>();
		
		dataLines.add(new String[] { "shopid", "userid" });
		
		for (Shop shop : shops) {
			dataLines.add(new String[] { shop.getShopId(), shop.printBrushingUsers() });
		}
		
		File csvOutputFile = new File(filename);
	    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
	        dataLines.stream()
	          .map(data -> Stream.of(data).collect(Collectors.joining(",")))
	          .forEach(pw::println);
	    }
	}
}
