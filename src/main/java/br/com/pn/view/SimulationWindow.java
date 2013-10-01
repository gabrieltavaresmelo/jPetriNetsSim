package br.com.pn.view;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import br.com.pn.business.base.Place;
import br.com.pn.business.base.Transition;
import br.com.pn.business.gui.PetriNetUI;
import br.com.pn.business.simulator.TreeNodeState;

public class SimulationWindow extends SimulationWindowView{

	private static final long serialVersionUID = 1L;
	
	public SimulationWindow(PetriNetUI petriNet, TreeNodeState treeNodeState) {
		super(petriNet, treeNodeState);
	}
	
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) getJTreeGraphReachability().getLastSelectedPathComponent();

		if(node != null){
			TreeNodeState nodeState = (TreeNodeState) node.getUserObject();
			
			if(nodeState != null){
				getTextAreaInfo().setText(getStateInfo(nodeState, getPetriNet()));
			} else{
				getTextAreaInfo().setText("Estado Nulo!");
			}
		} else{
			getTextAreaInfo().setText("Nó Nulo!");
		}
	}
	
	/**
	 * Adiciona um novo no' no final da arvore de sequencia de disparos
	 * 
	 * @param No
	 */
	public void addTreeNodeState(TreeNodeState state) {
		DefaultMutableTreeNode node = null;
		TreeNodeState test = null;
		boolean founded = false;

		for (int i = 0; i < getCurrentNode().getChildCount(); i++) {
			node = (DefaultMutableTreeNode) getCurrentNode().getChildAt(i);
			test = (TreeNodeState) node.getUserObject();
			
			if (test.equals(state)) {
				founded = true;
				setCurrentNode(node);
				break;
			}
		}

		if (!founded) {
			setCurrentNode(addObject(getCurrentNode(), state));
		}
		
		getJTreeGraphReachability().setSelectionPath(
				new TreePath(getCurrentNode().getPath()));
	}
	
	/**
	 * Adiciona um novo no' na arvore de sequencia de disparos
	 * 
	 * @param parentNode
	 * @param childState
	 * @return
	 */
	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parentNode, Object childState) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(childState);

		parentNode = parentNode == null ? getRootNode() : parentNode;
			
		getTreeGraphReachabilityModel().insertNodeInto(childNode, parentNode, parentNode.getChildCount());
		getJTreeGraphReachability().scrollPathToVisible(new TreePath(childNode.getPath()));
		
		return childNode;
	}
	
	/**
	 * Volta um no' na arvore de sequencia de disparos
	 * 
	 * @return
	 */
	public TreeNodeState backTreeStateNode() {
		setCurrentNode(getCurrentNode().getPreviousNode());
		getJTreeGraphReachability().setSelectionPath(new TreePath(getCurrentNode().getPath()));
		
		return (TreeNodeState) getCurrentNode().getUserObject();
	}
	
	/**
	 * Devolve o estado selecionado
	 * 
	 * @return
	 */
	public TreeNodeState getSelectedState() {
		Object nodeObj = getJTreeGraphReachability()
				.getLastSelectedPathComponent();
		
		if(nodeObj != null){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) nodeObj;
			
			Object obj = node.getUserObject();
			
			if(obj != null){
				return (TreeNodeState) obj;
			}
		}
		
		return null;
	}
	
	/**
	 * Altera o estado atual para o selecionado
	 */
	public void changeAtualForSelectedState() {
		setCurrentNode((DefaultMutableTreeNode) getJTreeGraphReachability()
				.getLastSelectedPathComponent());
	}

	public static String getStateInfo(TreeNodeState state, PetriNetUI petriNet) {
		String SPACE = "   ";
		
		// Cria a string com a sequencia de disparos
		StringBuffer sbTriggerSeq = new StringBuffer();
		sbTriggerSeq.append("Sequência de Disparos\n");
		
		int[] triggersSeq = state.getTriggersSequence();
		
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
		
		int[] markers = state.getMarkers();
		
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
		
		boolean[] transitions = state.getTriggerTransitions();
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

	public void refreshText() {		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) getJTreeGraphReachability()
				.getLastSelectedPathComponent();
		
    	if(node != null){
    		TreeNodeState state = (TreeNodeState) node.getUserObject();

    		if (state != null) {
    			getTextAreaInfo().setText(getStateInfo(state, getPetriNet()));
    		} else{
    			getTextAreaInfo().setText("");
    		}
    	}
	}

}
