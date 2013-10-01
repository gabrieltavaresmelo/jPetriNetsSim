package br.com.pn;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import br.com.pn.view.MainWindow;

/**
 * Simulador de Redes de Petri
 * 
 * @author Gabriel Tavares
 *
 */
public class App {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {	
			public void run() {
				try {
				    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				        if ("Nimbus".equals(info.getName())) {
				            UIManager.setLookAndFeel(info.getClassName());
				            break;
				        }
				    }
				} catch (Exception e) {
				    // If Nimbus is not available, you can set the GUI to another look and feel.
					try {
						UIManager.setLookAndFeel(new MetalLookAndFeel());
					} catch (UnsupportedLookAndFeelException e1) {
						System.err.println(e1.getMessage());
					}
				}
				
				MainWindow.getInstance().setVisible(true);
			}
		});
	}

}
