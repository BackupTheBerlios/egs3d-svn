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

package org.egs3d.ui.util;


import javax.media.j3d.Background;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Light;
import javax.media.j3d.SceneGraphObject;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.egs3d.core.StringUtils;
import org.egs3d.core.resources.IBranchGroupContainer;
import org.egs3d.core.resources.IModel;
import org.egs3d.core.resources.IModelContainer;
import org.egs3d.core.resources.ITexture;
import org.egs3d.core.resources.ITextureContainer;
import org.egs3d.ui.internal.ImageType;
import org.egs3d.ui.internal.Messages;
import org.egs3d.ui.internal.UIPlugin;
import org.egs3d.ui.views.SceneExplorerView;


/**
 * Impl�mentation de l'interface {@link ILabelProvider} pour l'explorateur de
 * sc�ne {@link SceneExplorerView}.
 * 
 * @author romale
 * @author brachet
 */
public class SceneLabelProvider extends LabelProvider {
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
        if (e instanceof IBranchGroupContainer) {
            return UIPlugin.getDefault().getImage(ImageType.BRANCH_GROUP);
        }
        if (e instanceof IModelContainer) {
            return UIPlugin.getDefault().getImage(ImageType.MODEL);
        }
        if (e instanceof ITextureContainer) {
            return UIPlugin.getDefault().getImage(ImageType.TEXTURE);
        }
        if (e instanceof Shape3D) {
            return UIPlugin.getDefault().getImage(ImageType.SHAPE);
        }
        if (e instanceof Light) {
            return UIPlugin.getDefault().getImage(ImageType.LIGHT);
        }
        if (e instanceof Background) {
            return UIPlugin.getDefault().getImage(ImageType.BACKGROUND);
        }
        if (e instanceof BranchGroup) {
            return UIPlugin.getDefault().getImage(ImageType.BRANCH_GROUP);
        }
        if (e instanceof TransformGroup) {
            return UIPlugin.getDefault().getImage(ImageType.TRANSFORM_GROUP);
        }
        if (e instanceof Group) {
            return UIPlugin.getDefault().getImage(ImageType.GROUP);
        }
        // image par d�faut
        return UIPlugin.getDefault().getImage(ImageType.FILE);
    }


    @Override
    public String getText(Object e) {
        if (e instanceof IResource) {
            final IResource resource = (IResource) e;
            return resource.getName();
        }
        if (e instanceof IBranchGroupContainer) {
            return Messages.Scene_Nodes;
        }
        if (e instanceof IModelContainer) {
            return Messages.Scene_Models;
        }
        if (e instanceof ITextureContainer) {
            return Messages.Scene_Textures;
        }
        if (e instanceof IModel) {
            return ((IModel) e).getName();
        }
        if (e instanceof ITexture) {
            return ((ITexture) e).getName();
        }
        if (e instanceof SceneGraphObject) {
            final SceneGraphObject sgo = (SceneGraphObject) e;
            final StringBuilder buf = new StringBuilder(sgo.getClass().getSimpleName());
            final String name = StringUtils.trimToNull(sgo.getName());
            if (name != null) {
                buf.append(" [").append(name).append(']');
            }
            return buf.toString();
        }
        // texte par d�faut
        return e.toString();
    }
}
