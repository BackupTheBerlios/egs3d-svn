package org.egs3d.core;


import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Nature d'un projet EGS3D. Utilisé dans la plateforme Eclipse pour identifier
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
