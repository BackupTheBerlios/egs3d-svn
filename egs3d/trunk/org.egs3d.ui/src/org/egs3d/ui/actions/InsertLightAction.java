/*
 * Cr�� le 6 mai 2006
 *
 * TODO Pour changer le mod�le de ce fichier g�n�r�, allez � :
 * Fen�tre - Pr�f�rences - Java - Style de code - Mod�les de code
 */
package org.egs3d.ui.actions;

import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Node;


/**
 * Ajoute une lumi�re
 * 
 * @author brachet
 */
public class InsertLightAction extends AbstractInsertAction {
	@Override
	protected Node createNode() {
		// TODO choisir le type de lumi�re
		return new DirectionalLight();
	}
}
