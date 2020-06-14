import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

public class Evaluator {
	public static void evaluateOrders(Shop shop, ArrayList<Order> orders) {
		// Minimum number of orders within same hour to hit concentration rate of 3 is 3
		// 3 orders by the same user
		if (orders.size() < 2) return;
		
		HashMap<String, Integer> buyersMap = mapUserToOrderCount(orders);
		
		int concentrationRate = orders.size() / buyersMap.keySet().size();
		
		if (concentrationRate < 3) return;
		
		// Flag orders as brushed if concentration rate is larger or equals to 3
		shop.addBrushedOrders(orders);
	}
	
	public static void evaluateBrushedOrders(Shop shop) {
		// Get all orders identified as being performed during brushing period
		ArrayList<Order> orders = shop.getBrushedOrders();
		
		if (orders.isEmpty()) return;
		
		HashMap<String, Integer> buyersMap = mapUserToOrderCount(orders);
		
		int maxCount = Collections.max(buyersMap.values());
		
		// Get users who performed the largest amount of orders
		// Total number of orders performed during brushing period is constant for all users
		for (Entry<String, Integer> entry : buyersMap.entrySet()) {
			if (entry.getValue() == maxCount) {
				shop.addBrushingUser(entry.getKey());
			}
		}
	}
	
	// Returns a map of buyer to number of orders performed
	private static HashMap<String, Integer> mapUserToOrderCount(ArrayList<Order> orders) {
		HashMap<String, Integer> buyersMap = new HashMap<>();
		
		for (Order order : orders) {
			String buyer = order.getUserid();
			if (!buyersMap.containsKey(buyer)) buyersMap.put(buyer, 0);
			
			
			int count = buyersMap.get(buyer) + 1;
			
			buyersMap.put(buyer, count);
		}
		
		return buyersMap;
	}
}
