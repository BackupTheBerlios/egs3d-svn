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


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Plugin;
import org.egs3d.core.Java3DUtils;
import org.egs3d.core.resources.internal.Scene;
import org.egs3d.core.resources.internal.SceneReader;
import org.egs3d.core.resources.internal.SceneWriter;


/**
 * {@link Plugin} pour les ressources.
 * 
 * @author romale
 */
public class ResourcesPlugin extends Plugin {
    public static final String PLUGIN_ID = "org.egs3d.core.resources";


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


    /**
     * Retourne une scène contenue dans un fichier. Si le fichier vient d'être
     * créé, une nouvelle scène sera initialisée. Si le fichier n'a pas
     * l'extension d'une scène, la valeur <code>null</code> est retournée.
     */
    public static synchronized IScene getScene(IFile file) throws IOException,
            CoreException {
        if (!SceneConstants.SCENE_FILE_EXTENSION
                .equals(file.getFileExtension())) {
            return null;
        }

        IScene scene = null;
        if (!file.exists()) {
            final File tempFile = File.createTempFile("scene-", //$NON-NLS-1$
                    "." + SceneConstants.SCENE_FILE_EXTENSION); //$NON-NLS-1$
            tempFile.deleteOnExit();

            // TODO optimiser le code : on écrit 2 fois sur le disque
            createSceneWriter().write(tempFile, createScene(file.getProject()));
            file.create(new FileInputStream(tempFile), true, null);
            tempFile.delete();
        }

        scene = (IScene) file
                .getSessionProperty(SceneConstants.SCENE_SESSION_RESOURCE_NAME);
        if (scene == null) {
            final File javaFile = file.getLocation().toFile();
            scene = createSceneReader().read(javaFile);
            scene.setProject(file.getProject());
            scene.setFile(file);
            scene.setName(file.getName());
            file.setSessionProperty(SceneConstants.SCENE_SESSION_RESOURCE_NAME,
                    scene);
        }

        return scene;
    }


    public static synchronized IScene getScene(Object obj) {
        if (obj instanceof ISceneObject) {
            return ((ISceneObject) obj).getScene();
        }
        if (obj instanceof IFile) {
            try {
                return getScene((IFile) obj);
            } catch (Exception e) {
            }
        }
        if (obj instanceof Node) {
            final Node root = Java3DUtils.getRootNode((Node) obj);

            try {
                final IResource[] projects = org.eclipse.core.resources.ResourcesPlugin
                        .getWorkspace().getRoot().members();
                for (int i = 0; i < projects.length; ++i) {
                    final IProject project = (IProject) projects[i];

                    final IResource[] projectMembers = project.members();
                    for (int j = 0; j < projectMembers.length; ++j) {
                        final IResource member = projectMembers[j];
                        if (member instanceof IFile) {
                            final IScene scene = getScene(member);
                            if (scene == null) {
                                continue;
                            }
                            for (final BranchGroup bg : scene
                                    .getBranchGroupContainer()) {
                                if (bg.equals(root)) {
                                    return scene;
                                }
                            }
                        }
                    }
                }
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
