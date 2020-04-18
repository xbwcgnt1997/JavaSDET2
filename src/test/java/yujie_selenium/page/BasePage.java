package yujie_selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected static WebDriver driver;

    //因为在finfElement之前经常需要显式等待，所以把显式等待和findElement结合起来组成一个方法，简化代码。
    public WebElement findElement(By by) {
        waitClickable(by);
        return driver.findElement(by);
    }

    //对于有一些不能被点击的Element新增一个findElement重载方法，
    // 增加参数超时时间，如果超时时间设置为小于零说明不用显式等待该元素可以被点击（
    // 其实我觉得这里可以优化，比如增加findElementClickable，和findElement这种）。
    public WebElement findElement(By by, long timeout) {
        if (timeout > 0) {
            waitClickable(by, timeout);
        }
        return driver.findElement(by);
    }

    public void waitClickable(By by, long timeout) {
        /**增加下面一行代码是因为有可能会发生元素页面上看不到但可以点击的情况
         *
         * 页面加载顺序是 presence -> visible -> clickable
         *
         * */
        new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(by));
        new WebDriverWait(driver, timeout).until(ExpectedConditions.elementToBeClickable(by));
    }

    public void waitClickable(By by) {
        waitClickable(by, 5);
    }

    //close webDriver and chrome
    public void quit() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }

}
