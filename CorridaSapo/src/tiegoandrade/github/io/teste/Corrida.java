package tiegoandrade.github.io.teste;

import tiegoandrade.github.io.multithread.Sapo;

/**
 * Classe que representa a corrida.
 * @author Tiego
 *
 */
public class Corrida {
	
	/**
	 * Quantidade de sapos que participarão da corrida.
	 */
	private static final int QTDE_SAPOS = 5;
	
	/**
	 * Distância total a ser percorrida por cada sapo
	 */
	private static final int DISTANCIA_TOTAL = 500;
	
	public static void main(String[] args) {
		
		// Cria um array de sapos.
		Sapo[] sapos = new Sapo[QTDE_SAPOS];
		
		// Instancia e inicia cada sapo.
		for (int i = 0; i <sapos.length; i++) {
			sapos[i] = new Sapo("Sapo_" + (i+1), DISTANCIA_TOTAL);
			sapos[i].start();
		}
	}
}
