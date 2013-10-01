package br.com.pn.business.gui;

import br.com.pn.business.base.Transition;

/**
 * Classe que define as Transicoes com os atributos necessarios
 * para o desenho na Interface Grafica.
 * 
 * @author Gabriel Tavares
 *
 */
public class TransitionUI extends Transition {

	public static final int ORIENTATION_VERTICAL = 1;
	public static final int ORIENTATION_DIAGONAL1 = 2;
	public static final int ORIENTATION_HORIZONTAL = 3;
	public static final int ORIENTATION_DIAGONAL2 = 4;
	public static final int ORIENTATION_ALL = 5;

	public static final int ALTURA = 6;
	public static final int COMPRIMENTO = 26;

	private int orientation;
	private int x;
	private int y;

	public TransitionUI() {

	}

	public TransitionUI(int x, int y, int position) {
		super(position);
		this.x = x;
		this.y = y;
		this.orientation = ORIENTATION_VERTICAL;
	}

	public TransitionUI(int position) {
		super(position);
		this.x = 0;
		this.y = 0;
		this.orientation = ORIENTATION_VERTICAL;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Rotaciona a orientacao
	 */
	public void cycleOrientation() {
		switch (orientation) {
		/*
		 * case ORIENTATION_VERTICAL: orientation = ORIENTATION_DIAGONAL1;
		 * break; case ORIENTATION_DIAGONAL1: orientation =
		 * ORIENTATION_HORIZONTAL; break; case ORIENTATION_HORIZONTAL:
		 * orientation = ORIENTATION_DIAGONAL2; break; case
		 * ORIENTATION_DIAGONAL2: orientation = ORIENTATION_ALL; break; case
		 * ORIENTATION_ALL: orientation = ORIENTATION_VERTICAL; break;
		 */
		case ORIENTATION_VERTICAL:
			orientation = ORIENTATION_HORIZONTAL;
			break;
		case ORIENTATION_HORIZONTAL:
			orientation = ORIENTATION_VERTICAL;
			break;
		}
	}

	/**
	 * Calcula a distancia da transicao ate um ponto (x,y)
	 */
	public double distance(double x, double y) {
		double dist;
		double dx;
		double dy;
		dx = (this.x - x);
		dy = (this.y - y);
		dist = Math.sqrt(dx * dx + dy * dy);
		return dist;
	}
	 
	 /**
	  * Checa se o ponto esta na transicao.
	  */
	public boolean inTransition(int x, int y) {
		/*
		int supEsqX = this.x - COMPRIMENTO/2;
		int supEsqY = this.y - ALTURA/2;
		
		int infDirX = this.x + COMPRIMENTO/2;
		int infDirY = this.y + ALTURA/2;
		
		System.out.println("( "+x+" , "+y+" )" +
				"\n S( "+supEsqX+" , "+supEsqY+" )" +
				"\n I( "+infDirX+" , "+infDirY+" )"); 
		
		if( x >= supEsqX &&
			x <= infDirX &&
			y <= supEsqY &&
			y >= infDirY)
			return true;
		else
			return false; */
		
		if (distance(x, y) < COMPRIMENTO / 2)
			return true;
		else
			return false;
	}
	
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof PlaceUI))
			return false;

		TransitionUI t = (TransitionUI) obj;
		if (t == this)
			return true;

		if ((this.getX() == t.getX()) && (this.getY() == t.getY())
				&& (this.getOrientation() == t.getOrientation())
				&& (this.getDensityCurve() == t.getDensityCurve())
				&& (this.getId() == t.getId())
				&& (this.getName() == t.getName())
				&& (this.getPosition() == t.getPosition())
				&& (this.getSeft() == t.getSeft())
				&& (this.getSlft() == t.getSlft())) {
			return true;
		} else
			return false;
	}
}
