package br.com.pn.business.gui;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import br.com.pn.business.base.Arc;
import br.com.pn.business.base.Place;
import br.com.pn.business.base.Transition;

/**
 * Classe que define os Arcos com os atributos necessarios
 * para o desenho na Interface Grafica.
 * 
 * @author Gabriel Tavares
 *
 */
public class ArcUI extends Arc {
	
	private List<Point> points = null;
	private Point  selectedPoint = null;	
	public static final int DiSTANCIA_MAX = 3;
		
	private static final int EXTREMO_NORTE = 0;
	private static final int EXTREMO_SUL   = 1;
	private static final int EXTREMO_LESTE = 2;
	private static final int EXTREMO_OESTE = 3;
	
	private int extreme = EXTREMO_NORTE;

	public ArcUI() {
		
	}

	/**
	 * Define quais os objetos de entrada e saida
	 * 
	 * @param start: lugar ou transicao
	 * @param end: lugar ou transicao
	 */
	public void setStartEndObjects(Object start, Object end) {
		if (((start instanceof Place) && (end instanceof Transition))
				|| ((start instanceof Transition) && (end instanceof Place))) {
			setStartObject(start);
			setEndObject(end);
		}
	}
	
	/**
	 * Define o objeto de origem do Arco
	 * @param obj
	 * @return
	 */
	public boolean setStartObject(Object obj) {
		if (obj instanceof PlaceUI) {
			setPlace((Place) obj);
			setInput();

			Point p = getStartPoint();
			points = new ArrayList<Point>();
			points.add(0, p);

			return true;
		} else {
			if (obj instanceof TransitionUI) {
				setTransition((Transition) obj);
				setOutput();

				Point p = getStartPoint();

				points = new ArrayList<Point>();
				points.add(0, p);

				return true;
			}
		}

		return false;
	}
	
	public boolean setEndObject(Object obj) {
		if ((obj instanceof PlaceUI) && (isOut())) {
			PlaceUI objeto = (PlaceUI) obj;
			setPlace((Place) obj);

			Point p = getEndPoint();
			points.add(p);

			return true;
		} else {
			if ((obj instanceof TransitionUI) && (isInput())) {
				TransitionUI objeto = (TransitionUI) obj;
				setTransition((Transition) obj);

				Point p = new Point(objeto.getX(), objeto.getY());
				points.add(p);

				return true;
			}
		}

		return false;
	}
	
	public Point getStartPoint() {
		Point start = new Point();
		Object obj = null;

		obj = this.getObjectStart();
		if (obj instanceof PlaceUI) {
			PlaceUI objeto = (PlaceUI) obj;

			switch (extreme) {
			case EXTREMO_NORTE:
				start = objeto.getExtremeNorte();
				break;
			case EXTREMO_LESTE:
				start = objeto.getExtremeLeste();
				break;
			case EXTREMO_SUL:
				start = objeto.getExtremeSul();
				break;
			case EXTREMO_OESTE:
				start = objeto.getExtremeOeste();
				break;
			}
		} else {
			if (obj instanceof TransitionUI) {
				TransitionUI objeto = (TransitionUI) obj;
				start = new Point(objeto.getX(), objeto.getY());
			}
		}

		return start;
	}
	
	public Point getEndPoint() {
		Point end = new Point();
		Object obj = null;

		obj = this.getObjectEnd();
		if (obj instanceof PlaceUI) {
			PlaceUI objeto = (PlaceUI) obj;
			switch (extreme) {
			case EXTREMO_NORTE:
				end = objeto.getExtremeNorte();
				break;
			case EXTREMO_LESTE:
				end = objeto.getExtremeLeste();
				break;
			case EXTREMO_SUL:
				end = objeto.getExtremeSul();
				break;
			case EXTREMO_OESTE:
				end = objeto.getExtremeOeste();
				break;
			}
		} else {
			if (obj instanceof TransitionUI) {
				TransitionUI objeto = (TransitionUI) obj;
				end = new Point(objeto.getX(), objeto.getY());
			}
		}

		return end;
	}
	
	/**
	 * Adiciona um ponto (Ficha) ao Arco
	 * 
	 * @param x
	 * @param y
	 */
	public void addPoint(int x, int y) {
		if (points != null) {
			points.add(new Point(x, y));
		}
	}
	
	/**
	 * @return A origem do Arco
	 */
	public Object getObjectStart() {
		if (isInput()){
			return getPlace();
		} else{
			return getTransition();
		}
	}
	
	/**
	 * @return O destino do Arco
	 */
	public Object getObjectEnd() {
		if (isOut()){
			return getPlace();
		} else{
			return getTransition();
		}
	}
	
	/**
	 * Checa se o ponto esta no Arco
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean inArc(int x, int y) {
		Point test = new Point(x, y);
		
		for (int i = 0; i < getNumPoints() - 1; i++) {
			Point start = getPoint(i);
			Point end = getPoint(i + 1);
			
			if (inRect(start, end, test)) {
				if ((i == 0) && (getNumPoints() > 2)) {
					selectedPoint = end;
				} else if ((i + 1 == getNumPoints() - 1) && (getNumPoints() > 2)) {
					selectedPoint = start;
				} else {
					if (getNumPoints() > 3) {
						double distStart = Point.distance(start.x, start.y, x, y);
						double distEnd = Point.distance(end.x, end.y, x, y);
						
						if ((distStart <= distEnd)
								&& (!start.equals(getPoint(0)))){
							selectedPoint = start;
						} else{
							selectedPoint = end;
						}
					} else{
						selectedPoint = null;
					}
				}

				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checa se o Ponto dado esta no segmento de reta, que compoe o arco.
	 * 
	 * @param start
	 * @param end
	 * @param test
	 * @return
	 */
	private boolean inRect(Point start, Point end, Point test) {
		if (end.y < start.y) {
			Point aux = end;
			end = start;
			start = aux;
		}

		Line2D.Double line = new Line2D.Double(start, end);
		int dist = (int) line.ptLineDist(test);
		
		if (dist < DiSTANCIA_MAX) {
			// Reta perpendicular ao eixo X
			if (end.x == start.x) {
				if (test.y <= end.y && test.y >= start.y){
					return true;
				} else{
					return false;
				}
			} else {
				// Reta paralela ao eixo X
				if (end.y == start.y) {
					if (end.x < start.x) {
						Point aux = end;
						end = start;
						start = aux;
					}

					if (test.x <= end.x && test.x >= start.x){
						return true;
					} else{
						return false;
					}
				} else {
					// Reta Obliqua
					if (((test.x < end.x) && (test.x > start.x))
							|| ((test.y <= end.y) && (test.y >= start.y))){
						return true;
					} else{
						return false;
					}
				}
			}
		} else{
			return false;
		}
	}
	
	/**
	 * Rotaciona o Ponto Extremo ao redor do circulo que representa o Lugar.
	 */
	public void cycleExtremePoint() {
		switch (extreme) {
		case EXTREMO_NORTE:
			extreme = EXTREMO_LESTE;
			break;
		case EXTREMO_LESTE:
			extreme = EXTREMO_SUL;
			break;
		case EXTREMO_SUL:
			extreme = EXTREMO_OESTE;
			break;
		case EXTREMO_OESTE:
			extreme = EXTREMO_NORTE;
			break;

		}
	}
	
	/**
	 * Atualiza os pontos iniciais e finais apos mover um objeto.
	 */
	public void refreshEndPoints() {
		int numPoints = getNumPoints();
		if (numPoints > 0) {
			Point start = getStartPoint();
			Point end = getEndPoint();
			getPoints().get(0).x = start.x;
			getPoints().get(0).y = start.y;

			getPoints().get(numPoints - 1).x = end.x;
			getPoints().get(numPoints - 1).y = end.y;
		}
	}
	
	/**
	 * Move o ponto Selecionado para a posicao X Y
	 */
	public void moveSelectedPoint(int x, int y) {
		if (selectedPoint != null) {
			selectedPoint.x = x;
			selectedPoint.y = y;
		}
	}
	
	/**
	 * Retorna um Ponto a uma distancia Delta do ultimo ponto do segmento de reta
	 * fornecido.
	 */
	public static Point getAuxPoint(Point start, Point end, int delta) {
		int dH = (int) Point.distance(start.x, start.y, end.x, end.y);
		return getAuxPoint(start, end, delta, dH);
	}
	
	/**
	 * Retorna um Ponto a uma distancia Delta do ultimo ponto do segmento de reta
	 * fornecido. Usando um divisor especificado para achar o delta adequado.
	 */
	public static Point getAuxPoint(Point start, Point end, int delta, int razao) {
		int dX = end.x - start.x;
		if (dX < 0)
			dX = -dX;

		int dY = end.y - start.y;
		if (dY < 0)
			dY = -dY;

		int deltaX = delta * dX / razao;
		int deltaY = delta * dY / razao;
		Point aux = new Point();

		if ((end.x < start.x) && (end.y > start.y)) {
			aux.x = end.x + deltaX;
			aux.y = end.y - deltaY;
		} else if ((end.x >= start.x) && (end.y >= start.y)) {
			aux.x = end.x - deltaX;
			aux.y = end.y - deltaY;
		} else if ((end.x >= start.x) && (end.y <= start.y)) {
			aux.x = end.x - deltaX;
			aux.y = end.y + deltaY;
		} else if ((end.x <= start.x) && (end.y <= start.y)) {
			aux.x = end.x + deltaX;
			aux.y = end.y + deltaY;
		}

		return aux;
	}
	
	/**
	 * Retorna String informando qual o valor do Extremo.
	 */
	public String getStrExtreme() {
		switch (extreme) {
		case EXTREMO_NORTE:
			return "Norte";
		case EXTREMO_LESTE:
			return "Leste";
		case EXTREMO_SUL:
			return "Sul";
		case EXTREMO_OESTE:
			return "Oeste";
		}
		return "Sem extreme";
	}

	public Point getPoint(int position) {
		if (getPoints() != null) {
			if ((position <= getPoints().size() - 1) && (position >= 0)){
				return getPoints().get(position);
			}
		}

		return null;
	}
	
	public int getNumPoints() {
		return (getPoints() == null ? 0 : getPoints().size());
	}

	private List<Point> getPoints() {
		return points;
	}
}
