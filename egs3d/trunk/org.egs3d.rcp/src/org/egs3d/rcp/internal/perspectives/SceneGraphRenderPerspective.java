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


import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.egs3d.ui.views.SceneGraphRenderView;


/**
 * Perspective de rendu de la scène.
 * 
 * @author romale
 */
public class SceneGraphRenderPerspective implements IPerspectiveFactory {
    public static final String PERSPECTIVE_ID = "org.egs3d.rcp.perspectives.sceneGraphRender";


    public void createInitialLayout(IPageLayout layout) {
        doLayout(layout);
        doShortcuts(layout);
    }


    /**
     * Mise en place des vues.
     */
    private void doLayout(IPageLayout layout) {
        layout.setEditorAreaVisible(false);
        layout.addView(SceneGraphRenderView.VIEW_ID, IPageLayout.TOP, 1,
                IPageLayout.ID_EDITOR_AREA);
        layout.getViewLayout(SceneGraphRenderView.VIEW_ID).setCloseable(false);
    }


    /**
     * Mise en place des raccourcis.
     */
    private void doShortcuts(IPageLayout layout) {
        layout.addPerspectiveShortcut(PERSPECTIVE_ID);
        layout
                .addPerspectiveShortcut(SceneGraphConstructionPerspective.PERSPECTIVE_ID);
    }
}
