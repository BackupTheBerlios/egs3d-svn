/*
 * Créé le 18 mai 2006
 *
 */

package org.egs3d.ui.actions;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;

import org.eclipse.ui.IWorkbenchWindow;


/**
 * Ajoute d'un BranchGroup
 * 
 * @author brachet
 */
public class InsertBranchGroup extends AbstractInsertAction {
	@Override
	protected Node createNode(IWorkbenchWindow window) {
		return new BranchGroup();
	}
}