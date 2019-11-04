package buscacep;

import java.util.Scanner;

public class BuscaCEP {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		while (true) {
			CEPCompleto cep = CEPUtils.buscarCEP(s.nextLine());

			if (cep.erro == null) {
				System.out.println(cep);
			} else {
				System.out.println(cep.erro);
			}
		}
	}

}
