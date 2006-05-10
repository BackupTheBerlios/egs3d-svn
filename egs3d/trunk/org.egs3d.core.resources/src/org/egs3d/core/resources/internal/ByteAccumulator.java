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


import java.nio.ByteBuffer;


/**
 * Tampon pour {@link ByteBuffer}.
 * 
 * @author romale
 */
public class ByteAccumulator {
    public static final int DEFAULT_BUFFER_SIZE = 1024;
    private ByteBuffer buffer;


    public ByteAccumulator(final ByteBuffer buffer) {
        this.buffer = buffer;
    }


    public ByteAccumulator() {
        this(DEFAULT_BUFFER_SIZE);
    }


    public ByteAccumulator(final int size) {
        this(ByteBuffer.allocate(size));
    }


    public void append(ByteBuffer buf) {
        if (buf == null) {
            return;
        }
        if (buffer.remaining() < buf.remaining()) {
            final int position = buffer.position();
            buffer.flip();
            final int newCap = (int) Math
                    .round((buffer.remaining() + buf.remaining()) * 2);

            final ByteBuffer newBuffer = buffer.isDirect() ? ByteBuffer
                    .allocateDirect(newCap) : ByteBuffer.allocate(newCap);
            newBuffer.put(buffer);
            buffer = newBuffer;
            buffer.position(position);
        }
        assert buffer.remaining() < buf.remaining();
        buffer.put(buf);
    }


    public ByteBuffer get() {
        return buffer.duplicate();
    }
}
