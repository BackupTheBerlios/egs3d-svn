/*
 * Cr�� le 6 mai 2006
 *
 * TODO Pour changer le mod�le de ce fichier g�n�r�, allez � :
 * Fen�tre - Pr�f�rences - Java - Style de code - Mod�les de code
 */
package org.egs3d.ui.actions;


import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Node;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.egs3d.core.resources.IBranchGroupContainer;
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

        // r�cup�ration de la s�lection dans l'explorateur de sc�ne
        // afin de d�terminer le group dans lequel le noeud doit �tre ajout�
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

        final IScene scene = UIPlugin.getDefault().getSelectedScene();
        IBranchGroupContainer bgc = null;
        if (scene != null && group == null) {
            bgc = scene.getBranchGroupContainer();
        }

        if (group == null && bgc == null) {
            log.info("La ressource s�lectionn�e ne permet pas "
                    + "l'ajout d'une nouvelle g�om�trie : " + selectedObj);
            return;
        }

        final Node node = createNode(window);
        if (node == null) {
            return;
        }
        if (group != null) {
            group.addChild(node);
        } else if (bgc != null) {
            final BranchGroup root = new BranchGroup();
            root.addChild(node);
            bgc.add(root);
        }

        if (scene != null) {
            scene.getBranchGroupContainer().refresh();
        }
    }


    public void selectionChanged(IAction action, ISelection selection) {
    }


    /**
     * Cr�e un noeud � ins�rer dans le groupe courant. Si la valeur renvoy�e est
     * <code>null</code>, aucune insertion n'est effectu�e.
     */
    protected abstract Node createNode(IWorkbenchWindow window);
}
