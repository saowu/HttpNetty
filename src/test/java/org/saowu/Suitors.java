package org.saowu;

/**
 * @description: 追求者（送礼物者）
 * @author: wuyanbo
 * @create: 2019-04-12 14:06
 **/

public class Suitors {

    private String name;
    //cglib增强类使用无参构造器
    public Suitors() {
        this.name = "tom";
    }

    public void giveFlowers() {
        System.out.println(name + "送鲜花");
    }


    public void call() {
        System.out.println("打电话");
    }


}
