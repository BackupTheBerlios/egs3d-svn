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

package org.egs3d.core.resources;


import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Plugin;
import org.egs3d.core.resources.internal.Scene;
import org.egs3d.core.resources.internal.SceneReader;
import org.egs3d.core.resources.internal.SceneWriter;


/**
 * {@link Plugin} pour les ressources.
 * 
 * @author romale
 */
public class ResourcesPlugin extends Plugin {
    /**
     * Retourne une instance de l'interface {@link ISceneReader}.
     */
    public static ISceneReader createSceneReader() {
        return new SceneReader();
    }


    /**
     * Retourne une instance de l'interface {@link ISceneWriter}.
     */
    public static ISceneWriter createSceneWriter() {
        return new SceneWriter();
    }


    /**
     * Crée une nouvelle instance d'un objet implémentant {@link IScene},
     * associée à une instance {@link IProject}.
     */
    public static IScene createScene(IProject project) {
        return new Scene(project);
    }


    /**
     * Crée une nouvelle instance d'un objet implémentant {@link IScene}.
     */
    public static IScene createScene() {
        return new Scene();
    }
}
