package com.evayInfo.Inglory.data.mining.platform.factories.machine.learning.recommend



/**
 * Created by sunlu on 18/11/23.
 */
class RecommendAlgorithm {


  def recommend(algorithm_option:String,algorithm_mode:String,
                trainTable:String,col_names:String,testTable:String,optTable:String,
                filePth:String,modelName:String,
                param0:String,param1:String,param2:String,param3:String,param4:String
                 ): Unit ={

    val col_seq = col_names.split(",").toSeq
    val user_col = col_seq(0)
    val item_col = col_seq(1)
    val rating_col = col_seq(2)
    val model_path = filePth + modelName

    if (algorithm_option == "als"){

      if(algorithm_mode == "train"){

        val implicit_value = param0.toBoolean
        val rank = param1.toInt
        val numIterations = param2.toInt
        val lambda = param3.toDouble
        val alpha = param4.toDouble

        val build_model = new BuildALSModel()
        if (implicit_value == false){
          build_model.ALSModel(trainTable,user_col,item_col,rating_col,
            rank,numIterations,lambda,
            model_path)
        }else if(implicit_value == true){
          build_model.ALSModelImplicit(trainTable,user_col,item_col,rating_col,
            rank,numIterations,lambda,alpha,
            model_path)
        } else {
          println(s"wrong $implicit_value value in implicit_value")
        }
      } else if(algorithm_mode == "predict"){
        val predict_mode = param0
        val topN = param1.toInt

        val model_application = new ALSModelApplication()
        if (predict_mode == "rating"){
          model_application.RatingPrediction(testTable,model_path,user_col,item_col,rating_col,optTable)

        } else if (predict_mode == "top_items"){
          model_application.TopNProductsForUsers(testTable,model_path,user_col,item_col,rating_col,topN,optTable)
        } else if (predict_mode == "top_users"){
          model_application.TopNUsersForProducts(testTable,model_path,user_col,item_col,rating_col,topN,optTable)
        } else {
          println(s"wrong $predict_mode value in predict_mode")
        }

      } else if (algorithm_mode == "both"){

        val implicit_value = param0.toBoolean
        val rank = param1.toInt
        val numIterations = param2.toInt
        val lambda = param3.toDouble
        val alpha = param4.toDouble

        val build_model = new BuildALSModel()

        if (implicit_value == false){
          build_model.ALSModel_test(trainTable,user_col,item_col,rating_col,
            rank,numIterations,lambda,
            model_path,testTable,optTable)
        }else if(implicit_value == true){
          build_model.ALSModelImplicit_Test(trainTable,user_col,item_col,rating_col,
            rank,numIterations,lambda,alpha,
            model_path,testTable,optTable)
        }else {
          println(s"wrong $implicit_value value in implicit_value")
        }

      } else {
        println(s"wrong $algorithm_mode value in algorithm_mode")
      }

    } else if(algorithm_option == "user-based"){
      if(algorithm_mode == "train"){

      }else if(algorithm_mode == "predict"){

      }else if (algorithm_mode == "both"){

      }else {
        println(s"wrong $algorithm_mode value in algorithm_mode")
      }

    } else if (algorithm_option == "item-based"){
      if(algorithm_mode == "train"){

      }else if(algorithm_mode == "predict"){

      }else if (algorithm_mode == "both"){

      }else {
        println(s"wrong $algorithm_mode value in algorithm_mode")
      }


    } else {
      println(s"wrong $algorithm_option")
    }
  }



}
