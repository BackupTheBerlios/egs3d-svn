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
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;

import javax.media.j3d.BranchGroup;

import org.egs3d.core.resources.IModel;
import org.egs3d.core.resources.IScene;

import com.sun.j3d.loaders.Loader;


/**
 * Implémentation de l'interface {@link IModel}.
 * 
 * @author romale
 */
public class Model extends AbstractSceneObject implements IModel {
    private final String name;
    private final Class<? extends Loader> loaderClass;
    private final String extension;
    private final ByteBuffer binaryData;


    public Model(final IScene scene, final String name, final String extension,
            final Class<? extends Loader> loaderClass, final InputStream input) {
        super(scene);
        this.name = name;
        this.extension = extension;
        this.loaderClass = loaderClass;

        try {
            binaryData = IOUtils.read(Channels.newChannel(input));
        } catch (Exception e) {
            throw new IllegalStateException("Erreur durant le chargement du modèle", e);
        }
    }


    public String getName() {
        return name;
    }


    public String getExtension() {
        return extension;
    }


    public Class<? extends Loader> getLoaderClass() {
        return loaderClass;
    }


    public BranchGroup getBranchGroup() {
        BranchGroup data = null;

        try {
            final File tempFile = File.createTempFile("model-", ".tmp");
            tempFile.deleteOnExit();
            final FileChannel channel = new FileOutputStream(tempFile).getChannel();
            binaryData.position(0);

            IOUtils.write(channel, binaryData);

            final Loader loader = loaderClass.newInstance();
            data = loader.load(tempFile.toURI().toURL()).getSceneGroup();
            tempFile.delete();
        } catch (Exception e) {
            throw new IllegalStateException("Erreur durant le chargement du modèle", e);
        } finally {
            binaryData.position(0);
        }

        return data;
    }


    public ByteBuffer getBinaryData() {
        return binaryData;
    }
}
