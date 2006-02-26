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

package org.egs3d.rcp.internal.perspectives;


import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.egs3d.ui.views.DiagnosticsView;
import org.egs3d.ui.views.SceneGraphExplorerView;
import org.egs3d.ui.views.SceneGraphRenderView;


/**
 * Perspective de construction de la scène.
 * 
 * @author romale
 */
public class SceneGraphConstructionPerspective implements IPerspectiveFactory {
    public static final String PERSPECTIVE_ID = "org.egs3d.rcp.perspectives.sceneGraphConstruction";


    public void createInitialLayout(IPageLayout layout) {
        doLayout(layout);
        doShortcuts(layout);
    }


    /**
     * Mise en place des vues.
     */
    private void doLayout(IPageLayout layout) {
        final IFolderLayout leftLayout = layout.createFolder("left",
                IPageLayout.LEFT, 0.3f, IPageLayout.ID_EDITOR_AREA);
        leftLayout.addView(SceneGraphExplorerView.VIEW_ID);

        final IFolderLayout bottomLeftLayout = layout.createFolder(
                "bottomLeft", IPageLayout.BOTTOM, 0.5f, "left");
        bottomLeftLayout.addView(IPageLayout.ID_OUTLINE);

        final IFolderLayout bottom = layout.createFolder("bottom",
                IPageLayout.BOTTOM, 0.7f, IPageLayout.ID_EDITOR_AREA);
        bottom.addPlaceholder(IPageLayout.ID_PROP_SHEET);
        bottom.addView(DiagnosticsView.VIEW_ID);

        layout.addPlaceholder(SceneGraphRenderView.VIEW_ID, IPageLayout.RIGHT,
                0.5f, IPageLayout.ID_EDITOR_AREA);
    }


    /**
     * Mise en place des raccourcis.
     */
    private void doShortcuts(IPageLayout layout) {
        layout.addShowViewShortcut(SceneGraphExplorerView.VIEW_ID);
        layout.addShowViewShortcut(SceneGraphRenderView.VIEW_ID);
        layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);

        layout.addPerspectiveShortcut(PERSPECTIVE_ID);
        layout
                .addPerspectiveShortcut(SceneGraphRenderPerspective.PERSPECTIVE_ID);
    }
}
