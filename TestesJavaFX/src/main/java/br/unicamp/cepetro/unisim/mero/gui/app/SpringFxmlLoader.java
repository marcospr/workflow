package br.unicamp.cepetro.unisim.mero.gui.app;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javafx.fxml.FXMLLoader;

public class SpringFxmlLoader {
	private static final ApplicationContext applicationContext =
			new AnnotationConfigApplicationContext(SpringApplicationConfig.class);

	public Object load(final String url) {
		try (InputStream fxmlStream = SpringFxmlLoader.class.getResourceAsStream(url)) {
			FXMLLoader loader = new FXMLLoader();
			loader.setControllerFactory(clazz -> applicationContext.getBean(clazz));
			return loader.load(fxmlStream);
		} catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}
}
