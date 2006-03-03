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


import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;


public class UIPlugin extends Plugin {
    public static final String PLUGIN_ID = "org.egs3d.ui"; //$NON-NLS-1$
    private static UIPlugin instance;


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


    public void logError(String msg, Throwable e) {
        getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, msg, e));
    }
}
