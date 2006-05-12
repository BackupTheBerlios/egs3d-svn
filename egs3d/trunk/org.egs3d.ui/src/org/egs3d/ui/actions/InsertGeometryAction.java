/*
 * Créé le 6 mai 2006
 *
 * TODO Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre - Préférences - Java - Style de code - Modèles de code
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
