/*
 * Créé le 6 mai 2006
 *
 * TODO Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre - Préférences - Java - Style de code - Modèles de code
 */
package org.egs3d.ui.actions;

import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Node;

import org.eclipse.ui.IWorkbenchWindow;


/**
 * Ajoute une lumière
 * 
 * @author brachet
 */
public class InsertLightAction extends AbstractInsertAction {
	@Override
	protected Node createNode(IWorkbenchWindow window) {
		// TODO choisir le type de lumière
		return new DirectionalLight();
	}
}
