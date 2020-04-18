package yujie_selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

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

public class ContactPage extends BasePage {

    //⽤公共⽅法代表UI所提供的功能
    //⽅法应该返回其他的PageObject或者返回⽤于断⾔的数据
    public ContactPage add(String username, String id, String phone) {
//        driver.findElement(By.name("username")).sendKeys(username);
//        driver.findElement(By.name("acctid")).sendKeys(id);
//        driver.findElement(By.name("mobile")).sendKeys(phone);
//        driver.findElement(By.linkText("保存")).click();
        waitClickable(By.name("username"), 5);
        findElement(By.name("username")).sendKeys(username);
        findElement(By.name("acctid")).sendKeys(id);
        findElement(By.name("mobile")).sendKeys(phone);
        findElement(By.linkText("保存")).click();
        return this;
    }


    public ContactPage delete(String keyWord) {

        //找到输入框，并填入搜索内容,界面上显示用户就是我搜索的用户
        findElement(By.id("memberSearchInput")).sendKeys(keyWord);

        //1. 代码执行到这部分报错了，因为找不到确认这个元素，判断是这个元素是动态加载的，这里需要增加显式等待或隐式等待元素可以被点击
//        findElement(By.linkText("删除")).click();
//        findElement(By.linkText("确认")).click();

        //2. 代码改到这里用例可以执行成功了，这时候需要优化一下代码结构，把显式等待的代码提取到BasePage中作为一个通用方法供所有PO复用，简化代码
//        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.linkText("删除")));
//        findElement(By.linkText("删除")).click();
//        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.linkText("确认")));
//        findElement(By.linkText("确认")).click();
//
//        /**下面这行代码一定要注意，这是我排错拍了好久才加的代码
//         * 如果不加这行代码，只执行
//         * waitClickable(By.linkText("删除"), 5);
//         * findElement(By.linkText("删除")).click();
//         * findElement(By.linkText("确认")).click();
//         * 界面会报错："未选中任何成员",并且用例会报错确认元素等待超时。这是为什么呢，因为在搜索框输入了搜索内容之后，
//         * 要搜出来的那个用户还没来得及在页面中加载出来，但是删除按钮已经可以被点击了，
//         * 所以用例就不会等待搜索的用户加载出来而直接点击删除用户，
//         * 所以点击删除按钮之后，界面上报错未选中任何用户，并且永远等不到确认按钮的出现。
//         * */
//        waitClickable(By.linkText("邀请加入"), 5);

        //对要删除的用户不存在的情况做处理，删除搜索框内容并返回
        try {
            waitClickable(By.linkText("邀请加入"), 5);
        } catch (Exception e) {
            //细节，找到输入框，清空输入框的内容，确保能够让下一步输入正确的搜索内容
            findElement(By.id("memberSearchInput")).clear();
            return this;
        }

        waitClickable(By.linkText("删除"), 5);
        findElement(By.linkText("删除")).click();
        findElement(By.linkText("确认")).click();

        //删除之后系统不会自动回到成员列表中，这里一定要控制方法执行完成之后回到目标页面中，需要注意控制好页面切换的顺序
        findElement(By.id("clearMemberSearchInput")).click();
        return this;
    }

    public ContactPage deleteCurrentPageUsers() {

        //老师看了selenium教程，讲了Selector,它有各种方法，但是老师那边试验下来对checkbox不适用。
        // Select和checkbox内容不同，checkbox是特殊的Selector。

        //在点击元素之前适当的显示等待一下，能避免很多问题
        waitClickable(By.cssSelector(".ww_checkbox"), 5);
        //1. 选中第一个checkbox，即删除全部的那个checkbox。删除时需要慎重操作的，尽量避免全部删除。
        findElement(By.cssSelector(".ww_checkbox")).click();
        waitClickable(By.linkText("删除"), 5);
        findElement(By.linkText("删除")).click();
        findElement(By.linkText("确认")).click();

//        waitClickable(By.cssSelector(".ww_checkbox"), 20);
//        //2. 当前页全部用户的另一种实现，不选中删除全部的那个checkbox，而是一个一个选中要删除的用户对应的checkbox。
//        List<WebElement> elements = driver.findElements(By.cssSelector(".ww_checkbox"));
//        for (int i = 1; i < elements.size(); i++) {
//            elements.get(i).click();
//        }
//        waitClickable(By.linkText("删除"), 5);
//        findElement(By.linkText("删除")).click();
//        findElement(By.linkText("确认")).click();


        return this;
    }

    public ContactPage importMemberFromFile() {
        findElement(By.linkText("批量导入/导出")).click();
        findElement(By.linkText("文件导入")).click();

        // 因为findElement()已经被改造了，方法中要等到元素可以被点击，才能继续findElement().
        // 但是元素By.id("js_upload_file_input")并不是可以被点击的元素，他只是input，这时就需要修改代码
        //findElement(By.id("js_upload_file_input")).sendKeys("/Users/yujie.sun/Downloads/通讯录批量导入模板.xlsx");

        findElement(By.id("js_upload_file_input"), 0).sendKeys("/Users/yujie.sun/Downloads/通讯录批量导入模板.xlsx");

        waitClickable(By.linkText("确认导入"), 5);
        findElement(By.linkText("确认导入")).click();
        waitClickable(By.linkText("前往查看"), 5);
        findElement(By.linkText("前往查看")).click();
        return this;
    }

    public void list() {

    }
}
