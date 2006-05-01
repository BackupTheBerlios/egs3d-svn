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


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import javax.media.j3d.BranchGroup;

import org.egs3d.core.resources.IModel;

import com.sun.j3d.loaders.Loader;


/**
 * Impl�mentation de l'interface {@link IModel}.
 * 
 * @author romale
 */
public class Model implements IModel {
    private final String name;
    private final Class<? extends Loader> loaderClass;
    private BranchGroup data;
    private final byte[] binaryData;


    public Model(final String name, final Class<? extends Loader> loaderClass,
            final URL url) {
        this.name = name;
        this.loaderClass = loaderClass;

        try {
            final ByteArrayOutputStream buf = new ByteArrayOutputStream();
            IOUtils.copy(url.openStream(), buf);
            binaryData = buf.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Erreur durant le chargement du mod�le", e);
        }
    }


    public String getName() {
        return name;
    }


    public Class<? extends Loader> getLoaderClass() {
        return loaderClass;
    }


    public BranchGroup getBranchGroup() {
        if (data == null) {
            try {
                final File tempFile = File.createTempFile("model-", ".tmp");
                tempFile.deleteOnExit();
                IOUtils.copy(new ByteArrayInputStream(binaryData), new FileOutputStream(
                        tempFile));

                final Loader loader = loaderClass.newInstance();
                loader.setFlags(Loader.LOAD_ALL);
                data = loader.load(tempFile.toURI().toURL()).getSceneGroup();
                tempFile.delete();
            } catch (Exception e) {
                throw new IllegalStateException("Erreur durant le chargement du mod�le",
                        e);
            }
        }
        return data;
    }


    public byte[] getBinaryData() {
        return binaryData;
    }
}
