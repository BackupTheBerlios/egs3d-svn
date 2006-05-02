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

package org.egs3d.core.resources.internal;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.media.j3d.BranchGroup;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.egs3d.core.resources.IBranchGroupContainer;
import org.egs3d.core.resources.IModel;
import org.egs3d.core.resources.IModelContainer;
import org.egs3d.core.resources.IScene;
import org.egs3d.core.resources.ISceneReader;
import org.egs3d.core.resources.ITexture;
import org.egs3d.core.resources.ITextureContainer;
import org.egs3d.core.resources.ResourcesPlugin;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.j3d.loaders.Loader;
import com.sun.j3d.utils.scenegraph.io.SceneGraphFileReader;


/**
 * Implémentation du lecteur d'arbre scénique.
 * 
 * @author romale
 */
public class SceneReader implements ISceneReader {
    public IScene read(File file) throws IOException {
        final IScene scene;
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(file);

            scene = ResourcesPlugin.createScene();

            readBranchGroups(zipFile, scene.getBranchGroupContainer());
            readModels(zipFile, scene.getModelContainer());
            readTextures(zipFile, scene.getTextureContainer());
        } catch (Exception e) {
            final IOException exc = new IOException(
                    "Erreur durant la lecture de la scène : " + file.getAbsolutePath());
            exc.initCause(e);
            throw exc;
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException ignore) {
                }
            }
        }

        return scene;
    }


    private void readBranchGroups(ZipFile zipFile, IBranchGroupContainer container)
            throws Exception {
        final ZipEntry entry = zipFile.getEntry(SceneIOConstants.SCENE_NAME);
        if (entry == null) {
            return;
        }
        final InputStream input = zipFile.getInputStream(entry);

        final File tempFile = File.createTempFile("scene-", ".tmp");
        tempFile.deleteOnExit();

        IOUtils.copy(input, new FileOutputStream(tempFile));

        final SceneGraphFileReader reader = new SceneGraphFileReader(tempFile);
        try {
            final BranchGroup[] roots = reader.readAllBranchGraphs();
            for (final BranchGroup root : roots) {
                container.add(root);
            }
        } finally {
            try {
                reader.close();
            } catch (Exception ignore) {
            }
        }

        tempFile.delete();
    }


    @SuppressWarnings("unchecked")
    private void readModels(ZipFile zipFile, IModelContainer container) throws Exception {
        final ZipEntry descEntry = zipFile.getEntry(SceneIOConstants.DESCRIPTOR_NAME);
        if (descEntry == null) {
            // pas de descripteur
            return;
        }

        final InputStream input = zipFile.getInputStream(descEntry);
        final XPath xpath = XPathFactory.newInstance().newXPath();
        final NodeList nodeList = (NodeList) xpath.evaluate("/egs/models/model",
                new InputSource(input), XPathConstants.NODESET);
        final int nodeListLen = nodeList.getLength();

        for (int i = 0; i < nodeListLen; ++i) {
            final Element elem = (Element) nodeList.item(i);
            final String name = elem.getAttribute("name");
            final String file = elem.getAttribute("file");
            final String loaderClassName = elem.getAttribute("loaderClass");

            if (name.length() == 0 || file.length() == 0 || loaderClassName.length() == 0) {
                // pas d'attribut "name" ou "file" ou "loaderClass"
                continue;
            }

            final ZipEntry modelEntry = zipFile.getEntry(SceneIOConstants.MODELS_NAME
                    + file);
            if (modelEntry != null) {
                final Class<? extends Loader> loaderClass;
                try {
                    loaderClass = (Class<? extends Loader>) Class
                            .forName(loaderClassName);
                } catch (ClassNotFoundException e) {
                    continue;
                }
                final IModel model = container.create(name, loaderClass, zipFile
                        .getInputStream(modelEntry));
                container.add(model);
            }
        }
    }


    private void readTextures(ZipFile zipFile, ITextureContainer container)
            throws Exception {
        final ZipEntry descEntry = zipFile.getEntry(SceneIOConstants.DESCRIPTOR_NAME);
        if (descEntry == null) {
            // pas de descripteur
            return;
        }

        final InputStream input = zipFile.getInputStream(descEntry);
        final XPath xpath = XPathFactory.newInstance().newXPath();
        final NodeList nodeList = (NodeList) xpath.evaluate("/egs/textures/texture",
                new InputSource(input), XPathConstants.NODESET);
        final int nodeListLen = nodeList.getLength();

        for (int i = 0; i < nodeListLen; ++i) {
            final Element elem = (Element) nodeList.item(i);
            final String name = elem.getAttribute("name");
            final String file = elem.getAttribute("file");

            if (name.length() == 0 || file.length() == 0) {
                // pas d'attribut "name" ou "file"
                continue;
            }

            final ZipEntry textureEntry = zipFile.getEntry(SceneIOConstants.TEXTURES_NAME
                    + file);
            if (textureEntry != null) {
                final ITexture texture = container.create(name, zipFile
                        .getInputStream(textureEntry));
                container.add(texture);
            }
        }
    }
}
