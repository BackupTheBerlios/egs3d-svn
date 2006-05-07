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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.egs3d.core.resources.IModel;
import org.egs3d.core.resources.IModelContainer;
import org.egs3d.core.resources.IScene;
import org.egs3d.core.resources.ISceneWriter;
import org.egs3d.core.resources.ITexture;
import org.egs3d.core.resources.ITextureContainer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.j3d.utils.scenegraph.io.SceneGraphFileWriter;
import com.sun.j3d.utils.scenegraph.io.UnsupportedUniverseException;
import com.sun.j3d.utils.universe.SimpleUniverse;


/**
 * Implémentation de l'enregistreur d'arbre scénique.
 * 
 * @author romale
 */
public class SceneWriter implements ISceneWriter {
    public void write(File file, IScene scene) throws IOException {
        ZipOutputStream zipOutput = null;
        try {
            zipOutput = new ZipOutputStream(new FileOutputStream(file));
            final UncloseableOutputStream proxyOutput = new UncloseableOutputStream(
                    zipOutput);

            // écriture du descripteur
            zipOutput.putNextEntry(new ZipEntry(SceneIOConstants.DESCRIPTOR_NAME));
            writeDescriptor(proxyOutput, scene);
            proxyOutput.flush();

            // écriture de la scène
            zipOutput.putNextEntry(new ZipEntry(SceneIOConstants.SCENE_NAME));
            writeScene(proxyOutput, scene);
            proxyOutput.flush();

            // écriture des modèles
            for (final IModel model : scene.getModelContainer()) {
                zipOutput.putNextEntry(new ZipEntry(SceneIOConstants.MODELS_NAME
                        + model.getName() + "." + model.getExtension()));
                zipOutput.write(model.getBinaryData());
            }
            zipOutput.flush();

            // écriture des textures
            for (final ITexture texture : scene.getTextureContainer()) {
                zipOutput.putNextEntry(new ZipEntry(SceneIOConstants.TEXTURES_NAME
                        + texture.getName() + ".png"));
                // les images sont écrites au format PNG pour conserver la même
                // qualité au fur et à mesure des enregistrements
                ImageIO.write(texture.getImage(), "PNG", proxyOutput);
            }
            proxyOutput.flush();
        } catch (Exception e) {
            final IOException exc = new IOException(
                    "Erreur durant l'enregistrement de la scène : " + scene.getName());
            exc.initCause(e);
            throw exc;
        } finally {
            IOUtils.close(zipOutput);
        }
    }


    private void writeScene(OutputStream output, IScene scene) throws Exception {
        final File tempFile = File.createTempFile("scene-", ".tmp");
        tempFile.deleteOnExit();

        SceneGraphFileWriter writer = null;
        try {
            final SimpleUniverse su = new SimpleUniverse(new Canvas3D(SimpleUniverse
                    .getPreferredConfiguration(), true));
            for (final BranchGroup bg : scene.getBranchGroupContainer()) {
                su.addBranchGraph(bg);
            }
            writer = new SceneGraphFileWriter(tempFile, su, true, "EGS3D", null);
        } catch (UnsupportedUniverseException e) {
            final IOException exc = new IOException("Univers non supporté");
            exc.initCause(e);
            throw exc;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception ignore) {
                }
            }
        }

        IOUtils.copy(new FileInputStream(tempFile), output);
        tempFile.delete();
    }


    private void writeDescriptor(OutputStream output, IScene scene) throws Exception {
        final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .newDocument();

        final Element root = doc.createElement("egs");
        root.setAttribute("version", "1.0");
        root.setAttribute("name", scene.getName());

        final IModelContainer modelContainer = scene.getModelContainer();
        if (modelContainer.getSize() > 0) {
            final Element modelsElem = doc.createElement("models");

            for (final IModel model : modelContainer) {
                final Element modelElem = doc.createElement("model");
                modelElem.setAttribute("name", model.getName());
                modelElem.setAttribute("file", model.getName() + "."
                        + model.getExtension());
                modelElem.setAttribute("loader", model.getLoaderClass().getName());
                modelsElem.appendChild(modelElem);
            }

            root.appendChild(modelsElem);
        }

        final ITextureContainer textureContainer = scene.getTextureContainer();
        if (textureContainer.getSize() > 0) {
            final Element texturesElem = doc.createElement("textures");

            for (final ITexture texture : textureContainer) {
                final Element textureElem = doc.createElement("texture");
                textureElem.setAttribute("name", texture.getName());
                textureElem.setAttribute("file", texture.getName() + ".png");
                texturesElem.appendChild(textureElem);
            }

            root.appendChild(texturesElem);
        }

        doc.appendChild(root);

        // création du document XML
        final Source source = new DOMSource(doc);
        final Result result = new StreamResult(output);
        final Transformer trans = TransformerFactory.newInstance().newTransformer();
        trans.transform(source, result);
    }
}
