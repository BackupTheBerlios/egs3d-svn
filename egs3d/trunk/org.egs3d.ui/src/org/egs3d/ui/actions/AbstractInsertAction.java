/*
 * Créé le 6 mai 2006
 *
 * TODO Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre - Préférences - Java - Style de code - Modèles de code
 */
package org.egs3d.ui.actions;

import javax.media.j3d.Group;
import javax.media.j3d.Node;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.egs3d.core.resources.IScene;
import org.egs3d.ui.internal.UIPlugin;
import org.egs3d.ui.views.SceneExplorerView;


/**
 * Action abstraite d'insertion de noeud Java3D.
 * 
 * @author brachet
 */
public abstract class AbstractInsertAction implements IWorkbenchWindowActionDelegate {
    private final Log log = LogFactory.getLog(getClass());
    private IWorkbenchWindow window;
    
	public void dispose() {
        window = null;
	}

	public void init(IWorkbenchWindow window) {
        this.window = window;
	}

	public void run(IAction action) {
		log.info("Ajout d'un noeud");

        // récupération de la sélection dans l'explorateur de scène
        // afin de déterminer le group dans lequel le noeud doit être ajouté
        final IStructuredSelection sel = (IStructuredSelection) window
                .getSelectionService().getSelection(SceneExplorerView.VIEW_ID);
        if (sel == null || sel.isEmpty()) {
            return;
        }
        final Object selectedObj = sel.getFirstElement();
        if (selectedObj == null) {
            return;
        }  
        
        Group group = null;
        if (selectedObj instanceof Group) {
            group = (Group) selectedObj;
        }

        if (group == null) {
            log.info("La ressource sélectionnée ne permet pas "
                    + "l'ajout d'une nouvelle géométrie : " + selectedObj);
            return;
        }

        final Node node = createNode(window);
        if(node == null) {
        	return;
        }
        group.addChild(node);
        
        final IScene scene = UIPlugin.getDefault().getSelectedScene();
        if(scene != null) {
        	scene.getBranchGroupContainer().refresh();
        }
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * Crée un noeud à insérer dans le groupe courant. Si la valeur renvoyée est
	 * <code>null</code>, aucune insertion n'est effectuée.
	 */
	protected abstract Node createNode(IWorkbenchWindow window);
}
