
/* First created by JCasGen Wed May 21 16:50:19 CEST 2014 */
package de.unidue.langtech.teaching.ba.type;

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
 * Updated by JCasGen Wed May 21 16:50:19 CEST 2014
 * @generated */
public class GoldPOS_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (GoldPOS_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = GoldPOS_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new GoldPOS(addr, GoldPOS_Type.this);
  			   GoldPOS_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new GoldPOS(addr, GoldPOS_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = GoldPOS.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.langtech.teaching.suenme.type.GoldPOS");
 
  /** @generated */
  final Feature casFeat_posTag;
  /** @generated */
  final int     casFeatCode_posTag;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getPosTag(int addr) {
        if (featOkTst && casFeat_posTag == null)
      jcas.throwFeatMissing("posTag", "de.unidue.langtech.teaching.suenme.type.GoldPOS");
    return ll_cas.ll_getRefValue(addr, casFeatCode_posTag);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPosTag(int addr, int v) {
        if (featOkTst && casFeat_posTag == null)
      jcas.throwFeatMissing("posTag", "de.unidue.langtech.teaching.suenme.type.GoldPOS");
    ll_cas.ll_setRefValue(addr, casFeatCode_posTag, v);}
    
  
 
  /** @generated */
  final Feature casFeat_cPosTag;
  /** @generated */
  final int     casFeatCode_cPosTag;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getCPosTag(int addr) {
        if (featOkTst && casFeat_cPosTag == null)
      jcas.throwFeatMissing("cPosTag", "de.unidue.langtech.teaching.suenme.type.GoldPOS");
    return ll_cas.ll_getStringValue(addr, casFeatCode_cPosTag);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCPosTag(int addr, String v) {
        if (featOkTst && casFeat_cPosTag == null)
      jcas.throwFeatMissing("cPosTag", "de.unidue.langtech.teaching.suenme.type.GoldPOS");
    ll_cas.ll_setStringValue(addr, casFeatCode_cPosTag, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public GoldPOS_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_posTag = jcas.getRequiredFeatureDE(casType, "posTag", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS", featOkTst);
    casFeatCode_posTag  = (null == casFeat_posTag) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_posTag).getCode();

 
    casFeat_cPosTag = jcas.getRequiredFeatureDE(casType, "cPosTag", "uima.cas.String", featOkTst);
    casFeatCode_cPosTag  = (null == casFeat_cPosTag) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_cPosTag).getCode();

  }
}



    