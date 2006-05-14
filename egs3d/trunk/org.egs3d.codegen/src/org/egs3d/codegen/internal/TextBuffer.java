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

package org.egs3d.codegen.internal;


/**
 * Tampon pour éditeur de texte.
 * 
 * @author romale
 */
public class TextBuffer {
    private final StringBuilder buffer = new StringBuilder();
    private int indentLevel = 0;
    private String indentString;


    public TextBuffer() {
        setIndentWidth(4);
    }


    public void newLine() {
        buffer.append("\n");
    }


    public void newLine(String str) {
        if (str != null) {
            addIndentation();
            buffer.append(str);
        }
        newLine();
    }


    public void addIndentLevel() {
        ++indentLevel;
    }


    public void removeIndentLevel() {
        indentLevel = Math.max(0, indentLevel - 1);
    }


    private void addIndentation() {
        for (int i = 0; i < indentLevel; ++i) {
            buffer.append(indentString);
        }
    }


    public StringBuilder getBuffer() {
        return buffer;
    }


    public int getIndentWidth() {
        return indentString.length();
    }


    public void setIndentWidth(int indentWidth) {
        indentWidth = Math.max(0, indentWidth);

        final StringBuilder buf = new StringBuilder(indentWidth);
        for (int i = 0; i < indentWidth; ++i) {
            buf.append(" ");
        }
        indentString = buf.toString();
    }
}
