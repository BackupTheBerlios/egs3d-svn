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

package org.egs3d.core.resources;


import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.media.j3d.Alpha;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ImageComponent;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.TransformGroup;

import junit.framework.TestCase;

import com.sun.j3d.utils.geometry.ColorCube;


public class SceneReaderWriterTest extends TestCase {
    public void testReadWriteScene() throws Exception {
        final ISceneReader reader = ResourcesPlugin.createSceneReader();
        final ISceneWriter writer = ResourcesPlugin.createSceneWriter();

        final TransformGroup cubeTG = new TransformGroup();
        cubeTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        final RotationInterpolator cubeRI = new RotationInterpolator(new Alpha(-1, 3000),
                cubeTG);
        cubeRI.setSchedulingBounds(new BoundingSphere());
        cubeTG.addChild(new ColorCube(0.2));
        cubeTG.addChild(cubeRI);

        final BufferedImage img = ImageIO.read(getClass().getResource("cnam.jpg"));
        final ImageComponent2D image2D = new ImageComponent2D(ImageComponent.FORMAT_RGB,
                img);
        image2D.setCapability(ImageComponent.ALLOW_IMAGE_READ);
        final Background bg = new Background(image2D);
        bg.setApplicationBounds(new BoundingSphere());

        final BranchGroup root = new BranchGroup();
        root.setCapability(BranchGroup.ALLOW_DETACH);
        root.addChild(bg);
        root.addChild(cubeTG);
        root.compile();

        final IScene scene = ResourcesPlugin.createScene();
        scene.getBranchGroupContainer().add(root);
        assertEquals(1, scene.getBranchGroupContainer().getSize());

        final File file = File.createTempFile("SceneReaderWriterTest-", ".egs");
        file.deleteOnExit();
        writer.write(file, scene);

        final IScene testScene = reader.read(file);
        assertNotNull(testScene);
        assertEquals(1, testScene.getBranchGroupContainer().getSize());

        final BranchGroup testRoot = testScene.getBranchGroupContainer()
                .getBranchGroup(0);
        assertNotNull(testRoot);
        assertEquals(2, testRoot.numChildren());
        assertTrue(testRoot.getChild(0) instanceof Background);
        assertTrue(testRoot.getChild(1) instanceof TransformGroup);
    }
}
