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
import java.io.FileNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.egs3d.core.resources.IModel;
import org.egs3d.core.resources.IScene;
import org.egs3d.core.resources.ITexture;
import org.egs3d.ui.internal.UIPlugin;
import org.egs3d.ui.wizards.ImportWizard;

import com.sun.j3d.loaders.Loader;


/**
 * Ouvre l'assistant d'importation de ressources.
 * 
 * @author romale
 */
public class ImportAction implements IWorkbenchWindowActionDelegate {
    private final Log log = LogFactory.getLog(getClass());
    private IWorkbenchWindow window;


    public void dispose() {
        window = null;
    }


    public void init(IWorkbenchWindow window) {
        this.window = window;
    }


    public void run(IAction action) {
        final IScene scene = UIPlugin.getDefault().getSelectedScene();
        if (scene == null) {
            return;
        }

        final ImportWizard wizard = new ImportWizard();
        final WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
        dialog.setBlockOnOpen(true);

        if (Window.OK != dialog.open()) {
            return;
        }

        final ImportWizard.ImportType type = wizard.getImportType();
        final Class<? extends Loader> modelLoaderClass = wizard.getModelLoaderClass();
        final String resourceName = wizard.getResourceName();
        final String resourcePath = wizard.getResourcePath();

        final File file = new File(resourcePath);
        if (!file.exists()) {
            return;
        }

        try {
            if (type.equals(ImportWizard.ImportType.TEXTURE)) {
                final ITexture texture = scene.getTextureContainer().create(resourceName,
                        new FileInputStream(file));
                scene.getTextureContainer().add(texture);
            } else if (type.equals(ImportWizard.ImportType.MODEL)) {
                final IModel model = scene.getModelContainer().create(resourceName,
                        modelLoaderClass, new FileInputStream(file));
                scene.getModelContainer().add(model);
            }
        } catch (FileNotFoundException e) {
            log.error("Fichier introuvable : " + file, e);
        } catch (Exception e) {
            log.error("Erreur lors de l'importation", e);
        }
    }


    public void selectionChanged(IAction action, ISelection selection) {
    }
}
