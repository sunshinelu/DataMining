
## 一.［机器学习算法库］

### 1.［机器学习算法库］－［关联规则］

#### 1）［机器学习算法库］－［关联规则］－［FPGrowth]

##### (1) train

POST 方法
http://0.0.0.0:5000/v1/machineLearning

{
	"FuncMode":{
		"algorithm": "associationRules",
		"option": "FPGrowth",
		"mode": "train"
	},
	"TableName":{
		"trainTable": "FPGrowth_data",
		"col_names":"items",
		"testTable": "",
		"optTable": "FPGrowth_data_pred"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "FPGrowth_model"
	},
	"ModelParam":{
		"param0": " ",
		"param1": "0.2",
		"param2": "0.8",
		"param3": "10",
		"param4": ""
	}
}

##### (2)predict

POST 方法
http://0.0.0.0:5000/v1/machineLearning


{
	"FuncMode":{
		"algorithm": "associationRules",
		"option": "FPGrowth",
		"mode": "predict"
	},
	"TableName":{
		"trainTable": "FPGrowth_data",
		"col_names":"items",
		"testTable": "",
		"optTable": "FPGrowth_data_pred_application"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "FPGrowth_model"
	},
	"ModelParam":{
		"param0": " ",
		"param1": "0.2",
		"param2": "0.8",
		"param3": "10",
        "param4": ""
	}
}

### 2.［机器学习算法库］－［推荐算法］

#### 1)测试 ［机器学习算法库］－［推荐算法］－［ALS］

##### (1)train

###### 显式

POST 方法
http://0.0.0.0:5000/v1/machineLearning

{
	"FuncMode":{
		"algorithm": "recommend",
		"option": "als",
		"mode": "train"
	},
	"TableName":{
		"trainTable": "recommenderSys_Demo_Data_sample",
		"col_names":"user,item,rating",
		"testTable": "recommenderSys_Demo_Data_sample",
		"optTable": "recommenderSys_Demo_Data_sample_pred"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "als_model_rdd"
	},
	"ModelParam":{
		"param0": "False",
		"param1": "10",
		"param2": "10",
		"param3": "0.01",
		"param4": "0.01"
	}
}

###### 隐式


POST 方法
http://0.0.0.0:5000/v1/machineLearning

{
	"FuncMode":{
		"algorithm": "recommend",
		"option": "als",
		"mode": "train"
	},
	"TableName":{
		"trainTable": "recommenderSys_Demo_Data_sample",
		"col_names":"user,item,rating",
		"testTable": "recommenderSys_Demo_Data_sample",
		"optTable": "recommenderSys_Demo_Data_sample_pred"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "als_model_rdd_implicit"
	},
	"ModelParam":{
		"param0": "True",
		"param1": "10",
		"param2": "10",
		"param3": "0.01",
		"param4": "0.01"
	}
}


##### (2)predict

###### rating_predict

POST 方法
http://0.0.0.0:5000/v1/machineLearning

{
	"FuncMode":{
		"algorithm": "recommend",
		"option": "als",
		"mode": "predict"
	},
	"TableName":{
		"trainTable": "",
		"col_names":"user,item,rating",
		"testTable": "recommenderSys_Demo_Data_sample",
		"optTable": "recommenderSys_Demo_Data_sample_pred"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "als_model_rdd"
	},
	"ModelParam":{
		"param0": "rating",
		"param1": "10",
		"param2": "",
		"param3": "",
		"param4": ""
	}
}

###### top_items

POST 方法
http://0.0.0.0:5000/v1/machineLearning

{
	"FuncMode":{
		"algorithm": "recommend",
		"option": "als",
		"mode": "predict"
	},
	"TableName":{
		"trainTable": "",
		"col_names":"user,item,rating",
		"testTable": "recommenderSys_Demo_Data_sample",
		"optTable": "recommenderSys_Demo_Data_sample_TopNProductsForUsers"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "als_model_rdd"
	},
	"ModelParam":{
		"param0": "top_items",
		"param1": "10",
		"param2": "",
		"param3": "",
		"param4": ""
	}
}

###### top_users

POST 方法
http://0.0.0.0:5000/v1/machineLearning

{
	"FuncMode":{
		"algorithm": "recommend",
		"option": "als",
		"mode": "predict"
	},
	"TableName":{
		"trainTable": "",
		"col_names":"user,item,rating",
		"testTable": "recommenderSys_Demo_Data_sample",
		"optTable": "recommenderSys_Demo_Data_sample_TopNUsersForProducts"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "als_model_rdd"
	},
	"ModelParam":{
		"param0": "top_users",
		"param1": "10",
		"param2": "",
		"param3": "",
		"param4": ""
	}
}

##### (3)both

###### 显式

POST 方法
http://0.0.0.0:5000/v1/machineLearning

{
	"FuncMode":{
		"algorithm": "recommend",
		"option": "als",
		"mode": "both"
	},
	"TableName":{
		"trainTable": "recommenderSys_Demo_Data_sample",
		"col_names":"user,item,rating",
		"testTable": "recommenderSys_Demo_Data_sample",
		"optTable": "recommenderSys_Demo_Data_sample_pred"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "als_model_rdd"
	},
	"ModelParam":{
		"param0": "False",
		"param1": "10",
		"param2": "10",
		"param3": "0.01",
		"param4": "0.01"
	}
}

###### 隐式


POST 方法
http://0.0.0.0:5000/v1/machineLearning

{
	"FuncMode":{
		"algorithm": "recommend",
		"option": "als",
		"mode": "both"
	},
	"TableName":{
		"trainTable": "recommenderSys_Demo_Data_sample",
		"col_names":"user,item,rating",
		"testTable": "recommenderSys_Demo_Data_sample",
		"optTable": "recommenderSys_Demo_Data_sample_pred"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "als_model_rdd_implicit"
	},
	"ModelParam":{
		"param0": "True",
		"param1": "10",
		"param2": "10",
		"param3": "0.01",
		"param4": "0.01"
	}
}

#### 2)测试 ［机器学习算法库］－［推荐算法］－［基于用户的协同过滤］

##### (1) train


POST 方法
http://0.0.0.0:5000/v1/machineLearning

{
	"FuncMode":{
		"algorithm": "recommend",
		"option": "user-based",
		"mode": "train"
	},
	"TableName":{
		"trainTable": "recommender_test",
		"col_names":"user,item,rating",
		"testTable": "recommender_test",
		"optTable": "recommenderSys_Demo_Data_sample_pred_user_based"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "user_simi"
	},
	"ModelParam":{
		"param0": "0.0",
		"param1": "",
		"param2": "",
		"param3": "",
		"param4": ""
	}
}

##### (2) predict

###### rating

POST 方法
http://0.0.0.0:5000/v1/machineLearning

{
	"FuncMode":{
		"algorithm": "recommend",
		"option": "user-based",
		"mode": "predict"
	},
	"TableName":{
		"trainTable": "recommender_test",
		"col_names":"user,item,rating",
		"testTable": "recommender_test",
		"optTable": "recommenderSys_Demo_Data_sample_pred_user_based"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "user_simi"
	},
	"ModelParam":{
		"param0": "0.0",
		"param1": "rating",
		"param2": "",
		"param3": "",
		"param4": ""
	}
}

###### top_users


POST 方法
http://0.0.0.0:5000/v1/machineLearning

{
	"FuncMode":{
		"algorithm": "recommend",
		"option": "user-based",
		"mode": "predict"
	},
	"TableName":{
		"trainTable": "recommender_test",
		"col_names":"user,item,rating",
		"testTable": "recommender_test",
		"optTable": "recommenderSys_Demo_Data_sample_pred_user_based"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "user_simi"
	},
	"ModelParam":{
		"param0": "0.0",
		"param1": "top_users",
		"param2": "10",
		"param3": "",
		"param4": ""
	}
}

###### top_items

POST 方法
http://0.0.0.0:5000/v1/machineLearning

{
	"FuncMode":{
		"algorithm": "recommend",
		"option": "user-based",
		"mode": "predict"
	},
	"TableName":{
		"trainTable": "recommender_test",
		"col_names":"user,item,rating",
		"testTable": "recommender_test",
		"optTable": "recommenderSys_Demo_Data_sample_pred_user_based"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "user_simi"
	},
	"ModelParam":{
		"param0": "0.0",
		"param1": "top_items",
		"param2": "10",
		"param3": "",
		"param4": ""
	}
}

##### (3) both


POST 方法
http://0.0.0.0:5000/v1/machineLearning

{
	"FuncMode":{
		"algorithm": "recommend",
		"option": "user-based",
		"mode": "both"
	},
	"TableName":{
		"trainTable": "recommender_test",
		"col_names":"user,item,rating",
		"testTable": "recommender_test",
		"optTable": "recommenderSys_Demo_Data_sample_pred_user_based"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "user_simi"
	},
	"ModelParam":{
		"param0": "0.0",
		"param1": "",
		"param2": "",
		"param3": "",
		"param4": ""
	}
}




#### 3)测试 ［机器学习算法库］－［推荐算法］－［基于物品的协同过滤］

##### (1) train


POST 方法
http://0.0.0.0:5000/v1/machineLearning

{
	"FuncMode":{
		"algorithm": "recommend",
		"option": "item-based",
		"mode": "train"
	},
	"TableName":{
		"trainTable": "recommender_test",
		"col_names":"user,item,rating",
		"testTable": "recommender_test",
		"optTable": "recommenderSys_Demo_Data_sample_pred_item_based"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "item_simi"
	},
	"ModelParam":{
		"param0": "0.0",
		"param1": "",
		"param2": "",
		"param3": "",
		"param4": ""
	}
}

##### (2) predict

###### rating

POST 方法
http://0.0.0.0:5000/v1/machineLearning

{
	"FuncMode":{
		"algorithm": "recommend",
		"option": "item-based",
		"mode": "predict"
	},
	"TableName":{
		"trainTable": "recommender_test",
		"col_names":"user,item,rating",
		"testTable": "recommender_test",
		"optTable": "recommenderSys_Demo_Data_sample_pred_item_based"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "item_simi"
	},
	"ModelParam":{
		"param0": "0.0",
		"param1": "rating",
		"param2": "",
		"param3": "",
		"param4": ""
	}
}

###### top_users


POST 方法
http://0.0.0.0:5000/v1/machineLearning

{
	"FuncMode":{
		"algorithm": "recommend",
		"option": "item-based",
		"mode": "predict"
	},
	"TableName":{
		"trainTable": "recommender_test",
		"col_names":"user,item,rating",
		"testTable": "recommender_test",
		"optTable": "recommenderSys_Demo_Data_sample_pred_item_based"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "item_simi"
	},
	"ModelParam":{
		"param0": "0.0",
		"param1": "top_users",
		"param2": "10",
		"param3": "",
		"param4": ""
	}
}

###### top_items

POST 方法
http://0.0.0.0:5000/v1/machineLearning

{
	"FuncMode":{
		"algorithm": "recommend",
		"option": "item-based",
		"mode": "predict"
	},
	"TableName":{
		"trainTable": "recommender_test",
		"col_names":"user,item,rating",
		"testTable": "recommender_test",
		"optTable": "recommenderSys_Demo_Data_sample_pred_item_based"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "item_simi"
	},
	"ModelParam":{
		"param0": "0.0",
		"param1": "top_items",
		"param2": "10",
		"param3": "",
		"param4": ""
	}
}

##### (3) both


POST 方法
http://0.0.0.0:5000/v1/machineLearning

{
	"FuncMode":{
		"algorithm": "recommend",
		"option": "item-based",
		"mode": "both"
	},
	"TableName":{
		"trainTable": "recommender_test",
		"col_names":"user,item,rating",
		"testTable": "recommender_test",
		"optTable": "recommenderSys_Demo_Data_sample_pred_item_based"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "item_simi"
	},
	"ModelParam":{
		"param0": "0.0",
		"param1": "",
		"param2": "",
		"param3": "",
		"param4": ""
	}
}


## 二.［文本分析］

### 1.［文本分析］－［特征抽取］

#### 1）［文本分析］－［特征抽取］－［WordCount]


##### (1) train

POST 方法
http://0.0.0.0:5000/v1/textAnalysis

{
	"FuncMode":{
		"algorithm": "featureExtraction",
		"option": "WordCount",
		"mode": "train"
	},
	"TableName":{
		"trainTable": "Sogou_Classification_mini_segWords_random100",
		"col_names":"seg_words",
		"testTable": "Sogou_Classification_mini_segWords_random100",
		"optTable": "feature_extraction_WordCount"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "feature_extraction_model_wordCount"
	},
	"ModelParam":{
		"param0": "20",
		"param1": "2",
		"param2": "",
		"param3": "",
		"param4": ""
	}
}


##### (2) predict

POST 方法
http://0.0.0.0:5000/v1/textAnalysis

{
	"FuncMode":{
		"algorithm": "featureExtraction",
		"option": "WordCount",
		"mode": "predict"
	},
	"TableName":{
		"trainTable": "Sogou_Classification_mini_segWords_random100",
		"col_names":"seg_words",
		"testTable": "Sogou_Classification_mini_segWords_random100",
		"optTable": "feature_extraction_WordCount_predict"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "feature_extraction_model_wordCount"
	},
	"ModelParam":{
		"param0": "20",
		"param1": "2",
		"param2": "",
		"param3": "",
		"param4": ""
	}
}



#### 2）［机器学习算法库］－［关联规则］－［TF_IDF]

##### (1) train

POST 方法
http://0.0.0.0:5000/v1/textAnalysis

{
	"FuncMode":{
		"algorithm": "featureExtraction",
		"option": "TF_IDF",
		"mode": "train"
	},
	"TableName":{
		"trainTable": "Sogou_Classification_mini_segWords_random100",
		"col_names":"seg_words",
		"testTable": "Sogou_Classification_mini_segWords_random100",
		"optTable": "feature_extraction_TF_IDF"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "feature_extraction_model_tfIdf"
	},
	"ModelParam":{
		"param0": "20",
		"param1": "2",
		"param2": "",
		"param3": "",
		"param4": ""
	}
}


##### (2) predict


POST 方法
http://0.0.0.0:5000/v1/textAnalysis

{
	"FuncMode":{
		"algorithm": "featureExtraction",
		"option": "TF_IDF",
		"mode": "predict"
	},
	"TableName":{
		"trainTable": "Sogou_Classification_mini_segWords_random100",
		"col_names":"seg_words",
		"testTable": "Sogou_Classification_mini_segWords_random100",
		"optTable": "feature_extraction_TF_IDF_predict"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "feature_extraction_model_tfIdf"
	},
	"ModelParam":{
		"param0": "20",
		"param1": "2",
		"param2": "",
		"param3": "",
		"param4": ""
	}
}


#### 3）［机器学习算法库］－［关联规则］－［Word2Vec]

##### (1) train

POST 方法
http://0.0.0.0:5000/v1/textAnalysis

{
	"FuncMode":{
		"algorithm": "featureExtraction",
		"option": "Word2Vec",
		"mode": "train"
	},
	"TableName":{
		"trainTable": "Sogou_Classification_mini_segWords_random100",
		"col_names":"seg_words",
		"testTable": "Sogou_Classification_mini_segWords_random100",
		"optTable": "feature_extraction_Word2Vec"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "feature_extraction_model_word2vec"
	},
	"ModelParam":{
		"param0": "20",
		"param1": "2",
		"param2": "",
		"param3": "",
		"param4": ""
	}
}


##### (2) predict

{
	"FuncMode":{
		"algorithm": "featureExtraction",
		"option": "Word2Vec",
		"mode": "predict"
	},
	"TableName":{
		"trainTable": "Sogou_Classification_mini_segWords_random100",
		"col_names":"seg_words",
		"testTable": "Sogou_Classification_mini_segWords_random100",
		"optTable": "feature_extraction_Word2Vec_predict"
	},
	"ModelPath":{
		"filePth": "/Users/sunlu/Documents/workspace/IDEA/DataMining/results/",
		"modelName": "feature_extraction_model_word2vec"
	},
	"ModelParam":{
		"param0": "20",
		"param1": "2",
		"param2": "",
		"param3": "",
		"param4": ""
	}
}