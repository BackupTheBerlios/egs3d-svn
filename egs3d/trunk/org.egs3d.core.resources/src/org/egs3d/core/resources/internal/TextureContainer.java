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


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.egs3d.core.resources.IScene;
import org.egs3d.core.resources.ITexture;
import org.egs3d.core.resources.ITextureContainer;


/**
 * Implémentation de l'interface {@link ITextureContainer}.
 * 
 * @author romale
 */
public class TextureContainer extends AbstractSceneObject implements ITextureContainer {
    private final List<ITexture> textures = new ArrayList<ITexture>(1);


    public TextureContainer(final IScene scene) {
        super(scene);
    }


    public void add(ITexture texture) {
        textures.add(texture);
        refreshProject();
    }


    public void remove(ITexture texture) {
        textures.remove(texture);
        refreshProject();
    }


    public int getSize() {
        return textures.size();
    }


    public ITexture getTexture(int i) {
        return textures.get(i);
    }


    public Iterator<ITexture> iterator() {
        return textures.iterator();
    }


    public ITexture[] toArray() {
        return textures.toArray(new ITexture[textures.size()]);
    }


    public ITexture create(String name, InputStream input) {
        return new Texture(getScene(), name, input);
    }
}
