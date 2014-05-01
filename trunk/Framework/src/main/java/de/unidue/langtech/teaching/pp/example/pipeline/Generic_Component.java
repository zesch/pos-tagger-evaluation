package de.unidue.langtech.teaching.pp.example.pipeline;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.parameter.ComponentParameters;
import de.tudarmstadt.ukp.dkpro.core.api.resources.MappingProvider;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.pp.type.GoldPOS;

@TypeCapability(
        outputs = { 
                "de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData",
            "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence",
            "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token",
            "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Lemma",
            "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS" })
public class Generic_Component
        extends JCasAnnotator_ImplBase
{
	
	/**
     * Use this language instead of the document language to resolve the model.
     */
    public static final String PARAM_LANGUAGE = ComponentParameters.PARAM_LANGUAGE;
    @ConfigurationParameter(name = PARAM_LANGUAGE, mandatory = false)
    protected String language;
	
    /**
     * Location of the mapping file for part-of-speech tags to UIMA types.
     */
    public static final String PARAM_POS_MAPPING_LOCATION = ComponentParameters.PARAM_POS_MAPPING_LOCATION;
    @ConfigurationParameter(name = PARAM_POS_MAPPING_LOCATION, mandatory = false)
    protected String posMappingLocation;

    /**
     * Use this part-of-speech tag set to use to resolve the tag set mapping instead of using the
     * tag set defined as part of the model meta data.
     */
    public static final String PARAM_POS_TAGSET = ComponentParameters.PARAM_POS_TAG_SET;
    @ConfigurationParameter(name = PARAM_POS_MAPPING_LOCATION, mandatory = false)
    protected String posTagset;

    private MappingProvider posMappingProvider;

    @Override
    public void initialize(UimaContext aContext)
            throws ResourceInitializationException
    {
            super.initialize(aContext);
            
            posMappingProvider = new MappingProvider();
            posMappingProvider.setDefault(MappingProvider.LOCATION, "classpath:/de/tudarmstadt/ukp/dkpro/" +
                            "core/api/lexmorph/tagset/${language}-${tagger.tagset}-tagger.map");
            posMappingProvider.setDefault(MappingProvider.BASE_TYPE, POS.class.getName());
            posMappingProvider.setDefault("tagger.tagset", "stts");
            posMappingProvider.setOverride(MappingProvider.LOCATION, posMappingLocation);
            posMappingProvider.setOverride(MappingProvider.LANGUAGE, language);
            posMappingProvider.setOverride("tagger.tagset", posTagset);
    }

     
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		
		CAS cas = aJCas.getCas();
		posMappingProvider.configure(cas);
				
		for (Token tokenAnno : JCasUtil.select(aJCas, Token.class)) {
			
			String pos = tokenAnno.getPos().getPosValue();
			
            Type posTag = posMappingProvider.getTagType(pos);
            POS goldPOS = (POS) cas.createAnnotation(posTag, tokenAnno.getBegin(), tokenAnno.getEnd());
            goldPOS.setPosValue(pos);
            System.out.println(goldPOS.getPosValue());
            goldPOS.addToIndexes();
            tokenAnno.setPos(goldPOS);
            
		
	}
	

}
}

