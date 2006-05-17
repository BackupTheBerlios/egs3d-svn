/**
 * $Id$
 *
 * EGS3D
 * http://egs3d.berlios.de
 * Copyright (c) 2006 EGS3D team
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.egs3d.ui.actions;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Node;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.egs3d.core.resources.IModel;
import org.egs3d.core.resources.IScene;
import org.egs3d.core.resources.ISceneObject;
import org.egs3d.core.resources.ITexture;
import org.egs3d.core.resources.ResourcesPlugin;
import org.egs3d.ui.internal.Messages;


/**
 * Supprime la ressource actuellement sélectionnée.
 * 
 * @author romale
 */
public class DeleteResourceAction implements IWorkbenchWindowActionDelegate {
    private final Log log = LogFactory.getLog(getClass());
    private ISelection selection;
    private IWorkbenchWindow window;


    public void dispose() {
        selection = null;
        window = null;
    }


    public void init(IWorkbenchWindow window) {
        this.window = window;
    }


    public void run(IAction action) {
        final Set<IResource> resourcesToDelete = new HashSet<IResource>();
        final Set<ISceneObject> sceneObjectsToDelete = new HashSet<ISceneObject>();
        final Set<Node> nodesToDelete = new HashSet<Node>();

        if (selection instanceof IStructuredSelection) {
            final IStructuredSelection ss = (IStructuredSelection) selection;
            for (final Iterator i = ss.iterator(); i.hasNext();) {
                final Object current = i.next();
                if (current instanceof IResource) {
                    resourcesToDelete.add((IResource) current);
                }
                if (current instanceof ISceneObject) {
                    sceneObjectsToDelete.add((ISceneObject) current);
                }
                if (current instanceof Node) {
                    nodesToDelete.add((Node) current);
                }
            }
        }

        if (resourcesToDelete.isEmpty() && sceneObjectsToDelete.isEmpty()
                && nodesToDelete.isEmpty()) {
            return;
        }
        if (!MessageDialog.openQuestion(window.getShell(),
                Messages.DeleteResourceAction_title,
                Messages.DeleteResourceAction_confirm)) {
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("Suppression des ressources : " + resourcesToDelete); //$NON-NLS-1$
        }

        final IResource[] resourcesToDeleteArray = resourcesToDelete
                .toArray(new IResource[resourcesToDelete.size()]);
        try {
            org.eclipse.core.resources.ResourcesPlugin.getWorkspace().delete(
                    resourcesToDeleteArray, true, null);
        } catch (CoreException e) {
            log.error("Erreur lors de la suppression de la ressource", e); //$NON-NLS-1$
        }

        for (final ISceneObject so : sceneObjectsToDelete) {
            final IScene scene = so.getScene();
            if (so instanceof ITexture) {
                scene.getTextureContainer().remove((ITexture) so);
            }
            if (so instanceof IModel) {
                scene.getModelContainer().remove((IModel) so);
            }
        }

        final Set<IScene> scenesToRefresh = new HashSet<IScene>();
        for (final Node node : nodesToDelete) {
            final IScene scene = ResourcesPlugin.getScene(node);
            if (scene != null) {
                scenesToRefresh.add(scene);
            }

            final Node parent = node.getParent();
            if (parent != null) {
                if (parent instanceof Group) {
                    final Group parentGroup = (Group) parent;
                    parentGroup.removeChild(node);
                }
            } else if(scene != null) {
                scene.getBranchGroupContainer().remove((BranchGroup) node);
            }
        }

        for (final IScene scene : scenesToRefresh) {
            scene.getBranchGroupContainer().refresh();
        }
    }


    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
    }
}
