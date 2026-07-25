package day9;

public class AlipayPayment implements Payment{
    @Override
    public void pay(double money){
        System.out.println("支付宝支付： " + money + "元");
    }
}
