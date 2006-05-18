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

package org.egs3d.ui.internal;


import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.egs3d.core.resources.IScene;
import org.egs3d.core.resources.ResourcesPlugin;
import org.egs3d.ui.views.SceneExplorerView;
import org.osgi.framework.BundleContext;


public class UIPlugin extends AbstractUIPlugin {
    public static final String PLUGIN_ID = "org.egs3d.ui"; //$NON-NLS-1$
    private static UIPlugin instance;
    private final Log log = LogFactory.getLog(getClass());


    public UIPlugin() {
        instance = this;
    }


    public static UIPlugin getDefault() {
        return instance;
    }


    @Override
    public void start(BundleContext context) throws Exception {
        instance = this;
        super.start(context);
    }


    @Override
    public void stop(BundleContext context) throws Exception {
        instance = null;
        super.stop(context);
    }


    public Image getImage(ImageType type) {
        Image img = getImageRegistry().get(type.name());
        if (img == null) {
            final URL url = getBundle().getEntry(type.path());
            if (url == null) {
                log.warn("Image non trouvée : " + type.path()); //$NON-NLS-1$
                return null;
            }
            final ImageDescriptor desc = ImageDescriptor.createFromURL(url);
            getImageRegistry().put(type.name(), desc);
            img = getImageRegistry().get(type.name());
        }

        return img;
    }


    public ImageDescriptor getIcon(IconType type) {
        ImageDescriptor desc = getImageRegistry().getDescriptor(type.name());
        if (desc == null) {
            final URL url = getBundle().getEntry(type.path());
            if (url == null) {
                log.warn("Icône non trouvée : " + type.path()); //$NON-NLS-1$
                return null;
            }
            desc = ImageDescriptor.createFromURL(url);
            getImageRegistry().put(type.name(), desc);
        }

        return desc;
    }


    /**
     * Retourne la scène sélectionnée dans l'explorateur de scène, ou
     * <code>null</code>.
     */
    public IScene getSelectedScene() {
        final IStructuredSelection sel = (IStructuredSelection) getWorkbench()
                .getActiveWorkbenchWindow().getSelectionService().getSelection(
                        SceneExplorerView.VIEW_ID);
        if (sel == null || sel.isEmpty()) {
            return null;
        }

        return ResourcesPlugin.getScene(sel.getFirstElement());
    }
}
