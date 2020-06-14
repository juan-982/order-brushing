import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

public class Shop {
	private String shopid;
	private ArrayList<Order> orders = new ArrayList<>();
	private HashSet<Order> brushedOrders = new HashSet<>();
	private TreeSet<Long> brushingUsers = new TreeSet<>();
	
	public Shop(String shopid) {
		this.shopid = shopid;
	}
	
	public String getShopId() {
		return shopid;
	}
	
	public void addOrder(Order order) {
		orders.add(order);
	}
	
	public void addBrushedOrders(List<Order> orders) {
		brushedOrders.addAll(orders);
	}
	
	public ArrayList<Order> getBrushedOrders() {
		return new ArrayList<Order>(brushedOrders);
	}
	
	public ArrayList<Order> getOrders() {
		return orders;
	}
	
	public ArrayList<Order> getSortedOrders() {
		Collections.sort(orders, new OrderComparator());
		
		return orders;
	}
	
	public void addBrushingUsers(List<Long> userids) {
		brushingUsers.addAll(userids);
	}
	
	public String printBrushingUsers() {
		if (brushingUsers.isEmpty()) return "0";
		else {
			
			StringBuilder builder = new StringBuilder();
			for (Long l : brushingUsers) {
				if (builder.length() > 0) builder.append("&");
				builder.append(l.toString());
			}
			return builder.toString();
		}
	}
	
	private class OrderComparator implements Comparator<Order> {
		public int compare(Order o1, Order o2) {
			return o1.getEventTime().compareTo(o2.getEventTime());
		}
	}
}
