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



package pt.ua.ieeta.biomedhub.convertcustomdictionaries;

import com.google.common.collect.Multimap;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pt.ua.ieeta.biomedhub.dictionaries.NejiWriter;
import pt.ua.ieeta.biomedhub.dictionaries.Reader;
import pt.ua.ieeta.biomedhub.dictionaries.Writer;
import pt.ua.ieeta.biomedhub.dictionaries.wrappers.CTakesLucene;

/**
 *
 * @author Luís A. Bastião Silva - <bastiao@ua.pt>
 */
public class Main {
    
    
    public static void main(String [] args)
    {
        
        Reader r = new CTakesLucene(args[0]);
        Multimap m = null;
        try {
            m = r.readBlocks(100);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Writer w = new NejiWriter();
        w.write(args[1], m);
        
    }

}
