/*
 * Créé le 6 mai 2006
 *
 * TODO Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre - Préférences - Java - Style de code - Modèles de code
 */
package org.egs3d.ui.actions;

import javax.media.j3d.Background;
import javax.media.j3d.Node;


/**
 * Ajoute un arrière-plan
 * 
 * @author brachet
 */
public class InsertBackgroundAction extends AbstractInsertAction {
	@Override
	protected Node createNode() {
		// TODO choisir l'image de fond
		return new Background();
	}
}
