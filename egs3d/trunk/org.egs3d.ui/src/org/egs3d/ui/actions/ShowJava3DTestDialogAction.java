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

package org.egs3d.ui.actions;


import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.egs3d.ui.internal.Messages;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;


/**
 * Affiche une fenêtre pour tester Java3D. Cette fenêtre est affichée dans le
 * menu de la vue "Diagnostique" (la petite flèche vers le bas à droite).
 * 
 * @author romale
 */
public class ShowJava3DTestDialogAction extends Action {
    public static final String ACTION_ID = "org.egs3d.ui.actions.showJava3DTestDialog"; //$NON-NLS-1$
    private final IWorkbench workbench;


    public ShowJava3DTestDialogAction(final IWorkbench workbench) {
        super(Messages.Java3DTest_title);
        this.workbench = workbench;
        setId(ACTION_ID);
    }


    @Override
    public void run() {
        final Dialog dialog = new Java3DTestDialog(workbench.getDisplay()
                .getActiveShell());
        dialog.open();
    }


    /**
     * Fenêtre de test Java3D.
     */
    private class Java3DTestDialog extends Dialog {
        public Java3DTestDialog(Shell shell) {
            super(shell);
        }


        @Override
        protected void configureShell(Shell newShell) {
            super.configureShell(newShell);
            newShell.setText(getText());
        }


        @Override
        protected Point getInitialSize() {
            return new Point(640, 480);
        }


        @Override
        protected Control createDialogArea(Composite parent) {
            parent.setLayout(new FillLayout());

            final Canvas3D canvas3D = new Canvas3D(SimpleUniverse
                    .getPreferredConfiguration());
            final SimpleUniverse universe = new SimpleUniverse(canvas3D);
            universe.addBranchGraph(createBranchGraph());
            universe.getViewingPlatform().setNominalViewingTransform();

            final Composite awtComponent = new Composite(parent, SWT.EMBEDDED);
            final Frame frame = SWT_AWT.new_Frame(awtComponent);
            frame.setLayout(new BorderLayout());
            frame.add(canvas3D, BorderLayout.CENTER);

            return awtComponent;
        }


        @Override
        protected Control createContents(Composite parent) {
            final Composite composite = new Composite(parent, SWT.NONE);
            createDialogArea(composite);
            parent.setLayout(new FillLayout());
            return composite;
        }


        @Override
        protected Control createButtonBar(Composite parent) {
            // pas de boutons dans la fenêtre
            return new Composite(parent, SWT.NONE);
        }


        /**
         * Création de l'arbre scénique. Contient un texte en 3D accompagné d'un
         * cube rotatif, le tout réagissant à la souris.
         */
        private BranchGroup createBranchGraph() {
            final BranchGroup root = new BranchGroup();
            final Bounds sceneBounds = new BoundingSphere();

            // création du fond de la scène
            final Background bg = new Background(1, 1, 1);
            bg.setApplicationBounds(sceneBounds);
            root.addChild(bg);

            // création d'un cube avec une translation
            final Transform3D cubeTranslate = new Transform3D();
            cubeTranslate.setTranslation(new Vector3f(0, -0.2f, 0));
            final TransformGroup cubeTranslateTG = new TransformGroup(cubeTranslate);
            cubeTranslateTG.addChild(new ColorCube(0.1));

            // ajout d'une rotation sur le cube
            final TransformGroup cubeTG = new TransformGroup();
            cubeTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            final RotationInterpolator cubeRI = new RotationInterpolator(new Alpha(-1,
                    4000), cubeTG);
            cubeRI.setSchedulingBounds(sceneBounds);
            cubeTG.addChild(cubeRI);
            cubeTG.addChild(cubeTranslateTG);

            // création de la forme géométrique du texte
            final Text3D textGeom = new Text3D(new Font3D(new Font(
                    "sans-serif", Font.PLAIN, 1), new FontExtrusion()), //$NON-NLS-1$
                    Messages.Java3DTest_text);
            textGeom.setAlignment(Text3D.ALIGN_CENTER);
            final Appearance textAppearance = new Appearance();
            textAppearance.setColoringAttributes(new ColoringAttributes(1f, 0.3f, 0.1f,
                    ColoringAttributes.FASTEST));

            // création de l'objet 3D lié au texte
            final Shape3D textShape = new Shape3D(textGeom, textAppearance);
            final Transform3D textScale = new Transform3D();
            // réduction du texte
            textScale.setScale(0.2);
            final TransformGroup textScaleTG = new TransformGroup(textScale);
            textScaleTG.addChild(textShape);

            // ajout d'un comportement réagissant aux mouvements de la souris
            final TransformGroup mouseRotateTG = new TransformGroup();
            mouseRotateTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            final MouseRotate mouseRotate = new MouseRotate(mouseRotateTG);
            mouseRotate.setSchedulingBounds(sceneBounds);
            mouseRotateTG.addChild(mouseRotate);
            mouseRotateTG.addChild(textScaleTG);
            mouseRotateTG.addChild(cubeTG);

            root.addChild(mouseRotateTG);

            return root;
        }
    }
}
