package de.unidue.ltl.tagger.task;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import java.io.File;
import java.io.IOException;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.dkpro.lab.engine.TaskContext;
import org.dkpro.lab.storage.StorageService.AccessMode;
import org.dkpro.lab.task.Discriminator;
import org.dkpro.lab.uima.task.impl.UimaTaskBase;

import de.tudarmstadt.ukp.dkpro.core.performance.Stopwatch;
import de.unidue.ltl.tagger.components.AccuracyWriter;
import de.unidue.ltl.tagger.components.Keys;
import de.unidue.ltl.tagger.components.MetaWriter;
import de.unidue.ltl.tagger.components.NamedCorpusReaderDescription;
import de.unidue.ltl.tagger.components.NamedTaggerDescription;
import de.unidue.ltl.tagger.components.TimeNormalizer;
import de.unidue.ltl.tagger.report.ConfusionMatrixReport;
import de.unidue.ltl.tagger.report.TagPrecisionRecallF1Report;
import de.unidue.ltl.tagger.uima.GoldPOSAnnotator;

public class RunTaggerTask
    extends UimaTaskBase
    implements Keys
{
    @Discriminator(name=DISCRIMINATOR_READER)
    protected Object readerDesc;
    @Discriminator(name=DISCRIMINATOR_TAGGER)
    protected Object taggerDesc;

    @Override
    public CollectionReaderDescription getCollectionReaderDescription(TaskContext aContext)
        throws ResourceInitializationException, IOException
    {
        NamedCorpusReaderDescription nrd = (NamedCorpusReaderDescription) readerDesc;
        return nrd.getReaderDesc();
    }

    @Override
    public AnalysisEngineDescription getAnalysisEngineDescription(TaskContext aContext)
        throws ResourceInitializationException, IOException
    {

        NamedCorpusReaderDescription nrd = (NamedCorpusReaderDescription) readerDesc;
        NamedTaggerDescription ntd = (NamedTaggerDescription) taggerDesc;

        File contextFolder = aContext.getFolder("", AccessMode.READWRITE);

        File timer = aContext.getFile(FILE_TIMER, AccessMode.READWRITE);
        File fine = aContext.getFile(FILE_ACC_FINE, AccessMode.READWRITE);
        File coarse = aContext.getFile(FILE_ACC_COARSE, AccessMode.READWRITE);
        File corpus = aContext.getFile(FILE_CORPUS, AccessMode.READWRITE);
        File tagger = aContext.getFile(FILE_TAGGER, AccessMode.READWRITE);
        File tokenCount = aContext.getFile(FILE_TOKEN_COUNT, AccessMode.READWRITE);
        File normalizedTokenTime = aContext.getFile(FILE_NORMALIZED_TOKEN_TIME,
                AccessMode.READWRITE);

        return createEngine(
                createEngineDescription(GoldPOSAnnotator.class),
                createEngineDescription(Stopwatch.class, Stopwatch.PARAM_TIMER_NAME, "t1"),
                ntd.getAnalysisEnginge(),
                createEngineDescription(Stopwatch.class, Stopwatch.PARAM_TIMER_NAME, "t1",
                        Stopwatch.PARAM_OUTPUT_FILE, timer),
                createEngineDescription(AccuracyWriter.class, AccuracyWriter.PARAM_FINE_RESULT,
                        fine, AccuracyWriter.PARAM_COARSE_RESULT, coarse),
                createEngineDescription(MetaWriter.class, MetaWriter.PARAM_CORPUS_NAME,
                        nrd.getName(), MetaWriter.PARAM_CORPUS_DOMAIN, nrd.getDomain(),
                        MetaWriter.PARAM_CORPUS_FILE, corpus, MetaWriter.PARAM_TAGGER_FILE, tagger,
                        MetaWriter.PARAM_TAGGER_NAME, ntd.getTaggerName(),
                        MetaWriter.PARAM_TAGGER_MODEL_NAME, ntd.getModelName(),
                        MetaWriter.PARAM_TOKEN_COUNT_FILE, tokenCount),
                // enables a HTML report for a tagging error analysis
                // createEngineDescription(SequenceReportHTML.class,
                // SequenceReportHTML.PARAM_OUTPUT_FOLDER, contextFolder),
                createEngineDescription(ConfusionMatrixReport.class,
                        ConfusionMatrixReport.PARAM_OUTPUT_FOLDER, contextFolder),
                createEngineDescription(TagPrecisionRecallF1Report.class,
                        TagPrecisionRecallF1Report.PARAM_OUTPUT_FOLDER, contextFolder),                
                createEngineDescription(TimeNormalizer.class, TimeNormalizer.PARAM_TIMER_FILE,
                        timer, TimeNormalizer.PARAM_TOKEN_COUNT_FILE, tokenCount,
                        TimeNormalizer.PARAM_NORMALIZED_FILE, normalizedTokenTime));

    }

}
