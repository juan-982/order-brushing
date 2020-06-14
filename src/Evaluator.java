import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

public class Evaluator {
	public static boolean identifyBrushingPeriod(ArrayList<Order> orders) {
		// Minimum number of orders within same hour to hit concentration rate of 3 is 3
		// 3 orders by the same user
		if (orders.size() < 2) return false;
		
		HashMap<Long, Integer> buyersMap = mapUserToOrderCount(orders);
		
		int concentrationRate = orders.size() / buyersMap.keySet().size();
		
		// Flag orders as brushed if concentration rate is larger or equals to 3
		return !(concentrationRate < 3);
	}
	
	public static ArrayList<Long> identifyBrushingUsers(Shop shop) {
		ArrayList<Long> userids = new ArrayList<>();
		
		// Get all orders identified as being performed during brushing period
		ArrayList<Order> orders = shop.getBrushedOrders();
		
		if (orders.isEmpty()) return userids;
		
		HashMap<Long, Integer> buyersMap = mapUserToOrderCount(orders);
		
		int maxCount = Collections.max(buyersMap.values());
		
		// Get users who performed the largest amount of orders
		// Total number of orders performed during brushing period is constant for all users
		for (Entry<Long, Integer> entry : buyersMap.entrySet()) {
			if (entry.getValue() == maxCount) {
				userids.add(entry.getKey());
				
			}
		}
		
		return userids;
	}
	
	// Returns a map of buyer to number of orders performed
	private static HashMap<Long, Integer> mapUserToOrderCount(ArrayList<Order> orders) {
		HashMap<Long, Integer> buyersMap = new HashMap<>();
		
		for (Order order : orders) {
			long buyer = order.getUserid();
			
			if (!buyersMap.containsKey(buyer)) buyersMap.put(buyer, 0);
			
			
			int count = buyersMap.get(buyer) + 1;
			
			buyersMap.put(buyer, count);
		}
		
		return buyersMap;
	}
}
