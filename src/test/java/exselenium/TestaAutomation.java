package exselenium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

public class TestaAutomation {

    protected WebDriver driver;
    protected WebDriverWait espera;

    // passos 1 e 2: abre o navegador e acessa o site antes de cada teste
    @BeforeEach
    public void createDriver() {
        // bloqueia os anúncios do Google: o site abre anúncios de tela cheia
        // ficam por cima da página e atrapalham os cliques do teste
        ChromeOptions opcoes = new ChromeOptions();
        opcoes.addArguments("--host-resolver-rules=MAP *.googlesyndication.com ~NOTFOUND, MAP *.doubleclick.net ~NOTFOUND");

        driver = new ChromeDriver(opcoes);
        espera = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("http://automationexercise.com");
    }

    @Test
    public void testLoginEmailESenhaIncorretos() {
        
        // passo 3: confere se a página inicial carregou
        assertEquals("Automation Exercise", driver.getTitle());
        WebElement logo = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[alt='Website for automation practice']")));
        assertTrue(logo.isDisplayed(), "A página inicial não está visível!");

        // passo 4: clica no botão Signup/Login
        driver.findElement(By.cssSelector("a[href='/login']")).click();

        // passo 5: confere se Login to your account aparece
        WebElement tituloLogin = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.login-form h2")));
        assertEquals("Login to your account", tituloLogin.getText());

        // passo 6: preenche e-mail e senha incorretos
        driver.findElement(By.cssSelector("input[data-qa='login-email']"))
                .sendKeys("naocadastrado_" + System.currentTimeMillis() + "@teste.com");
        driver.findElement(By.cssSelector("input[data-qa='login-password']")).sendKeys("senhaErrada123");

        // passo 7: clica no botão 'Login'
        driver.findElement(By.cssSelector("button[data-qa='login-button']")).click();

        // passo 8: confere se a mensagem de erro aparece
        WebElement erro = espera.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.login-form p")));
        assertEquals("Your email or password is incorrect!", erro.getText());
    }

    @Test
    public void testRegistrarUsuario() {
        String nome = "Maria Silva";
        String email = "maria_" + System.currentTimeMillis() + "@teste.com";

        // passo 3: confere se a página inicial carregou
        assertEquals("Automation Exercise", driver.getTitle());
        WebElement logo = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[alt='Website for automation practice']")));
        assertTrue(logo.isDisplayed(), "A página inicial não está visível!");

        // passo 4: clica no botão Signup/Login
        driver.findElement(By.cssSelector("a[href='/login']")).click();

        // passo 5: confere se New User Signup! aparece
        WebElement tituloCadastro = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.signup-form h2")));
        assertEquals("New User Signup!", tituloCadastro.getText());

        // passo 6: preenche nome e e-mail
        driver.findElement(By.cssSelector("input[data-qa='signup-name']")).sendKeys(nome);
        driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys(email);

        // passo 7: clica no botão Signup
        driver.findElement(By.cssSelector("button[data-qa='signup-button']")).click();

        // passo 8: confere se ENTER ACCOUNT INFORMATION aparece
        WebElement tituloConta = espera.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Enter Account Information']")));
        assertEquals("ENTER ACCOUNT INFORMATION", tituloConta.getText());

        // passo 9: preenche título, nome, e-mail, senha e data de nascimento
        driver.findElement(By.cssSelector("input[name='title'][value='Mrs']")).click();
        // nome e e-mail já vêm preenchidos com o que foi digitado no passo 6
        assertEquals(nome, driver.findElement(By.cssSelector("input[data-qa='name']")).getDomProperty("value"));
        assertEquals(email, driver.findElement(By.cssSelector("input[data-qa='email']")).getDomProperty("value"));
        driver.findElement(By.cssSelector("input[data-qa='password']")).sendKeys("senha123");
        new Select(driver.findElement(By.cssSelector("select[data-qa='days']"))).selectByVisibleText("15");
        new Select(driver.findElement(By.cssSelector("select[data-qa='months']"))).selectByVisibleText("June");
        new Select(driver.findElement(By.cssSelector("select[data-qa='years']"))).selectByVisibleText("2000");

        // passo 10: marca Sign up for our newsletter!
        driver.findElement(By.cssSelector("label[for='newsletter']")).click();
        assertTrue(driver.findElement(By.name("newsletter")).isSelected(), "A caixa da newsletter não foi marcada!");

        // passo 11: marca Receive special offers from our partners!
        driver.findElement(By.cssSelector("label[for='optin']")).click();
        assertTrue(driver.findElement(By.name("optin")).isSelected(), "A caixa de ofertas não foi marcada!");

        // passo 12: preenche os dados de endereço
        driver.findElement(By.cssSelector("input[data-qa='first_name']")).sendKeys("Maria");
        driver.findElement(By.cssSelector("input[data-qa='last_name']")).sendKeys("Silva");
        driver.findElement(By.cssSelector("input[data-qa='company']")).sendKeys("Empresa Teste");
        driver.findElement(By.cssSelector("input[data-qa='address']")).sendKeys("100 King Street West");
        driver.findElement(By.cssSelector("input[data-qa='address2']")).sendKeys("Suite 200");
        new Select(driver.findElement(By.cssSelector("select[data-qa='country']"))).selectByVisibleText("Canada");
        driver.findElement(By.cssSelector("input[data-qa='state']")).sendKeys("Ontario");
        driver.findElement(By.cssSelector("input[data-qa='city']")).sendKeys("Toronto");
        driver.findElement(By.cssSelector("input[data-qa='zipcode']")).sendKeys("M5H 1J9");
        driver.findElement(By.cssSelector("input[data-qa='mobile_number']")).sendKeys("4165550100");

        // passo 13: clica em Create Account
        driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();

        // passo 14: confere se ACCOUNT CREATED! aparece
        WebElement contaCriada = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2[data-qa='account-created']")));
        assertEquals("ACCOUNT CREATED!", contaCriada.getText());

        // passo 15: clica em Continue
        driver.findElement(By.cssSelector("a[data-qa='continue-button']")).click();

        // passo 16: confere se Logged in as <nome> aparece
        WebElement usuarioLogado = espera.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Logged in as")));
        assertEquals("Logged in as " + nome, usuarioLogado.getText().trim());

        // passo 17: clica em Delete Account
        driver.findElement(By.cssSelector("a[href='/delete_account']")).click();

        // passo 18: confere se ACCOUNT DELETED! aparece e clica em Continue
        WebElement contaExcluida = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2[data-qa='account-deleted']")));
        assertEquals("ACCOUNT DELETED!", contaExcluida.getText());
        driver.findElement(By.cssSelector("a[data-qa='continue-button']")).click();
    }

    @AfterEach
    public void quitDriver() {
        driver.quit();
    }
}