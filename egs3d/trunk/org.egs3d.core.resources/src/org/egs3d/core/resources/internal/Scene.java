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


import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.egs3d.core.resources.IBranchGroupContainer;
import org.egs3d.core.resources.IModelContainer;
import org.egs3d.core.resources.IScene;
import org.egs3d.core.resources.ITextureContainer;


/**
 * Implémentation de l'interface {@link IScene}.
 * 
 * @author romale
 */
public class Scene implements IScene {
    private final IBranchGroupContainer branchGroupContainer = new BranchGroupContainer(
            this);
    private final IModelContainer modelContainer = new ModelContainer(this);
    private final ITextureContainer textureContainer = new TextureContainer(this);
    private IProject project;
    private IFile file;
    private String name;


    public Scene() {
        this(null);
    }


    public Scene(final IProject project) {
        setProject(project);
    }


    public IProject getProject() {
        return project;
    }


    public void setProject(IProject project) {
        this.project = project;
    }


    public IBranchGroupContainer getBranchGroupContainer() {
        return branchGroupContainer;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public IModelContainer getModelContainer() {
        return modelContainer;
    }


    public ITextureContainer getTextureContainer() {
        return textureContainer;
    }


    public IFile getFile() {
        return file;
    }


    public void setFile(IFile file) {
        this.file = file;
    }
}
