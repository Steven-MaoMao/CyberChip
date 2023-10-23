import{r as s,o as u,c as d,a,w as r,F as _,b,d as n,e as x,f,t as y,g as w,p as C,h as D}from"./index-01eeb769.js";import{_ as j}from"./_plugin-vue_export-helper-c27b6911.js";const U={data(){return{interval:0,cyberChipRoomList:{},createRoomDialogVisible:!1,newRoom:{roomName:null,roomManager:null,roomPassword:null},createRoomForm:!1,joinRoomDialogVisible:!1,joinRoomForm:!1,joinRoomData:{roomName:null,name:null,roomPassword:null},snackbar:{show:!1,text:null,color:null}}},watch:{createRoomDialogVisible(o){o===!1&&(this.newRoom={roomName:null,roomManager:null,roomPassword:null})},joinRoomDialogVisible(o){o===!1&&(this.joinRoomData={roomName:null,name:null,roomPassword:null})}},mounted(){document.title="赛博筹码房间大厅",this.getCyberChipRoomList();let o=this;this.interval=setInterval(function(){o.getCyberChipRoomList()},1e3)},unmounted(){clearInterval(this.interval)},methods:{getCyberChipRoomList(){this.axios.get(this.baseURL+"/cyberchip/getCyberChipRoomList").then(o=>{o.data.flag===!1?(this.snackbar.color="error",this.snackbar.text="获取数据失败，寄了",this.snackbar.show=!0):this.cyberChipRoomList=o.data.data}).catch(o=>{this.snackbar.color="error",this.snackbar.text="获取数据失败，寄了",this.snackbar.show=!0,console.log(o)})},createRoom(){this.createRoomForm&&(sessionStorage.setItem("name",this.newRoom.roomManager),this.axios.post(this.baseURL+"/cyberchip/createRoom",this.newRoom).then(o=>{o.data.flag===!0?(this.snackbar.color="success",sessionStorage.setItem("token",o.data.data),this.$router.push("/cyberchip")):this.snackbar.color="error",this.snackbar.text=o.data.message,this.snackbar.show=!0}).catch(o=>{this.snackbar.color="error",this.snackbar.text="创建失败，寄了",this.snackbar.show=!0,console.log(o)}),this.cancelCreateRoom())},cancelCreateRoom(){this.createRoomDialogVisible=!1},onJoinRoomDialogVisible(o){this.joinRoomData.roomName=o,this.joinRoomDialogVisible=!0},joinRoom(){this.joinRoomForm&&(sessionStorage.setItem("name",this.joinRoomData.name),this.axios.post(this.baseURL+"/cyberchip/joinRoom",this.joinRoomData).then(o=>{o.data.flag===!0?(this.snackbar.color="success",sessionStorage.setItem("token",o.data.data),this.$router.push("/cyberchip")):this.snackbar.color="error",this.snackbar.text=o.data.message,this.snackbar.show=!0}).catch(o=>{this.snackbar.color="error",this.snackbar.text="加入失败，寄了",this.snackbar.show=!0,console.log(o)}),this.cancelJoinRoom())},cancelJoinRoom(){this.joinRoomDialogVisible=!1},required(o){return!!o||"填一下"}}},L=o=>(C("data-v-24bf7cab"),o=o(),D(),o),N={class:"d-flex align-center"},S=L(()=>b("div",{class:"text-h4 text-no-wrap",style:{margin:"10px"}}," 赛博筹码 ",-1)),F={key:0,class:"d-flex justify-center align-center"},I={key:1,class:"d-flex flex-wrap"},P={style:{margin:"24px"}},M={style:{margin:"24px"}};function q(o,l,z,J,e,i){const k=s("router-link"),h=s("v-container"),m=s("v-btn"),v=s("v-divider"),p=s("v-card"),c=s("v-text-field"),g=s("v-form"),R=s("v-dialog"),V=s("v-snackbar");return u(),d(_,null,[a(h,{class:"header rounded-xl"},{default:r(()=>[b("div",N,[a(k,{to:"/",class:"d-flex align-center",style:{"text-decoration":"none",color:"black"}},{default:r(()=>[S]),_:1})])]),_:1}),a(h,{id:"block",class:"rounded-xl"},{default:r(()=>[a(m,{id:"create_room_dialog_visible_button","prepend-icon":"mdi-plus-circle-outline",size:"large",variant:"outlined",onClick:l[0]||(l[0]=t=>e.createRoomDialogVisible=!0)},{default:r(()=>[n(" 创建房间 ")]),_:1}),a(v,{class:"border-opacity-50",style:{"margin-top":"20px","margin-bottom":"20px"}}),Object.keys(e.cyberChipRoomList).length<=0?(u(),d("div",F,[a(m,{id:"create_room_dialog_visible_button","prepend-icon":"mdi-plus-circle-outline",size:"large",onClick:l[1]||(l[1]=t=>e.createRoomDialogVisible=!0)},{default:r(()=>[n(" 创建房间 ")]),_:1})])):(u(),d("div",I,[(u(!0),d(_,null,x(e.cyberChipRoomList,t=>(u(),w(p,{title:t.roomName,subtitle:"房主："+t.roomManager,class:"room_card",onClick:B=>i.onJoinRoomDialogVisible(t.roomName)},null,8,["title","subtitle","onClick"]))),256))]))]),_:1}),a(R,{modelValue:e.createRoomDialogVisible,"onUpdate:modelValue":l[6]||(l[6]=t=>e.createRoomDialogVisible=t),width:"50%"},{default:r(()=>[a(p,{title:"创建房间"},{default:r(()=>[b("div",P,[a(g,{modelValue:e.createRoomForm,"onUpdate:modelValue":l[5]||(l[5]=t=>e.createRoomForm=t),onSubmit:f(i.createRoom,["prevent"])},{default:r(()=>[a(c,{modelValue:e.newRoom.roomName,"onUpdate:modelValue":l[2]||(l[2]=t=>e.newRoom.roomName=t),rules:[i.required],label:"房间名称",variant:"outlined",style:{"margin-bottom":"12px"}},null,8,["modelValue","rules"]),a(c,{modelValue:e.newRoom.roomManager,"onUpdate:modelValue":l[3]||(l[3]=t=>e.newRoom.roomManager=t),rules:[i.required],label:"自己昵称",variant:"outlined",style:{"margin-bottom":"12px"}},null,8,["modelValue","rules"]),a(c,{modelValue:e.newRoom.roomPassword,"onUpdate:modelValue":l[4]||(l[4]=t=>e.newRoom.roomPassword=t),rules:[i.required],label:"房间密码",variant:"outlined",style:{"margin-bottom":"12px"}},null,8,["modelValue","rules"]),a(m,{size:"large",block:"",variant:"outlined",type:"submit",color:"success",style:{"margin-top":"20px","margin-bottom":"20px"}},{default:r(()=>[n(" 确认 ")]),_:1}),a(m,{onClick:i.cancelCreateRoom,size:"large",block:"",variant:"outlined",color:"error"},{default:r(()=>[n(" 取消 ")]),_:1},8,["onClick"])]),_:1},8,["modelValue","onSubmit"])])]),_:1})]),_:1},8,["modelValue"]),a(R,{modelValue:e.joinRoomDialogVisible,"onUpdate:modelValue":l[10]||(l[10]=t=>e.joinRoomDialogVisible=t),width:"50%"},{default:r(()=>[a(p,{title:"加入房间"},{default:r(()=>[b("div",M,[a(g,{modelValue:e.joinRoomForm,"onUpdate:modelValue":l[9]||(l[9]=t=>e.joinRoomForm=t),onSubmit:f(i.joinRoom,["prevent"])},{default:r(()=>[a(c,{modelValue:e.joinRoomData.name,"onUpdate:modelValue":l[7]||(l[7]=t=>e.joinRoomData.name=t),rules:[i.required],label:"自己昵称",variant:"outlined",style:{"margin-bottom":"12px"}},null,8,["modelValue","rules"]),a(c,{modelValue:e.joinRoomData.roomPassword,"onUpdate:modelValue":l[8]||(l[8]=t=>e.joinRoomData.roomPassword=t),rules:[i.required],label:"房间密码",variant:"outlined",type:"password",style:{"margin-bottom":"12px"}},null,8,["modelValue","rules"]),a(m,{size:"large",block:"",variant:"outlined",type:"submit",color:"success",style:{"margin-top":"20px","margin-bottom":"20px"}},{default:r(()=>[n(" 确认 ")]),_:1}),a(m,{onClick:i.cancelJoinRoom,size:"large",block:"",variant:"outlined",color:"error"},{default:r(()=>[n(" 取消 ")]),_:1},8,["onClick"])]),_:1},8,["modelValue","onSubmit"])])]),_:1})]),_:1},8,["modelValue"]),a(V,{modelValue:e.snackbar.show,"onUpdate:modelValue":l[11]||(l[11]=t=>e.snackbar.show=t),timeout:"2000",color:e.snackbar.color},{default:r(()=>[n(y(e.snackbar.text),1)]),_:1},8,["modelValue","color"])],64)}const T=j(U,[["render",q],["__scopeId","data-v-24bf7cab"]]);export{T as default};