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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.egs3d.ui.internal.Messages;
import org.egs3d.ui.wizards.NewProjectWizard;


/**
 * Ouvre une fenêtre de dialogue pour la création d'un nouveau projet.
 * 
 * @author romale
 */
public class NewProjectAction implements IWorkbenchWindowActionDelegate {
    private final Log log = LogFactory.getLog(getClass());
    private IWorkbenchWindow window;


    public void dispose() {
        window = null;
    }


    public void init(IWorkbenchWindow window) {
        this.window = window;
    }


    public void run(IAction action) {
        log.debug("Ouverture de la fenêtre de création d'un projet"); //$NON-NLS-1$

        final NewProjectWizard wizard = new NewProjectWizard();
        final WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
        dialog.setBlockOnOpen(true);

        if (Window.OK != dialog.open()) {
            // action annulée
            return;
        }

        final String projectName = wizard.getProjectName();
        log.info("Création du projet : " + projectName); //$NON-NLS-1$

        // création du projet dans l'espace de travail
        final IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(projectName);
        try {
            project.create(null);
        } catch (CoreException e) {
            throw new IllegalStateException(NLS.bind(
                    Messages.Error_createProject, projectName), e);
        }
    }


    public void selectionChanged(IAction action, ISelection selection) {
    }
}
