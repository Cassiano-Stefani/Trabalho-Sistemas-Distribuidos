package buscacep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.ServerSocket;
import java.net.Socket;

import utilidade.CustomSocket;
import utilidade.EnderecoVirtual;

public class BuscaCEP {

	private static EnderecoVirtual endBuscaCEP = new EnderecoVirtual("localhost", 5110);
	private static ServerSocket socketServidorBuscaCEP;
	
	private static EnderecoVirtual endBancoDeDados = new EnderecoVirtual("localhost", 5111);
	private static CustomSocket socketBancoDeDados;

	private static CustomSocket socketFrontEnd;

	public static void main(String[] args) {

	}
}
