package day9;

public class PaymentTest {
    public static void main(String[] args) {
        Payment payment = new AlipayPayment();
        payment.pay(100);
        
        payment = new WechatPayment();
        payment.pay(200);
    }
}