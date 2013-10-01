package br.com.pn.controller;

import br.com.pn.business.base.Transition;
import br.com.pn.business.gui.PetriNetUI;
import br.com.pn.business.simulator.PetriNetSimulator;
import br.com.pn.business.simulator.TreeNodeState;
import br.com.pn.view.SimulationWindow;

/**
 * Classe que implementa a area de desenho da Rede de Petri.
 * 
 * @author Gabriel Tavares
 *
 */
public class SimulationController {
	
	private PetriNetUI petriNetGraph = null;
	private PetriNetSimulator simulator = null;
	private TreeNodeState state = null;
	
	private SimulationWindow simWindow = null;
	
	
	public SimulationController(PetriNetUI petriNetGraph) {
		this.petriNetGraph = petriNetGraph;
		
		simulator = new PetriNetSimulator(this.petriNetGraph);

		int markers[] = simulator.getMarkers().clone();
		boolean triggerTransitions[] = simulator.getAvailableTransitions().clone();

		state = createState(markers, triggerTransitions, new int[0]);
		
		// TODO
		simWindow = new SimulationWindow(petriNetGraph, state);
		simWindow.setVisible(true);
	}
	
	/**
	 * Cria um novo estado
	 * 
	 * @param markers
	 * @param triggerTransitions
	 * @param triggersSequence
	 * @return
	 */
	public TreeNodeState createState(int markers[], boolean[] triggerTransitions, int[] triggersSequence){
		TreeNodeState state = new TreeNodeState(markers, triggerTransitions, triggersSequence);
		return state;
	}
	
	/**
	 * Adiciona um novo estado de marcacoes e transicoes disponiveis
	 * 
	 * @param markers
	 * @param triggerTransitions
	 * @param triggersSequence
	 */
	public void addState(int markers[], boolean[] triggerTransitions, int[] triggersSequence){
		state = new TreeNodeState(markers, triggerTransitions, triggersSequence);
		// TODO
		simWindow.addTreeNodeState(state);
		simWindow.repaint();
	}
	
	public void goToState() {
		// TODO
		TreeNodeState selected = simWindow.getSelectedState();
		
		if(selected != null){
			state = selected;
			simWindow.changeAtualForSelectedState();
			
			simulator.setState(state.getMarkers(), state.getTriggerTransitions());
		}
	}
	
	/**
	 * Retorna o numero de fichas em uma determinado lugar, 
	 * apos o ultimo disparo.
	 * 
	 * @param position
	 * @return
	 */
	public int getTokens(int position) {
		return state.getMarkers()[position];
	}
	
	/**
	 * Verifica se determinada transicao pode ser disparada, 
	 * apos o ultimo disparo.
	 * 
	 * @param position
	 * @return
	 */
	public boolean canTriggerTransition(int position) {
		return state.getTriggerTransitions()[position];
	}
	
	public PetriNetUI getPetriNetGraph() {
		return petriNetGraph;
	}
	
	/**
	 * Tenta disparar a transicao verificando, primeiramente, se
	 * ela pode ser disparada.
	 * 
	 * @param transition
	 * @return
	 */
	public boolean tryTriggerTransition(Transition transition) {
				
		if (canTriggerTransition(transition.getPosition())) {
			simulator.triggerTransition(transition.getPosition());
			
			int[] markers = simulator.getMarkers().clone();
			boolean[] availableTransitions = simulator.getAvailableTransitions().clone();

			int arrOldTriggersSequence[] = state.getTriggersSequence();
			int arrTriggersSequence[] = new int[arrOldTriggersSequence.length + 1];

			for (int i = 0; i < arrOldTriggersSequence.length; i++) {
				arrTriggersSequence[i] = arrOldTriggersSequence[i];
			}
			
			arrTriggersSequence[arrTriggersSequence.length - 1] = transition.getPosition();			
			addState(markers, availableTransitions, arrTriggersSequence);

			return true;

		} else {
			return false;
		}
	}

	private void printDebugInfo(int[] markers, boolean[] availableTransitions,
			int[] arrOldTriggersSequence, int[] arrTriggersSequence) {
		System.out.print("markers: ");
		for (int i = 0; i < markers.length; i++) {
			System.out.print(markers[i] + ", ");
		}
		
		System.out.println();
		System.out.print("availableTransitions: ");
		for (int i = 0; i < availableTransitions.length; i++) {
			System.out.print(availableTransitions[i] + ", ");
		}
		
		System.out.println();
		System.out.println("arrOldTriggersSequence");
		for (int i = 0; i < arrOldTriggersSequence.length; i++) {
			System.out.print(arrOldTriggersSequence[i] + ", ");
		}
		
		System.out.println();
		System.out.println("arrTriggersSequence");
		for (int i = 0; i < arrTriggersSequence.length; i++) {
			System.out.print(arrTriggersSequence[i] + ", ");
		}
		
		System.out.println();
	}
	
	public void refreshText() {
		// TODO
		if(simWindow != null){
			simWindow.refreshText();
		}
	}
	
	public void showSimulationWindow(){
		// TODO
//		SimulationWindow.setDefaultLookAndFeelDecorated(true);
		simWindow.setDefaultCloseOperation(SimulationWindow.DO_NOTHING_ON_CLOSE);
		simWindow.setFocusable(true);
		simWindow.setVisible(true);
	}
	
	public void closeSimulationWindow()	{
		// TODO
		simWindow.dispose();
	}
}
