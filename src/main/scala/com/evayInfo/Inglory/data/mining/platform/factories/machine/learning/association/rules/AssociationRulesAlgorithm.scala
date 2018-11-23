package com.evayInfo.Inglory.data.mining.platform.factories.machine.learning.association.rules

/**
 * Created by sunlu on 18/11/23.
 */
class AssociationRulesAlgorithm {

  def associationRules(algorithm_option:String,algorithm_mode:String,
                       trainTable:String,col_names:String,testTable:String,optTable:String,
                       filePth:String,modelName:String,
                       param0:String,param1:String,param2:String,param3:String
                        ): Unit ={

    if (algorithm_option == "FPGrowth"){

      val ipt_table = trainTable
      val col_name = col_names
      val sep = param0
      val support = param1.toDouble
      val confidence = param2.toDouble
      val partitions = param3.toInt
      val opt_table = optTable
      val model_path = filePth + modelName

      if(algorithm_mode == "train"){
        val fpg = new BuildFPGrowthModel()
        fpg.BuildFPGrowthModel(ipt_table, col_name, sep, support, confidence, partitions, opt_table, model_path)
      }else if (algorithm_mode == "predict"){
        val model_application = new FPGrowthModelApplication()
        model_application.FPGrowthModelApplication(model_path, ipt_table, col_name,sep, confidence, opt_table)
      }else if(algorithm_mode == "both"){
        println(s"FPGrowth do not have $algorithm_mode option")
      } else {
        println("wrong algorithm_mode name")
      }

    } else if(algorithm_option == "Apriori"){

    }

  }

}
