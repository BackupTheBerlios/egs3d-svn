/*
 * Cr�� le 6 mai 2006
 *
 * TODO Pour changer le mod�le de ce fichier g�n�r�, allez � :
 * Fen�tre - Pr�f�rences - Java - Style de code - Mod�les de code
 */
package org.egs3d.ui.actions;

import javax.media.j3d.Background;
import javax.media.j3d.Node;

import org.eclipse.ui.IWorkbenchWindow;


/**
 * Ajoute un arri�re-plan
 * 
 * @author brachet
 */
public class InsertBackgroundAction extends AbstractInsertAction {
	@Override
	protected Node createNode(IWorkbenchWindow window) {
		// TODO choisir l'image de fond
		return new Background();
	}
}
