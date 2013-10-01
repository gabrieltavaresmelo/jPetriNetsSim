package br.com.pn.business.simulator;


/**
 * Define um estado da Rede de Petri. Um estado e' caracterizado pela marcacao
 * dos lugares, transicoes disponiveis e sequencia de disparos para chegar ao
 * estado em determinado momento.
 * 
 * @author Gabriel Tavares
 * 
 */
public class TreeNodeState {

	// Lista de lugares
	private int[] markers = null;

	// Informa quais as transicoes podem ser disparadas
	private boolean[] triggerTransitions = null;

	// Lista com frequencia de disparos
	private int[] triggersSequence = null;

	public TreeNodeState(int[] markers, boolean[] triggerTransitions,
			int[] triggersSequence) {
		this.markers = markers;
		this.triggerTransitions = triggerTransitions;
		this.triggersSequence = triggersSequence;
	}

	public int[] getMarkers() {
		return markers;
	}

	public boolean[] getTriggerTransitions() {
		return triggerTransitions;
	}

	public int[] getTriggersSequence() {
		return triggersSequence;
	}

	@Override
	public String toString() {
		String ret = "";
		
		if (getTriggersSequence() == null || getTriggersSequence().length < 1) {
			ret = "Estado Inicial";
		} else {
			ret = "T" + getTriggersSequence()[getTriggersSequence().length - 1];
		}
		
		if ((markers != null) && (markers.length > 1)) {
			String strMarking = "[";
			
			for (int i = 0; i < markers.length; i++) {
				strMarking += markers[i] + ",";
			}
			
			strMarking = strMarking.substring(0, strMarking.lastIndexOf(','));
			strMarking += "]";

			ret += " " + strMarking;
		}
				
		return ret.trim();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (obj instanceof TreeNodeState) {
			TreeNodeState state = (TreeNodeState) obj;
			
			int[] objArrMarking = state.getMarkers();
			boolean[] objArrTransFire = state.getTriggerTransitions();
			int[] objArrFireSequence = state.getTriggersSequence();

			if (objArrFireSequence.length != this.getTriggersSequence().length){
				return false;
			}

			if (objArrTransFire.length != this.getTriggerTransitions().length){
				return false;
			}

			for (int i = 0; i < objArrMarking.length; i++) {
				if (objArrMarking[i] != this.getMarkers()[i]){
					return false;
				}
			}

			for (int i = 0; i < objArrTransFire.length; i++) {
				if (objArrTransFire[i] != this.getTriggerTransitions()[i]){
					return false;
				}
			}

			for (int i = 0; i < objArrFireSequence.length; i++) {
				if (objArrFireSequence[i] != this.getTriggersSequence()[i]){
					return false;
				}
			}

			return true;
		} else{
			return false;
		}
	}
}
