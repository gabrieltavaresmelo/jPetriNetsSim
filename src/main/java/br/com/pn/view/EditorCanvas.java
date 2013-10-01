package br.com.pn.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import br.com.pn.business.base.Arc;
import br.com.pn.business.base.Place;
import br.com.pn.business.base.Transition;
import br.com.pn.business.gui.ArcUI;
import br.com.pn.business.gui.Marker;
import br.com.pn.business.gui.PlaceUI;
import br.com.pn.business.gui.TransitionUI;
import br.com.pn.util.Constants;
import br.com.pn.util.ModeRun;

public class EditorCanvas extends JTabbedPane implements MouseListener {

	private static final long serialVersionUID = 1L;

	private MainWindowView window;
	private ModeRun mode = ModeRun.NORMAL;
	private int subMode;

	// Objeto correntemente selecionado
	private Object selectObj = null;

	// Objeto a ser movido
	private Object moveObj = null;

	// Arco a ser desenhado
	private ArcUI arc = null;

	public EditorCanvas(MainWindowView mainWindow) {
		setWindow(mainWindow);
		setBackground(Color.WHITE);
		setAutoscrolls(true);
	}

	public MainWindowView getWindow() {
		return window;
	}

	public void setWindow(MainWindowView window) {
		this.window = window;
	}

	public ModeRun getMode() {
		return mode;
	}

	public void setMode(ModeRun mode) {
		this.mode = mode;
	}

	public int getSubMode() {
		return subMode;
	}
	
	public void setSubMode(int subMode){
		this.subMode = subMode;
	}

	public void eraseArc() {
		this.arc = null;
	}

	/**
	 * Adiciona um Lugar caso o ponto clicado esteja fora de algum objeto
	 * existente.
	 */
	public void addPlace(MouseEvent evt) {
		int x = evt.getX();
		int y = evt.getY();

		if (window.getPetriNetGraph().verifyPosition(x, y) == false) {
			PlaceUI place = new PlaceUI(x, y, window.getPetriNetGraph()
					.getNumPlace());
			window.getPetriNetGraph().addPlace(place);
		} else {
			selectObject(evt);
		}
	}

	/**
	 * Adiciona uma Transicao caso o ponto clicado esteja fora de algum objeto
	 * existente.
	 */
	public void addTransition(MouseEvent evt) {
		int x = evt.getX();
		int y = evt.getY();

		if (window.getPetriNetGraph().verifyPosition(x, y) == false) {
			TransitionUI transition = new TransitionUI(x, y, window.getPetriNetGraph()
					.getNumTransition());
			transition.setOrientation(TransitionUI.ORIENTATION_HORIZONTAL);
			window.getPetriNetGraph().addTransition(transition);
		} else {
			selectObject(evt);
		}
	}

	/**
	 * Adiciona um rotulo caso o ponto clicado esteja fora de algum objeto
	 * existente.
	 */
	public void addMarker(MouseEvent evt) {
		int x = evt.getX();
		int y = evt.getY();

		if (window.getPetriNetGraph().verifyPosition(x, y) == false) {
			String text = inputTextMarker();
			if (text != null) {
				Marker label = new Marker(x, y, window.getPetriNetGraph().getNumMarker());
				label.setText(text);

				window.getPetriNetGraph().addMarker(label);
			}
		} else {
			selectObject(evt);
		}
	}

	/**
	 * Seta qual o objeto da rede de petri e o inicio do arco.
	 */
	public void setStartArc(MouseEvent evt) {
		Object obj = window.getPetriNetGraph().getObjectPosition(evt.getX(), evt.getY());
		if (obj != null) {
			if (obj instanceof Place)
				window.getPetriNetGraph().deselectTransition();
			else
				window.getPetriNetGraph().deselectPlace();

			arc = new ArcUI();
			if (arc.setStartObject(obj) == true) {
				setSubMode(Constants.DRAW_ARC);
			} else {
				selectObject(evt);
			}
		}
	}

	/**
	 * Seta um ponto intermediario do arco.
	 */
	public void addPointArc(MouseEvent evt) {
		Object obj = window.getPetriNetGraph().getObjectPosition(evt.getX(), evt.getY());
		if (obj != null) {
			setEndArc(evt, obj);
		} else {
			arc.addPoint(evt.getX(), evt.getY());
		}
	}

	/**
	 * Seta o objeto de rede de petri, final do arco.
	 */
	public void setEndArc(MouseEvent evt, Object obj) {
		if (arc.setEndObject(obj) == true) {
			if (obj instanceof Place)
				window.getPetriNetGraph().deselectTransition();
			else
				window.getPetriNetGraph().deselectPlace();

			setSubMode(Constants.EDIT_ARC);
			arc.setPosition(window.getPetriNetGraph().getNumArc());
			window.getPetriNetGraph().addArc(arc);
			arc = null;
		}
	}

	/**
	 * Seleciona o objeto clicado pelo mouse.
	 */
	public void selectObject(MouseEvent evt) {
		Object obj = window.getPetriNetGraph().getObjectPosition(evt.getX(), evt.getY());
		if (obj != null) {
			selectObj = obj;
			if (selectObj instanceof PlaceUI) {
				// window.placeAction.setSelectedPlace((PlaceGraph) selectObj);
				// window.placeAction.enablePlaceInfo();
				// window.placeAction.showInfo();
				// window.transAction.disableTransitionInfo();
				// window.arcAction.disableArcInfo();
				window.getPetriNetGraph().deselectTransition();
				window.getPetriNetGraph().deselectArc();
			} else if (selectObj instanceof TransitionUI) {
				// window.transAction.setSelectedTransition((TransitionGraph)
				// selectObj);
				// window.transAction.enableTransInfo();
				// window.transAction.showInfo();
				// window.arcAction.disableArcInfo();
				// window.placeAction.disablePlaceInfo();
				window.getPetriNetGraph().deselectPlace();
				window.getPetriNetGraph().deselectArc();

				if (evt.getButton() != MouseEvent.BUTTON1) {
					TransitionUI t = (TransitionUI) selectObj;
					t.cycleOrientation();
				}

			} else if (selectObj instanceof ArcUI) {
				// window.arcAction.setSelectedArc((ArcGraph) selectObj );
				// window.arcAction.enableArcInfo();
				// window.arcAction.showInfo();
				// window.transAction.disableTransitionInfo();
				// window.placeAction.disablePlaceInfo();
				window.getPetriNetGraph().deselectPlace();
				window.getPetriNetGraph().deselectTransition();

				if (evt.getButton() != MouseEvent.BUTTON1) {
					ArcUI arc = window.getPetriNetGraph().getSelectedArc();
					arc.cycleExtremePoint();
					arc.refreshEndPoints();
					arc = null;

				}
			} else if (selectObj instanceof Marker) {
				// window.transAction.disableTransitionInfo();
				// window.placeAction.disablePlaceInfo();
				window.getPetriNetGraph().deselectPlace();
				window.getPetriNetGraph().deselectTransition();

				if (evt.getButton() != MouseEvent.BUTTON1) {
					Marker marker = window.getPetriNetGraph().getSelectedMarker();
					String text = inputTextMarker();
					if (text != null) {
						marker.setText(text);
					}
				}
			}

			moveObj = selectObj;
		} else {
			// window.arcAction.focusLostPerformed();
			// window.placeAction.focusLostPerformed();
			// window.transAction.focusLostPerformed();

			window.getPetriNetGraph().deselectPlace();
			window.getPetriNetGraph().deselectTransition();
			window.getPetriNetGraph().deselectArc();

			moveObj = null;
			selectObj = null;
		}
	}

	/**
	 * Move o objeto clicado e liberado pelo mouse.
	 */
	public void moveObject(MouseEvent evt) {
		boolean bolRefresh = false;
		if ((moveObj != null) && (evt.getButton() == MouseEvent.BUTTON1)) {
			int newX = evt.getX();
			int newY = evt.getY();

			if (moveObj instanceof PlaceUI) {
				PlaceUI p = (PlaceUI) moveObj;
				p.setX(newX);
				p.setY(newY);
				bolRefresh = true;
			} else if (moveObj instanceof TransitionUI) {
				TransitionUI t = (TransitionUI) moveObj;
				t.setX(newX);
				t.setY(newY);
				bolRefresh = true;
			} else if (moveObj instanceof ArcUI) {
				ArcUI arc = (ArcUI) moveObj;
				arc.moveSelectedPoint(newX, newY);
				bolRefresh = true;
			} else if (moveObj instanceof Marker) {
				Marker marker = (Marker) moveObj;
				marker.moveMarker(newX, newY);
				bolRefresh = true;
			}

			if (bolRefresh) {
				moveObj = null;
				for (int i = 0; i < window.getPetriNetGraph().getNumArc(); i++){
					((ArcUI) window.getPetriNetGraph().getArc(i)).refreshEndPoints();
				}
			}
		}

	}

	/**
	 * Deleta objeto de rede de petri desenhado na tela.
	 */
	public void deleteObject(MouseEvent evt) {
		Object obj = window.getPetriNetGraph().getObjectPosition(evt.getX(), evt.getY());
		if (obj != null) {
			selectObj = obj;
			if (selectObj instanceof PlaceUI) {
				window.getPetriNetGraph().deselectPlace();
				window.getPetriNetGraph().removePlace((Place) selectObj);
			} else if (selectObj instanceof TransitionUI) {
				window.getPetriNetGraph().deselectTransition();
				window.getPetriNetGraph().removeTransition(((Transition) selectObj));
			} else if (selectObj instanceof ArcUI) {
				window.getPetriNetGraph().deselectArc();
				window.getPetriNetGraph().removeArc((Arc) selectObj);
			} else if (selectObj instanceof Marker) {
				window.getPetriNetGraph().deselectMarker();
				window.getPetriNetGraph().removeMarker((Marker) selectObj);
			}
		}
	}

	/**
	 * Mostra as informacoes do objeto selecionado, quando esta no modo
	 * Simulacao.
	 */
	public void showInfoObjectAndFireTransition(MouseEvent evt) {
		Object obj = window.getPetriNetGraph().getObjectPosition(evt.getX(), evt.getY());
		if (obj != null) {
			selectObj = obj;
			if (selectObj instanceof PlaceUI) {
				window.setSelectedPlace((PlaceUI) selectObj);
				window.getPetriNetGraph().deselectTransition();
				window.getPetriNetGraph().deselectArc();
				window.getPetriNetGraph().deselectMarker();
				
			} else if (selectObj instanceof TransitionUI) {
				TransitionUI transition = (TransitionUI) selectObj;
				window.setSelectedTransition(transition);
				window.getPetriNetGraph().deselectPlace();
				window.getPetriNetGraph().deselectArc();
				window.getPetriNetGraph().deselectMarker();
				
				// Tenta disparar transicao, se o botao do mouse for o esquerdo
				if (evt.getButton() == MouseEvent.BUTTON1){
					// TODO
					if(window.getSimulation() != null){
						window.getSimulation().tryTriggerTransition(transition);
					}
				}
			} else if (selectObj instanceof ArcUI) {
				window.setSelectedArc((ArcUI) selectObj );
				window.getPetriNetGraph().deselectPlace();
				window.getPetriNetGraph().deselectTransition();
				window.getPetriNetGraph().deselectMarker();
				
			} else if (selectObj instanceof Marker) {
				window.getPetriNetGraph().deselectPlace();
				window.getPetriNetGraph().deselectTransition();
				window.getPetriNetGraph().deselectArc();
				
			}
		} else {
			selectObj = null;
		}
	}

	public void update(Graphics g) {
		if (mode == ModeRun.SIMULATION) {
			Color color = new Color(245, 245, 245);
			setBackground(color);
		} else {
			setBackground(Color.WHITE);
		}
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		drawPlaces(g);
		drawTrasitions(g);
		drawArcs(g);
		drawMarkers(g);
	}

	public void paint(Graphics g) {
		if (mode == ModeRun.SIMULATION) {
			Color color = new Color(240, 240, 240);
			setBackground(color);
		} else {
			setBackground(Color.WHITE);
		}

		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		drawPlaces(g);
		drawTrasitions(g);
		drawArcs(g);
		drawMarkers(g);
	}

	/**
	 * Metodo que desenha os Lugares da Rede de Petri na tela.
	 */
	private void drawPlaces(Graphics g) {
		for(int i=0; i < window.getPetriNetGraph().getNumPlace(); i++ ){
			PlaceUI p = (PlaceUI) window.getPetriNetGraph().getPlace(i);
			
			if (window.getPetriNetGraph().verifyPlaceSelected(p) == true){
				g.setColor(new Color(0, 51, 102));
			} else{
				g.setColor(new Color(51, 153, 0));
			}
			
			g.drawOval(p.getX() - PlaceUI.RAIO, p.getY() - PlaceUI.RAIO,
					2 * PlaceUI.RAIO, 2 * PlaceUI.RAIO);

			int nTokens = 0;
			if (mode == ModeRun.SIMULATION){
				// TODO
				nTokens = window.getSimulation().getTokens(i);
			} else{
				nTokens = p.getTokens();
			}

    		g.setColor(new Color(200, 0, 0));
			
			if (nTokens > 1 && nTokens < 10) {
				Font f = g.getFont();
				int size = f.getSize();
				int x = (int) (p.getX() - (size / 4));
				int y = (int) (p.getY() + (size / 2.5f));
				g.drawString(Integer.toString(nTokens), x, y);
			} else {
				if (nTokens >= 10) {
					Font f = g.getFont();
					int size = f.getSize();
					int x = (int) (p.getX() - (size / 1.5f));
					int y = (int) (p.getY() + (size / 2.5f));
					g.drawString(Integer.toString(nTokens), x, y);
				} else {
					g.drawOval(p.getX() - PlaceUI.RAIO / 5, p.getY()
							- PlaceUI.RAIO / 5, 2 * PlaceUI.RAIO / 5,
							2 * PlaceUI.RAIO / 5);

					if (nTokens == 1) {

						g.fillOval(p.getX() - PlaceUI.RAIO / 5, p.getY()
								- PlaceUI.RAIO / 5, 2 * PlaceUI.RAIO / 5,
								2 * PlaceUI.RAIO / 5);
					}
				}
			}
		}
	}

	/**
	 * Metodo que desenha as Transicoes da Rede de Petri na tela.
	 */
	private void drawTrasitions(Graphics g) {
		for (int i = 0; i < window.getPetriNetGraph().getNumTransition(); i++) {
			TransitionUI t = (TransitionUI) window.getPetriNetGraph()
					.getTransition(i);

			if (window.getPetriNetGraph().verifyTransitionSelected(t)){
				g.setColor(new Color(0, 51, 102));
			} else{
				g.setColor(new Color(255, 153, 0));
			}
			
			if (mode == ModeRun.SIMULATION) {
				// TODO
				if (window.getSimulation().canTriggerTransition(i)){
					g.setColor(new Color(200, 0, 0));
				}
			}

			int x = 0, y = 0;
			int lx = 0, ly = 0;
			switch (t.getOrientation()) {
			case TransitionUI.ORIENTATION_VERTICAL: {
				x = t.getX() - TransitionUI.ALTURA / 2;
				y = t.getY() - TransitionUI.COMPRIMENTO / 2;

				lx = TransitionUI.ALTURA;
				ly = TransitionUI.COMPRIMENTO;
				break;
			}
			default: {
				// ORIENTATION_HORIZONTAL
				x = t.getX() - TransitionUI.COMPRIMENTO / 2;
				y = t.getY() - TransitionUI.ALTURA / 2;

				lx = TransitionUI.COMPRIMENTO;
				ly = TransitionUI.ALTURA;
			}
			}

			g.drawRect(x, y, lx, ly);
			g.fillRect(x, y, lx, ly);
		}
	}

	/**
	 * Desenha os arcos da rede de petri.
	 */
	private void drawArcs(Graphics g) {
		if (arc != null) {
			drawArc(g, arc);
		}

		for (int i = 0; i < window.getPetriNetGraph().getNumArc(); i++) {
			ArcUI a = (ArcUI) window.getPetriNetGraph().getArc(i);
			drawArc(g, a);

		}
	}

	/**
	 * Desenha cada arco da rede de petri.
	 */
	private void drawArc(Graphics g, ArcUI a) {
		if (a.getNumPoints() < 2)
			return;

		Point start = null, end = null;
		if (window.getPetriNetGraph().verifyArcSelected(a) == true){
			g.setColor(new Color(55, 171, 200, 255)); // azul claro
		} else{
			g.setColor(new Color(16, 16, 16)); // verde escuro
		}
		
		int j = 0;
		for (j = 0; j < a.getNumPoints() - 1; j++) {
			start = a.getPoint(j);
			end = a.getPoint(j + 1);
			if (start != null && end != null) {
				g.drawLine(start.x, start.y, end.x, end.y);
			}
		}

		end = a.getPoint(a.getNumPoints() - 1);
		start = a.getPoint(a.getNumPoints() - 2);

		Point strPoint = ArcUI.getAuxPoint(start, end, 1, 2);
		Point auxPoint = ArcUI.getAuxPoint(start, end, 14);

		if (a.getWeight() > 1) {
			g.drawString("" + a.getWeight(), strPoint.x, strPoint.y);
		}

		drawHeadArrow(g, auxPoint.x, auxPoint.y, end.x, end.y);
	}

	/**
	 * Desenha a ponta da Seta do arco.
	 */
	private static void drawHeadArrow(Graphics g, int xS, int yS, int xE,
			int yE) {
		int xP[] = new int[3];
		int yP[] = new int[3];

		if (xE < 0.0)
			xP[0] = (int) (xE - 1.0);
		else
			xP[0] = (int) (xE + 1.0);

		if (yE < 0.0)
			yP[0] = (int) (yE - 1.0);
		else
			yP[0] = (int) (yE + 1.0);

		double dx = xS - xE;
		double dy = yS - yE;

		double length = Math.sqrt(dx * dx + dy * dy);

		double xAdd = 9.0 * dx / length;
		double yAdd = 9.0 * dy / length;

		xP[1] = (int) Math.round(xE + xAdd - (yAdd / 3.0));
		yP[1] = (int) Math.round(yE + yAdd + (xAdd / 3.0));
		xP[2] = (int) Math.round(xE + xAdd + (yAdd / 3.0));
		yP[2] = (int) Math.round(yE + yAdd - (xAdd / 3.0));

		g.drawLine(xS, yS, xP[0], yP[0]);
		g.fillPolygon(xP, yP, 3);
	}

	/**
	 * Metodo que desenha o rotulo na janela de desenho
	 */
	public void drawMarkers(Graphics g) {

		for (int i = 0; i < window.getPetriNetGraph().getNumMarker(); i++) {

			Marker l = (Marker) window.getPetriNetGraph().getMarker(i);

			Point p = l.getStartPoint();

			g.setFont(l.getFont());
			g.setColor(Color.BLACK);
			g.drawString(l.getText(), p.x, p.y);

		}
	}

	public String inputTextMarker() {
		String strText = JOptionPane.showInputDialog(null, "Rotulo:", "Rotulo",
				JOptionPane.INFORMATION_MESSAGE);
		if ((strText != null) && (strText.trim().length() > 0)) {
			return strText;
		} else {
			return null;
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		switch (subMode) {
		case Constants.EDIT_LUGAR:
			addPlace(e);
			break;
		case Constants.EDIT_TRANS:
			addTransition(e);
			break;
		case Constants.EDIT_LABEL:
			addMarker(e);
			break;
		case Constants.EDIT_ARC:
			setStartArc(e);
			break;
		case Constants.EDIT_MOUSE:
			selectObject(e);
			break;
		case Constants.EDIT_DELETE:
			deleteObject(e);
			break;
		case Constants.DRAW_ARC:
			addPointArc(e);
			break;

		case Constants.SIM_START:
			showInfoObjectAndFireTransition(e);
			break;

		}

		repaint();
	}

	public void mouseReleased(MouseEvent e) {
		repaint();
	}

	public void mouseEntered(MouseEvent e) {
		repaint();
	}

	public void mouseExited(MouseEvent e) {
		repaint();
	}
}
