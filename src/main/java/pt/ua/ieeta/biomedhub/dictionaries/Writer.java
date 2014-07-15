/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.ua.ieeta.biomedhub.dictionaries;

import com.google.common.collect.Multimap;
import java.util.Map;

/**
 *
 * @author Luís A. Bastião Silva - <bastiao@ua.pt>
 */
public interface Writer {
    
    
    public void write(String file, Multimap syn);
}
