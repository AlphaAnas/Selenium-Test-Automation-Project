package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LoginPage {
    
    // this is the constructor of the Page Object class
    public LoginPage(WebDriver d) {


        // Any Page Object , the call to PageFactory.initElements is required as it will start the process of locating all the WebElements
        PageFactory.initElements(d, this);
    }

    @FindBy(id="username")
   public WebElement usernameField;

    @FindBy(id="password")
    public WebElement passwordField;

    @FindBy(id="login")
    public WebElement loginButton;
}
