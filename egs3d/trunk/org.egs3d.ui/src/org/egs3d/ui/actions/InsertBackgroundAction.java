/*
 * Cr�� le 6 mai 2006
 *
 * TODO Pour changer le mod�le de ce fichier g�n�r�, allez � :
 * Fen�tre - Pr�f�rences - Java - Style de code - Mod�les de code
 */
package org.egs3d.ui.actions;

import javax.media.j3d.Background;
import javax.media.j3d.Node;


/**
 * Ajoute un arri�re-plan
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
