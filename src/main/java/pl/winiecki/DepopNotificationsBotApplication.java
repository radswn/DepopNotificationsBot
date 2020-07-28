package pl.winiecki;

import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.send.MessengerSendClient;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import pl.winiecki.domain.WebManager;

@SpringBootApplication
public class DepopNotificationsBotApplication {

	private static final Logger logger = LoggerFactory.getLogger(DepopNotificationsBotApplication.class);

	@Bean
	public MessengerSendClient messengerSendClient(@Value("${messenger4j.pageAccessToken}") String pageAccessToken) {
		logger.debug("Initializing MessengerSendClient - pageAccessToken: {}", pageAccessToken);
		return MessengerPlatform.newSendClientBuilder(pageAccessToken).build();
	}

	@Bean
	public WebDriver getDriver(){
		return new HtmlUnitDriver();
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(DepopNotificationsBotApplication.class, args);

		WebManager manager = ctx.getBean(WebManager.class);
		manager.assignFirstItems();

	}
}
