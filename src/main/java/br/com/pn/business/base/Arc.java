package br.com.pn.business.base;

/**
 * Classe que define os Arcos
 * 
 * @author Gabriel Tavares
 * 
 */
public class Arc {

	// Pseo do Arco (valor padrao: 1)
	private int weight = 1;

	// Posicao do Arco na Lista
	private int position;

	// Lugar ligado ao Arco
	private Place place;

	// Trasicao ligada ao Arco
	private Transition transition;

	// Um arco de entrada e' aquele que sai de um Lugar e entra em uma Transicao
	private boolean isInput = true;

	/**
	 * Construtor do Arco
	 * 
	 * @param place: Lugar ligado ao arco
	 * @param transition: Transicao ligada ao Arco
	 * @param isInput: Informa se o lugar sai de uma transicao e entra em um lugar ou nao
	 */
	public Arc(Place place, Transition transition, boolean isInput) {
		setPlace(place);
		setTransition(transition);
		this.isInput = isInput;
		setWeight(1);
	}

	public Arc(Place place, Transition transition, int weight, boolean isInput) {
		setPlace(place);
		setTransition(transition);
		this.isInput = isInput;
		setWeight(weight);
	}
	
	public Arc() {
		setInput();
		setWeight(1);
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public Transition getTransition() {
		return transition;
	}

	public void setTransition(Transition transition) {
		this.transition = transition;
	}

	public boolean isInput() {
		return isInput;
	}

	public void setInput() {
		this.isInput = true;
	}

	public void setOutput() {
		this.isInput = false;
	}

	public boolean isOut() {
		return !isInput;
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("Arco de ");
		sb.append((isInput() ? "Entrada" : "Saida"));
		sb.append("Lugar:");
		sb.append("[" + getPlace().getName() + "]");
		sb.append("\n");
		sb.append("Transicao:");
		sb.append("[" + getTransition().getName() + "]");
		
		return sb.toString();
	}
}
