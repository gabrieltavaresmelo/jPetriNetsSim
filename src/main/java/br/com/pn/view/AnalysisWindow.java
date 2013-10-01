package br.com.pn.view;

import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;

import br.com.pn.business.base.PetriNet;
import br.com.pn.business.base.Place;
import br.com.pn.business.base.Transition;
import br.com.pn.business.gui.PetriNetUI;
import br.com.pn.business.simulator.PetriNetAnalyzer;
import br.com.pn.business.simulator.PetriNetState;
import br.com.pn.controller.PetriNetProperties;

public class AnalysisWindow extends AnalysisWindowView {

	private static final long serialVersionUID = 1L;
	
	public static int TAB = 3;

	public AnalysisWindow(PetriNet petriNet) {
		super(petriNet);
		
		loadData();
	}

	@Override
	protected void loadData() {
		super.loadData();
		
		try {
			setAnalyzer(new PetriNetAnalyzer(getPetriNet()));
			getAnalyzer().generateTreeReachability();
			
			setJTreeGraphReachability(getAnalyzer().getTreeModel());			
			getTextAreaAnalysis().setText(getStrProperties());
						
		} catch (Exception e) {
			dispose();
			
			JOptionPane.showMessageDialog(null, "Erro ao fazer análise da Rede de Petri",
					"Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static boolean verifyPn(PetriNet pn) {
		String strError = "Rede inválida para análise!";
		String strTitle = "Rede inválida";
		
		boolean error = false;
		
		if (pn == null) {
			error = true;
		} else if (pn.getNumPlace() == 0) {
			error = true;
		} else if (pn.getNumTransition() == 0) {
			error = true;
		} else if (pn.getNumArc() == 0) {
			error = true;
		}

		if (error == true) {
			JOptionPane.showMessageDialog(null, strError, strTitle,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) getJTreeGraphReachability().getLastSelectedPathComponent();

		if(node != null){
			PetriNetState nodeState = (PetriNetState) node.getUserObject();
			
			if(nodeState != null){
				getTextAreaInfo().setText(getStateInfo(nodeState, (PetriNetUI) getPetriNet()));
			} else{
				getTextAreaInfo().setText("Estado Nulo!");
			}
		} else{
			getTextAreaInfo().setText("Nó Nulo!");
		}
	}
	
	public static String getStateInfo(PetriNetState nodeState, PetriNetUI petriNet) {
		String SPACE = "   ";
		
		// Cria a string com a sequencia de disparos
		StringBuffer sbTriggerSeq = new StringBuffer();
		sbTriggerSeq.append("Sequência de Disparos\n");
		
		int[] triggersSeq = nodeState.getTriggerSequence();
		
		for (int i = 0; i < triggersSeq.length; i++) {
			Transition item = petriNet.getTransition(i);
			if(item != null){
				sbTriggerSeq.append(SPACE);
				sbTriggerSeq.append(i+1);
				sbTriggerSeq.append(" - ");
				sbTriggerSeq.append(item.getId());
				sbTriggerSeq.append(" - ");
				sbTriggerSeq.append(item.getName());
				sbTriggerSeq.append("\n");
			}
		}
		
		if(triggersSeq.length < 1){
			sbTriggerSeq.append(SPACE);
			sbTriggerSeq.append("Estado Inicial");
			sbTriggerSeq.append("\n");
		}
		
		// Cria uma string com as marcacoes dos lugares
		StringBuffer sbMarkers = new StringBuffer();
		sbMarkers.append("Marcações dos Estados\n");
		
		int[] markers = nodeState.getMarkers();
		
		for (int i = 0; i < petriNet.getNumPlace(); i++) {
			Place item = petriNet.getPlace(i);
			sbMarkers.append(SPACE);
			sbMarkers.append(item.getName());
			sbMarkers.append(" = ");
			sbMarkers.append(markers[i]);
			sbMarkers.append("\n");
		}
		
		// Cria uma string com a lista de trasicoes disponiveis
		StringBuffer sbTransitions = new StringBuffer();
		sbTransitions.append("Marcações dos Estados\n");
		
		boolean[] transitions = nodeState.getTriggerTransitions();
		boolean isTrasitionEnable = false;
		int cont = 0;
		
		for (int i = 0; i < petriNet.getNumTransition(); i++) {
			if(transitions[i]){
				isTrasitionEnable = true;
				cont++;
				
				Transition item = petriNet.getTransition(i);
				sbTransitions.append(SPACE);
				sbTransitions.append(cont);
				sbTransitions.append(") ");
				sbTransitions.append(item.getId());
				sbTransitions.append(" - ");
				sbTransitions.append(item.getName());
				sbTransitions.append("\n");
			}
		}
		
		if(!isTrasitionEnable){
			sbTransitions.append("Não há transições habilitadas para disparo.");
			sbTransitions.append("\n");			
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(sbTriggerSeq);
		sb.append("\n");
		sb.append(sbMarkers);
		sb.append("\n");
		sb.append(sbTransitions);
		
		return sb.toString();
	}
	
	public String getStrProperties() {
		String SPACE = "   ";
		
		StringBuffer strLimit = new StringBuffer();
		StringBuffer strConserve = new StringBuffer();
		StringBuffer strLive = new StringBuffer();
		StringBuffer strArrIn =  new StringBuffer();
		StringBuffer strArrOut =  new StringBuffer();
		StringBuffer strArrIncid =  new StringBuffer();
		StringBuffer strProp = new StringBuffer();
		
		PetriNetProperties pnProp = getAnalyzer().getProperties();

		String strNome = "Nome da Rede de Petri: " + pnProp.getNameNet();
		
		if (pnProp.getNameNet().toLowerCase().indexOf(".xml") > 0){
			strNome = strNome.substring(0, strNome.toLowerCase()
					.indexOf(".xml"));
		}
		
		if (pnProp.isOrdinary()) {
			strLimit.append("A rede é ordinária.");
		} else {
			strLimit.append("A rede não é ordinária.");
		}
		
		if (pnProp.isLimited()) {
			strLimit.append("\nA rede é limitada.");
			strLimit.append("\nLimite da rede igual à ");
			strLimit.append(pnProp.getLimit() + ".");
		} else {
			strLimit.append("\nA rede não é limitada.");
		}

		if (pnProp.isConservative()) {
			strConserve.append("A rede é conservativa.");
			strConserve.append("\ntotal de fichas da rede: ");
			strConserve.append(pnProp.getQtdTokens() + ".");
		} else {
			strConserve.append("A rede não é conservativa.");
		}

		String strSeq = "";
		
		if (pnProp.isLive()) {
			strLive.append("A rede é viva.");
			
		} else {
			strLive.append("A rede não é viva.");

//			strSeq = "Seqüência de disparos que levam a deadlock:";
//			
//			for (int i = 0; i < pnProp.getTotalDeadLockStates(); i++) {
//				strSeq += "\n" + "Sequência" + " " + (i + 1) + ":";
//				int[] arrSeq = pnProp.getTriggersSequenceDeadLock(i);
//				for (int j = 0; j < arrSeq.length; j++) {
//					Transition trans = (Transition) getPetriNet()
//							.getTransition(arrSeq[j]);
//					strSeq += "\n" + SPACE + (j + 1) + " - T" + arrSeq[j]
//							+ " - " + trans.getName();
//				}
//
//				strSeq += "\nLeva ao estado " + pnProp.getDeadLockState(i);
//				strSeq += "\n";
//			}
		}

		/* Exibe as Matrizes de Entrada, Saida e Incidencia */
		String strAux = "";
		int max = 0;
		int tam = 0;
		for (int i = 0; i < getPetriNet().getNumArc(); i++) {
			strAux = "" + getPetriNet().getArc(i).getWeight();
			tam = strAux.length();
			if (tam > max)
				max = tam;
		}
		
		TAB = max + 4;

		strArrIn.append("Matriz de Entrada:");
		strArrIn.append("\n");
		strArrIn.append(getArrayIn(getPetriNet().getArrayIn(), getPetriNet()
				.getNumTransition(), getPetriNet().getNumPlace()));
		
		strArrOut.append("Matriz de Saída:");
		strArrOut.append("\n");
		strArrOut.append(getArrayOut(getPetriNet().getArrayOut(), getPetriNet()
				.getNumTransition(), getPetriNet().getNumPlace()));
		
		strArrIncid.append("Matriz de Incidência:");
		strArrIncid.append("\n");
		strArrIncid.append(getArrayIncidence(getPetriNet().getArrayIncidence(),
				getPetriNet().getNumTransition(), getPetriNet()
				.getNumPlace()));
		
		strProp.append(strNome);
		strProp.append("\n");
		strProp.append(strLive);
		strProp.append("\n");
		strProp.append(strLimit);
		strProp.append("\n");
		strProp.append(strConserve);
		strProp.append("\n\n");
		strProp.append(strArrIn);
		strProp.append("\n\n");
		strProp.append(strArrOut);
		strProp.append("\n\n");
		strProp.append(strArrIncid);
		strProp.append("\n\n");
		strProp.append(strSeq);
		strProp.append("\n\n");

		return strProp.toString();
    }

	public String getArrayIn(int[][] in, int numTrans,
			int numPlace) {
		return arrToString(in, numTrans, numPlace);
	}

	public String getArrayOut(int[][] out, int numTrans, int numPlace) {
		return arrToString(out, numTrans, numPlace);
	}

	public String getArrayIncidence(int[][] incid, int numTrans,
			int numPlace) {
		return arrToString(incid, numTrans, numPlace);
	}

	public static String arrToString(int[][] arr, int numRow, int numCol) {
		String strReturn = "";

		/*
		 * Cada Linha representa uma transicao enquanto cada coluna representa
		 * um Lugar A iteracao linha/coluna representa o arco
		 */

		for (int c = 0; c < numCol; c++) {
			if (c == 0)
				strReturn += completeWithSpaces("", TAB + 1);
			strReturn += completeWithSpaces("P" + c, TAB);

		}

		for (int r = 0; r < numRow; r++) {
			strReturn += "\n";
			strReturn += completeWithSpaces("T" + r, TAB);

			for (int c = 0; c < numCol; c++) {
				strReturn += completeWithSpaces("" + arr[r][c],
						TAB);
			}

		}

		return strReturn;
	}

	public static String completeWithSpaces(String strIn, int qtdSpaces) {
		String strReturn = "";

		int totalSpaces = qtdSpaces - strIn.length();
		if (totalSpaces < 0)
			totalSpaces = 0;

		while (totalSpaces > 0) {
			strReturn += " ";
			totalSpaces--;
		}

		strReturn += strIn;
		return strReturn;

	}
}
