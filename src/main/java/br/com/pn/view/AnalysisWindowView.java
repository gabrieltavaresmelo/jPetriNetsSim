package br.com.pn.view;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import br.com.pn.business.base.PetriNet;
import br.com.pn.business.simulator.PetriNetAnalyzer;

public abstract class AnalysisWindowView extends JFrame implements TreeSelectionListener {

	private static final long serialVersionUID = 1L;

	// Widgets
	private JPanel jContentPane = null;
	private JPanel jPanelHeader = null;
	private JPanel jPanelBody = null;
	private JPanel jPanelBody01;
	private JPanel jPanelBody02;
	private JPanel jPanelBody03;
	private JLabel lbTitleTree = null;
	private JLabel lbTitleInfo = null;
	private JTree treeGraphReachability = null;
	private JScrollPane scrollPaneGraphReachability = null;
	private JTextArea textAreaInfo = null;
	private JScrollPane scrollPaneInfo = null;
	private JTextArea textAreaAnalysis = null;
	private JScrollPane scrollPaneAnalysis = null;

	private DefaultMutableTreeNode rootNode = null;
	private DefaultMutableTreeNode currentNode = null;

	private DefaultTreeModel treeGraphReachabilityModel = null;

	private PetriNet petriNet = null;
	private PetriNetAnalyzer analyzer = null;

	public AnalysisWindowView(PetriNet petriNet) {
		super();

		this.petriNet = petriNet;

		initialize();
		loadData();
		loadColors();
	}

	private void initialize() {
		this.setSize(900, 550);
		this.setContentPane(getJContentPane());
		this.setTitle("Simulação");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocation(
				((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - (this
						.getWidth() / 2)), ((Toolkit.getDefaultToolkit()
						.getScreenSize().height / 2) - (this.getHeight() / 2)));

		// URL url =
		// this.getClass().getClassLoader().getResource("resources/logo.png");
		// this.setIconImage(new ImageIcon(url).getImage());
	}

	protected JPanel getJContentPane() {
		if (jContentPane == null) {
			// GridBagConstraints gridBagConstraints3 =
			// GridBagUtil.getGridBagConstraints(0, 3,
			// 0, 0, GridBagConstraints.SOUTH, 1, 1,
			// GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5));

			GridBagConstraints gridBagConstraints2 = GridBagUtil
					.getGridBagConstraints(0, 2, 80, 2,
							GridBagConstraints.CENTER, 1, 1,
							GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));

			GridBagConstraints gridBagConstraints0 = GridBagUtil
					.getGridBagConstraints(0, 0, 0, 0,
							GridBagConstraints.NORTHWEST, 1, 1,
							GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5,
									0));

			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane
					.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jContentPane.setPreferredSize(new Dimension(850, 850));
			jContentPane.add(getJPanelHeader(), gridBagConstraints0);
			jContentPane.add(getJPanelBody(), gridBagConstraints2);

		}

		return jContentPane;
	}

	protected JPanel getJPanelHeader() {
		if (jPanelHeader == null) {
			jPanelHeader = new JPanel();
			jPanelHeader.setLayout(new GridBagLayout());
			jPanelHeader
					.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

			// jPanelHeader.add(lbTitle, GridBagUtil.getGridBagConstraints(0,
			// 0, 1, 1, GridBagConstraints.CENTER, 1, 1,
			// GridBagConstraints.HORIZONTAL, new Insets(10, 25, 10, 5)));
		}

		return jPanelHeader;
	}

	protected JPanel getJPanelBody() {
		if (jPanelBody == null) {
			GridBagConstraints gridBagConstraints0 = GridBagUtil
					.getGridBagConstraints(0, 0, 10, 20,
							GridBagConstraints.NORTHWEST, 1, 1,
							GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));

			GridBagConstraints gridBagConstraints1 = GridBagUtil
					.getGridBagConstraints(1, 0, 3, 20,
							GridBagConstraints.NORTHEAST, 1, 1,
							GridBagConstraints.BOTH, new Insets(5, 5, 5,
									5));

			GridBagConstraints gridBagConstraints2 = GridBagUtil
					.getGridBagConstraints(0, 1, 20, 20,
							GridBagConstraints.NORTHEAST, 2, 1,
							GridBagConstraints.BOTH, new Insets(5, 5, 5,
									5));

			jPanelBody = new JPanel();
			jPanelBody.setLayout(new GridBagLayout());
			jPanelBody
					.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jPanelBody.add(getJPanelBody01(), gridBagConstraints0);
			jPanelBody.add(getJPanelBody02(), gridBagConstraints1);
			jPanelBody.add(getJPanelBody03(), gridBagConstraints2);
			// jPanelBody.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		}

		return jPanelBody;
	}

	protected JPanel getJPanelBody01() {
		if (jPanelBody01 == null) {
			GridBagConstraints gridBagConstraints0 = GridBagUtil
					.getGridBagConstraints(0, 0, 10, 2,
							GridBagConstraints.NORTH, 1, 1,
							GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5));
			
			GridBagConstraints gridBagConstraints1 = GridBagUtil
					.getGridBagConstraints(0, 1, 10, 20,
							GridBagConstraints.NORTH, 1, 1,
							GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));

			jPanelBody01 = new JPanel();
			jPanelBody01.setLayout(new GridBagLayout());
			jPanelBody01.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jPanelBody01.add(getLbTitleTree(), gridBagConstraints0);
			jPanelBody01.add(getScrollPaneGraphReachability(), gridBagConstraints1);
//			jPanelBody01.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}

		return jPanelBody01;
	}

	protected JPanel getJPanelBody02() {
		if (jPanelBody02 == null) {
			GridBagConstraints gridBagConstraints0 = GridBagUtil
					.getGridBagConstraints(0, 0, 10, 2,
							GridBagConstraints.NORTH, 1, 1,
							GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5));
			
			GridBagConstraints gridBagConstraints1 = GridBagUtil
					.getGridBagConstraints(0, 1, 10, 20,
							GridBagConstraints.NORTH, 1, 1,
							GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));

			jPanelBody02 = new JPanel();
			jPanelBody02.setLayout(new GridBagLayout());
			jPanelBody02.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jPanelBody02.add(getLbTitleInfo(), gridBagConstraints0);
			jPanelBody02.add(getScrollPaneInfo(), gridBagConstraints1);
//			jPanelBody02.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		}

		return jPanelBody02;
	}

	protected JPanel getJPanelBody03() {
		if (jPanelBody03 == null) {
			GridBagConstraints gridBagConstraints1 = GridBagUtil
					.getGridBagConstraints(0, 0, 10, 20,
							GridBagConstraints.NORTH, 1, 1,
							GridBagConstraints.BOTH, new Insets(5, 5, 5, 5));

			jPanelBody03 = new JPanel();
			jPanelBody03.setLayout(new GridBagLayout());
			jPanelBody03.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jPanelBody03.add(getScrollPaneAnalysis(), gridBagConstraints1);
//			jPanelBody03.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		}

		return jPanelBody03;
	}
	
	public JLabel getLbTitleTree() {
		if(lbTitleTree == null){
			lbTitleTree = new JLabel("Grafo de Alcançabilidade");
		}
		return lbTitleTree;
	}
	
	public JLabel getLbTitleInfo() {
		if(lbTitleInfo == null){
			lbTitleInfo = new JLabel("Informações do Estado");
		}
		return lbTitleInfo;
	}

	protected JScrollPane getScrollPaneAnalysis() {
		if (scrollPaneAnalysis == null) {
			scrollPaneAnalysis = new JScrollPane(getTextAreaAnalysis());
			scrollPaneAnalysis
					.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPaneAnalysis
					.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		}

		return scrollPaneAnalysis;
	}

	protected JTextArea getTextAreaAnalysis() {
		if (textAreaAnalysis == null) {
			textAreaAnalysis = new JTextArea();
			textAreaAnalysis.setEditable(false);
		}

		return textAreaAnalysis;
	}

	protected JScrollPane getScrollPaneInfo() {
		if (scrollPaneInfo == null) {
			scrollPaneInfo = new JScrollPane(getTextAreaInfo());
			scrollPaneInfo
					.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPaneInfo
					.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		}

		return scrollPaneInfo;
	}

	protected JTextArea getTextAreaInfo() {
		if (textAreaInfo == null) {
			textAreaInfo = new JTextArea();
			textAreaInfo.setEditable(false);
		}

		return textAreaInfo;
	}

	protected JScrollPane getScrollPaneGraphReachability() {
		if (scrollPaneGraphReachability == null) {
			scrollPaneGraphReachability = new JScrollPane(
					getJTreeGraphReachability());
			scrollPaneGraphReachability.setAutoscrolls(true);
			scrollPaneGraphReachability
					.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPaneGraphReachability
					.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		}

		return scrollPaneGraphReachability;
	}
	
	protected void setJTreeGraphReachability(DefaultTreeModel defaultTreeModel){
		treeGraphReachability.setModel(defaultTreeModel);
		
		getJTreeGraphReachability().repaint();
		getScrollPaneGraphReachability().repaint();
	}

	protected JTree getJTreeGraphReachability() {
		if (treeGraphReachability == null) {
			treeGraphReachability = new JTree(getTreeGraphReachabilityModel());
			treeGraphReachability.setEditable(true);
			treeGraphReachability.getSelectionModel().setSelectionMode(
					TreeSelectionModel.SINGLE_TREE_SELECTION);
			treeGraphReachability.setShowsRootHandles(true);
			treeGraphReachability.addTreeSelectionListener(this);
		}

		return treeGraphReachability;
	}

	protected DefaultTreeModel getTreeGraphReachabilityModel() {
		if (treeGraphReachabilityModel == null) {
			treeGraphReachabilityModel = new DefaultTreeModel(getRootNode());
		}
		return treeGraphReachabilityModel;
	}

	protected DefaultMutableTreeNode getRootNode() {
		if (rootNode == null && analyzer != null) {
			rootNode = new DefaultMutableTreeNode(analyzer);
		}
		return rootNode;
	}
	
	public void setRootNode(DefaultMutableTreeNode rootNode) {
		this.rootNode = rootNode;
	}

	protected DefaultMutableTreeNode getCurrentNode() {
		if (currentNode == null) {
			currentNode = new DefaultMutableTreeNode();
		}
		return currentNode;
	}
	
	public void setCurrentNode(DefaultMutableTreeNode currentNode) {
		this.currentNode = currentNode;
	}

	protected void loadData() {
		currentNode = rootNode;

	}

	protected PetriNet getPetriNet() {
		return this.petriNet;
	}
	
	public void setAnalyzer(PetriNetAnalyzer analyzer) {
		this.analyzer = analyzer;
	}
	
	protected PetriNetAnalyzer getAnalyzer() {
		return analyzer;
	}
	
	public abstract void valueChanged(TreeSelectionEvent e);
	
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
		
		Color colorDefault = new Color(248,248,248);		
		
		getJPanelBody().setBackground(colorDefault);
		getJPanelBody01().setBackground(colorDefault);
		getJPanelBody02().setBackground(colorDefault);
		getJContentPane().setBackground(colorDefault);
	}
}