
package fr.matis.bddtr.simulator.client;

import java.util.Random;

public class PoissonUtil {
	private PoissonUtil(){
	}
	
	public static int nextEvent(double lambda){
		Random r = new Random();
		double L = Math.exp(-lambda);
		int k = 1;
		double p = 1.0;
		do{
			p = p * r.nextDouble();
			k++;
		}while (p > L);
		return k - 1;
	}
}
