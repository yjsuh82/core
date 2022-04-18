package hello.core.order;

public interface OrderService {
    Order createOrder(Long memgerId, String itemName, int itemPrice);
}
