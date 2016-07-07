package tiegoandrade.github.io.multithread;

/**
 * Essa classe apresenta cada um dos sapos que irá correr.
 * Cada sapo é uma thread.
 * @author Tiego
 */
public class Sapo extends Thread {
	
	/*
	 * Tempo máximo que um sapo pode decansar antes de dar o próximo pulo. 
	 */
	private static final int MAXIMO_DESCANSO = 500;
	
	/*
	 * Distância máxima que um sapo pode alcançar com seu pulo.
	 */
	private static final int DISTANCIA_MAXIMA = 50;
	
	/*
	 * Monitor usado para sincronizar o acesso
	 * ao atributo "colocacao".
	 */
	private static Object monitor = new Object();
	
	/*
	 * Nome do sapo.
	 */
	private String nome;
	
	/*
	 * Distância total a ser percorrida pelo sapo. 
	 */
	private int distanciaTotal;
	
	/*
	 * Distância já percorrida pelo sapo. 
	 */
	private int distanciaPercorrida;
	
	/*
	 * Distância que o sapo percorreu no último pulo. 
	 */
	private int ultimoPulo;
	
	/*
	 * Colocação dos sapos. 
	 */
	private static int colocacao;
	
	/**
	 * Construtor
	 * @param nome
	 * @param distanciaTotal
	 */
	public Sapo(String nome, int distanciaTotal) {
		this.nome = nome;
		this.distanciaTotal = distanciaTotal;
	}
	
	/**
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		
		/*
		 * Esse código será executado por cada thread (sapo).
		 * Cada sapo realizará três ações: pular, 
		 * avisar seu estado atual na corrida, e descansar.
		 *  Este código será executado até que o sapo percorra todo o caminho.
		 */
		while (distanciaPercorrida < distanciaTotal) {
			pular();
			avisarSituacao();
			descansar();
		}
		
		// Depois que o sapo atingiu a linha de chegada, a sua colocação deve ser gravada.
		cruzarLinhaDeChegada();
	}
	
	/**
	 * Faz o sapo pular. 
	 */
	private void pular() {
		
		// A distância do pulo um número randômico entre 0 e "DISTANCIA_MAXIMA".
		ultimoPulo = (int) (Math.random() * DISTANCIA_MAXIMA);
		
		// Incrementa a distância percorrida com a distância do último pulo.
		distanciaPercorrida += ultimoPulo;
		
		// Caso a distância percorrida ultrapasse a distância total, realiza-se um truncamento
		if (distanciaPercorrida > distanciaTotal) {
			distanciaPercorrida = distanciaTotal;
		}
	}
	
	/**
	 * Faz o sapo descansar.
	 */
	private void descansar() {
		
		// O tempo de descanso é um número randômico entre 0 e MAXIMO_DESCANSO.
		int tempo = (int) (Math.random() * MAXIMO_DESCANSO);
		
		try {
			
			// Faz o sapo descansar por um determinado período de tempo.
			Thread.sleep(tempo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Mostra a situação atual do sapo na corrida
	 */
	private void avisarSituacao() {
		System.out.println(nome + " pulou " + ultimoPulo + " cm. A distância percorrida foi de " + distanciaPercorrida + " cm");
	}
	
	/**
	 * Marca a colocação dos sapos.
	 * O acesso a esse método deve ser sincronizado, pois pode haver dois sapos 
	 * cruzando a linha de chegada ao mesmo tempo.
	 */
	private void cruzarLinhaDeChegada() {
		synchronized(monitor) {
			colocacao++;
			System.out.println("==> " + nome + " cruzou a linha de chegada em " + colocacao + "° lugar!");
		}
	}
}
