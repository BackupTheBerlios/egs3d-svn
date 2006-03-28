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


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.actions.ActionFactory;


/**
 * Affiche la bo�te de dialogue "A propos". Action d�l�gu�e �
 * {@link ActionFactory#ABOUT}.
 * 
 * @author romale
 */
public class AboutAction implements IWorkbenchWindowActionDelegate {
    private final Log log = LogFactory.getLog(getClass());
    private IWorkbenchWindow window;


    public void dispose() {
        window = null;
    }


    public void init(IWorkbenchWindow window) {
        this.window = window;
    }


    public void run(IAction action) {
        log.debug("Ouverture de la fen�tre 'A propos'");
        ActionFactory.ABOUT.create(window).run();
    }


    public void selectionChanged(IAction action, ISelection selection) {
    }
}
