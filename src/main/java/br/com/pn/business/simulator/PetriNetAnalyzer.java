package br.com.pn.business.simulator;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import br.com.pn.business.base.Arc;
import br.com.pn.business.base.PetriNet;
import br.com.pn.controller.PetriNetProperties;
import br.com.pn.util.Constants;

public class PetriNetAnalyzer {

	private PetriNetSimulator simulator = null;
	private PetriNetProperties properties = null;
	
	private PetriNet petriNet = null;

	
	/**
	 * No raiz da arvore de alcancabilidade
	 */
	private DefaultMutableTreeNode root = null;
	
	/**
	 * Model usado para tratar a arvore de alcancabilidade, apenas prepara
	 * a arvore para ser usada na interface grafica.
	 */
	private DefaultTreeModel treeModel = null;
	
	
	public PetriNetAnalyzer(PetriNet petriNet) {
		this.petriNet = petriNet;
		
		if(this.petriNet != null){
			this.petriNet.buildArrayInOut();
			this.petriNet.buildArrayIncidence();
			
			simulator = new PetriNetSimulator(this.petriNet);
			properties = new PetriNetProperties(this.petriNet.getName());
			
			PetriNetState.setTotalState(0);
			PetriNetState rootState = new PetriNetState(this.petriNet.getArrayMarkers(),
					simulator.getAvailableTransitions(), 
					simulator.isAvailableTransition(),
					new int[0]);
			
			root = new DefaultMutableTreeNode(rootState);
			treeModel = new DefaultTreeModel(root);
			
			properties.setOrdinary(verifyOrdinary());
		}
	}
	
	/**
	 * Gera a arvore de alcancabilidade da Rede de Petri e
	 * verifica propriedades.
	 * <PRE>
	 * Algoritmo: 
	 *  root = Estado Inicial;
	 *  processNode(root);
	 *  verifyProperties(root);
	 *  geraArvore(root);
	 * </PRE>
	 */
	public void generateTreeReachability() {
		PetriNetState rootState = (PetriNetState) root.getUserObject();
		properties.setQtdTokens(rootState.getTotalMarking());

		processNode(root);
		verifyProperties((PetriNetState) root.getUserObject());
		generateTree(root);
	}
	
	/**
	 * Funcao recursiva, que vai varrendo a arvore que vai sendo criada, e processando
	 * seus nos.
	 * <PRE>
	 * Algoritmo: Seja N o no recebido como parametro.
	 *  Para i de 1 ate Numero de filhos de N Faca
	 *     X = N.getFilho(i);
	 *     processNode(X);
	 *     geraArvore(X);
	 *     verifyProperties(X);
	 *  Fim Para
	 * </PRE>
	 
	 * @param node No pai, cujos filhos serao processados.
	 */
	public void generateTree(DefaultMutableTreeNode node) {
		for (int i = 0; i < node.getChildCount(); i++) {
			processNode((DefaultMutableTreeNode) node.getChildAt(i));
			generateTree((DefaultMutableTreeNode) node.getChildAt(i));
			PetriNetState stateX = (PetriNetState) ((DefaultMutableTreeNode) node
					.getChildAt(i)).getUserObject();
			verifyProperties(stateX);
		}
	}
	
	/**
	 * Processa o No da arvore de alcancabilidade
	 * <PRE>
	 * Algoritmo:
	 * Seja o X o no a ser processado.
	 * 1) Se existe um no Y na arvore que nao e' fronteira e tem a mesma marcacao que X.
	 *    entao X e' um no DUPLICADO.
	 *
	 *   Para cada no Y da arvore Faca
	 *      Se ((A(Y) = A(X)) E (Y != X) E (Y.tipo != FRONTEIRA)) Entao 
	 *         X.tipo = DUPLICADO; 
	 *         retorna;
	 *      Fim Se
	 *   Fim Para.
	 * 
	 * 2) Se nao existem transicoes sensibilizadas associadas a marcacao A(x),
	 * ou seja {d (A(x),tj) indefinida PARA todo tj pertencente a T};
	 * entao X e' um no TERMINAL.
	 *  
	 *   Se (X.temTransicaoDisponivel == false) Entao
	 *      X.tipo = TERMINAL;
	 *      retorna;
	 *   Fim Se
	 *    
	 * 3) Para todas as transicoes tj Pertencente a T, que sao habilitadas em A[x],
	 * cria-se um novo no Z, na arvore de alcancabilidade, ligados ao primeiro,
	 * atraves de um arco ao qual se associa a transicao disparada.
	 * 
	 * Um arco, designado tj e' dirigido do no X para o no Z. O no X e' redefinido como no INTERIOR
	 * e Z, torna-se um no FRONTEIRA.
	 *
	 *  Para cada transicao habilitada  T de X Faca
	 *     Z = createNewState(X);
	 *     Z.tipo = FRONTEIRA;
	 *     AdicionaNoNaArvore(Z);
	 *  Fim Para
	 *  X.tipo = INTERIOR;
	 *
	 * </PRE>
	 */
	public void processNode(DefaultMutableTreeNode nodeX) {
		PetriNetState stateX = (PetriNetState) nodeX.getUserObject();

		/*
		 * 1) Se existe um no Y na arvore que nao e' fronteira e tem a mesma
		 * marcacao que X, A(x) = A(y), entao x e' um no duplicado. X e'
		 * DUPLICADO
		 */
		Enumeration conjNode = root.breadthFirstEnumeration();
		while (conjNode.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) conjNode
					.nextElement();
			PetriNetState stateY = (PetriNetState) node.getUserObject();

			if ((stateX.equals(stateY)) && (stateX.getId() != stateY.getId())
					&& (stateY.getStateType() != Constants.STATE_FRONTEIRA)) {
				stateX.setStateType(Constants.STATE_DUPLICADO);
				stateX.setEqualsState(stateY.getId());

				return;
			}
		}

		/*
		 * 2) Se nao existem transicoes sensibilizadas associadas a marcacao
		 * A(x), ou seja {d (A(x),tj)  indefinida PARA todo tj pertencente a
		 * T}; entao X e' um no TERMINAL.
		 */
		if (!stateX.isEnableTransition()) {
			stateX.setStateType(Constants.STATE_TERMINAL);

			return;
		}

		/*
		 * 3) Para todas as transicoes tj Pertencente a T, que sao habilitadas
		 * em A[x], cria-se um novo no' Z, na arvore de alcancabilidade, ligados
		 * ao primeiro, atraves de um arco ao qual se associa a transicao
		 * disparada.
		 * 
		 * Um arco, designado tj e' dirigido do no X para o no' Z. O no' X e'
		 * redefinido como no' INTERIOR e Z, torna-se um no' FRONTEIRA.
		 */
		boolean[] arrTransFire = stateX.getTriggerTransitions();
		for (int i = 0; i < arrTransFire.length; i++) {
			if (arrTransFire[i] == true) {
				PetriNetState stateZ = createNewState(nodeX, i);
				stateZ.setStateType(Constants.STATE_FRONTEIRA);
				addObject(nodeX, stateZ);
			}
		}

		stateX.setStateType(Constants.STATE_INTERIOR);

	}

	/**
	 * Adiciona um novo no na arvore de sequencia de disparos.
	 * 
	 * @param parent No pai
	 * @param child  Estado filho
	 * @return No criado
	 */
	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
			Object child) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

		if (parent == null) {
			parent = root;
		}

		treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

		return childNode;
	}
	
	/**
	 * Cria um Novo Estado para a arvore de alcancabilidade.
	 * <BR>A marcacao do novo estado criado, estado Z, segue a seguinte regra:
	 * <BR>Inicialmente marcacao de Z e' o resultado do disparo da transicao tj a partir do estado X.
	 * <BR>Se existe um no Y no percurso da raiz ate X cuja marcacao de Z seja superior
	 * A marcacao resultante do disparo da transicao isto e':
	 * <BR> A[z] > A[y] e A[z]i > A[y]i, Entao A[z]i = w. 
	 * <BR>O simbolo 'w' representa um crescimento de fichas em determinado lugar da rede.
	 * <PRE>
	 * Algoritmo: Seja X o pai de Z na arvore, e Z o no a ser processado.
	 * Z = Disparo de Tj a partir de X;
	 * Y = X;
	 * Enquanto ( Y <> null ) Faca
	 *   Se Marcacao(Z) > Marcacao(Y) Entao
	 *     Para i de 1 ate total de lugares Faca
	 *       Se (Z.lugar[i].fichas > Y.lugar[i].fichas) Entao
	 *         Z.lugar[i].fichas = w;
	 *     Fim Para
	 *   Fim Se
	 *		
	 *   Y = Y.getPai();
	 *		
	 * Fim Enquanto
	 *
	 * retorna Z;
	 * </PRE>
	 * 
	 * @param nodeX No pai
	 * @param transicao Numero da transicao disparada
	 * @return Estado apos disparo da transicao.
	 */
	public PetriNetState createNewState(DefaultMutableTreeNode nodeX,
			int transicao) {
		PetriNetState stateX = (PetriNetState) nodeX.getUserObject();
		simulator.setState(stateX.getMarkers(),
				stateX.getTriggerTransitions());

		/* Monta o Array com a sequencia de disparos */
		int[] arrFireSequenceX = stateX.getTriggerSequence();
		int[] arrFireSequence = new int[arrFireSequenceX.length + 1];
		for (int i = 0; i < arrFireSequenceX.length; i++) {
			arrFireSequence[i] = arrFireSequenceX[i];
		}
		arrFireSequence[arrFireSequence.length - 1] = transicao;

		simulator.triggerTransition(transicao);
		PetriNetState stateZ = new PetriNetState(simulator.getMarkers(),
				simulator.getAvailableTransitions(),
				simulator.isAvailableTransition(), arrFireSequence);

		int totalState = PetriNetState.getTotalState();

		PetriNetState copyZ = new PetriNetState(simulator.getMarkers(),
				simulator.getAvailableTransitions(),
				simulator.isAvailableTransition(), arrFireSequence);

		PetriNetState.setTotalState(totalState);
		
		/*
		 * Se existe um no Y no percurso da raiz ate X cuja marcacao seja inferior 
		 * a marcacao resultante do disparo da transicao isto e':
		 * 
		 * Marcacao do estado Y < Marcacao apos disparo da transicao do Estado X
		 * E
		 * Marcacao do Estado Y no Lugar i < Marcacao do Lugar i apos disparo da
		 * transicao do Estado X
		 * Entao 
		 * 		Marcacao do Lugar i no Estado Z = w
		 * 
		 * A [y] < d (A [x], tj) e 
		 * A [y]i< d (A [x], tj) i, entao d [z] i = w
		 * 	 
		 */ 			
		DefaultMutableTreeNode nodeY = nodeX;
		PetriNetState stateA = (PetriNetState) nodeY.getUserObject();

		while (nodeY != null) {
			PetriNetState stateY = (PetriNetState) nodeY.getUserObject();
			if ((compareMarking(copyZ, stateY) > 0)) {
				for (int i = 0; i < stateZ.getMarkers().length; i++) {
					if (copyZ.getMarker(i) > stateY.getMarker(i))
						stateZ.setMarker(i, Constants.TOKEN_INFINITO);
				}
			}

			nodeY = (DefaultMutableTreeNode) nodeY.getParent();
		}

		return stateZ;
	
	}
	
	/**
	* Metodo que compara a marcacao de 2 estados recebidos como parametro.
	* <UL>
	* <LI>Se marcacao do estado A > Marcacao do estado B retorna +1
	* <LI>Se marcacao do estado A = Marcacao do estado B retorna  0
	* <LI>Se marcacao do estado A < Marcacao do estado B retorna -1
	* </UL>
	* <pre>
	* Algoritmo: ComparaMarcacao(A,B) <--> [Marcacao(A) > Marcacao(B)]
	*
	* boolean existe = false;  // Existe um lugar de A que possui fichas > Lugar de B
	* boolean maior  = true;   // Todo lugar de A possui fichas > Todo Lugar de B
	*
	* Para i de 1 ate numero de lugares Faca:
	*    Se (A.lugar[i].fichas >= B.lugar[i].fichas) E (maior = true)Entao
	*       maior = true;
	*       Se (A.lugar[i].fichas > B.lugar[i].fichas) 
	*           existe = true;
	*    Senao
	*       maior = false;
	*    Fim Se
	* Fim Para
	*
	* Se ( maior = true ) E ( existe = true) Entao
	*    retorna 1;
	* Se ( maior = true ) E ( existe = false ) Entao
	*    retorna 0;
	* Se (maior = false) Entao
	*    retorna -1;
	* </pre>
	* @param stateA Estado A
	* @param stateB Estado B
	* @return resultado da comparacao.
	*/
	public static int compareMarking(PetriNetState stateA, PetriNetState stateB) {

		boolean existe = false;
		boolean maior = true;

		int[] arrMarkingA = stateA.getMarkers();
		int[] arrMarkingB = stateB.getMarkers();

		for (int i = 0; ((i < arrMarkingA.length) && (maior == true)); i++) {
			if ((arrMarkingA[i] >= arrMarkingB[i]) && (maior == true)) {
				maior = true;
				if (arrMarkingA[i] > arrMarkingB[i])
					existe = true;
			} else {
				maior = false;
			}
		}

		if ((maior == true) && (existe == true))
			return 1;

		if ((maior == true) && (existe == false))
			return 0;

		return -1;
	}
	
	/**
	 * Verifica varias propriedades da Rede de Petri.
	 * <OL>
	 * <LI>Limitacao
	 * <LI>Conservacao
	 * <LI>Vivacidade
	 * </OL> 
	 * @param stateX Estado a ser verificado
	 */
	public void verifyProperties(PetriNetState stateX) {
		verifyLimit(stateX);
		verifyConservative(stateX);
		verifyLimit(stateX);
		verifyVivacity(stateX);
	}
	
	/**
	 * Uma rede e' limitada, se para todas as marcacoes acessiveis, o numero de fichas
	 * em qualquer lugar da Rede nao exceder K (inteiro - limite).
	 * O algoritmo parte do principio que ela e' limitada. Se ela em algum momento
	 * deixar de ser limitada, ele para de testar qual o limite. E' ilimitada quando
	 * for encontrado em algum Lugar um numero infinito de fichas (w).
	 * 
	 */
	public void verifyLimit(PetriNetState stateX) {
		if (properties.isLimited()) {
			if (properties.getLimit() < stateX.getLimit()) {
				properties.setLimit(stateX.getLimit());
			}
		}
	}
	
	/**
	 * Uma Rede e' conservativa se o total de fichas na rede se mantem.
	 * O algoritmo parte do principio que a rede r' conservativa, se ela deixar de ser conservativa
	 * ele para de fazer o teste.
	 */
	public void verifyConservative(PetriNetState stateX) {
		if (properties.isConservative()) {
			if (properties.getQtdTokens() != stateX.getTotalMarking()) {
				properties.setConservative(false);
			}
		}
	}
	
	/**
	 * Uma rede e' viva se pelo menos uma transicao puder ser disparada, em qualquer
	 * estado, ou seja ela e' morta, se chegar a algum estado que nao tenha transicoes
	 * para disparar.
	 * @param stateX Estado a ser verificado.
	 */
	public void verifyVivacity(PetriNetState stateX) {
		if (!stateX.isEnableTransition()) {
			properties.addDeadLock(stateX.getTriggerSequence(),
					stateX.getId());
			properties.setLive(false);
		}
	}
	
	public boolean verifyOrdinary() {
		for (Arc arc : this.petriNet.getArcs()) {
			if(arc.getWeight() > 1){
				return false;
			}
		}
		
		return true;
	}

	public DefaultTreeModel getTreeModel() {
		return treeModel;
	}
	
	public PetriNetProperties getProperties() {
		return properties;
	}
}
