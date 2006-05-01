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


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.egs3d.core.resources.IScene;
import org.egs3d.core.resources.ResourcesPlugin;
import org.egs3d.core.resources.SceneConstants;
import org.egs3d.ui.wizards.NewSceneWizard;


/**
 * Ouvre une fenêtre de dialogue pour la création d'une nouvelle scène 3D.
 * 
 * @author romale
 */
public class NewSceneAction implements IWorkbenchWindowActionDelegate {
    private final Log log = LogFactory.getLog(getClass());
    private IWorkbenchWindow window;


    public void dispose() {
        window = null;
    }


    public void init(IWorkbenchWindow window) {
        this.window = window;
    }


    public void run(IAction action) {
        log.debug("Ouverture de la fenêtre de création d'une scène"); //$NON-NLS-1$

        final NewSceneWizard wizard = new NewSceneWizard();
        final WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
        dialog.setBlockOnOpen(true);

        if (Window.OK != dialog.open()) {
            return;
        }

        final IProject project = wizard.getProject();
        final String fileName = wizard.getFileName();
        final IFile file = project.getFile(fileName);
        final String path = file.getLocation().toOSString();
        log.info("Création d'une scène dans le projet " + project.getName() + " : " //$NON-NLS-1$ //$NON-NLS-2$
                + path);

        final IScene scene = ResourcesPlugin.createScene(project);
        scene.setName(fileName);

        try {
            final File tempFile = File.createTempFile("scene-", //$NON-NLS-1$
                    "." + SceneConstants.SCENE_FILE_EXTENSION); //$NON-NLS-1$
            tempFile.deleteOnExit();

            ResourcesPlugin.createSceneWriter().write(tempFile, scene);
            final InputStream input = new FileInputStream(tempFile);
            file.create(input, true, null);

            tempFile.delete();
        } catch (Exception e) {
            log.warn("Erreur durant la création du fichier : " + path, e); //$NON-NLS-1$
        }
    }


    public void selectionChanged(IAction action, ISelection selection) {
    }
}
