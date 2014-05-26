package de.unidue.langtech.tagger;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.parameter.ComponentParameters;

public class TaggerConfiguration {

    @SuppressWarnings("rawtypes")
	Class tagger;
	String lang;
	String variant;
	String humanRedableName;
	String posMapping;

	@SuppressWarnings("rawtypes")
	public TaggerConfiguration(Class tagger, String posMapping, String lang,
			String variant) {
		this.tagger = tagger;
		this.lang = lang;
		this.variant = variant;
		this.posMapping = posMapping;

		String name = tagger.getName();
		int lastIndexOf = name.lastIndexOf(".");
		humanRedableName = lang + "-"
				+ tagger.getName().substring(lastIndexOf + 1)
				+ (variant != null ? "-" + variant : "");
	}

	@SuppressWarnings("unchecked")
    public AnalysisEngineDescription makeEngine()
			throws ResourceInitializationException {
		if (variant == null) {
			return createEngineDescription(tagger,
					ComponentParameters.PARAM_LANGUAGE, lang,
					ComponentParameters.PARAM_POS_MAPPING_LOCATION, posMapping);
		}
		return createEngineDescription(tagger,
				ComponentParameters.PARAM_LANGUAGE, lang,
				ComponentParameters.PARAM_VARIANT, variant,
				ComponentParameters.PARAM_POS_MAPPING_LOCATION, posMapping);
	}

}
