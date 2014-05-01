
/* First created by JCasGen Tue Jan 21 10:31:45 CET 2014 */
package de.unidue.langtech.teaching.pp.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Thu May 01 16:40:53 CEST 2014
 * @generated */
public class SentimentIndex_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (SentimentIndex_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = SentimentIndex_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new SentimentIndex(addr, SentimentIndex_Type.this);
  			   SentimentIndex_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new SentimentIndex(addr, SentimentIndex_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = SentimentIndex.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.langtech.teaching.pp.type.SentimentIndex");
 
  /** @generated */
  final Feature casFeat_beginIndex;
  /** @generated */
  final int     casFeatCode_beginIndex;
  /** @generated */ 
  public int getBeginIndex(int addr) {
        if (featOkTst && casFeat_beginIndex == null)
      jcas.throwFeatMissing("beginIndex", "de.unidue.langtech.teaching.pp.type.SentimentIndex");
    return ll_cas.ll_getIntValue(addr, casFeatCode_beginIndex);
  }
  /** @generated */    
  public void setBeginIndex(int addr, int v) {
        if (featOkTst && casFeat_beginIndex == null)
      jcas.throwFeatMissing("beginIndex", "de.unidue.langtech.teaching.pp.type.SentimentIndex");
    ll_cas.ll_setIntValue(addr, casFeatCode_beginIndex, v);}
    
  
 
  /** @generated */
  final Feature casFeat_endIndex;
  /** @generated */
  final int     casFeatCode_endIndex;
  /** @generated */ 
  public int getEndIndex(int addr) {
        if (featOkTst && casFeat_endIndex == null)
      jcas.throwFeatMissing("endIndex", "de.unidue.langtech.teaching.pp.type.SentimentIndex");
    return ll_cas.ll_getIntValue(addr, casFeatCode_endIndex);
  }
  /** @generated */    
  public void setEndIndex(int addr, int v) {
        if (featOkTst && casFeat_endIndex == null)
      jcas.throwFeatMissing("endIndex", "de.unidue.langtech.teaching.pp.type.SentimentIndex");
    ll_cas.ll_setIntValue(addr, casFeatCode_endIndex, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public SentimentIndex_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_beginIndex = jcas.getRequiredFeatureDE(casType, "beginIndex", "uima.cas.Integer", featOkTst);
    casFeatCode_beginIndex  = (null == casFeat_beginIndex) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_beginIndex).getCode();

 
    casFeat_endIndex = jcas.getRequiredFeatureDE(casType, "endIndex", "uima.cas.Integer", featOkTst);
    casFeatCode_endIndex  = (null == casFeat_endIndex) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_endIndex).getCode();

  }
}



    