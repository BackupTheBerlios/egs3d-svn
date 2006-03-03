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


import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.egs3d.core.Java3DUtils;


/**
 * @author romale
 */
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {
    public ApplicationWorkbenchWindowAdvisor(
            IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }


    @Override
    public ActionBarAdvisor createActionBarAdvisor(
            IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }


    @Override
    public void preWindowOpen() {
        checkJava3D();

        final IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(800, 600));
        configurer.setShowCoolBar(false);
        configurer.setShowStatusLine(false);
        configurer.setShowPerspectiveBar(true);
        configurer.setShowProgressIndicator(true);
    }


    /**
     * Vérification que Java3D est installé : un message est affiché si ce n'est
     * pas le cas, et l'application est fermée.
     */
    private void checkJava3D() {
        if (!Java3DUtils.isJava3DInstalled()) {
            final IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
            final IWorkbench workbench = configurer.getWorkbenchConfigurer()
                    .getWorkbench();
            MessageDialog.openInformation(workbench.getDisplay()
                    .getActiveShell(), Messages.Erreur_titre,
                    Messages.Erreur_noJava3D);

            // fermeture de l'application
            workbench.close();
        }
    }
}
