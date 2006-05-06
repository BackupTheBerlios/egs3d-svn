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

package org.egs3d.core.resources.internal;


import org.eclipse.core.resources.IProject;
import org.egs3d.core.resources.IScene;
import org.egs3d.core.resources.ISceneObject;


/**
 * Classe de base pour un objet appartenant à une instance implémentant
 * l'interface {@link IScene}.
 * 
 * @author romale
 */
public abstract class AbstractSceneObject implements ISceneObject {
    private final IScene scene;


    public AbstractSceneObject(final IScene scene) {
        this.scene = scene;
    }


    public IScene getScene() {
        return scene;
    }


    /**
     * Rafraîchit le projet associé à la scène, s'il y en a un.
     */
    protected void refreshProject() {
        final IProject project = scene.getProject();
        if (project == null) {
            return;
        }
        try {
            project.touch(null);
        } catch (Exception ignore) {
        }
    }

}
