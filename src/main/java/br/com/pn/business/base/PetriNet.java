package br.com.pn.business.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * Classe base que representa a Rede de Petri
 * 
 * @author Gabriel Tavares
 *
 */
public class PetriNet implements PetriNetInterface{
	
	public static final String DEFAULT_NAME_NET = "NovoRP";

	private String name;
	
	private List<Place> places = new ArrayList<Place>();
	private List<Transition> transitions = new ArrayList<Transition>();
	private List<Arc> arcs = new ArrayList<Arc>();
	
	private int[][] arrayInput = null; 		// Matriz I
	private int[][] arrayOutput = null; 	// Matriz O
	private int[][] arrayIncidence = null; 	// Matriz D = O - I
	
	
	public PetriNet() {
		this.name = DEFAULT_NAME_NET;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addPlace(Place place) {
		place.setPosition(getNumPlace());
		getPlaces().add(place);
	}

	public void removePlace(Place place) {
		for (Iterator<Place> iterator = getPlaces().iterator(); iterator.hasNext();) {
			Place current = (Place) iterator.next();
			
			if(current.equals(place)){
				iterator.remove();
				removeArcJoinedPlace(place);
				
				for (int i = 0; i < getPlaces().size(); i++) {
					getPlace(i).setPosition(i);
				}
				
				break;
			}
		}
	}

	public Place getPlace(String name) {
		for (Iterator<Place> iterator = getPlaces().iterator(); iterator.hasNext();) {
			Place current = (Place) iterator.next();

			if(current.getName().equalsIgnoreCase(name)){
				return current;
			}
		}
		
		return null;
	}

	public Place getPlace(int pos) {
		if(pos <= getNumPlace()-1){
			return places.get(pos);
		}
		
		return null;
	}

	public void addTransition(Transition transition) {
		transition.setPosition(getNumTransition());
		getTransitions().add(transition);
	}

	public void removeTransition(Transition transition) {
		for (Iterator<Transition> iterator = getTransitions().iterator(); iterator.hasNext();) {
			Transition current = (Transition) iterator.next();
			
			if(current.equals(transition)){
				iterator.remove();
				removeArcJoinedTransition(transition);
				
				for (int i = 0; i < getTransitions().size(); i++) {
					getTransition(i).setPosition(i);
				}
				
				break;
			}
		}
	}

	public Transition getTransition(String name) {
		for (Iterator<Transition> iterator = getTransitions().iterator(); iterator.hasNext();) {
			Transition current = (Transition) iterator.next();

			if(current.getName().equalsIgnoreCase(name)){
				return current;
			}
		}
		
		return null;
	}

	public Transition getTransition(int pos) {
		if(pos <= getNumTransition()-1){
			return transitions.get(pos);
		}
		
		return null;
	}

	public void addArc(Arc arc) {
		getArcs().add(arc);
	}

	public void removeArc(Arc arc) {
		for (Iterator<Arc> iterator = getArcs().iterator(); iterator.hasNext();) {
			Arc current = (Arc) iterator.next();
			
			if(current.equals(arc)){
				iterator.remove();
				
				for (int i = 0; i < getArcs().size(); i++) {
					getArc(i).setPosition(i);
				}
				
				break;
			}
		}
	}

	public Arc getArc(int pos) {
		if(pos <= getNumArc()-1){
			return arcs.get(pos);
		}
		
		return null;
	}

	public List<Place> getPlaces() {
		return places;
	}

	public List<Transition> getTransitions() {
		return transitions;
	}

	public List<Arc> getArcs() {
		return arcs;
	}

	public int[] getArrayMarkers() {
		if (places != null) {
			int[] arrMarking = new int[getNumPlace()];

			for (int i = 0; i < getNumPlace(); i++)
				arrMarking[i] = getPlace(i).getTokens();

			return arrMarking;
		}
		return null;
	}
	
	public void buildArrayInOut() {
		arrayInput = new int[getNumTransition()][getNumPlace()];
		arrayOutput = new int[getNumTransition()][getNumPlace()];
		
		for (Iterator<Arc> iterator = getArcs().iterator(); iterator.hasNext();) {
			Arc current = (Arc) iterator.next();
			
			Transition transition = current.getTransition();
			Place place = current.getPlace();
			
			int row = transition.getPosition();
			int column = place.getPosition();
			
			if(current.isInput()){
				arrayInput[row][column] = current.getWeight();
			} else{
				arrayOutput[row][column] = current.getWeight();
			}			
		}
	}

	public void buildArrayIncidence() {
		arrayIncidence = new int[getNumTransition()][getNumPlace()];
		
		if((arrayInput == null) || (arrayOutput == null)){
			buildArrayInOut();
		}
		
		for (int row = 0; row < getNumTransition(); row++) {
			for (int column = 0; column < getNumPlace(); column++) {
				arrayIncidence[row][column] = arrayOutput[row][column] - arrayInput[row][column];
			}
		}
	}

	public int[][] getArrayIn() {
		if(arrayInput == null){
			buildArrayInOut();
		}
		return arrayInput;
	}

	public int[][] getArrayOut() {
		if(arrayOutput == null){
			buildArrayInOut();
		}
		return arrayOutput;
	}

	public int[][] getArrayIncidence() {
		if(arrayIncidence == null){
			buildArrayIncidence();
		}
		return arrayIncidence;
	}

	/**
	 * Devolve a dimensao das matrizes que representam a Rede de Petri
	 */
	public int getDimension() {
		return (getNumTransition() > getNumPlace() ? getNumTransition() : getNumPlace());
	}

	public int getNumPlace() {
		return (getPlaces() == null ? 0 : getPlaces().size());
	}

	public int getNumTransition() {
		return (getTransitions() == null ? 0 : getTransitions().size());
	}

	public int getNumArc() {
		return (getArcs() == null ? 0 : getArcs().size());
	}

	private void removeArcJoinedPlace(Place place) {
		for (Iterator<Arc> iterator = getArcs().iterator(); iterator.hasNext();) {
			Arc arc = (Arc) iterator.next();
			
			if(arc.getPlace().equals(place)){
				removeArc(arc);
			}
		}		
	}

	private void removeArcJoinedTransition(Transition transition) {
		for (Iterator<Arc> iterator = getArcs().iterator(); iterator.hasNext();) {
			Arc arc = (Arc) iterator.next();
			
			if(arc.getTransition().equals(transition)){
				removeArc(arc);
			}
		}		
	}

}
