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

package org.egs3d.ui.util;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.egs3d.core.resources.IBranchGroupContainer;
import org.egs3d.core.resources.IModelContainer;
import org.egs3d.core.resources.IScene;
import org.egs3d.core.resources.ITextureContainer;
import org.egs3d.core.resources.ResourcesPlugin;
import org.egs3d.core.resources.SceneConstants;
import org.egs3d.ui.views.SceneExplorerView;


/**
 * Implémentation de l'interface {@link IContentProvider} pour l'explorateur de
 * scène {@link SceneExplorerView}.
 * 
 * @author romale
 */
public class SceneContentProvider implements ITreeContentProvider,
        IResourceChangeListener {
    private static final Object[] EMPTY_ARRAY = new Object[0];
    private final Log log = LogFactory.getLog(getClass());
    private IWorkspaceRoot root;
    private Viewer viewer;


    public Object[] getChildren(Object parent) {
        if (parent instanceof IContainer) {
            final IContainer cont = (IContainer) parent;
            try {
                return filterResources(cont.members());
            } catch (CoreException e) {
                log.warn("Erreur dans getChildren(" + parent + ")", e); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        if (parent instanceof IFile) {
            final IFile file = (IFile) parent;
            try {
                final IScene scene = ResourcesPlugin.getScene(file);
                if (scene != null) {
                    return new Object[] { scene.getBranchGroupContainer(),
                            scene.getModelContainer(), scene.getTextureContainer() };
                }
            } catch (Exception e) {
                log.warn("Erreur dans getChildren(" + parent + ")", e); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        if (parent instanceof IBranchGroupContainer) {
            final IBranchGroupContainer container = (IBranchGroupContainer) parent;
            return container.toArray();
        }
        if (parent instanceof IModelContainer) {
            final IModelContainer container = (IModelContainer) parent;
            return container.toArray();
        }
        if (parent instanceof ITextureContainer) {
            final ITextureContainer container = (ITextureContainer) parent;
            return container.toArray();
        }
        return EMPTY_ARRAY;
    }


    public Object getParent(Object e) {
        if (e instanceof IResource) {
            final IResource rsc = (IResource) e;
            return rsc.getParent();
        }
        return null;
    }


    public boolean hasChildren(Object e) {
        if (e instanceof IContainer) {
            final IContainer cont = (IContainer) e;
            try {
                return filterResources(cont.members()).length > 0;
            } catch (CoreException exc) {
                log.warn("Erreur dans hasChildren(" + e + ")", exc); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        if (e instanceof IFile) {
            final IFile file = (IFile) e;
            return SceneConstants.SCENE_FILE_EXTENSION.equals(file.getFileExtension());
        }
        if (e instanceof IBranchGroupContainer) {
            final IBranchGroupContainer container = (IBranchGroupContainer) e;
            return container.getSize() > 0;
        }
        if (e instanceof IModelContainer) {
            final IModelContainer container = (IModelContainer) e;
            return container.getSize() > 0;
        }
        if (e instanceof ITextureContainer) {
            final ITextureContainer container = (ITextureContainer) e;
            return container.getSize() > 0;
        }

        return false;
    }


    public Object[] getElements(Object e) {
        return getChildren(e);
    }


    public void dispose() {
        if (root != null) {
            root.getWorkspace().removeResourceChangeListener(this);
            root = null;
        }
        viewer = null;
    }


    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        final IWorkspaceRoot oldRoot = (IWorkspaceRoot) oldInput;
        final IWorkspaceRoot newRoot = (IWorkspaceRoot) newInput;

        if (oldRoot != null) {
            oldRoot.getWorkspace().removeResourceChangeListener(this);
        }
        if (newRoot != null) {
            newRoot.getWorkspace().addResourceChangeListener(this);
            root = newRoot;
        }

        this.viewer = viewer;
    }


    public void resourceChanged(IResourceChangeEvent evt) {
        log.debug("Rafraîchissement de l'affichage suite à " //$NON-NLS-1$
                + "un changement dans les ressources"); //$NON-NLS-1$
        // chaque changement d'une ressource provoque le rafraîchissement
        // complet de l'affichage : cette opération est coûteuse et devrait
        // faire l'objet d'une optimisation
        viewer.refresh();
    }


    /**
     * Filtre une liste de ressources. Utilisée pour enlever certains éléments
     * qui doivent être "cachés".
     * 
     * @param resources ressources à filtrer
     * @return une nouvelle liste filtrée de ressources
     */
    private IResource[] filterResources(IResource[] resources) {
        // liste des noms de ressource filtrés
        final Set<String> filteredNames = new HashSet<String>();
        filteredNames.add(".project"); //$NON-NLS-1$

        final List<IResource> newResources = new ArrayList<IResource>();
        for (final IResource resource : resources) {
            log.debug("Application du filtre sur la ressource : " + resource.getName()); //$NON-NLS-1$
            if (filteredNames.contains(resource.getName())) {
                continue;
            }
            newResources.add(resource);
        }

        return newResources.toArray(new IResource[newResources.size()]);
    }
}
