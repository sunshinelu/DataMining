package com.evayInfo.Inglory.data.mining.platform.factories.text.analysis

import com.evayInfo.Inglory.data.mining.platform.factories.text.analysis.featureExtraction.FeatureExtraction

/**
 * Created by sunlu on 18/11/23.
 */
class TextAnalysisApplication {

  def textAnalysis(algorithm:String,algorithm_option:String,algorithm_mode:String,
                   trainTable:String,col_names:String,testTable:String,optTable:String,
                   filePth:String,modelName:String,
                   param0:String,param1:String,param2:String,param3:String,param4:String): Unit ={

    if (algorithm == "featureExtraction"){
      new FeatureExtraction().featureExtraction(algorithm_option,algorithm_mode,
        trainTable,col_names,testTable,optTable,
        filePth,modelName,
        param0,param1,param2,param3,param4)
    } else {
      println(s"worng $algorithm in algorithm name")
    }

  }
}
