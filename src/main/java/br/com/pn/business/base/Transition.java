package br.com.pn.business.base;



/**
 * Classe que define as Transicoes
 * 
 * @author Gabriel Tavares
 * 
 */
public class Transition {

	private String name;
	private int position;
	private String id;
	
	// Static Earliest Firing Time (temporizacao segundo o modelo de Merlin) 
	private double seft = 0.0;
	
	// Static Latest Firing Time (temporizacao segundo o modelo de Merlin)
	private double slft = 0.0;

	// Curva de Densidade de probabilidade: Altera o modo como sera interpretado os valores de SEFT e SLFT
	private int densityCurve;

	public final static int UNIFORME = 0;
	public final static int NORMAL = 1;
	public final static int EXPONENCIAL = 2;
	
	
	public Transition() {
	}
	
	public Transition(String name, int position) {
		setName(name);
		setPosition(position);
	}
	
	public Transition(int position) {
		setPosition(position);
	}

	public String getName() {
		if((name == null) || ( "".equals(name.trim()) ))
			return getId();
		else
			return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
		this.id = "T" + position;
	}

	public String getId() {
		return id;
	}

	public int getDensityCurve() {
		return densityCurve;
	}

	public void setDensityCurve(int densityCurve) {
		this.densityCurve = densityCurve;
	}
	
	public double getSeft() {
		return seft;
	}
	
	public void setSeft(double seft) {
		this.seft = seft;
	}
	
	public double getSlft() {
		return slft;
	}
	
	public void setSlft(double slft) {
		this.slft = slft;
	}
	
	@Override
	public boolean equals(Object obj) {
		return isEqual(obj);
	}

	public boolean isEqual(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof Transition) {
			Transition trans = (Transition) obj;
			if ((trans.densityCurve == this.densityCurve)
					&& (trans.id.equals(this.id))
					&& (trans.name.equals(this.name))
					&& (trans.position == this.position)
					&& (trans.seft == this.seft) && (trans.slft == this.slft))
				return true;
		}

		return false;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Transicao:");
		sb.append("[" + getName() + "]");
		sb.append("\n");
		sb.append("Identificador:");
		sb.append("[" + getId() + "]");
		sb.append("\n");
		sb.append("Posicao:");
		sb.append("[" + getPosition() + "]");
		
		return sb.toString();
	}
	
	public String toDebug() {
		StringBuffer sb = new StringBuffer();
		sb.append(toString());
		sb.append("\n");
		sb.append("SEFT:");
		sb.append("[" + getSeft() + "]");
		sb.append("\n");
		sb.append("SLFT:");
		sb.append("[" + getSlft() + "]");
		sb.append("\n");
		sb.append("Curva de Densidade:");
		sb.append("[" + getDensityCurve() + "]");
		
		return sb.toString();
	}
}
