package buscacep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BuscaCEP {

	private static String ipBancoDeDados = "localhost";
	private static int portaBancoDeDados = 5111;

	private static int porta = 5110;
	private static ServerSocket socketServidor;

	private static Socket socketBancoDeDados;
	private static BufferedReader inBancoDeDados;
	private static BufferedWriter outBancoDeDados;

	private static Socket socketFrontEnd;
	private static BufferedReader inFrontEnd;
	private static BufferedWriter outFrontEnd;

	public static void main(String[] args) {
		inicializarSockets();
		inicializarFluxos();
	}

	public static void inicializarSockets() {
		try {
			socketServidor = new ServerSocket(porta);
		} catch (Exception e) {
			// TODO
		}

		try {
			socketFrontEnd = socketServidor.accept();
		} catch (Exception e) {
			// TODO
		}

		try {
			socketBancoDeDados = new Socket(InetAddress.getByName(ipBancoDeDados), portaBancoDeDados);
		} catch (Exception e) {
			// TODO
		}
	}

	public static void inicializarFluxos() {
		try {
			inBancoDeDados = new BufferedReader(new InputStreamReader(socketBancoDeDados.getInputStream()));
			outBancoDeDados = new BufferedWriter(new OutputStreamWriter(socketBancoDeDados.getOutputStream()));
		} catch (Exception e) {
			// TODO
		}
		
		try {
			inFrontEnd = new BufferedReader(new InputStreamReader(socketFrontEnd.getInputStream()));
			outFrontEnd = new BufferedWriter(new OutputStreamWriter(socketFrontEnd.getOutputStream()));
		} catch (Exception e) {
			// TODO
		}
	}

}
