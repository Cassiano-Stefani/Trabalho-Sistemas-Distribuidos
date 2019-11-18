package trabalho;

import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import utilidade.CEP;
import utilidade.CustomSocket;
import utilidade.CustomSocket.TipoMensagem;
import utilidade.EnderecoVirtual;
import utilidade.Mensagem;
import utilidade.Pesquisa;
import utilidade.Usuario;

public class BancoDeDados {

	public static EnderecoVirtual endBanco = new EnderecoVirtual("localhost", 5111);
	public static ServerSocket socketBanco;
	public static CustomSocket socketBuscaCEP;

	public static void main(String[] args) {
		System.out.println("Abrindo conexao com postgres");
		carregarDriverPostgres();
		Connection cnx = abrirConexaoPostgres();

		try {
			System.out.println("Abrindo servidor...");
			socketBanco = new ServerSocket(endBanco.porta);

			System.out.println("Esperando BuscaCEP");
			socketBuscaCEP = new CustomSocket(socketBanco.accept());
			System.out.println("BuscaCEP conectado");

			while (true) {
				Mensagem m = socketBuscaCEP.receberMensagem();
				if (m.tipo == TipoMensagem.PESQUISA.ordinal()) {
					Pesquisa pesquisa = socketBuscaCEP.parsePesquisa(m);

					try {
						System.out.println("Salvando pesquisa no banco de dados...");
						int codUser = 0;
						Statement statement = cnx.createStatement();
						ResultSet users = statement
								.executeQuery("SELECT cod FROM USERS WHERE codapi = '" + pesquisa.getUsuario().getId()
										+ "' AND api = '" + pesquisa.getUsuario().getApiUsada() + "';");
						if (users.next()) {
							codUser = users.getInt("cod");
							users.close();
						} else {
							users.close();
							System.out.println("Usuario nao cadastrado no Banco, realizando cadastro...");
							Usuario u = pesquisa.getUsuario();
							int afetad = statement.executeUpdate(
									"INSERT INTO Users (codapi, api, nome, image) VALUES ('" + u.getId() + "','"
											+ u.getApiUsada() + "','" + u.getNome() + "','" + u.getImagem() + "');");
							if (afetad == 0) {
								System.out.println("ERRO: Usuario nao cadastro corretamente");
							} else {
								System.out.println("Usuario cadastrado no Banco com sucesso!");
								users = statement.executeQuery(
										"SELECT cod FROM USERS WHERE codapi = '" + pesquisa.getUsuario().getId()
												+ "' AND api = '" + pesquisa.getUsuario().getApiUsada() + "';");
								if (users.next()) {
									codUser = users.getInt("cod");
								}
								users.close();
							}
						}

						CEP c1 = pesquisa.getOrigem();
						CEP c2 = pesquisa.getDestino();
						int rows = statement.executeUpdate(
								"INSERT INTO Pesquisa (user_cod,cep_1,rua_1,cidade_1,estado_1,cep_2,rua_2,cidade_2,estado_2) VALUES ("
										+ codUser + ",'" + c1.getCep().replace("-", "") + "','" + c1.getLogradouro()
										+ "','" + c1.getLocalidade() + "','" + c1.getUf() + "','"
										+ c2.getCep().replace("-", "") + "','" + c2.getLogradouro() + "','"
										+ c2.getLocalidade() + "','" + c2.getUf() + "');");

						if (rows != 0)
							System.out.println("Pesquisa salva com sucesso!");
						else
							System.out.println("ERRO ao salvar pesquisa no Banco");

						statement.close();
					} catch (Exception e) {
						System.out.println("Erro ao tentar salvar pesquisa no banco de dados");
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Erro na conexao. Terminando aplicação!");
			try {
				cnx.close();
			} catch (Exception e1) {
				System.out.println("Erro ao fechar conexao com postgres. Terminando aplicação!");
			}
		}
	}

	private static void carregarDriverPostgres() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			System.out.println("Erro ao carregar driver do postgresSQL");
			System.exit(1);
		}
	}

	private static Connection abrirConexaoPostgres() {
		try {

			String url = "jdbc:postgresql://localhost:5432/postgres";
			String usuario = "postgres";
			String senha = "password";

			return DriverManager.getConnection(url, usuario, senha);

		} catch (Exception e) {
			System.out.println("Erro ao abrir conexao com postgres");
			System.exit(2);
		}

		return null;
	}
}
