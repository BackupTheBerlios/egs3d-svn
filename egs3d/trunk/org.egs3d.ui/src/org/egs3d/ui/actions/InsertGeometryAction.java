/*
 * Cr�� le 6 mai 2006
 *
 * TODO Pour changer le mod�le de ce fichier g�n�r�, allez � :
 * Fen�tre - Pr�f�rences - Java - Style de code - Mod�les de code
 */
package org.egs3d.ui.actions;

import javax.media.j3d.Node;

import com.sun.j3d.utils.geometry.ColorCube;


/**
 * Ajoute une forme
 * 
 * @author brachet
 */
public class InsertGeometryAction extends AbstractInsertAction {
	@Override
	protected Node createNode() {
		return new ColorCube();
	}
}
