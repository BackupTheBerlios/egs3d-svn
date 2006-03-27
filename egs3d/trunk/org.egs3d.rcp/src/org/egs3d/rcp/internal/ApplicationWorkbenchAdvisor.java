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

package org.egs3d.rcp.internal;


import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.egs3d.ui.perspectives.SceneGraphConstructionPerspective;


/**
 * @author romale
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {
    @Override
    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
            IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }


    @Override
    public String getInitialWindowPerspectiveId() {
        return SceneGraphConstructionPerspective.PERSPECTIVE_ID;
    }


    @Override
    public void initialize(IWorkbenchConfigurer configurer) {
        super.initialize(configurer);

        // la configuration de la fenêtre, des vues et des perspectives est
        // sauvegardée pour être restaurée au prochain redémarrage
        configurer.setSaveAndRestore(true);
    }
}
