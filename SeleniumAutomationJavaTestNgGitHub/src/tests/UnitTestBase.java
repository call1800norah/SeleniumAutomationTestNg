package tests;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.BeforeTest;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class UnitTestBase {

	 public WebDriver driver ; 
	    
  @BeforeTest
  public void Initialize() {
     //setting the driver executable
	 System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");

	//Initiating your chromedriver
	 driver = new ChromeDriver();
		
	  //Applied wait time
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  
	  //maximize window
	  driver.manage().window().maximize();
	  
	  //open browser with desried URL
	  driver.get("http://automationpractice.com/index.php");
	  String expectedTitle = "My Store";
      String actualTitle = "";
      
      //get the actual value of the title
      actualTitle = driver.getTitle();
      if (actualTitle.contentEquals(expectedTitle))
      {
          System.out.println("Open Website Test Passed!");
      } else
      {
          System.out.println("Open Website Test Failed");
      }      
  }
  
  @AfterTest
  public void TestClean()
  {
      driver.quit();
  }
  
  //Wait for IWebElement to be displayed using WebDriverWait
  public void WaitForDisplayed(WebElement element)
  {
  	Assert.assertNotNull(element, "The element return as null.");
	
	    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			  .withTimeout(Duration.ofSeconds(30))
			  .pollingEvery(Duration.ofSeconds(5))
			  .ignoring(NoSuchElementException.class);
	
	    ExpectedCondition<Boolean> elementIsDisplayed = arg0 -> element.isDisplayed();
	    wait.until(elementIsDisplayed);
	    if(element.isDisplayed()==false){
	    	System.out.print("WaitForDisplayed timed out in 30seconds");
      }   	    	
  }
  
  //Wait for IWebElement to be enabled using WebDriverWait
  public void WaitForEnabled(WebElement element)
  {
	  Assert.assertNotNull(element, "The element return as null.");
	
	    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			  .withTimeout(Duration.ofSeconds(30))
			  .pollingEvery(Duration.ofSeconds(5))
			  .ignoring(NoSuchElementException.class);
	
	    ExpectedCondition<Boolean> elementIsDisplayed = arg0 -> element.isEnabled();
	    wait.until(elementIsDisplayed);
	    if(element.isDisplayed()==false){
	    	System.out.print("WaitForEnabled timed out in 30seconds");
      }   	    	
  }

  
//Verify a list of elements are all displayed.
  public void ElementListDisplayed(List<WebElement> elements)
  {  	
	 for(WebElement e:elements) {
			WaitForDisplayed(e);
	 Assert.assertTrue(e.isDisplayed());
     }
  }
  
  //Verify a array of elements are all displayed.
  public void ElementArrayDisplayed(WebElement[] elements)
  {  	
	 for(WebElement e:elements) {
			WaitForDisplayed(e);
	 Assert.assertTrue(e.isDisplayed());
     }
  }
  
  
  //wait for the element to be clickable. Retry maxTries times. 
  public void ClickWhenAble(WebElement element, long maxTries) {
  	WaitForEnabled(element);
  	 for (int retryCount = 1; retryCount <= maxTries; retryCount++) {
  	{
  		try {
  			element.click();
  			break;
  			
  		}catch(WebDriverException e)
  		{
  			e.printStackTrace();
  		}
  		if(retryCount > maxTries)
  		{
  			System.out.println("Click reached maximum tries of" + maxTries);
  		}
  	}
  
  }
}
  
  public void IsEachElementDisplayed(List<WebElement> elements)
  {
  	
  	elements.stream()
  		.filter((e)->e.isDisplayed())
  		
  		.forEach((e)-> System.out.println(e));

  }
}
