var $mc = {
	 layer:{
	  	   confirm:function(msg,callback){ 
	  	   	    layer.confirm(msg, {
				  btn: ['确定','取消'] //按钮
				}, function(index){
					   if(typeof callback ==="function"){
	                      callback(index);
	                  }
				}, function(index){
				      layer.close(index)
				});
	  	  }
	 }
}
