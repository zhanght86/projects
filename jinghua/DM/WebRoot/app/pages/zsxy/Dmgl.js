/**
 * 应用管理
 */
NS.define('Pages.zsxy.Dmgl',{
	extend : 'Pages.zsxy.BaseList',

	modelConfig : {
		serviceConfig : {
			'queryLmC' 			: 'zsxyDm_queryLmC',
			'add' 				: 'zsxyDm_add',
			'edit' 				: 'zsxyDm_edit',
			'delete' 			: 'zsxyDm_delete',
			'queryById' 		: 'zsxyDm_queryById'
		}
	},

	init : function(){
		var me=this;

		this.initPage();

		//FN:查看
		var openView=function(e){
			me.grid.selectRow(this.idx,true,true);
			me.openView();
			EG.Event.stopPropagation(e);
		};

		//配置
		this.cfg={
			keyId:"id",title:"代码管理",

			grid:{
				columns:[
					{header:"名称"		,field:"mc"		,width:100	},
					{header:"标识"		,field:"bs"		,width:100	},
					{header:"数值"		,field:"sz"		,width:200	},
					{header:"操作"		,field:"oper"	,width:120	,handle:function(data,g){
						return EG.CE({tn:"a",cls:"zsxy_orange",idx:g,href:"javascript:void(0)",onclick:openView,innerHTML:"查看"});
					}}
				]
			},

			searchForm:{
				items:[
					{xtype:"formItem",width:160,title:"名称"		,name:"mc"	,type:"text"	,searchOnReady:true},
					{xtype:"formItem",width:160,title:"标识"		,name:"bs"	,type:"text"	,searchOnReady:true},
					{xtype:"panel",layout:{type:"default",middleChilds:true},style:"text-align:left",items:[
						new EG.ui.Button({text:"查询",click:function(){
							me.doSearch();
						}})
					]}
				]
			},

			tools:{
				height:30,
				items:["view","add","edit","delete"]
			},

			form:{
				items:[
					{pos:[0,0],xtype:"formItem",title:"名称"		,name:"mc"	,type:"text"	,length:20},
					{pos:[1,0],xtype:"formItem",title:"标识"		,name:"bs"	,type:"text"	,length:20},
					{pos:[2,0],xtype:"formItem",title:"数值"		,name:"sz"	,type:"text"	,length:200}
				]
			}
		};

		this._init();
	}
});