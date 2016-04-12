package de.unidue.ltl.tagger;

import org.apache.uima.collection.CollectionReaderDescription;
import org.dkpro.lab.task.Discriminable;

public class NamedCorpusReaderDescription
    implements Discriminable
{
    private CollectionReaderDescription crd;
    private String name;
    private String domain;

    public NamedCorpusReaderDescription(String name, String domain, CollectionReaderDescription crd)
    {
        this.crd = crd;
        this.name = name;
        this.domain = domain;
    }

    public String getName()
    {
        return name;
    }

    public String getDomain()
    {
        return domain;
    }

    public CollectionReaderDescription getReaderDesc()
    {
        return crd;
    }

    @Override
    public Object getDiscriminatorValue()
    {
        return (!domain.isEmpty() ? domain + "-" : "") + name;
    }

    @Override
    public Object getActualValue()
    {
        return null;
    }

}
