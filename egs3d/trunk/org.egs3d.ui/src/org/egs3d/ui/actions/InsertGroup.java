/*
 * Créé le 18 mai 2006
 *
 */

package org.egs3d.ui.actions;

import javax.media.j3d.Group;
import javax.media.j3d.Node;

import org.eclipse.ui.IWorkbenchWindow;


/**
 * Ajoute d'un groupe
 * 
 * @author brachet
 */
public class InsertGroup extends AbstractInsertAction {
	@Override
	protected Node createNode(IWorkbenchWindow window) {
		return new Group();
	}
}