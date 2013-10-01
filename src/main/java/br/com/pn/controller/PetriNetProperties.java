package br.com.pn.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.pn.util.Constants;

public class PetriNetProperties {

	private String nameNet = "";

	/**
	 * Uma rede viva e' aquela que em todos os estados possui alguma transicao
	 * habilitada para disparo.
	 */
	private boolean isLive = true;

	/**
	 * Uma rede e' limitada quando e' possivel encontrar um valor maximo de
	 * fichas nos Lugares, ou seja, nao existe um acumulo indefinido de fichas
	 * em nenhum lugar da rede.
	 */
	private boolean isLimited = true;

	/**
	 * Uma Rede e' conservativa quando o numero total de fichas na rede e'
	 * constante em todos os estados. Uma rede conservativa e' tambem limitada.
	 */
	private boolean isConservative = true;

	/**
	 * Array com as sequencias de disparos que levam a deadlock.
	 */
	private List<Integer> triggersDeadLock = null;

	/**
	 * Array com os Estados finais apos a sequencia de disparos, estados de
	 * deadlock.
	 */
	private List<Integer> statesDeadLock = null;

	/**
	 * Limite da rede, caso ela seja limitada.
	 */
	private int limit = 0;

	/**
	 * RP Ordinaria: todos os arcos possuem peso = 1.
	 */
	private boolean isOrdinary = true;

	/**
	 * Total de fichas na rede
	 */
	private int qtdTokens = 0;

	public PetriNetProperties() {
		isConservative = true;
		isLimited = true;
		isLive = true;
		limit = 0;
		qtdTokens = 0;
		isOrdinary = true;

		triggersDeadLock = new ArrayList<Integer>();
		statesDeadLock = new ArrayList<Integer>();
	}

	public PetriNetProperties(String nameNet) {
		this.nameNet = nameNet;

		isConservative = true;
		isLimited = true;
		isLive = true;
		limit = 0;
		qtdTokens = 0;
		isOrdinary = true;

		triggersDeadLock = new ArrayList<Integer>();
		statesDeadLock = new ArrayList<Integer>();
	}

	public String getNameNet() {
		return nameNet;
	}

	public void setNameNet(String nameNet) {
		this.nameNet = nameNet;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public boolean isLimited() {
		return isLimited;
	}

	public void setLimited(boolean isLimited) {
		this.isLimited = isLimited;
	}

	public boolean isConservative() {
		return isConservative;
	}

	public void setConservative(boolean isConservative) {
		this.isConservative = isConservative;
	}

	public List<Integer> getTriggersDeadLock() {
		return triggersDeadLock;
	}

	public void setTriggersDeadLock(List<Integer> triggersDeadLock) {
		this.triggersDeadLock = triggersDeadLock;
	}

	public List<Integer> getStatesDeadLock() {
		return statesDeadLock;
	}

	public void setStatesDeadLock(List<Integer> statesDeadLock) {
		this.statesDeadLock = statesDeadLock;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		if (limit == Constants.TOKEN_INFINITO) {
			this.limit = Constants.TOKEN_INFINITO;
			isLimited = false;
		} else {
			this.limit = limit;
			isLimited = true;
		}
	}

	public int getQtdTokens() {
		return qtdTokens;
	}

	public void setQtdTokens(int qtdTokens) {
		this.qtdTokens = qtdTokens;
	}
	
	public boolean isOrdinary() {
		return isOrdinary;
	}
	
	public void setOrdinary(boolean isOrdinary) {
		this.isOrdinary = isOrdinary;
	}

	/**
	 * Adiciona uma sequencia de disparos que levam a Rede a um 
	 * estado de DeadLock, e qual o identificador do Estado final.
	 * 
	 * @param arrfireSequence
	 * @param idEstado
	 */
	public void addDeadLock(int[] arrfireSequence, int idEstado) {
		Integer[] newArray = new Integer[arrfireSequence.length];
		int i = 0;
		for (int value : arrfireSequence) {
		    newArray[i++] = Integer.valueOf(value);
		}
		
		triggersDeadLock.addAll(Arrays.asList(newArray.clone()));
		statesDeadLock.add(new Integer(idEstado));
	}
	
	/**
	 * Retorna a sequencia de disparos que levam a deadlock especificados pela
	 * posicao.
	 * 
	 * @param pos
	 * @return
	 */
	public int[] getTriggersSequenceDeadLock(int pos) {
		return new int[] { triggersDeadLock.get(pos) };
	}

	/**
	 * Retorna o Estado final de uma sequencia de disparo que levou a rede a
	 * deadlock.
	 * 
	 * @param pos
	 * @return 
	 */
	public int getDeadLockState(int pos) {
		Integer id = triggersDeadLock.get(pos);
		return id.intValue();
	}

	/**
	 * Retorna o total de estados de deadlock
	 * 
	 * @return Total de estados de deadlock
	 */
	public int getTotalDeadLockStates() {
		if (statesDeadLock != null) {
			return statesDeadLock.size();
		} else {
			return 0;
		}
	}
}
