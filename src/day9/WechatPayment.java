package day9;

public class WechatPayment implements Payment {

    @Override
    public void pay(double money) {
        System.out.println("微信支付：" + money + "元");
    }
}