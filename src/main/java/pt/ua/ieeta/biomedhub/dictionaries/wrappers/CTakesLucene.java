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


package pt.ua.ieeta.biomedhub.dictionaries.wrappers;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import pt.ua.ieeta.biomedhub.dictionaries.Reader;

/**
 *
 * @author Luís A. Bastião Silva - <bastiao@ua.pt>
 */
public class CTakesLucene  implements Reader
{
    
    private String directory ;
    private Directory index;
    private Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_4_9);
    private Multimap<String, String> synons = ArrayListMultimap.create();

    

    
    public CTakesLucene(String directory)
    {
        this.directory = directory;
        try
        {
            index = FSDirectory.open(new File(directory));
        }
        catch(Exception e )
        {
        }
    }
    
    public Multimap readBlocks(int blockSize) throws IOException
    {
        
        try {
            if (!DirectoryReader.indexExists(index)) {
                return null;
            }
        } catch (IOException ex) {
            Logger.getLogger("lucene").log(Level.SEVERE, null, ex);
        }


        String querystr = "*:*";
        Query q = null;
        try {
            q = new QueryParser(Version.LUCENE_4_9, "title", analyzer).parse(querystr);
        } catch (ParseException ex) {
            Logger.getLogger(CTakesLucene.class.getName()).log(Level.SEVERE, null, ex);
        }
        int hitsPerPage = 100;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(200000, true);
        searcher.search(q, collector);
        
        ScoreDoc[] hits = collector.topDocs().scoreDocs;
        System.out.println("Found " + hits.length + " hits.");
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            //System.out.println((i + 1) + ". " + d.get("first_word") + "\t" + d.get("code"));
            System.out.print(".");
            synons.put(d.get("code"), d.get("first_word"));
        }
        
        
        
        return synons;

    
    }
}
