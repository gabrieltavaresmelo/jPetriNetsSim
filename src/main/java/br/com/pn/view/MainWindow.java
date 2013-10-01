package br.com.pn.view;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import br.com.pn.business.gui.PetriNetUI;
import br.com.pn.controller.SimulationController;
import br.com.pn.util.Constants;
import br.com.pn.util.FileFilterXML;
import br.com.pn.util.ModeRun;

import com.thoughtworks.xstream.XStream;

public class MainWindow extends MainWindowView{
	
	private static final long serialVersionUID = 1L;
	
	// Singleton
	private static MainWindow instance = null;
		
	public MainWindow() {
		super();
		initialize();
		loadData();
	}
	
	public static MainWindow getInstance() {
		if (instance == null) {
			instance = new MainWindow();
		}
		return instance;
	}

	private void initialize() {
		this.addWindowListener(new WindowAdapter() {
			// Fecha a janela
			public void windowClosing (WindowEvent e) {
				
			}
		});
	}
	
	private void loadData() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(getJMenuItemNew())){
		
		} else if(e.getSource().equals(getJMenuItemOpen())){
			openFileWindow();
		
		} else if(e.getSource().equals(getJMenuItemExit())){
			this.dispose();
			System.exit(0);
			
		} else if(e.getSource().equals(getJMenuItemAnalysis())){
			AnalysisWindow analysisWindow = new AnalysisWindow(getPetriNetGraph());
			analysisWindow.setVisible(true);
						
		} else if(e.getSource().equals(getBtBack())){
			if(getSimulation() != null){
				getSimulation().goToState();	
				getEditorCanvas().repaint();
			}
			
		} else if(e.getSource().equals(getJMenuItemAbout())){
			String conteudo = "";

			conteudo += "Curso:";
			conteudo += "<br/>";
			conteudo += "Engenharia de Computação";
			conteudo += "<br/>";
			conteudo += "<br/>";
			
			conteudo += "Disciplina:";
			conteudo += "<br/>";
			conteudo += "Sistemas Embarcados 2";
			conteudo += "<br/>";
			conteudo += "21/09/2013";
			conteudo += "<br/>";
			conteudo += "<br/>";

			conteudo += "Trabalho:";
			conteudo += "<br/>";
			conteudo += "Redes de Petri";
			conteudo += "<br/>";
			conteudo += "<br/>";
			
			conteudo += "Aluno:";
			conteudo += "<br/>";
			conteudo += "Gabriel Tavares";
			conteudo += "<br/>";
			conteudo += "gabrieltavaresmelo@gmail.com";
			conteudo += "<br/>";
			conteudo += "https://github.com/gabrieltavaresmelo";
			conteudo += "<br/>";
			conteudo += "<br/>";

//		    URL url = this.getClass().getClassLoader().getResource("resources/logo.png");
			File url = new File("resources/logo.png");
		    
			JOptionPane.showMessageDialog(null, "<html><body>" + conteudo
					+ "</body></html>", "Redes de Petri", 
					JOptionPane.INFORMATION_MESSAGE,
				    new ImageIcon(url.getAbsolutePath())
			);
		}
	}

	private void openFileWindow() {
		JFileChooser chooser = new JFileChooser();

		chooser.setCurrentDirectory(new File("."));	
		chooser.addChoosableFileFilter(new FileFilterXML());
		chooser.setAcceptAllFileFilterUsed(false);
		
		if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
			File file = chooser.getSelectedFile();
			openFileXML(file);
		}
	}

	private void openFileXML(File file) {
		try {			
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			InputStreamReader isr = new InputStreamReader(fis);
			
			XStream xStream = new XStream();
			setPetriNetGraph((PetriNetUI) xStream.fromXML(isr));
			
			getPetriNetGraph().deselectArc();
			getPetriNetGraph().deselectPlace();
			getPetriNetGraph().deselectTransition();
			
			isr.close();
			fis.close();
			
			// Operacoes na View
			setTitle(file.getName());			
			getEditorCanvas().repaint();
			
			enableFields();
			
		} catch (FileNotFoundException e) {
			disableFields();
			JOptionPane.showMessageDialog(null, 
					"Arquivo não encontrado!",
					"Erro", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (IOException e) {
			disableFields();
			JOptionPane.showMessageDialog(null, 
					"Arquivo não encontrado!",
					"Erro", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (Exception e) {
			disableFields();
			JOptionPane.showMessageDialog(null, 
					"Verifique se as TAGS do arquivo XML estão corretas!",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource().equals(getTbStatus())){
			if(e.getStateChange() == java.awt.event.ItemEvent.SELECTED){
				turnOn();

				setSimulation(new SimulationController(getPetriNetGraph()));
				
				getEditorCanvas().setMode(ModeRun.SIMULATION);
				getEditorCanvas().setSubMode(Constants.SIM_START);
				getEditorCanvas().repaint();
				
				getSimulation().showSimulationWindow();
				// TODO MOSTRAR NUM LABEL QUE ESTA NO MODO SIMULACAO
				// TODO SimulationWindow
				
			} else if(e.getStateChange() == java.awt.event.ItemEvent.DESELECTED){
				turnOff();

				getSimulation().closeSimulationWindow();
				
				getEditorCanvas().setMode(ModeRun.NORMAL); //MODO_EDICAO
				getEditorCanvas().setSubMode(Constants.EDIT_MOUSE);
				getEditorCanvas().repaint();
				
				setSimulation(null);
			}
		}		
	}
}
