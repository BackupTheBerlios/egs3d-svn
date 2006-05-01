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

package org.egs3d.ui.wizards;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;


/**
 * Implémentation de l'interface {@link IContentProvider} pour afficher la liste
 * des projets.
 * 
 * @author romale
 */
public class ProjectContentProvider implements ITreeContentProvider {
    private static final Object[] EMPTY_ARRAY = new Object[0];
    private final Log log = LogFactory.getLog(getClass());


    public Object[] getChildren(Object parent) {
        if (parent instanceof IWorkspaceRoot) {
            final IWorkspaceRoot root = (IWorkspaceRoot) parent;
            try {
                return filterResources(root.members());
            } catch (CoreException exc) {
                log.warn("Erreur dans getChildren(" + parent + ")", exc); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        return EMPTY_ARRAY;
    }


    public Object getParent(Object e) {
        return null;
    }


    public boolean hasChildren(Object e) {
        if (e instanceof IWorkspaceRoot) {
            final IWorkspaceRoot root = (IWorkspaceRoot) e;
            try {
                return filterResources(root.members()).length > 0;
            } catch (CoreException exc) {
                log.warn("Erreur dans hasChildren(" + e + ")", exc); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        return false;
    }


    public Object[] getElements(Object e) {
        return getChildren(e);
    }


    public void dispose() {
    }


    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }


    /**
     * Filtre un tableau de ressources. Seuls les projets ouverts sont
     * conservés.
     */
    private Object[] filterResources(Object[] elements) {
        final List<Object> filteredObjects = new ArrayList<Object>();
        for (final Object obj : elements) {
            if (obj instanceof IProject) {
                final IProject project = (IProject) obj;
                if (project.isOpen()) {
                    filteredObjects.add(project);
                }
            }
        }

        return filteredObjects.toArray(new Object[filteredObjects.size()]);
    }
}
