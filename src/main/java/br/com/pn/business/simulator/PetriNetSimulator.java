package br.com.pn.business.simulator;

import br.com.pn.business.base.Arc;
import br.com.pn.business.base.PetriNet;
import br.com.pn.business.base.Place;
import br.com.pn.business.base.Transition;
import br.com.pn.util.Constants;

/**
 * Implementa a engine de funcionamento da simulacao.
 * 
 * @author Gabriel Tavares
 *
 */
public class PetriNetSimulator implements PetriNetSimulatorInterface{

	// Rede de Petri a ser simulada
	private PetriNet petriNet = null;
	
	// Array de marcacoes
	private int[] arrayMarkers = null;
	
	// Array que informa se a transicao esta ou nao habilitada apos o disparo
	private boolean[] arrayTrasitionTrigger = null;

	public PetriNetSimulator(PetriNet petriNet) {
		this.petriNet = petriNet;

		this.arrayMarkers = new int[this.petriNet.getNumPlace()];
		this.arrayTrasitionTrigger = new boolean[this.petriNet
				.getNumTransition()];

		for (int i = 0; i < arrayMarkers.length; i++) {
			arrayMarkers[i] = this.petriNet.getPlace(i).getTokens();
		}
	}
	
	/**
	 * Uma transicao esta habilitada se, todos os Lugares de Entrada da Transicao
	 * possuirem fichas maior ou igual ao peso do arco, que liga ate a transicao.
	 * <pre>
	 * Algoritmo:
	 * Para toda Transicao Faca
	 * 		Transicao.habilitada = true
	 * Fim Para;
	 * Para todo Arco de entrada
	 * 		Se Arco.Lugar.fichas >= arco.peso E Arco.transicao.habilitada = true Entao
	 * 			Arco.transicao esta habilitada.
	 * 		Senao
	 * 			Arco.transicao esta desabilitada.
	 * 		Fim Se;
	 * Fim Para;
	 * </pre>
	 * 
	 * @return Array boleano informando se a transicao esta ou nao habilitada.
	 */	
	public boolean[] getAvailableTransitions() {
		for (int i = 0; i < arrayTrasitionTrigger.length; i++){
			arrayTrasitionTrigger[i] = true;
		}

		for (int i = 0; i < petriNet.getNumArc(); i++) {
			Arc arc = petriNet.getArc(i);
			
			if (arc.isInput()) {
				Place place = arc.getPlace();
				Transition trans = arc.getTransition();
				
				int transPos = trans.getPosition();
				int placePos = place.getPosition();
				
				if ((arrayMarkers[placePos] >= arc.getWeight())
						&& (arrayTrasitionTrigger[transPos] == true)) {
					arrayTrasitionTrigger[transPos] = true;
				} else {
					arrayTrasitionTrigger[transPos] = false;
				}
			}
		}

		return arrayTrasitionTrigger;
	}

	/**
	 * Dispara transicao indicada pela posicao.
	 * <pre>
	 * Algoritmo:
	 * Para todo Lugar de Entrada da Transicao Faca
	 * 		Lugar.fichas = Lugar.fichas - peso;
	 * Fim Para; 
	 * 
     * Para todo Lugar de Saida da Transicao Faca
	 * 		Lugar.fichas = Lugar.fichas + peso; 
	 * Fim Para;
	 * </pre>
	 * 
	 * @param posicao
	 */
	public void triggerTransition(int position) {
		Transition trans = petriNet.getTransition(position);

		for (int i = 0; i < petriNet.getNumArc(); i++) {
			Arc arc = petriNet.getArc(i);
			
			/* Se o Arco esta ligado a Transicao */			
			if (arc.getTransition().isEqual(trans)) {
				int pos = arc.getPlace().getPosition();
			
				if (arc.isInput()) { 	/* Se o Arco e' de Entrada (Lugar --> Transicao) */
					if (arrayMarkers[pos] != Constants.TOKEN_INFINITO){
						arrayMarkers[pos] = arrayMarkers[pos] - arc.getWeight();
					}
					
				} else{ /* Se o Arco e' de Saida (Transicao --> Lugar) */
					if (arrayMarkers[pos] != Constants.TOKEN_INFINITO){
						arrayMarkers[pos] = arrayMarkers[pos] + arc.getWeight();
					}
				}
			}
		}
	}

	public int[] getMarkers() {
		return arrayMarkers;
	}

	public boolean isAvailableTransition() {
		if (arrayTrasitionTrigger == null)
			return false;

		boolean canTriggerTransition = false;
		
		for (int i = 0; i < arrayTrasitionTrigger.length; i++) {
			if (arrayTrasitionTrigger[i] == true) {
				canTriggerTransition = true;
				break;
			}
		}

		return canTriggerTransition;
	}
	
	/**
	 * Seta o estado da simulacao para algum especifico. 
	 * 
	 * @param arrayMarkers
	 * @param arrayTrasitionTrigger
	 */
	public void setState(int[] arrayMarkers, boolean[] arrayTrasitionTrigger) {
		this.arrayMarkers = arrayMarkers.clone();
		this.arrayTrasitionTrigger = arrayTrasitionTrigger.clone();
	}

}
