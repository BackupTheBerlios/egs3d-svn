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


import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;


/**
 * Sc�ne graphique contenant :
 * <ul>
 * <li>un conteneur d'arbres sc�niques Java3D {@link IBranchGroupContainer},</li>
 * <li>un conteneur de textures,</li>
 * <li>un conteneur de mod�les 3D.</li>
 * </ul>
 * 
 * @author romale
 */
public interface IScene {
    /**
     * Retourne l'instance {@link IProject} associ�e � la sc�ne.
     */
    IProject getProject();


    /**
     * Enregistre la sc�ne aupr�s d'une instance {@link IProject}.
     */
    void setProject(IProject project);


    /**
     * Retourne l'instance {@link IFile} associ�e � la sc�ne.
     */
    IFile getFile();


    /**
     * Enregistre l'instance {@link IFile} associ�e � la sc�ne.
     */
    void setFile(IFile file);


    /**
     * Retourne le nom de la sc�ne.
     */
    String getName();


    /**
     * Enregistre le nom de la sc�ne.
     */
    void setName(String name);


    /**
     * Retourne le conteneur {@link IBranchGroupContainer}.
     */
    IBranchGroupContainer getBranchGroupContainer();


    /**
     * Retourne le conteneur {@link IModelContainer}.
     */
    IModelContainer getModelContainer();


    /**
     * Retourne le conteneur {@link ITextureContainer}.
     */
    ITextureContainer getTextureContainer();
}
