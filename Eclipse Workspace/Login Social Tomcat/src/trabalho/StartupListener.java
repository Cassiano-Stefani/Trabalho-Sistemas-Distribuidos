package trabalho;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartupListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent e) {
		ConexaoBuscaCEP.getInstancia(); // cria a instancia quando o servidor starta
	}
}
