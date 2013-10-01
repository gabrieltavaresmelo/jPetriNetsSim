package br.com.pn.util;

public class Constants {

	/**
	 * Constantes dos modos de funcionamento do programa
	 */
	public static final int MODO_EDICAO = 1;
	public static final int MODO_SIMULACAO = 2;
	public static final int MODO_ANALISE = 3;

	/**
	 * Constantes referentes ao objeto selecionado na edicao
	 */
	public static final int FILE_SAVE = 10;
	public static final int EDIT_MOUSE = 11;
	public static final int EDIT_LUGAR = 12;
	public static final int EDIT_TRANS = 13;
	public static final int EDIT_ARC = 14;
	public static final int EDIT_DELETE = 15;
	public static final int EDIT_LABEL = 16;

	/**
	 * Constantes referentes ao desenho do arco
	 */
	public static final int DRAW_ARC = 141;

	/**
	 * Constantes referentes ao objeto selecionado para simulacao
	 */
	public static final int SIM_START = 20;
	public static final int SIM_STOP = 21;
	public static final int SIM_BACK = 22;

	/**
	 * Constantes referentes a Simulacao
	 */
	public static final int STATE_FRONTEIRA = 0;
	public static final int STATE_DUPLICADO = 1;
	public static final int STATE_INTERIOR  = 2;
	public static final int STATE_TERMINAL  = 3;
	
	public static final int TOKEN_INFINITO	= Integer.MAX_VALUE/100;
	
	public static final int ERROR_CODE = -999997;
}
