

/* First created by JCasGen Fri Apr 29 10:31:53 CEST 2016 */
package de.unidue.ltl.tagger.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Fri Apr 29 10:31:53 CEST 2016
 * XML source: /Users/toobee/Documents/Eclipse/pos-tagger-evaluation/de.unidue.langtech.pos-tagger-evaluation/src/main/resources/desc/type/POSType.xml
 * @generated */
public class GoldPOS extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(GoldPOS.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected GoldPOS() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public GoldPOS(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public GoldPOS(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public GoldPOS(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: posTag

  /** getter for posTag - gets 
   * @generated
   * @return value of the feature 
   */
  public POS getPosTag() {
    if (GoldPOS_Type.featOkTst && ((GoldPOS_Type)jcasType).casFeat_posTag == null)
      jcasType.jcas.throwFeatMissing("posTag", "de.unidue.ltl.tagger.type.GoldPOS");
    return (POS)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((GoldPOS_Type)jcasType).casFeatCode_posTag)));}
    
  /** setter for posTag - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setPosTag(POS v) {
    if (GoldPOS_Type.featOkTst && ((GoldPOS_Type)jcasType).casFeat_posTag == null)
      jcasType.jcas.throwFeatMissing("posTag", "de.unidue.ltl.tagger.type.GoldPOS");
    jcasType.ll_cas.ll_setRefValue(addr, ((GoldPOS_Type)jcasType).casFeatCode_posTag, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: cPosTag

  /** getter for cPosTag - gets 
   * @generated
   * @return value of the feature 
   */
  public String getCPosTag() {
    if (GoldPOS_Type.featOkTst && ((GoldPOS_Type)jcasType).casFeat_cPosTag == null)
      jcasType.jcas.throwFeatMissing("cPosTag", "de.unidue.ltl.tagger.type.GoldPOS");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GoldPOS_Type)jcasType).casFeatCode_cPosTag);}
    
  /** setter for cPosTag - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setCPosTag(String v) {
    if (GoldPOS_Type.featOkTst && ((GoldPOS_Type)jcasType).casFeat_cPosTag == null)
      jcasType.jcas.throwFeatMissing("cPosTag", "de.unidue.ltl.tagger.type.GoldPOS");
    jcasType.ll_cas.ll_setStringValue(addr, ((GoldPOS_Type)jcasType).casFeatCode_cPosTag, v);}    
  }

    