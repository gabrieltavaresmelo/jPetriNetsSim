package br.com.pn.business.gui;

import java.awt.Point;
import br.com.pn.business.base.Place;

/**
 * Classe que define os Lugares com os atributos necessarios
 * para o desenho na Interface Grafica.
 * 
 * @author Gabriel Tavares
 *
 */
public class PlaceUI extends Place {

	public static final int RAIO = 12;

	private int x;
	private int y;

	public PlaceUI() {

	}

	public PlaceUI(int x, int y, int position) {
		super(position);
		this.x = x;
		this.y = y;
	}

	public PlaceUI(int position) {
		super(position);
		this.x = 0;
		this.y = 0;
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
	 * Calcula a distancia do Lugar para um ponto recebido como parametro.
	 */
	public double distance(double x, double y) {
		double dx = (this.x - x);
		double dy = (this.y - y);
		double dist = Math.sqrt(dx * dx + dy * dy);

		return dist;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof PlaceUI))
			return false;

		PlaceUI p = (PlaceUI) obj;
		if (p == this)
			return true;

		if ((this.getX() == p.getX()) && (this.getY() == p.getY())
				&& (this.getTokens() == p.getTokens())
				&& (this.getId() == p.getId())
				&& (this.getName() == p.getName())
				&& (this.getPosition() == p.getPosition())) {
			return true;
		} else
			return false;
	}

	public boolean inPlace(int x, int y) {
		if (distance(x, y) < RAIO)
			return true;
		else
			return false;
	}
	
	/**
	 * Retorna o Ponto Extremo (Sul, Norte, Leste ou Oeste), o que for mais proximo 
	 * do ponto que vem como parametro.
	 */
	public Point getExtremePoint(Point q) {
		/*
		 *       N
		 *       |
		 *       |
		 * O --------- L
		 * 		 |
		 * 		 |
		 * 		 S
		 * 
		 */
		
		Point norte = getExtremeNorte();
		Point sul = getExtremeSul();

		Point oeste = getExtremeOeste();
		Point leste = getExtremeLeste();
		double distNorte = Point.distanceSq(q.x, q.y, norte.x, norte.y);
		double distSul = Point.distanceSq(q.x, q.y, sul.x, sul.y);
		double distOeste = Point.distanceSq(q.x, q.y, oeste.x, oeste.y);
		double distLeste = Point.distanceSq(q.x, q.y, leste.x, leste.y);

		Point ret1 = null;
		Point ret2 = null;

		if (distLeste <= distOeste)
			ret1 = leste;
		else
			ret1 = oeste;

		if (distNorte <= distSul)
			ret2 = norte;
		else
			ret2 = sul;

		double distRet1 = Point.distanceSq(q.x, q.y, ret1.x, ret1.y);
		double distRet2 = Point.distanceSq(q.x, q.y, ret2.x, ret2.y);

		if (distRet1 <= distRet2 + 5)
			return ret1;
		else
			return ret2;

	}

	public Point getExtremeNorte() {
		return new Point(this.x, this.y - RAIO);
	}

	public Point getExtremeSul() {
		return new Point(this.x, this.y + RAIO);
	}

	public Point getExtremeOeste() {
		return new Point(this.x - RAIO, this.y);
	}

	public Point getExtremeLeste() {
		return new Point(this.x + RAIO, this.y);
	}
}
