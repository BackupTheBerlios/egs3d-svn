/*
 * Créé le 6 mai 2006
 *
 * TODO Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre - Préférences - Java - Style de code - Modèles de code
 */
package org.egs3d.ui.actions;

import javax.media.j3d.Background;
import javax.media.j3d.Node;

import org.eclipse.ui.IWorkbenchWindow;


/**
 * Ajoute un arrière-plan
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
