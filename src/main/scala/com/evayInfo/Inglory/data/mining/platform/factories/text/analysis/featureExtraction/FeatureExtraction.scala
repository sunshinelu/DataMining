package com.evayInfo.Inglory.data.mining.platform.factories.text.analysis.featureExtraction



/**
 * Created by sunlu on 18/11/29.
 */
class FeatureExtraction {

  def featureExtraction(algorithm_option:String,algorithm_mode:String,
                        trainTable:String,col_names:String,testTable:String,optTable:String,
                        filePth:String,modelName:String,
                        param0:String,param1:String,param2:String,param3:String,param4:String): Unit ={

    val feature_size = param0.toInt
    val min_count = param1.toInt
    val model_path = filePth + modelName

    if (algorithm_option == "WordCount"){
      if (algorithm_mode == "train"){

        val feature_model: BuildFeatureExtractionModel = new BuildFeatureExtractionModel
        feature_model.WordCount(trainTable, col_names, feature_size, min_count, model_path, optTable)

      } else if(algorithm_mode == "predict"){

        val model_application:FeatureExtractionModelApplication = new FeatureExtractionModelApplication()
        model_application.FeatureExtractionModel_WordCount(model_path, testTable, optTable)

      } else {
        println(s"$algorithm_mode is wrong algorithm_mode name.")
      }

    } else if (algorithm_option == "TF_IDF"){

      if (algorithm_mode == "train"){

        val feature_model: BuildFeatureExtractionModel = new BuildFeatureExtractionModel
        feature_model.TF_IDF(trainTable, col_names, feature_size, min_count, model_path, optTable)

      } else if(algorithm_mode == "predict"){

        val model_application:FeatureExtractionModelApplication = new FeatureExtractionModelApplication()
        model_application.FeatureExtractionModel_TF_IDF(model_path, testTable, optTable)

      } else {
        println(s"$algorithm_mode is wrong algorithm_mode name.")
      }

    } else if(algorithm_option == "Word2Vec"){
      if (algorithm_mode == "train"){

        val feature_model: BuildFeatureExtractionModel = new BuildFeatureExtractionModel
        feature_model.Word2Vec(trainTable, col_names, feature_size, min_count, model_path, optTable)

      } else if(algorithm_mode == "predict"){

        val model_application:FeatureExtractionModelApplication = new FeatureExtractionModelApplication()
        model_application.FeatureExtractionModel_Word2Vec(model_path, testTable, optTable)

      } else {
        println(s"$algorithm_mode is wrong algorithm_mode name.")
      }

    } else {
      println(s" $algorithm_option is wrong algorithm_option name")
    }
  }
}
