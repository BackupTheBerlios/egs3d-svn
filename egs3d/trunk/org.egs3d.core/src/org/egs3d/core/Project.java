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

package org.egs3d.core;


import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Nature d'un projet EGS3D. Utilis� dans la plateforme Eclipse pour identifier
 * un projet de type EGS3D.
 * 
 * @author romale
 */
public class Project implements IProjectNature {
    public static final String PROJECT_NATURE_ID = "org.egs3d.core.project";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private IProject project;


    public void configure() throws CoreException {
        log.info("Configuration du projet : " + project.getName());
    }


    public void deconfigure() throws CoreException {
    }


    public IProject getProject() {
        return project;
    }


    public void setProject(IProject project) {
        this.project = project;
    }
}
