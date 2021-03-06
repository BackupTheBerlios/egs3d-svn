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


import java.text.MessageFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.egs3d.ui.internal.Messages;
import org.egs3d.ui.wizards.NewProjectWizard;


/**
 * Ouvre une fen�tre de dialogue pour la cr�ation d'un nouveau projet.
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
        log.debug("Ouverture de la fen�tre de cr�ation d'un projet"); //$NON-NLS-1$

        final NewProjectWizard wizard = new NewProjectWizard();
        final WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
        dialog.setBlockOnOpen(true);

        if (Window.OK != dialog.open()) {
            // action annul�e
            return;
        }

        final String projectName = wizard.getProjectName();
        log.info("Cr�ation du projet : " + projectName); //$NON-NLS-1$

        // cr�ation du projet
        final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
                projectName);
        try {
            if (wizard.getProjectPath() != null) {
                final IPath projectPath = new Path(wizard.getProjectPath());
                final IProjectDescription desc = ResourcesPlugin.getWorkspace()
                        .newProjectDescription(projectName);
                desc.setLocation(projectPath);
                project.create(desc, null);
            } else {
                project.create(null);
            }
        } catch (CoreException e) {
            log.error("Erreur � la cr�ation du projet : " + projectName, e); //$NON-NLS-1$
            final String msg = MessageFormat.format(Messages.Error_createProject,
                    projectName);
            ErrorDialog.openError(window.getShell(), null, msg, e.getStatus());
        }

        if (!project.exists()) {
            // si le projet n'existe pas � ce stade, c'est qu'il y a eu une
            // erreur : inutile de continuer donc...
            return;
        }

        log.info("Ouverture du projet : " + projectName); //$NON-NLS-1$

        // ouverture du projet
        try {
            project.open(null);
        } catch (CoreException e) {
            log.error("Erreur � l'ouverture du projet : " + projectName, e); //$NON-NLS-1$
            final String msg = MessageFormat.format(Messages.Error_openProject,
                    projectName);
            ErrorDialog.openError(window.getShell(), null, msg, e.getStatus());
        }
    }


    public void selectionChanged(IAction action, ISelection selection) {
    }
}
