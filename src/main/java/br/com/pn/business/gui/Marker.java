package br.com.pn.business.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

/**
 * Classe que representa os rotulos a serem colocados no desenho da Rede de
 * Petri
 * 
 * @author Gabriel Tavares
 * 
 */
public class Marker {

	private Point downLeft; // Coordenada inferior esquerda
	private String text = null;

	private int position;

	private static Font font;
	private static Color color;
	private static Color fontCor;

	public static final int WIDTH_LETTER = 5;
	public static final int HEIGHT_LETTER = 10;
	private int width;

	/**
	 * Contrutor que recebe as coordendadas do ponto superior esquerdo.
	 */
	public Marker(int infEsqX, int infEsqY, int posicao) {
		downLeft = new Point(infEsqX, infEsqY);

		this.position = posicao;
		font = new Font("Arial", Font.BOLD, 10);
	}

	public void setText(String text) {
		this.text = text;
		width = getText().length() * WIDTH_LETTER;
	}

	public String getText() {
		return text;
	}

	public Point getStartPoint() {
		return downLeft;
	}

	public Point getPointLeftUp() {
		Point p = new Point(downLeft.x, downLeft.y - HEIGHT_LETTER);

		return p;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	public void setFont(Font newFont) {
		font = newFont;
	}

	public Font getFont() {
		return font;
	}

	public void setColor(Color newColor) {
		color = newColor;
	}

	public Color getColor() {
		return color;
	}

	public void setFontColor(Color newFontColor) {
		fontCor = newFontColor;
	}

	public Color getFontColor() {
		return fontCor;
	}

	public int getLargura() {
		return width;
	}

	/**
	 * Verifica se o ponto recebido como parametro esta sobre o rotulo. Caso
	 * esteja retorna true, caso contrario retorna false.
	 */
	public boolean inLabel(int x, int y) {
		if ((y >= downLeft.y - HEIGHT_LETTER) && (x >= downLeft.x)
				&& (y <= downLeft.y) && (x <= downLeft.x + getLargura())) {
			return true;
		} else{
			return false;
		}
	}

	/**
	 * Move o retangulo do rotulo para uma nova posicao (x,y)
	 */
	public void moveMarker(int infEsqX, int infEsqY) {
		downLeft = new Point(infEsqX, infEsqY);
	}

	public String toString() {
		return this.getText();
	}

}
