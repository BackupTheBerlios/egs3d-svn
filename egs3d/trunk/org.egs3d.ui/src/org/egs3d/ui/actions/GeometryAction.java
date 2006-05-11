/*
 * Créé le 6 mai 2006
 *
 * TODO Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre - Préférences - Java - Style de code - Modèles de code
 */
package org.egs3d.ui.actions;

import javax.media.j3d.Group;
import javax.media.j3d.BranchGroup;
import com.sun.j3d.utils.geometry.ColorCube;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.egs3d.ui.views.SceneExplorerView;


/**
 * Ajoute une forme
 * 
 * @author brachet
 */
public class GeometryAction implements IWorkbenchWindowActionDelegate {
    private final Log log = LogFactory.getLog(getClass());
    private IWorkbenchWindow window;
    
	public void dispose() {
        window = null;
	}

	public void init(IWorkbenchWindow window) {
        this.window = window;
	}

	public void run(IAction action) {
		log.info("Ajout d'une forme");

        // récupération de la sélection dans l'explorateur de scène
        // afin de déterminer le branchgroup dans lequel la shape doit être ajoutée
        final IStructuredSelection sel = (IStructuredSelection) window
                .getSelectionService().getSelection(SceneExplorerView.VIEW_ID);
        if (sel == null || sel.isEmpty()) {
            return;
        }
        final Object selectedObj = sel.getFirstElement();
        if (selectedObj == null) {
            return;
        }  
        
        BranchGroup bg = null;
        if (selectedObj instanceof BranchGroup) {
            bg = (BranchGroup) selectedObj;
        }

        if (bg == null) {
            log.info("La ressource sélectionnée ne permet pas "
                    + "l'ajout d'une nouvelle géométrie : " + selectedObj);
            return;
        }		

        ColorCube myCube = new ColorCube(5.0);
        //Group myGroup = new Group();
        //myGroup.addChild(myCube);
        bg.addChild(myCube);
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Raccord de méthode auto-généré

	}

}
