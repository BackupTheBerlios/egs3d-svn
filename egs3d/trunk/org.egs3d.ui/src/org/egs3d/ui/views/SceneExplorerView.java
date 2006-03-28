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

package org.egs3d.ui.views;


import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;


/**
 * Explorateur de sc�ne 3D.
 * 
 * @author romale
 */
public class SceneExplorerView extends ViewPart {
    public static final String VIEW_ID = "org.egs3d.ui.views.sceneExplorer"; //$NON-NLS-1$
    private TreeViewer treeViewer;


    @Override
    public void createPartControl(Composite parent) {
        treeViewer = new TreeViewer(parent);
        parent.setLayout(new FillLayout());
    }


    @Override
    public void setFocus() {
        treeViewer.getControl().setFocus();
    }


    @Override
    public void dispose() {
        if (treeViewer != null) {
            treeViewer.getControl().dispose();
            treeViewer = null;
        }
        super.dispose();
    }
}
