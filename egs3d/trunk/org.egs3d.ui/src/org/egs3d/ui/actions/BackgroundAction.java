/*
 * Cr�� le 6 mai 2006
 *
 * TODO Pour changer le mod�le de ce fichier g�n�r�, allez � :
 * Fen�tre - Pr�f�rences - Java - Style de code - Mod�les de code
 */
package org.egs3d.ui.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;


/**
 * Ajoute un arri�re-plan
 * 
 * @author brachet
 */
public class BackgroundAction implements IWorkbenchWindowActionDelegate {
    private final Log log = LogFactory.getLog(getClass());
    
	public void dispose() {
		// TODO Raccord de m�thode auto-g�n�r�

	}

	public void init(IWorkbenchWindow window) {
		// TODO Raccord de m�thode auto-g�n�r�

	}

	public void run(IAction action) {
		// TODO Raccord de m�thode auto-g�n�r�
		log.info("Ajout d'un arri�re-plan");
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Raccord de m�thode auto-g�n�r�

	}

}
