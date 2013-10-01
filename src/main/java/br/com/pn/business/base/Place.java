package br.com.pn.business.base;

/**
 * Classe que define os Lugares
 * 
 * @author Gabriel Tavares
 * 
 */
public class Place {

	private String name;
	private int position;
	private int tokens;
	private String id;

	public Place() {
		setPosition(0);
		setTokens(0);
	}
	
	public Place(String name, int tokens, int position){
		setName(name);
		setPosition(position);
		setTokens(tokens);
	}
	
	public Place(int tokens, int position){
		setPosition(position);
		setTokens(tokens);
	}
	
	public Place(int position){
		setPosition(position);
		setTokens(0);
	}

	public String getName() {
		if((name == null) || ( "".equals(name.trim()) ))
			return getId();
		else
			return name;
	}

	public void setName(String name) {
		if(name != null)
			this.name = name.trim();
		else
			this.name = null;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
		this.id = "P" + position;
	}

	public int getTokens() {
		return tokens;
	}

	public void setTokens(int tokens) {
		this.tokens = tokens;
	}

	public String getId() {
		return id;
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("Lugar:");
		sb.append("[" + getName() + "]");
		sb.append("\n");
		sb.append("Identificador:");
		sb.append("[" + getId() + "]");
		sb.append("\n");
		sb.append("Fichas:");
		sb.append("[" + getTokens() + "]");
		sb.append("\n");
		sb.append("Posicao:");
		sb.append("[" + getPosition() + "]");
		
		return sb.toString();
	}
}
