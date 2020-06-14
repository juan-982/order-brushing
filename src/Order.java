import java.time.LocalDateTime;

public class Order {
	private String orderid;
	private Shop shop;
	private long userid;
	private LocalDateTime eventTime;
	private Order previousOrder;
	private Order nextOrder;
	
	public Order(String orderid, Shop shop, long userid, LocalDateTime eventTime) {
		this.setOrderid(orderid);
		this.setShop(shop);
		this.setUserid(userid);
		this.setEventTime(eventTime);
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public LocalDateTime getEventTime() {
		return eventTime;
	}

	public void setEventTime(LocalDateTime eventTime) {
		this.eventTime = eventTime;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}
	
	public boolean isWithinSameHour(Order order) {
		LocalDateTime lowerLimit = eventTime.minusMinutes(60);
		LocalDateTime upperLimit = eventTime.plusMinutes(60);
		return order.getEventTime().isEqual(lowerLimit) || 
				order.getEventTime().isEqual(upperLimit) ||
				order.getEventTime().isBefore(upperLimit) && order.getEventTime().isAfter(lowerLimit);
	}

	public Order getPreviousOrder() {
		return previousOrder;
	}

	public void setPreviousOrder(Order previousOrder) {
		this.previousOrder = previousOrder;
	}

	public Order getNextOrder() {
		return nextOrder;
	}

	public void setNextOrder(Order nextOrder) {
		this.nextOrder = nextOrder;
	}
}
