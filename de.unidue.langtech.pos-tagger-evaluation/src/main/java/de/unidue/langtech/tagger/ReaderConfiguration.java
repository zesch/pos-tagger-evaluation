package de.unidue.langtech.tagger;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.parameter.ComponentParameters;

public class ReaderConfiguration
{

    @SuppressWarnings("rawtypes")
    Class theClass;
    String posMapping;
    String inputPath;
    String[] pattern;
    String humanReadableName;
    String lang;

    @SuppressWarnings("rawtypes")
    public ReaderConfiguration(Class readerClass,
            String humanReadable, String lang, String posMapping, String inputPath, String[] pattern)
    {
        theClass = readerClass;
        this.posMapping = posMapping;
        this.lang = lang;
        this.inputPath = inputPath;
        this.pattern = pattern;

        humanReadableName = humanReadable;
    }

    @SuppressWarnings("unchecked")
    public CollectionReaderDescription makeReader()
        throws ResourceInitializationException
    {
        if (pattern == null || pattern.length == 0) {

            return createReaderDescription(theClass, ComponentParameters.PARAM_LANGUAGE, lang,
                    ComponentParameters.PARAM_POS_MAPPING_LOCATION, posMapping,
                    ComponentParameters.PARAM_SOURCE_LOCATION, inputPath);
        }
        return createReaderDescription(theClass, ComponentParameters.PARAM_LANGUAGE, lang,
                ComponentParameters.PARAM_POS_MAPPING_LOCATION, posMapping,
                ComponentParameters.PARAM_SOURCE_LOCATION, inputPath, "patterns", pattern);
    }

}
