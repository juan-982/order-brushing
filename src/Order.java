import java.time.LocalDateTime;

public class Order {
	private String orderid;
	private String shopid;
	private String userid;
	private LocalDateTime eventTime;
	private Order previousOrder;
	private Order nextOrder;
	
	public Order(String orderid, String shopid, String userid, LocalDateTime eventTime) {
		this.setOrderid(orderid);
		this.setShopid(shopid);
		this.setUserid(userid);
		this.setEventTime(eventTime);
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}

	public LocalDateTime getEventTime() {
		return eventTime;
	}

	public void setEventTime(LocalDateTime eventTime) {
		this.eventTime = eventTime;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
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
