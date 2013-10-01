package br.com.pn.view;


import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import br.com.pn.business.gui.ArcUI;
import br.com.pn.business.gui.PetriNetUI;
import br.com.pn.business.gui.PlaceUI;
import br.com.pn.business.gui.TransitionUI;
import br.com.pn.controller.SimulationController;

/**
 * Classe de layout da TelaPrincipal 
 * 
 * @author: Gabriel Tavares
 *
 */
public abstract class MainWindowView extends JFrame implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	
	// Widgets
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;
	private JPanel jPanelHeader = null;
	private JPanel jPanelTools = null;
	private JPanel jPanelBody = null;
	private JPanel jPanelFooter = null;
	private JPanel jPanelBody01;
	private JPanel jPanelBody02;
	private JMenu jMenuFile = null;
	private JMenu jMenuProcess = null;
	private JMenu jMenuHelp = null;
	private JMenuItem jMenuItemOpen = null;
	private JMenuItem jMenuItemNew = null;
	private JMenuItem jMenuItemExit = null;
	private JMenuItem jMenuItemAnalysis = null;
	private JMenuItem jMenuItemAbout = null;
	private EditorCanvas editorCanvas = null;
	private JLabel lbTitle = null;
	private JLabel lbTitlePlace = null;
	private JLabel lbPlaceName = null;
	private JLabel lbPlaceNameVal = null;
	private JLabel lbPlaceTokens = null;
	private JLabel lbPlaceTokensVal = null;
	private JLabel lbTitleTransition = null;
	private JLabel lbTransitionName = null;
	private JLabel lbTransitionNameVal = null;
	private JLabel lbTitleArc = null;
	private JLabel lbArcPlace = null;
	private JLabel lbArcPlaceVal = null;
	private JLabel lbArcTransition = null;
	private JLabel lbArcTransitionVal = null;
	private JLabel lbArcWeight = null;
	private JLabel lbArcWeightVal = null;
	private JToggleButton tbStatus = null;
	private JButton btBack = null;
	
	private JPanel jPanelBody02sub01;
	private JPanel jPanelBody02sub02;
	private JPanel jPanelBody02sub03;

	private PetriNetUI rp = null;
	
	private SimulationController simulation = null;
	
	
	public MainWindowView() {
		super();
		initialize();
		
		disableFields();
		loadColors();
	}

	private void initialize() {
		this.setSize(800, 650);
		this.setJMenuBar(getJJMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("Simulador Redes de Petri");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(
						((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - (this
								.getWidth() / 2)),
						((Toolkit.getDefaultToolkit().getScreenSize().height / 2) - (this
								.getHeight() / 2)));
		
		// Inicializa a aplicacao com a Janela Maximizada
//	    this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	    
		File url = new File("src/main/resources/logo.png");
	    this.setIconImage(new ImageIcon(url.getAbsolutePath()).getImage());
	}
	
	protected JPanel getJContentPane() {
		if (jContentPane == null) {			
//			GridBagConstraints gridBagConstraints3 = GridBagUtil.getGridBagConstraints(0, 3,
//					0, 0, GridBagConstraints.SOUTH, 1, 1,
//					GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5));
			
			GridBagConstraints gridBagConstraints2 = GridBagUtil.getGridBagConstraints(0, 2,
					80, 2, GridBagConstraints.CENTER, 1, 1,
					GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));
			
			GridBagConstraints gridBagConstraints1 = GridBagUtil.getGridBagConstraints(0, 1,
					0, 0, GridBagConstraints.NORTHWEST, 1, 1,
					GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5));
			
			GridBagConstraints gridBagConstraints0 = GridBagUtil.getGridBagConstraints(0, 0,
					0, 0, GridBagConstraints.NORTHWEST, 1, 1,
					GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5));
			
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jContentPane.setPreferredSize(new Dimension(850, 850));
			jContentPane.add(getJPanelHeader(), gridBagConstraints0);
			jContentPane.add(getJPanelTools(), gridBagConstraints1);
			jContentPane.add(getJPanelBody(), gridBagConstraints2);
//			jContentPane.add(getJPanelFooter(), gridBagConstraints3);
			
		}
		
		return jContentPane;
	}

	protected JPanel getJPanelHeader() {
		if (jPanelHeader == null) {			
			lbTitle = new JLabel("Simulador Redes de Petri");
			
			jPanelHeader = new JPanel();
			jPanelHeader.setLayout(new GridBagLayout());
			jPanelHeader.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			
//			jPanelHeader.add(getLbLogoImg(), GridBagUtil.getGridBagConstraints(1,
//					0, 1, 1, GridBagConstraints.CENTER, 1, 1,
//					GridBagConstraints.HORIZONTAL, new Insets(10, 25, 10, 40)));
//			
			jPanelHeader.add(lbTitle, GridBagUtil.getGridBagConstraints(0,
					0, 1, 1, GridBagConstraints.CENTER, 1, 1,
					GridBagConstraints.HORIZONTAL, new Insets(10, 25, 10, 5)));
		}
		
		return jPanelHeader;
	}

	protected JPanel getJPanelTools() {
		if (jPanelTools == null) {							
			GridBagConstraints gridBagConstraints1 = GridBagUtil.getGridBagConstraints(0, 0,
					0, 0, GridBagConstraints.NORTHWEST, 1, 1,
					GridBagConstraints.NONE, new Insets(5, 5, 5, 5));
						
			GridBagConstraints gridBagConstraints2 = GridBagUtil.getGridBagConstraints(1, 0,
					0, 0, GridBagConstraints.NORTHWEST, 1, 1,
					GridBagConstraints.NONE, new Insets(5, 5, 5, 5));
			
			
			jPanelTools = new JPanel();
			jPanelTools.setLayout(new GridBagLayout());
			jPanelTools.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
//			jPanelTools.add(getTfPath(), gridBagConstraints8);
			jPanelTools.add(getTbStatus(), gridBagConstraints1);
			jPanelTools.add(getBtBack(), gridBagConstraints2);
		}
		
		return jPanelTools;
	}

	protected JPanel getJPanelBody() {
		if (jPanelBody == null) {			
			GridBagConstraints gridBagConstraints0 = GridBagUtil.getGridBagConstraints(0, 0,
					10, 20, GridBagConstraints.WEST, 1, 1,
					GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));
			
			GridBagConstraints gridBagConstraints1 = GridBagUtil.getGridBagConstraints(0, 1,
					3, 2, GridBagConstraints.SOUTH, 1, 1,
					GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5));
			
			jPanelBody = new JPanel();
			jPanelBody.setLayout(new GridBagLayout());
			jPanelBody.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jPanelBody.add(getJPanelBody01(), gridBagConstraints0);
			jPanelBody.add(getJPanelBody02(), gridBagConstraints1);
//			jPanelBody.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
		}
		
		return jPanelBody;
	}

	protected JPanel getJPanelBody01() {
		if (jPanelBody01 == null) {			
			GridBagConstraints gridBagConstraints0 = GridBagUtil.getGridBagConstraints(0, 0,
					10, 2, GridBagConstraints.NORTH, 1, 1,
					GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));
			
			jPanelBody01 = new JPanel();
			jPanelBody01.setLayout(new GridBagLayout());
			jPanelBody01.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jPanelBody01.add(getEditorCanvas(), gridBagConstraints0);
			jPanelBody01.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
		}
		
		return jPanelBody01;
	}

	protected JPanel getJPanelBody02() {
		if (jPanelBody02 == null) {			
			GridBagConstraints gridBagConstraints0 = GridBagUtil.getGridBagConstraints(0, 0,
					10, 2, GridBagConstraints.NORTH, 1, 1,
					GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));
			GridBagConstraints gridBagConstraints1 = GridBagUtil.getGridBagConstraints(1, 0,
					10, 2, GridBagConstraints.NORTH, 1, 1,
					GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));
			GridBagConstraints gridBagConstraints2 = GridBagUtil.getGridBagConstraints(2, 0,
					10, 2, GridBagConstraints.NORTH, 1, 1,
					GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));
			
			
			jPanelBody02 = new JPanel();
			jPanelBody02.setLayout(new GridBagLayout());
			jPanelBody02.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			
			jPanelBody02.add(getJPanelBody02sub01(), gridBagConstraints0);
			jPanelBody02.add(getJPanelBody02sub02(), gridBagConstraints1);
			jPanelBody02.add(getJPanelBody02sub03(), gridBagConstraints2);
			jPanelBody02.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
		}
		
		return jPanelBody02;
	}

	protected JPanel getJPanelBody02sub01() {
		if (jPanelBody02sub01 == null) {			
			GridBagConstraints gridBagConstraints0 = GridBagUtil.getGridBagConstraints(0, 0,
					10, 2, GridBagConstraints.CENTER, 2, 1,
					GridBagConstraints.CENTER, new Insets(5, 5, 5, 5));
			GridBagConstraints gridBagConstraints1 = GridBagUtil.getGridBagConstraints(0, 1,
					1, 2, GridBagConstraints.WEST, 1, 1,
					GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));
			GridBagConstraints gridBagConstraints2 = GridBagUtil.getGridBagConstraints(1, 1,
					10, 2, GridBagConstraints.WEST, 1, 1,
					GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));
			GridBagConstraints gridBagConstraints3 = GridBagUtil.getGridBagConstraints(0, 2,
					1, 2, GridBagConstraints.WEST, 1, 1,
					GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));
			GridBagConstraints gridBagConstraints4 = GridBagUtil.getGridBagConstraints(1, 2,
					10, 2, GridBagConstraints.WEST, 1, 1,
					GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));
			
			jPanelBody02sub01 = new JPanel();
			jPanelBody02sub01.setLayout(new GridBagLayout());
			jPanelBody02sub01.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			
			jPanelBody02sub01.add(getLbTitlePlace(), gridBagConstraints0);
			jPanelBody02sub01.add(getLbPlaceName(), gridBagConstraints1);
			jPanelBody02sub01.add(getLbPlaceNameVal(), gridBagConstraints2);
			jPanelBody02sub01.add(getLbPlaceTokens(), gridBagConstraints3);
			jPanelBody02sub01.add(getLbPlaceTokensVal(), gridBagConstraints4);
			jPanelBody02sub01.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
		}
		
		return jPanelBody02sub01;
	}

	protected JPanel getJPanelBody02sub02() {
		if (jPanelBody02sub02 == null) {			
			GridBagConstraints gridBagConstraints0 = GridBagUtil.getGridBagConstraints(0, 0,
					10, 2, GridBagConstraints.NORTH, 2, 1,
					GridBagConstraints.CENTER, new Insets(5, 5, 5, 5));
			GridBagConstraints gridBagConstraints1 = GridBagUtil.getGridBagConstraints(0, 1,
					1, 2, GridBagConstraints.NORTHWEST, 1, 1,
					GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5));
			GridBagConstraints gridBagConstraints2 = GridBagUtil.getGridBagConstraints(1, 1,
					10, 2, GridBagConstraints.NORTHWEST, 1, 1,
					GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5));
			GridBagConstraints gridBagConstraints3 = GridBagUtil.getGridBagConstraints(0, 2,
					1, 2, GridBagConstraints.NORTHWEST, 1, 1,
					GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5));
			
			jPanelBody02sub02 = new JPanel();
			jPanelBody02sub02.setLayout(new GridBagLayout());
			jPanelBody02sub02.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			
			jPanelBody02sub02.add(getLbTitleTransition(), gridBagConstraints0);
			jPanelBody02sub02.add(getLbTransitionName(), gridBagConstraints1);
			jPanelBody02sub02.add(getLbTransitionNameVal(), gridBagConstraints2);
			jPanelBody02sub02.add(new JLabel(), gridBagConstraints3);
			jPanelBody02sub02.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
		}
		
		return jPanelBody02sub02;
	}

	protected JPanel getJPanelBody02sub03() {
		if (jPanelBody02sub03 == null) {			
			GridBagConstraints gridBagConstraints0 = GridBagUtil.getGridBagConstraints(0, 0,
					10, 2, GridBagConstraints.CENTER, 2, 1,
					GridBagConstraints.CENTER, new Insets(5, 5, 5, 5));
			GridBagConstraints gridBagConstraints1 = GridBagUtil.getGridBagConstraints(0, 1,
					1, 2, GridBagConstraints.WEST, 1, 1,
					GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));
			GridBagConstraints gridBagConstraints2 = GridBagUtil.getGridBagConstraints(1, 1,
					10, 2, GridBagConstraints.WEST, 1, 1,
					GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));
			GridBagConstraints gridBagConstraints3 = GridBagUtil.getGridBagConstraints(0, 2,
					1, 2, GridBagConstraints.WEST, 1, 1,
					GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));
			GridBagConstraints gridBagConstraints4 = GridBagUtil.getGridBagConstraints(1, 2,
					10, 2, GridBagConstraints.WEST, 1, 1,
					GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));
			GridBagConstraints gridBagConstraints5 = GridBagUtil.getGridBagConstraints(0, 3,
					1, 2, GridBagConstraints.WEST, 1, 1,
					GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));
			GridBagConstraints gridBagConstraints6 = GridBagUtil.getGridBagConstraints(1, 3,
					10, 2, GridBagConstraints.WEST, 1, 1,
					GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));
			
			jPanelBody02sub03 = new JPanel();
			jPanelBody02sub03.setLayout(new GridBagLayout());
			jPanelBody02sub03.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			
			jPanelBody02sub03.add(getLbTitleArc(), gridBagConstraints0);
			jPanelBody02sub03.add(getLbArcPlace(), gridBagConstraints1);
			jPanelBody02sub03.add(getLbArcPlaceVal(), gridBagConstraints2);
			jPanelBody02sub03.add(getLbArcTransition(), gridBagConstraints3);
			jPanelBody02sub03.add(getLbArcTransitionVal(), gridBagConstraints4);
			jPanelBody02sub03.add(getLbArcWeight(), gridBagConstraints5);
			jPanelBody02sub03.add(getLbArcWeightVal(), gridBagConstraints6);
			jPanelBody02sub03.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
		}
		
		return jPanelBody02sub03;
	}

	protected JPanel getJPanelFooter() {
		if (jPanelFooter == null) {			
			jPanelFooter = new JPanel();
			jPanelFooter.setLayout(new GridBagLayout());
			jPanelFooter.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jPanelFooter.add(new JLabel("GNU GPL"), GridBagUtil.getGridBagConstraints(0, 0));
		}
		
		return jPanelFooter;
	}

	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getJMenuFile());
			jJMenuBar.add(getJMenuProcess());
			jJMenuBar.add(getJMenuHelp());
		}
		
		return jJMenuBar;
	}

	private JMenu getJMenuFile() {
		if (jMenuFile == null) {
			jMenuFile = new JMenu();
			jMenuFile.setText("Arquivo");
			jMenuFile.add(getJMenuItemNew());
			jMenuFile.add(getJMenuItemOpen());
			jMenuFile.addSeparator();
			jMenuFile.add(getJMenuItemExit());
		}
		return jMenuFile;
	}

	private JMenu getJMenuProcess() {
		if (jMenuProcess == null) {
			jMenuProcess = new JMenu();
			jMenuProcess.setText("Processar");
			jMenuProcess.add(getJMenuItemAnalysis());
		}
		return jMenuProcess;
	}

	private JMenu getJMenuHelp() {
		if (jMenuHelp == null) {
			jMenuHelp = new JMenu();
			jMenuHelp.setText("Ajuda");
			jMenuHelp.add(getJMenuItemAbout());
		}
		return jMenuHelp;
	}

	protected JMenuItem getJMenuItemNew() {
		if (jMenuItemNew == null) {
			jMenuItemNew = new JMenuItem();
			jMenuItemNew.setText("Novo");
			jMenuItemNew.addActionListener(this);
			jMenuItemNew.setVisible(false);
		}
		return jMenuItemNew;
	}

	protected JMenuItem getJMenuItemOpen() {
		if (jMenuItemOpen == null) {
			jMenuItemOpen = new JMenuItem();
			jMenuItemOpen.setText("Abrir");
			jMenuItemOpen.addActionListener(this);
		}
		return jMenuItemOpen;
	}

	protected JMenuItem getJMenuItemExit() {
		if (jMenuItemExit == null) {
			jMenuItemExit = new JMenuItem();
			jMenuItemExit.setText("Sair");
			jMenuItemExit.addActionListener(this);
		}
		return jMenuItemExit;
	}

	protected JMenuItem getJMenuItemAnalysis() {
		if (jMenuItemAnalysis == null) {
			jMenuItemAnalysis = new JMenuItem();
			jMenuItemAnalysis.setText("Análise");
			jMenuItemAnalysis.addActionListener(this);
		}
		return jMenuItemAnalysis;
	}

	protected JMenuItem getJMenuItemAbout() {
		if (jMenuItemAbout == null) {
			jMenuItemAbout = new JMenuItem();
			jMenuItemAbout.setText("Sobre");
			jMenuItemAbout.addActionListener(this);
		}
		return jMenuItemAbout;
	}

	protected EditorCanvas getEditorCanvas() {
		if(editorCanvas == null){
			getPetriNetGraph(); // Cria a instancia
			
			editorCanvas = new EditorCanvas(this);
			editorCanvas.addMouseListener(editorCanvas);
		}
		
		return editorCanvas;
	}

	protected void setPetriNetGraph(PetriNetUI petriNetGraph) {
		this.rp = petriNetGraph;
	}

	protected PetriNetUI getPetriNetGraph() {
		if(rp == null){
			rp = new PetriNetUI();
		}
		
		return rp;
	}
	
	protected SimulationController getSimulation() {
		return simulation;
	}
	
	protected void setSimulation(SimulationController simulation) {
		this.simulation = simulation;
	}

	protected JLabel getLbTitlePlace() {
		if(lbTitlePlace == null){
			lbTitlePlace = new JLabel("Lugar");
		}
		
		return lbTitlePlace;
	}

	protected JLabel getLbPlaceName() {
		if(lbPlaceName == null){
			lbPlaceName = new JLabel("Nome:");
		}
		
		return lbPlaceName;
	}

	protected JLabel getLbPlaceNameVal() {
		if(lbPlaceNameVal == null){
			lbPlaceNameVal = new JLabel("");
		}
		
		return lbPlaceNameVal;
	}

	protected JLabel getLbPlaceTokens() {
		if(lbPlaceTokens == null){
			lbPlaceTokens = new JLabel("Fichas:");
		}
		
		return lbPlaceTokens;
	}

	protected JLabel getLbPlaceTokensVal() {
		if(lbPlaceTokensVal == null){
			lbPlaceTokensVal = new JLabel("");
		}
		
		return lbPlaceTokensVal;
	}

	protected JLabel getLbTitleTransition() {
		if(lbTitleTransition == null){
			lbTitleTransition = new JLabel("Transição");
		}
		
		return lbTitleTransition;
	}

	protected JLabel getLbTransitionName() {
		if(lbTransitionName == null){
			lbTransitionName = new JLabel("Nome:");
		}
		
		return lbTransitionName;
	}

	protected JLabel getLbTransitionNameVal() {
		if(lbTransitionNameVal == null){
			lbTransitionNameVal = new JLabel("");
		}
		
		return lbTransitionNameVal;
	}

	protected JLabel getLbTitleArc() {
		if(lbTitleArc == null){
			lbTitleArc = new JLabel("Arco");
		}
		
		return lbTitleArc;
	}

	protected JLabel getLbArcPlace() {
		if(lbArcPlace == null){
			lbArcPlace = new JLabel("Lugar:");
		}
		
		return lbArcPlace;
	}

	protected JLabel getLbArcPlaceVal() {
		if(lbArcPlaceVal == null){
			lbArcPlaceVal = new JLabel("");
		}
		
		return lbArcPlaceVal;
	}

	protected JLabel getLbArcTransition() {
		if(lbArcTransition == null){
			lbArcTransition = new JLabel("Transição:");
		}
		
		return lbArcTransition;
	}

	protected JLabel getLbArcTransitionVal() {
		if(lbArcTransitionVal == null){
			lbArcTransitionVal = new JLabel("");
		}
		
		return lbArcTransitionVal;
	}

	protected JLabel getLbArcWeight() {
		if(lbArcWeight == null){
			lbArcWeight = new JLabel("Peso:");
		}
		
		return lbArcWeight;
	}

	protected JLabel getLbArcWeightVal() {
		if(lbArcWeightVal == null){
			lbArcWeightVal = new JLabel("");
		}
		
		return lbArcWeightVal;
	}

	protected JToggleButton getTbStatus() {
		if (tbStatus == null) {
			tbStatus  = new JToggleButton();
			tbStatus.setText("ON");
			tbStatus.setToolTipText("Inicia a Simulação da Rede de Petri");
			tbStatus.addItemListener(this);
		}
		return tbStatus;
	}

	protected JButton getBtBack() {
		if (btBack == null) {
			btBack = new JButton();
			btBack.setText("Voltar");
			btBack.addActionListener(this);
			btBack.setVisible(false);
		}
		return btBack;
	}
	
	protected void turnOn(){
		getTbStatus().setText("OFF");
		getTbStatus().setToolTipText("Encerra a Simulação da Rede de Petri");
	}
	
	protected void turnOff(){
		getTbStatus().setText("ON");
		getTbStatus().setToolTipText("Inicia a Simulação da Rede de Petri");
	}
	
	protected void disableFields() {
		statusFields(false);
	}
	
	protected void enableFields() {
		statusFields(true);
	}
	
	private void statusFields(boolean status) {
		getTbStatus().setEnabled(status);
		getBtBack().setEnabled(status);
		
		getJMenuItemAnalysis().setEnabled(status);
	}

	public abstract void actionPerformed(ActionEvent e);
	
	public abstract void itemStateChanged(ItemEvent e);

	public void setSelectedPlace(PlaceUI place) {
//		this.place = place;
		
		getLbPlaceNameVal().setText(place.getName());
		getLbPlaceTokensVal().setText(String.valueOf(place.getTokens()));
	}

	public void setSelectedTransition(TransitionUI transition) {
//		this.transition = transition;
		
		getLbTransitionNameVal().setText(transition.getName());
	}

	public void setSelectedArc(ArcUI arc) {
//		this.arc = arc;
		
		getLbArcPlaceVal().setText(arc.getPlace().getName());
		getLbArcTransitionVal().setText(arc.getTransition().getName());
		getLbArcWeightVal().setText(String.valueOf(arc.getWeight()));
	}
	
	private void loadColors() {
		Font font = null;
		
		try {
			File url = new File("src/main/resources/desib.ttf");
			FileInputStream is = new FileInputStream(url.getAbsoluteFile());			
			
			font = Font.createFont(Font.TRUETYPE_FONT, is);
			font = font.deriveFont(Font.PLAIN, 30);
			is.close();
		} catch (Exception ex) {
			System.err.println("Erro ao carregar a fonte: " + ex.getMessage());
			font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
		}
		
		lbTitle.setFont(font);
		lbTitle.setForeground(new Color(0, 51, 102));
		
		Color colorUp = new Color(172, 210, 67);
		Color colorUp2 = new Color(209, 105, 67);
		Color colorUp3 = new Color(0, 51, 102);
		Color colorDefault = new Color(248,248,248);		
		
		getJPanelHeader().setBackground(colorUp);
		getJPanelTools().setBackground(colorDefault);
		getJPanelBody().setBackground(colorDefault);
		getJPanelBody01().setBackground(colorDefault);
		getJPanelBody02().setBackground(colorUp);
		getJPanelFooter().setBackground(colorDefault);
		getJPanelBody02sub01().setBackground(colorDefault);
		getJPanelBody02sub02().setBackground(colorDefault);
		getJPanelBody02sub03().setBackground(colorDefault);
		getJContentPane().setBackground(colorDefault);
		
		getLbArcPlace().setForeground(colorUp2);
		getLbArcTransition().setForeground(colorUp2);
		getLbArcWeight().setForeground(colorUp2);
		getLbPlaceName().setForeground(colorUp2);
		getLbPlaceTokens().setForeground(colorUp2);
		getLbTransitionName().setForeground(colorUp2);

		getLbArcPlaceVal().setForeground(colorUp3);
		getLbArcTransitionVal().setForeground(colorUp3);
		getLbArcWeightVal().setForeground(colorUp3);
		getLbPlaceNameVal().setForeground(colorUp3);
		getLbPlaceTokensVal().setForeground(colorUp3);
		getLbTransitionNameVal().setForeground(colorUp3);
	}
}
