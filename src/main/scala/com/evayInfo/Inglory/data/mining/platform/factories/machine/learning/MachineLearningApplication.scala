package com.evayInfo.Inglory.data.mining.platform.factories.machine.learning


import com.evayInfo.Inglory.data.mining.platform.factories.machine.learning.association.rules.{AssociationRulesAlgorithm, FPGrowthModelApplication, BuildFPGrowthModel}
import com.evayInfo.Inglory.data.mining.platform.factories.machine.learning.classification.ClassificationAlgorithm
import com.evayInfo.Inglory.data.mining.platform.factories.machine.learning.clustering.ClusteringAlgorithm
import com.evayInfo.Inglory.data.mining.platform.factories.machine.learning.graph.GraphAlgorithm
import com.evayInfo.Inglory.data.mining.platform.factories.machine.learning.recommend.{RecommendAlgorithm, ALSModelApplication, BuildALSModel}
import com.evayInfo.Inglory.data.mining.platform.factories.machine.learning.regression.RegressionAlgorithm
import com.evayInfo.Inglory.data.mining.platform.factories.machine.learning.time.series.TimeSeriesAlgorithm
import org.dmg.pmml.{True, False}


/**
 * Created by sunlu on 18/11/22.
 */
class MachineLearningApplication() {

  def machineLearning (algorithm:String,algorithm_option:String,algorithm_mode:String,
                       trainTable:String,col_names:String,testTable:String,optTable:String,
                       filePth:String,modelName:String,
                       param0:String,param1:String,param2:String,param3:String,param4:String): Unit ={
    if (algorithm == "associationRules"){
      new AssociationRulesAlgorithm().associationRules(algorithm_option,algorithm_mode,
        trainTable,col_names,testTable,optTable,
        filePth,modelName,
        param0,param1,param2,param3)
    } else if (algorithm == "classification"){
      new ClassificationAlgorithm().classification(algorithm_option,algorithm_mode,
        trainTable,col_names,testTable,optTable,
        filePth,modelName,
        param0,param1,param2,param3)
    } else if (algorithm== "clustering"){
      new ClusteringAlgorithm().clustering(algorithm_option,algorithm_mode,
        trainTable,col_names,testTable,optTable,
        filePth,modelName,
        param0,param1,param2,param3)
    } else if(algorithm== "graph"){
      new GraphAlgorithm().graph(algorithm_option,algorithm_mode,
        trainTable,col_names,testTable,optTable,
        filePth,modelName,
        param0,param1,param2,param3)
    } else if(algorithm== "recommend"){
      new RecommendAlgorithm().recommend(algorithm_option,algorithm_mode,
        trainTable,col_names,testTable,optTable,
        filePth,modelName,
        param0,param1,param2,param3,param4)
    } else if(algorithm== "regression"){
      new RegressionAlgorithm().regression(algorithm_option,algorithm_mode,
        trainTable,col_names,testTable,optTable,
        filePth,modelName,
        param0,param1,param2,param3)
    } else if(algorithm== "timeSeries"){
      new TimeSeriesAlgorithm().timeSeries(algorithm_option,algorithm_mode,
        trainTable,col_names,testTable,optTable,
        filePth,modelName,
        param0,param1,param2,param3)
    } else{
      println(s"wrong $algorithm name!")
    }
  }


}
