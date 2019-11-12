package trabalho;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class UsuariosLogados {
	private static Map<Usuario, HttpSession> logadosGoogle = new HashMap<>();

	public static void logarUsuario(Usuario usuario, HttpSession sessao) {
		if (usuario.getApiUsada().equals("google")) {
			logadosGoogle.put(usuario, sessao);
		}
	}

	public static Usuario getUsuarioBySessao(HttpSession sessao) {
		
		for (Usuario u : logadosGoogle.keySet()) {
			if(logadosGoogle.get(u) != null && logadosGoogle.get(u).equals(sessao))
				return u;
		}
		// TODO fazer o mesmo para outras apis

		return null; // usuario nao esta logado
	}

	public static void deslogarUsuario(Usuario usuario) {
		if (usuario.getApiUsada().equals("google")) {
			logadosGoogle.put(usuario, null);
		}
	}
}
