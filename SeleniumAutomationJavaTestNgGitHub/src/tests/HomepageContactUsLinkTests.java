package tests;

import static org.testng.Assert.assertTrue;

import java.util.Random;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageFactoryPackage.HomepageContactUsLinkPO;
import pageFactoryPackage.HomepageLoadTestPO;

public class HomepageContactUsLinkTests extends UnitTestBase{
	 Random random = new Random();
	 HomepageLoadTestPO homePage;
	 HomepageContactUsLinkPO contactUsTab;
	
    //Method to verify homepage is loading
	@Test
	public void HomepageLoadTests() {

		homePage =PageFactory.initElements(driver, HomepageLoadTestPO.class);
		
		if (driver != null) {

		ElementListDisplayed(homePage.ContentItemCards);
		ElementListDisplayed(homePage.HomepageTabs);
		ElementListDisplayed(homePage.ProductImageLinks);
		ElementListDisplayed(homePage.CustomInfoBlocks);
		ElementListDisplayed(homePage.CustomInfoEachBlocks);
		}				
    }
	
	@BeforeMethod
	public void HomepateContactUsLinkTest() {
		homePage =PageFactory.initElements(driver, HomepageLoadTestPO.class);
		contactUsTab = PageFactory.initElements(driver, HomepageContactUsLinkPO.class);
		homePage.ContactUs.click();
		
	    WaitForDisplayed(contactUsTab.ContactUsContainer);
		
		WebElement[] arrayElements = {contactUsTab.HomeIcon, contactUsTab.NavigationPageContact,
				contactUsTab.CustomerServiceHeader, contactUsTab.SendAMessageHeader};
		
		ElementArrayDisplayed(arrayElements);
	}
	
	// Test for the subject heading dropdown in the Send Message form 
	@Test(priority=0)
	 public void SendAMessageSubjectHeadingDropdownTest() {
		homePage =PageFactory.initElements(driver, HomepageLoadTestPO.class);
		contactUsTab = PageFactory.initElements(driver, HomepageContactUsLinkPO.class);
		
		ClickWhenAble(contactUsTab.SubjectHeadingOptionDropdown,10);
		ElementListDisplayed(contactUsTab.SubjectHeadingOptions);
		System.out.println("Test Passed!");
		
	 }	
	
	//Test for the Email Address, Order Reference and attach file fields in the Send Message form
	@Test(priority=1)
	public void SendAMessageEmailAddressAndOrderReferenceTest() {
		homePage =PageFactory.initElements(driver, HomepageLoadTestPO.class);
		contactUsTab = PageFactory.initElements(driver, HomepageContactUsLinkPO.class);
		
		contactUsTab.SubjectHeadingRandomChoice();
		boolean email = contactUsTab.EmailAddressLabel.isDisplayed() ? contactUsTab.EmailAddressLabel.getText().contains("Email address") : false;
        assertTrue(email);
		
        WaitForDisplayed(contactUsTab.EmailInputBox);
        contactUsTab.EmailInputBox.click();
        
        String[] emailAddressList = { "test@test.com", "test@test", "test.com", "" };
        String[] messageBoxContents = { "message test", "" };
        int randomEmailAddress = random.nextInt(emailAddressList.length);
        contactUsTab.EmailInputBox.sendKeys(emailAddressList[randomEmailAddress]);
        
        boolean orderReference = contactUsTab.OrderReferenceLabel.isDisplayed() ? contactUsTab.OrderReferenceLabel.getText().contains("Order reference") : false;
        assertTrue(orderReference);
        contactUsTab.OrderReferenceInputBox.sendKeys("12345");
        
        boolean messageLabel = contactUsTab.MessageLabel.isDisplayed() ? contactUsTab.MessageLabel.getText().equals("Message") : false;
        assertTrue(messageLabel);

        int randomMessage = random.nextInt(messageBoxContents.length);
        contactUsTab.MessageBox.sendKeys(messageBoxContents[randomMessage]);
        
        String filePath = "C:/Users/norah/Source/Repos/FileUploadTest.txt";
        contactUsTab.InputFilePath.sendKeys(filePath);
        contactUsTab.SendButton.click();
        boolean isValidEmail = false;
        if (emailAddressList[randomEmailAddress].contains("@") && emailAddressList[randomEmailAddress].contains(".com"))
        {
            isValidEmail = true;
        }

        boolean isEmailValid = isValidEmail ? true : false;

        boolean isMessageBoxEmpty = messageBoxContents[randomMessage].isEmpty() ? true : false;

        if (isEmailValid && !isMessageBoxEmpty)
        {
            Assert.assertTrue(contactUsTab.AlertSuccessMessage.isDisplayed(), "Alert Success Message was not displayed.");
           

            contactUsTab.FooterLinksHome.click();
           homePage.VerifyHomepageLoadTest();
        }
        else if (!isEmailValid)
        {
            assertTrue(contactUsTab.ErrorMessage.getText().contains("Invalid email address."));

        }
        else if (isMessageBoxEmpty)
        {
            assertTrue(contactUsTab.ErrorMessage.getText().contains("The message cannot be blank."));
        }	
			
	}
}