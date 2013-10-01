package br.com.pn.business.simulator;

import br.com.pn.util.Constants;

public class PetriNetState {

	/**
	 * Array de marcacoes dos Lugares na rede.
	 */
	private int[] arrayMarkers = null;

	/**
	 * Array informando se a transicao esta ou nao habilitada para disparo.
	 */
	private boolean[] arrayTrasitionTrigger = null;

	/**
	 * Array com a sequencia de disparos.
	 */
	private int[] arraySequenceTrigger = null;

	/**
	 * Informa o tipo de estado: STATE_TERMINAL, STATE_FRONTEIRA,
	 * STATE_DUPLICADO ou STATE_INTERIOR
	 */
	private int stateType = Constants.STATE_FRONTEIRA;

	/**
	 * Soma das marcacoes no Estado.
	 */
	private int totalMarking = 0;

	/**
	 * Informa se tem transicao habilitada para disparo.
	 */
	private boolean isEnableTransition = false;

	/**
	 * Identificador do Estado, recebe o valor de um contador estatico que e'
	 * incrementado a cada estado criado.
	 */
	private int id = 0;

	/**
	 * Maximo de fichas encontrado em algum lugar da rede.
	 */
	private int limit = 0;

	/**
	 * Informa a qual estado e' igual se ele for do tipo STATE_DUPLICADO
	 */
	private int equalsState = -1;

	/**
	 * Contador estatico que e' incrementado a cada novo estado criado.
	 */
	private static int totalState = 0;
	
	
	public PetriNetState(int [] arrayMarkers, boolean [] arrayTrasitionTrigger, boolean isEnableTransition,int [] arraySequenceTrigger) {
		this.stateType = Constants.STATE_FRONTEIRA;
		
		this.isEnableTransition = isEnableTransition;
		setArrayMarkers(arrayMarkers);
		setArrayTrasitionTrigger(arrayTrasitionTrigger);
		this.arraySequenceTrigger = arraySequenceTrigger.clone();
		equalsState = -1;
		
		id = totalState;
		totalState++;
	}
	

	public int[] getMarkers() {
		return arrayMarkers;
	}

	public void setArrayMarkers(int[] arrayMarkers) {
		if(arrayMarkers == null){
			return;
		}
		
		this.arrayMarkers = arrayMarkers.clone();
		totalMarking = 0;
		limit = 0;
		
		for (int i = 0; i < this.arrayMarkers.length; i++) {
			totalMarking += this.arrayMarkers[i];
			
			if (this.arrayMarkers[i] > limit) {
				limit = this.arrayMarkers[i];
			}
		}
	}

	public void setMarker(int pos, int value) {
		int remove = getMarker(pos);
		
		if (remove != Constants.ERROR_CODE) {
			totalMarking = totalMarking - remove;
			if (value == Constants.TOKEN_INFINITO) {
				totalMarking = Constants.TOKEN_INFINITO;
				arrayMarkers[pos] = Constants.TOKEN_INFINITO;
				limit = Constants.TOKEN_INFINITO;
			} else {
				totalMarking = totalMarking + value;
				arrayMarkers[pos] = value;
				if (limit < value)
					limit = value;
			}
		}
	}

	public int getMarker(int pos) {
		if (pos >= 0 && pos < arrayMarkers.length)
			return arrayMarkers[pos];

		return Constants.ERROR_CODE;
	}

	public boolean[] getTriggerTransitions() {
		return arrayTrasitionTrigger;
	}

	public void setArrayTrasitionTrigger(boolean[] arrayTrasitionTrigger) {
		if(arrayTrasitionTrigger != null){
			this.arrayTrasitionTrigger = arrayTrasitionTrigger.clone();
		}
	}

	public int[] getTriggerSequence() {
		return arraySequenceTrigger;
	}

	public void setArraySequenceTrigger(int[] arraySequenceTrigger) {
		this.arraySequenceTrigger = arraySequenceTrigger;
	}

	public int getStateType() {
		return stateType;
	}

	public void setStateType(int stateType) {
		this.stateType = stateType;
	}

	public int getTotalMarking() {
		return totalMarking;
	}

	public void setTotalMarking(int totalMarking) {
		this.totalMarking = totalMarking;
	}

	public boolean isEnableTransition() {
		return isEnableTransition;
	}

	public void setEnableTransition(boolean isEnableTransition) {
		this.isEnableTransition = isEnableTransition;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getEqualsState() {
		return equalsState;
	}

	public void setEqualsState(int equalsState) {
		this.equalsState = equalsState;
	}

	public static int getTotalState() {
		return totalState;
	}

	public static void setTotalState(int totalState) {
		PetriNetState.totalState = totalState;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (obj instanceof PetriNetState) {
			PetriNetState objState = (PetriNetState) obj;
			int[] arrMarkingObj = objState.getMarkers();

			if (this.arrayMarkers.length != arrMarkingObj.length)
				return false;

			for (int i = 0; i < this.arrayMarkers.length; i++) {
				if (arrayMarkers[i] != arrMarkingObj[i])
					return false;
			}

			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		String str = "Estado " + getId();

		/* Monta assinatura */
		if ((arrayMarkers != null) && (arrayMarkers.length > 1)) {
			String strMarking = "[";
			for (int i = 0; i < arrayMarkers.length; i++) {
				if (arrayMarkers[i] >= Constants.TOKEN_INFINITO)
					strMarking += "w,";
				else
					strMarking += arrayMarkers[i] + ",";
			}
			strMarking = strMarking.substring(0, strMarking.lastIndexOf(','));
			strMarking += "]";

			str += " " + strMarking;
		}

		return str;
	}
}
