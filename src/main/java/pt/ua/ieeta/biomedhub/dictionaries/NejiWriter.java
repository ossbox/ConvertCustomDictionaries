/*  Copyright   2014 - Luís A. Bastião Silva and University of Aveiro
 *
 *
 *  Author: Luís A. Bastião Silva <bastiao@ua.pt>
 *
 *  This is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with application.  If not, see <http://www.gnu.org/licenses/>.
 */
package pt.ua.ieeta.biomedhub.dictionaries;

import com.google.common.collect.BiMap;
import com.google.common.collect.Multimap;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author Luís A. Bastião Silva - <bastiao@ua.pt>
 */
public class NejiWriter implements Writer {

    
    public String removeLastChar(String s) {
    if (s == null || s.length() == 0) {
        return s;
    }
    return s.substring(0, s.length()-1);
    }
    @Override
    public void write(String file, Multimap syn) {
        BufferedWriter writer = null;
        
        Multimap<String, String> synons = (Multimap) syn;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            
            for (String s: synons.keySet())
            {
                String _syns = "";
                for (String _syn : synons.get(s))
                {
                    _syns += _syn + "|";
                }
                writer.write("UMLS:"+s+":T023:ANAT\t" + removeLastChar(_syns) + "\n" );
                System.out.println("UMLS:"+s+":T023:ANAT\t" + removeLastChar(_syns) );
                
            }
        } catch (IOException e) {
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
            }
        }
    }

    // source, id , type, group 
    // UMLS:C0001327:T047:DISO acute laryngitis|acute laryngitis nos
}
