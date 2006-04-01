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

package org.egs3d.ui.views;


import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.egs3d.ui.internal.ImageType;
import org.egs3d.ui.internal.UIPlugin;


/**
 * Implémentation de l'interface {@link ILabelProvider} pour l'explorateur de
 * scène {@link SceneExplorerView}.
 * 
 * @author romale
 */
public class SceneExplorerLabelProvider extends LabelProvider {
    @Override
    public Image getImage(Object e) {
        if (e instanceof IProject) {
            final IProject project = (IProject) e;
            return UIPlugin.getDefault().getImage(
                    project.isOpen() ? ImageType.OPENED_PROJECT
                            : ImageType.CLOSED_PROJECT);
        }
        if (e instanceof IFolder) {
            return UIPlugin.getDefault().getImage(ImageType.FOLDER);
        }
        return UIPlugin.getDefault().getImage(ImageType.FILE);
    }


    @Override
    public String getText(Object e) {
        if (e instanceof IResource) {
            final IResource resource = (IResource) e;
            return resource.getName();
        }
        // texte par défaut
        return e.toString();
    }
}
