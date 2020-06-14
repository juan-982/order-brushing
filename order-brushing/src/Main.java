import java.io.FileNotFoundException;
import java.util.ArrayList;  

public class Main {

	public static void main(String[] args) {
		ArrayList<Shop> shops = CsvHelper.readFile("order_brush_order.csv");
		
		for (Shop shop : shops) {
			ArrayList<Order> orders = shop.getSortedOrders();
			
			for (int i = 0; i < orders.size(); i++) {
				Order previousOrder = null;
				
				if (i > 0) previousOrder = orders.get(i - 1);
				
				ArrayList<Order> ordersWithinHour = new ArrayList<>();
				
				ordersWithinHour.add(orders.get(i));
				
				for (int j = 1; i + j < orders.size(); j++) {
					Order nextOrder = null;
					
					if (i + j + 1 < orders.size()) nextOrder = orders.get(i + j + 1);
					
					Order order = orders.get(i + j);
					
					if (!ordersWithinHour.get(0).isWithinSameHour(order)) break;
					
					ordersWithinHour.add(order);
					
					// Minimum number of orders within same hour to hit concentration rate of 3 is 3
					// 3 orders by the same user
					if (ordersWithinHour.size() < 2) continue;	
					
					// Assume:
					// Order 1: 0010
					// Order 2: 0020 -> i
					// Order 3: 0050 -> j
					// Order 4: 0105
					// Orders 1 and 4 are within the same hour 
					// Hence orders 2 and 3 cannot be within the same hour without order 4 (0011 to 0111)
					if (previousOrder != null && 
							nextOrder != null && 
							previousOrder.isWithinSameHour(nextOrder)) continue;
					
					// Assume:
					// Order 1: 0010
					// Order 2: 0020 -> i
					// Order 3: 0050 -> j
					// Order 4: 0120
					// Orders 1 and 4 are not within the same hour 
					// Hence orders 2 and 3 can be within the same hour without orders 1 and 4 (0019 to 0119)
					Evaluator.evaluateOrders(shop, ordersWithinHour);
				}
			}
			
			Evaluator.evaluateBrushedOrders(shop);
		}
		
		try {
			CsvHelper.writeFile(shops, "output.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}