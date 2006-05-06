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


import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.egs3d.core.resources.IScene;
import org.egs3d.core.resources.ResourcesPlugin;
import org.egs3d.ui.internal.UIPlugin;


/**
 * Enregistre la scène sélectionée.
 * 
 * @author brachet
 * @author romale
 */
public class SaveAction implements IWorkbenchWindowActionDelegate {
    private final Log log = LogFactory.getLog(getClass());


    public void dispose() {
    }


    public void init(IWorkbenchWindow window) {
    }


    public void run(IAction action) {
        final IScene scene = UIPlugin.getDefault().getSelectedScene();
        if (scene == null) {
            return;
        }

        log.info("Enregistrement de la scène : " + scene);

        try {
            ResourcesPlugin.createSceneWriter().write(
                    scene.getFile().getLocation().toFile(), scene);
        } catch (IOException e) {
            log.error("Erreur lors de l'enregistrement de la scène : " + scene, e);
        }
    }


    public void selectionChanged(IAction action, ISelection selection) {
    }
}
