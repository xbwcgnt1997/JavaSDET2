package yujie_selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;


/**
 * PageObject模式原则解读
 * ⽅法意义:
 * ⽤公共⽅法代表UI所提供的功能
 * ⽅法应该返回其他的PageObject或者返回⽤于断⾔的数据
 * 同样的⾏为不同的结果可以建模为不同的⽅法
 * 不要在⽅法内加断⾔
 * 字段意义:
 * 不要暴露页⾯内部的元素给外部
 * 不需要建模UI内的所有元素
 **/

public class AppPage extends BasePage {

    public AppPage loginWithCookie() {
        driver = new ChromeDriver();

        //增加了隐式等待，否则之后添加成员找用户名等元素时会因元素还没来得及加载而报错：no such element
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //利用cookie登陆企业微信
        driver.get("https://work.weixin.qq.com/");
        driver.manage().window().maximize();
        findElement(By.linkText("企业登录")).click();
        driver.manage().addCookie(new Cookie("wwrtx.refid", "34265662561168939"));
        driver.manage().addCookie(new Cookie("wwrtx.sid", "qxOsjgVHRI7YcR-qLt4aoT3nvnhHyoejLcrORVe7M3u3PTcfAzmHEmmZSPtRpK_i"));
        driver.navigate().refresh();
        return this;
    }

    //⽤公共⽅法代表UI所提供的功能
    //⽅法应该返回其他的PageObject或者返回⽤于断⾔的数据
    public ContactPage toAddMember() {
        /**下面这行代码一定要注意，这是我排错拍了好久才加的代码
         * 跑测试用例类的所有用例时（先添加用户用例，紧接着继续添加用户并删除用户用例），
         * 如果不加这一行，在添加完成用户之后，继续添加用户的时候就报错，无法等到username元素出现，事实上也的确不会出现，
         * 因为点击添加用户那一步就没成功。判断的原因是添加用户元素没有点击成功。
         * 但是很诡异的是，测试用例再点击添加元素的时候也没报错，无语。这个错我真的是排查了好久，崩溃边缘。
         * */
        waitClickable(By.linkText("添加成员"), 5);
        findElement(By.linkText("添加成员")).click();
        return new ContactPage();
    }

    public ContactPage toContact() {
        findElement(By.linkText("通讯录")).click();
        return new ContactPage();
    }

}
