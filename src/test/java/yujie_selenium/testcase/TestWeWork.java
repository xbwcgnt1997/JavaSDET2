package yujie_selenium.testcase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import yujie_selenium.page.AppPage;

public class TestWeWork {

//    @Test
//    public void testLogin() {
//        WebDriver driver = new ChromeDriver();
//        driver.get("https://work.weixin.qq.com/");
//        driver.manage().window().maximize();
//        driver.findElement(By.linkText("企业登录")).click();
//
//        // System.out.println(driver.manage().getCookies());
//        driver.manage().addCookie(new Cookie("wwrtx.refid", "3426566256280940"));
//        driver.manage().addCookie(new Cookie("wwrtx.sid", "qxOsjgVHRI7YcR-qLt4aobEsxbCiWQWT-cLHqYZy0BO6Rh8xFWsiZkwqOp8N008q"));
//
//        driver.navigate().refresh();
//    }

    private static AppPage app;

    @BeforeClass
    public static void beforeAll() {
        /** 跑整个用例之前先执行一次登陆 */
        app = new AppPage().loginWithCookie();

        String user = "13572515078";
        app.toContact().delete(user);
        user = "13572515079";
        app.toContact().delete(user);
    }

    @AfterClass
    public static void afterAll() throws InterruptedException {
        app.quit();
    }

    @Test
    public void testAddMember() {
        /** 被移到AppPage的公共方法 loginWithCookie() */
//        WebDriver driver = new ChromeDriver();
//
//        //增加了隐式等待，否则之后添加成员找用户名等元素时会因元素还没来得及加载而报错：no such element
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//
//        //利用cookie登陆企业微信
//        driver.get("https://work.weixin.qq.com/");
//        driver.manage().window().maximize();
//        driver.findElement(By.linkText("企业登录")).click();
//        driver.manage().addCookie(new Cookie("wwrtx.refid", "3426566256280940"));
//        driver.manage().addCookie(new Cookie("wwrtx.sid", "qxOsjgVHRI7YcR-qLt4aobEsxbCiWQWT-cLHqYZy0BO6Rh8xFWsiZkwqOp8N008q"));
//        driver.navigate().refresh();

//        简单架构：
//        AppPage appPage = new AppPage();
//        appPage.toappPage()
//        assertThat

//        AppPage appPage = new AppPage();
//        appPage.loginWithCookie();
        String user = "13572515078";
        app.toAddMember().add(user, user, user);
    }

    @Test
    public void testDeleteUser() {
        String userId = "13572515079";
        app.toAddMember().add(userId, userId, userId).delete(userId);
    }

    @Test
    public void testDeleteCurrentPageUsers() {
        app.toContact().deleteCurrentPageUsers();
    }

    @Test
    public void testImportMemberFromFile(){
        app.toContact().importMemberFromFile();
    }
}
