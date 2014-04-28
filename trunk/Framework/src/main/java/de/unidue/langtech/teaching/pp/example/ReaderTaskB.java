package de.unidue.langtech.teaching.pp.example;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

import de.unidue.langtech.teaching.pp.type.OriginalSentiment;


/**
 * Our simple reader that reads the text file which contains generally the tweets and their
 * sentiment
 * and puts each line of the file in a single document.
 * 
 * @author suenme
 *
 */
public class ReaderTaskB
    extends JCasCollectionReader_ImplBase
{

    /**
     * Input file
     */
    public static final String PARAM_INPUT_FILE = "InputFile";
    @ConfigurationParameter(name = PARAM_INPUT_FILE, mandatory = true)
    private File inputFile;    
    
    private List<String> lines;
    private int currentLine;
    
    /* 
     * initializes the reader
     */
    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        
        try {
           lines = FileUtils.readLines(inputFile);
           currentLine = 0;
        }
        catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }
    
    
    /* 
     * true, if there is a next document, false otherwise
     */
    public boolean hasNext()
        throws IOException, CollectionException
    {
        return currentLine < lines.size();
    }
    
    
    /* 
     * feeds the next document into the pipeline
     */
    @Override
    public void getNext(JCas jcas)
        throws IOException, CollectionException
    {
        // split line into gold standard language and actual text
        String[] parts = lines.get(currentLine).split("\t");
        
        // it is always good to do some sanity checks
        if (parts.length != 4) {
            throw new IOException("Wrong line format: " + lines.get(currentLine));
        }
        
        // add original sentiment as annotation
        OriginalSentiment originalsentiment = new OriginalSentiment(jcas);
        String sentiment = parts[2].replaceAll("\"", "");
        originalsentiment.setSentiment(sentiment);
        originalsentiment.addToIndexes();
        
        // add actual text of the document
        jcas.setDocumentText(parts[3]);
        
        currentLine++;
    }

    
    /* 
     * informs the pipeline about the current progress
     */
    public Progress[] getProgress()
    {
        return new Progress[] { new ProgressImpl(currentLine, lines.size(), "lines") };
    }
}