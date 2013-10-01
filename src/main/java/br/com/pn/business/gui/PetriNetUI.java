package br.com.pn.business.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.pn.business.base.PetriNet;

/**
 * 
 * Classe base que representa a Rede de Petri com os atributos necessarios para
 * o desenho na Interface Grafica.
 * 
 * @author Gabriel Tavares
 * 
 */
public class PetriNetUI extends PetriNet {

	private int selectedPlace = -1;
	private int selectedTrans = -1;
	private int selectedArc = -1;
	private int selectedLabel = -1;

	private List<Marker> markers = new ArrayList<Marker>();

	public PetriNetUI() {
	}

	public void addMarker(Marker marker) {
		if (markers == null) {
			markers = new ArrayList<Marker>();
		}

		markers.add(marker);
	}

	/**
	 * Retorna true caso exista algum objeto que ocupe as proximidades
	 * da posicao x, y fornecida.
	 */
	public boolean verifyPosition(int x, int y) {
		for (int i = 0; i < getNumPlace(); i++) {
			PlaceUI p = (PlaceUI) getPlace(i);
			if (p.inPlace(x, y))
				return true;
		}

		for (int i = 0; i < getNumTransition(); i++) {
			TransitionUI t = (TransitionUI) getTransition(i);
			if (t.inTransition(x, y))
				return true;
		}

		return false;
	}

	/**
	 * Retorna o objeto nas proximidades da posicao dada
	 */
	public Object getObjectPosition(int x, int y) {
		for (int i = 0; i < getNumPlace(); i++) {
			PlaceUI p = (PlaceUI) getPlace(i);
			if (p.inPlace(x, y)) {
				selectedPlace = i;
				return p;
			}
		}

		for (int i = 0; i < getNumTransition(); i++) {
			TransitionUI t = (TransitionUI) getTransition(i);
			if (t.inTransition(x, y)) {
				selectedTrans = i;
				return t;
			}
		}

		for (int i = 0; i < getNumArc(); i++) {
			ArcUI a = (ArcUI) getArc(i);
			if (a.inArc(x, y)) {
				selectedArc = i;
				return a;
			}
		}

		for (int i = 0; i < getNumMarker(); i++) {
			Marker l = getMarker(i);
			if (l.inLabel(x, y)) {
				selectedLabel = i;
				return l;
			}
		}

		return null;
	}

	public boolean verifyPlaceSelected(PlaceUI place) {
		return place.getPosition() == selectedPlace ? true : false;
	}
	
	public boolean verifyTransitionSelected(TransitionUI trans) {
		return trans.getPosition() == selectedTrans ? true : false;
	}

	public boolean verifyArcSelected(ArcUI arc) {
		return arc.getPosition() == selectedArc ? true : false;
	}

	public boolean verifyLabelSelected(Marker marker) {
		return marker.getPosition() == selectedLabel ? true : false;
	}

	public void deselectPlace() {
		selectedPlace = -1;
	}

	public void deselectTransition() {
		selectedTrans = -1;
	}
	
	public void deselectArc() {
		selectedArc = -1;
	}
	
	public void deselectMarker() {
		selectedLabel = -1;
	}

	public ArcUI getSelectedArc() {
		return selectedArc > -1 ? (ArcUI) this.getArc(selectedArc) : null;
	}

	public Marker getSelectedMarker() {
		if (selectedLabel > -1)
			return (Marker) this.getMarker(selectedLabel);
		else
			return null;
	}

	public void removeMarker(Marker marker) {
		for (Iterator<Marker> iterator = getMarkers().iterator(); iterator.hasNext();) {
			Marker current = (Marker) iterator.next();
			
			if(current.equals(marker)){
				iterator.remove();
				
				for (int i = 0; i < getMarkers().size(); i++) {
					getMarkers().get(i).setPosition(i);
				}
				
				break;
			}
		}
	}

	public Marker getMarker(int position) {
		if (getMarkers() != null) {
			if ((position <= getMarkers().size() - 1) && (position >= 0)){
				return getMarkers().get(position);
			}
		}

		return null;
	}
	
	public int getNumMarker() {
		return (getMarkers() == null ? 0 : getMarkers().size());
	}

	private List<Marker> getMarkers() {
		return markers;
	}
}
