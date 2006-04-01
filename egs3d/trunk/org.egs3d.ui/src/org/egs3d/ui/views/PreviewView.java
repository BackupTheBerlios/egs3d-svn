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


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;
import org.egs3d.ui.internal.Messages;


/**
 * Vue d'aperçu.
 * 
 * @author romale
 */
public class PreviewView extends ViewPart {
    private Label noPreviewLabel;


    @Override
    public void createPartControl(Composite parent) {
        noPreviewLabel = new Label(parent, SWT.NONE);
        noPreviewLabel.setText(Messages.PreviewView_noPreview);
        parent.setLayout(new FillLayout());
    }


    @Override
    public void setFocus() {
    }


    @Override
    public void dispose() {
        if (noPreviewLabel != null) {
            noPreviewLabel.dispose();
            noPreviewLabel = null;
        }
        super.dispose();
    }
}
