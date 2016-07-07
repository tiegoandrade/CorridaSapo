package tiegoandrade.github.io.multithread;

/**
 * Essa classe apresenta cada um dos sapos que ir� correr.
 * Cada sapo � uma thread.
 * @author Tiego
 */
public class Sapo extends Thread {
	
	/*
	 * Tempo m�ximo que um sapo pode decansar antes de dar o pr�ximo pulo. 
	 */
	private static final int MAXIMO_DESCANSO = 500;
	
	/*
	 * Dist�ncia m�xima que um sapo pode alcan�ar com seu pulo.
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
	 * Dist�ncia total a ser percorrida pelo sapo. 
	 */
	private int distanciaTotal;
	
	/*
	 * Dist�ncia j� percorrida pelo sapo. 
	 */
	private int distanciaPercorrida;
	
	/*
	 * Dist�ncia que o sapo percorreu no �ltimo pulo. 
	 */
	private int ultimoPulo;
	
	/*
	 * Coloca��o dos sapos. 
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
		 * Esse c�digo ser� executado por cada thread (sapo).
		 * Cada sapo realizar� tr�s a��es: pular, 
		 * avisar seu estado atual na corrida, e descansar.
		 *  Este c�digo ser� executado at� que o sapo percorra todo o caminho.
		 */
		while (distanciaPercorrida < distanciaTotal) {
			pular();
			avisarSituacao();
			descansar();
		}
		
		// Depois que o sapo atingiu a linha de chegada, a sua coloca��o deve ser gravada.
		cruzarLinhaDeChegada();
	}
	
	/**
	 * Faz o sapo pular. 
	 */
	private void pular() {
		
		// A dist�ncia do pulo um n�mero rand�mico entre 0 e "DISTANCIA_MAXIMA".
		ultimoPulo = (int) (Math.random() * DISTANCIA_MAXIMA);
		
		// Incrementa a dist�ncia percorrida com a dist�ncia do �ltimo pulo.
		distanciaPercorrida += ultimoPulo;
		
		// Caso a dist�ncia percorrida ultrapasse a dist�ncia total, realiza-se um truncamento
		if (distanciaPercorrida > distanciaTotal) {
			distanciaPercorrida = distanciaTotal;
		}
	}
	
	/**
	 * Faz o sapo descansar.
	 */
	private void descansar() {
		
		// O tempo de descanso � um n�mero rand�mico entre 0 e MAXIMO_DESCANSO.
		int tempo = (int) (Math.random() * MAXIMO_DESCANSO);
		
		try {
			
			// Faz o sapo descansar por um determinado per�odo de tempo.
			Thread.sleep(tempo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Mostra a situa��o atual do sapo na corrida
	 */
	private void avisarSituacao() {
		System.out.println(nome + " pulou " + ultimoPulo + " cm. A dist�ncia percorrida foi de " + distanciaPercorrida + " cm");
	}
	
	/**
	 * Marca a coloca��o dos sapos.
	 * O acesso a esse m�todo deve ser sincronizado, pois pode haver dois sapos 
	 * cruzando a linha de chegada ao mesmo tempo.
	 */
	private void cruzarLinhaDeChegada() {
		synchronized(monitor) {
			colocacao++;
			System.out.println("==> " + nome + " cruzou a linha de chegada em " + colocacao + "� lugar!");
		}
	}
}
