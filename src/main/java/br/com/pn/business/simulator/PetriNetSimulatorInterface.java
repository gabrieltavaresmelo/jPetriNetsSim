package br.com.pn.business.simulator;

/**
 * Metodos necessarios para que o simulador interaja com
 * a GUI.
 * 
 * @author Gabriel Tavares
 *
 */
public interface PetriNetSimulatorInterface {

	// Array boleano que informa se a transicao esta habilitada
	public boolean[] getAvailableTransitions();
	
	// Dispara a trasicao
	public void triggerTransition(int position);
	
	// Array com as marcacoes dos lugares, apos o disparo da trasicao
	public int[] getMarkers();

	// Checa se ha alguma transicao disponivel a ser disparada
	public boolean isAvailableTransition();
}
