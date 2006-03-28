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


import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;


/**
 * Affiche la fenêtre d'ouverture des vues.
 * 
 * @author romale
 */
public class ShowViewAction implements IWorkbenchWindowActionDelegate {
    private final Log log = LogFactory.getLog(getClass());


    public void dispose() {
    }


    public void init(IWorkbenchWindow window) {
    }


    public void run(IAction action) {
        log.debug("Ouverture de la fenêtre d'ouverture de vue");
        try {
            final ICommandService cs = (ICommandService) PlatformUI.getWorkbench()
                    .getAdapter(ICommandService.class);
            cs.getCommand("org.eclipse.ui.views.showView").execute(
                    new ExecutionEvent(Collections.EMPTY_MAP, null, null));
        } catch (Exception e) {
            log.error("Erreur lors de l'ouverture de la fenêtre d'ouverture de vue", e);
        }
    }


    public void selectionChanged(IAction action, ISelection selection) {
    }
}
