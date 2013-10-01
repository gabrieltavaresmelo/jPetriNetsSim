package br.com.pn.business.base;

import java.util.List;

/**
 * Interface que define a Rede de Petri
 * 
 * @author Gabriel Tavares
 *
 */
public interface PetriNetInterface {

	public void setName(String name);
	public String getName();
	
	public void addPlace(Place place);
//	public void removePlace(String name);
//	public void removePlace(int position);
	public void removePlace(Place place);
	public Place getPlace(String name);
	public Place getPlace(int position);
	
	public void addTransition(Transition transition);
//	public void removeTransition(String name);
//	public void removeTransition(int position);
	public void removeTransition(Transition transition);
//	public Transition getTransition(String name);
	public Transition getTransition(int position);
	
	public void addArc(Arc arc);
//	public void removeArc(int position);
//	public void removeArc(Arc arc);
	public Arc getArc(int position);
	
	public List<Place> getPlaces();
	public List<Transition> getTransitions();
	public List<Arc> getArcs();
	
	public void buildArrayInOut();
	public void buildArrayIncidence();
	
	public int[][] getArrayIn();
	public int[][] getArrayOut();
	public int[][] getArrayIncidence();
	
	public int getDimension();
	
	public int getNumPlace();
	public int getNumTransition();
	public int getNumArc();
	
}
