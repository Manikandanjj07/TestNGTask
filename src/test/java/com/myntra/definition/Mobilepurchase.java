package com.myntra.definition;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.beust.jcommander.Parameter;
public class Mobilepurchase {
	public static WebDriver driver;
	
	@DataProvider(name="product name")
	public Object[][] getMobileName()
	{
		return new Object[][]{{"Realme"},{"boAt"},{"OnePlus"}};
			}
		@BeforeClass
		
public static void browserlaunch()
{
	System.out.println("Browser Launching");
	System.setProperty("webdriver.chrome.driver", "C:\\Users\\DD\\workspace\\SeleniumNew\\Driver\\chromedriver.exe");
	driver= new ChromeDriver();
	driver.get("https://www.amazon.in/");
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}
@AfterClass
public void quitBrowser()
{
	System.out.println("quit browser");
	driver.quit();
}
@BeforeMethod
public void before()
{
	System.out.println("Starting Time");
	System.out.println(java.time.LocalTime.now());
}
@AfterMethod
public void after()
{
	System.out.println("Ending Time");
	System.out.println(java.time.LocalTime.now());
}
@Parameters({"user","pass"}) 
@Test(priority=1)
public void login(String user1, String pass1) throws InterruptedException
{
	driver.findElement(By.xpath("//span[contains(text(),'Hello,')]")).click();
	driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	driver.findElement(By.id("ap_email")).sendKeys(user1 ,Keys.ENTER);
	driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	driver.findElement(By.id("ap_password")).sendKeys(pass1 ,Keys.ENTER);

	}
@Test(priority=2, dataProvider ="product name")
public void search(String dName) throws Exception
{	
	driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	WebElement findElement = driver.findElement(By.id("twotabsearchtextbox"));
	findElement.clear();
	driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	findElement.sendKeys(dName, Keys.ENTER);
	List<WebElement> products = driver.findElements(By.xpath("//h2/a/span"));
	File ip = new File("C:\\Users\\DD\\Desktop\\testng.xlsx"); 
	FileInputStream f = new FileInputStream(ip);
	Workbook wb = new XSSFWorkbook(f);
	Sheet sh = wb.createSheet(dName);
		
	for(int i =0;i<products.size(); i++)
{
			WebElement product = products.get(i);
			Row cr = sh.createRow(i);
			Cell cc = cr.createCell(0);
			cc.setCellValue(product.getText());
}
		FileOutputStream v = new FileOutputStream(ip);
		wb.write(v);
		System.out.println("Excel Writed");
		WebElement fE2 = driver.findElement(By.xpath("//h2/a/span[1]"));
		fE2.click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Set<String> cw = driver.getWindowHandles();
		 List<String> lw= new ArrayList<String>(cw);
		String text = fE2.getText();
		if(text.contains("Realme"))
		{
		driver.switchTo().window(lw.get(1));	
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		TakesScreenshot ts =(TakesScreenshot)driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File trg = new File ("C:\\Users\\DD\\workspace\\TestNGtask\\targetmocktask\\realme.png");
		FileUtils.copyFile(src, trg);
		}
		if(text.contains("boAt"))
		{
		driver.switchTo().window(lw.get(2));	
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		TakesScreenshot ts =(TakesScreenshot)driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File trg = new File ("C:\\Users\\DD\\workspace\\TestNGtask\\targetmocktask\\boAt.png");
		FileUtils.copyFile(src, trg);
		}
		if(text.contains("OnePlus"))
		{
		driver.switchTo().window(lw.get(3));	
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		TakesScreenshot ts =(TakesScreenshot)driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File trg = new File ("C:\\Users\\DD\\workspace\\TestNGtask\\targetmocktask\\OnePlus.png");
		FileUtils.copyFile(src, trg);
				}
		WebElement pName = driver.findElement(By.id("productTitle"));
	 	String pn1=pName.getText();
	 	File ip1 = new File("C:\\Users\\DD\\Desktop\\ip1.xlsx"); 
		FileInputStream f1 = new FileInputStream(ip1);
		Workbook wb1 = new XSSFWorkbook(f1);
		Sheet sheet = wb.getSheet(dName);
		Row row = sheet.getRow(0);
		Cell cell = row.getCell(0);
		String cellValue = cell.getStringCellValue();
		org.testng.Assert.assertEquals(pn1,cellValue);
		System.out.println(" ASSERT PASSED -Values Equal ");
		driver.switchTo().window(lw.get(0));	
}		}