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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
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

        if (selection instanceof IStructuredSelection) {
            final IStructuredSelection ss = (IStructuredSelection) selection;
            for (final Iterator i = ss.iterator(); i.hasNext();) {
                final Object current = i.next();
                if (current instanceof IResource) {
                    resourcesToDelete.add((IResource) current);
                }
            }
        }

        if (resourcesToDelete.isEmpty()) {
            return;
        }
        if (!MessageDialog.openQuestion(window.getShell(), Messages.DeleteResourceAction_title,
                Messages.DeleteResourceAction_confirm)) {
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("Suppression des ressources : " + resourcesToDelete); //$NON-NLS-1$
        }

        final IResource[] resourcesToDeleteArray = resourcesToDelete
                .toArray(new IResource[resourcesToDelete.size()]);
        try {
            ResourcesPlugin.getWorkspace().delete(resourcesToDeleteArray, true, null);
        } catch (CoreException e) {
            log.error("Erreur lors de la suppression de la ressource", e); //$NON-NLS-1$
        }
    }


    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
    }
}
