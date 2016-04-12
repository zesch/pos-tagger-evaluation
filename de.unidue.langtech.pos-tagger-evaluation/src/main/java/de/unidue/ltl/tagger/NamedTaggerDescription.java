package de.unidue.ltl.tagger;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.dkpro.lab.task.Discriminable;

public class NamedTaggerDescription
    implements Discriminable
{

    private String tagger;
    private String model;
    private AnalysisEngineDescription desc;

    public NamedTaggerDescription(String tagger, String model, AnalysisEngineDescription desc)
    {
        this.tagger = tagger;
        this.model = model;
        this.desc = desc;
    }

    public String getTaggerName()
    {
        return tagger;
    }

    public String getModelName()
    {
        return model;
    }

    public AnalysisEngineDescription getAnalysisEnginge()
    {
        return desc;
    }

    @Override
    public Object getDiscriminatorValue()
    {
        return tagger + (model.isEmpty() ? "" : "-" + model);
    }

    @Override
    public Object getActualValue()
    {
        return null;
    }

}
