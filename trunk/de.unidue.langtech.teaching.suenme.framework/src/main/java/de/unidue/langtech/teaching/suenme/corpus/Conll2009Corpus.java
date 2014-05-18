/*******************************************************************************
 * Copyright 2011
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.unidue.langtech.teaching.suenme.corpus;

import static de.tudarmstadt.ukp.dkpro.core.api.io.ResourceCollectionReaderBase.INCLUDE_PREFIX;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;

import de.unidue.langtech.teaching.suenme.reader.Conll2009Reader;


/**
 * Conll2009 German Corpus
 * 
 * @author Onur
 *
 */
public class Conll2009Corpus
    extends CorpusBase
{

    static final String LANGUAGE = "de";
    static final String NAME = "Conll2009";

    CollectionReaderDescription reader;

    public Conll2009Corpus() throws Exception
    {
    	System.setProperty("PROJECT_HOME", "src\test\resources\test");
    	final String dkproHome = System.getenv("PROJECT_HOME");
        String conll2009 = dkproHome + "\\de";

        initialize(conll2009);
    }

    public Conll2009Corpus(String conll2009) throws Exception
    {
        initialize(conll2009);
    }

    private void initialize(String conll2009) throws Exception {
        reader = CollectionReaderFactory.createReaderDescription(
                Conll2009Reader.class,
                Conll2009Reader.PARAM_SOURCE_LOCATION, conll2009,
                Conll2009Reader.PARAM_LANGUAGE, LANGUAGE,
                Conll2009Reader.PARAM_PATTERNS, new String[] {INCLUDE_PREFIX + "*.txt"}
        );
    }

    @Override
    public CollectionReaderDescription getReader()
    {
        return reader;
    }

    @Override
    public String getLanguage()
    {
        return LANGUAGE;
    }

    @Override
    public String getName()
    {
        return NAME;
    }
}