
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
		"param0": "",
		"param1": "",
		"param2": "",
		"param3": "",
		"param4": ""
	}
}